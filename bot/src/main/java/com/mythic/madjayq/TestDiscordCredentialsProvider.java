package com.mythic.madjayq;

import com.google.inject.Provider;

import java.io.IOException;

public class TestDiscordCredentialsProvider implements Provider<DiscordCredentials> {

    public TestDiscordCredentialsProvider() throws IOException {
    }

    FileRead fileRead = new FileRead();
    String discToken = fileRead.configRead(2, 11);

    private final String apiToken = discToken;

    @Override
    public DiscordCredentials get() {
        return new DiscordCredentials(apiToken);
    }

}
