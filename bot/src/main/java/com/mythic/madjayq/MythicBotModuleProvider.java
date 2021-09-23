package com.mythic.madjayq;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;

import java.util.Collection;

public class MythicBotModuleProvider {

    public static Collection<AbstractModule> modules() {
        return Lists.newArrayList(
                new MythicBotModule(),
                new DiscordModule(),
                new GuildModule()
        );
    }
}
