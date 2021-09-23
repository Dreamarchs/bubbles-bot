package com.mythic.madjayq;

import com.google.inject.Provider;

public class TestDiscordCredentialsProvider implements Provider<DiscordCredentials> {

    private final String apiToken = "<put-token-here>";
    @Override
    public DiscordCredentials get() {
        return new DiscordCredentials(apiToken);
    }
}
