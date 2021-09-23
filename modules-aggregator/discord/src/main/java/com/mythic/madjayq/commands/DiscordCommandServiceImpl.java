package com.mythic.madjayq.commands;

import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.mythic.madjayq.DiscordEventListener;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Map;

public class DiscordCommandServiceImpl implements DiscordCommandService {

    private final EventBus eventBus;

    private final Map<String, DiscordCommand> commandsByPrefixPattern = Maps.newHashMap();

    @Inject
    public DiscordCommandServiceImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    @Override
    public void registerCommand(String prefixPattern, DiscordCommand command) {
        commandsByPrefixPattern.put(prefixPattern, command);
    }

    @Subscribe
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) throws Exception {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if(commandsByPrefixPattern.containsKey(args[0])) {
            event.getMessage().suppressEmbeds(true).queue();
            DiscordCommand command = commandsByPrefixPattern.get(args[0]);
            command.execute(event.getAuthor(), event.getChannel(), args);
        }
    }
}
