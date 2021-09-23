package com.mythic.madjayq;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

import java.util.Collection;

public class MythicBotModule extends AbstractModule {

    private EventBus eventBus = new EventBus("Mythic Event Bus");
    @Override
    protected void configure() {
        bind(EventBus.class).toInstance(eventBus);
        bind(StatusService.class).to(StatusServiceImpl.class);
        bind(DiscordCredentials.class).toProvider(TestDiscordCredentialsProvider.class);
        bind(AWSCredentialsProvider.class).toProvider(TestAWSCredentialsProvider.class);
    }
}
