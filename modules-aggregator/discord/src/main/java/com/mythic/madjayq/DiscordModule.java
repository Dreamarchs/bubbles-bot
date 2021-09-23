package com.mythic.madjayq;

import com.google.inject.AbstractModule;
import com.mythic.madjayq.commands.DiscordCommandService;
import com.mythic.madjayq.commands.DiscordCommandServiceImpl;

public class DiscordModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DiscordService.class).to(DiscordServiceImpl.class);
        bind(DiscordEventListener.class);
        bind(DiscordCommandService.class).to(DiscordCommandServiceImpl.class);
    }
}