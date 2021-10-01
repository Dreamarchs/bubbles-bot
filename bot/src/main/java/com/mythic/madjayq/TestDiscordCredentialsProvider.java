package com.mythic.madjayq;

import com.google.inject.Provider;
import java.io.IOException;

public class TestDiscordCredentialsProvider implements Provider<DiscordCredentials> {

    public TestDiscordCredentialsProvider() throws IOException {
    }

    private final ConfigFileRead fileRead = new ConfigFileRead();
    private final String discordToken = fileRead.configRead(2, 11);

    @Override
    public DiscordCredentials get() {
        return new DiscordCredentials(discordToken);

    }
}
