package com.mythic.madjayq.commands;

import com.google.common.eventbus.EventBus;
import com.mythic.madjayq.DiscordService;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class DiscordCommand {

    protected final DiscordService discordService;
    protected final EventBus eventBus;

    public DiscordCommand(DiscordService discordService, EventBus eventBus) {
        this.discordService = discordService;
        this.eventBus = eventBus;
    }

    abstract void usage(User sender) throws Exception;
    abstract void execute(User sender, TextChannel channel, String[] arguments) throws Exception;
}
