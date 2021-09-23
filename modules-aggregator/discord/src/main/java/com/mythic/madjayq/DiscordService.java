package com.mythic.madjayq;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.function.Consumer;

public interface DiscordService {
    void start() throws Exception;
    void sendMessage(long userId, String message);
    void sendMessage(User user, String message);
    void sendMessage(long userId, MessageEmbed embed);
    void sendMessage(User user, MessageEmbed embed);
    void sendMessageToChannel(long channelId, String message);
    void sendMessageToChannel(TextChannel channel, String message);
    void sendMessageToChannel(long channelId, MessageEmbed message);
    void sendMessageToChannel(TextChannel channel, MessageEmbed message);
    void sendMessageToChannel(TextChannel channel, MessageEmbed message, Consumer<? super Message> consumer);

    User getUser(long userId);
    TextChannel getChannel(long channelId);
}