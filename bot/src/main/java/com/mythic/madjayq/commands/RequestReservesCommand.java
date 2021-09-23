package com.mythic.madjayq.commands;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.mythic.madjayq.DiscordService;
import com.mythic.madjayq.GuildMemberService;
import com.mythic.madjayq.generated.Guild;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RequestReservesCommand extends DiscordCommand {

    private final GuildMemberService guildMemberService;

    private final Map<Long, Message> requestsByMessageId = Maps.newHashMap();

    public RequestReservesCommand(DiscordService discordService,
                                  EventBus eventBus,
                                  GuildMemberService guildMemberService) {
        super(discordService, eventBus);
        this.eventBus.register(this);
        this.guildMemberService = guildMemberService;
    }

    @Override
    void usage(User sender) throws Exception {
        discordService.sendMessage(sender.getIdLong(), "Invalid usage");
    }

    @Override
    void execute(User sender, TextChannel channel, String[] arguments) throws Exception {
        if(arguments.length != 3) {
            usage(sender);
            return;
        }
        String name = arguments[1];
        String newRequest = arguments[2];
        Guild.GuildCharacter character = guildMemberService.getGuildCharacter(name);
        Document doc = Jsoup.connect(newRequest).get();
        Map<String, Element> elementsByName = doc.getElementsByTag("meta").stream()
                .collect(Collectors.toMap(tag -> tag.attr("property"), Function.identity(), (a, b) -> {return a;}));

        String itemTitle = elementsByName.get("og:title").attr("content");
        String itemUrl = elementsByName.get("og:image").attr("content");
        discordService.sendMessageToChannel(channel, buildMessageEmbed(character, newRequest, itemTitle, itemUrl), message -> {
            message.addReaction("\uD83D\uDC4D").queue();
            message.addReaction("\uD83D\uDC4E").queue();
            requestsByMessageId.put(message.getIdLong(), message);
        });
    }

    private MessageEmbed buildMessageEmbed(Guild.GuildCharacter character,
                                           String newRequest, String itemTitle, String itemUrl) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle( character.getGuildie() + " is requesting a new reserve", newRequest);
        eb.setDescription("Currently pending officer approval");
        eb.setColor(Color.blue);
        eb.setThumbnail("https://wow.zamimg.com/images/logos/share-icon.png");
        eb.setImage(itemUrl);
        MessageEmbed.Field field = new MessageEmbed.Field(itemTitle
                ,"[" + newRequest + "](" + newRequest + ")", false);
        eb.addField(field);
        eb.addField(new MessageEmbed.Field("\u200B", "\u200B", false));
        eb.addField(new MessageEmbed.Field("Current reserves",
                character.getGuildie() + " presently has " +
                        character.getReservesList().size() + " reserves!", false));
        return eb.build();
    }

    @Subscribe
    public void onMessageReactionAdd(MessageReactionAddEvent event) throws Exception {
        Long id = event.getMessageIdLong();
        if(event.getUser().isBot()) {
            return;
        }
        if(requestsByMessageId.containsKey(id)) {
            Message message = requestsByMessageId.get(id);
            if(event.getReactionEmote().getName().equals("\uD83D\uDC4D")) {
                message.getChannel().sendMessage("Approved by <@" + event.getUserIdLong() + ">!").queue();
                requestsByMessageId.remove(id);
            }
            else if (event.getReactionEmote().getEmoji().equals("\uD83D\uDC4E")) {
                message.getChannel().sendMessage("Denied by <@" + event.getUserIdLong() + ">!").queue();
                requestsByMessageId.remove(id);
            }
        }
    }

}
