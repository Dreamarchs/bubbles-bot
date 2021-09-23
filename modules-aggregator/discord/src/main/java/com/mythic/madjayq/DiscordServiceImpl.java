package com.mythic.madjayq;

import com.google.inject.Inject;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class DiscordServiceImpl implements DiscordService {

    public static Logger log = LoggerFactory.getLogger(DiscordServiceImpl.class);

    private final DiscordCredentials credentials;
    private final DiscordEventListener eventListener;
    private final StatusService statusService;

    private JDA discordApi;

    @Inject
    //TODO: Safe destroy credentials after initialization of the service
    public DiscordServiceImpl(DiscordCredentials credentials,
                              DiscordEventListener eventListener,
                              StatusService statusService) {
        this.credentials = credentials;
        this.eventListener = eventListener;
        this.statusService = statusService;
        statusService.setServiceStatus(DiscordServiceImpl.class, ServiceStatus.PENDING);
    }

    @Override
    public void start() throws Exception {
        discordApi = JDABuilder.createDefault(credentials.consumeApiKey())
                .addEventListeners(eventListener)
                .build();
        discordApi.awaitStatus(JDA.Status.CONNECTED);
        if(discordApi.getStatus() != JDA.Status.CONNECTED) {
            statusService.setServiceStatus(DiscordServiceImpl.class, ServiceStatus.ERROR);
        }
        statusService.setServiceStatus(DiscordServiceImpl.class, ServiceStatus.OK);
    }

    @Override
    public void sendMessage(long userId, String message) {
        //Attempt to find an available private channel with this user
        PrivateChannel channel = findUserPrivateChannel(userId);
        if(channel == null) {
            User user = getUser(userId);
            if(user == null) {
                log.warn("Attempting to send message to unknown user {}", userId);
                return;
            }
            user.openPrivateChannel().flatMap(newChannel -> newChannel.sendMessage(message)).queue();
        }
        channel.sendMessage(message).queue();
    }

    @Override
    public void sendMessage(User user, String message) {

    }

    @Override
    public void sendMessage(long userId, MessageEmbed embed) {

    }

    @Override
    public void sendMessage(User user, MessageEmbed embed) {

    }

    @Override
    public void sendMessageToChannel(long channelId, String message) {
        sendMessageToChannel(getChannel(channelId), message);
    }

    @Override
    public void sendMessageToChannel(TextChannel channel, String message) {
        channel.sendMessage(message).queue();
    }

    @Override
    public void sendMessageToChannel(long channelId, MessageEmbed message) {
        sendMessageToChannel(getChannel(channelId), message);
    }

    @Override
    public void sendMessageToChannel(TextChannel channel, MessageEmbed message) {
        channel.sendMessage(message).queue();
    }

    @Override
    public void sendMessageToChannel(TextChannel channel, MessageEmbed message, Consumer<? super Message> consumer) {
        channel.sendMessage(message).queue(consumer);
    }

    @Override
    public User getUser(long userId) {
        return discordApi.getUserById(userId);
    }

    @Override
    public TextChannel getChannel(long channelId) {
        return discordApi.getTextChannelById(channelId);
    }

    private PrivateChannel findUserPrivateChannel(long userId) {
        return discordApi.getPrivateChannelCache().stream()
                .filter(c -> c.getUser().getIdLong() == userId)
                .findAny()
                .orElse(null);
    }
}