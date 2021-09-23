package com.mythic.madjayq;

public class DiscordCredentials {
    protected final String apiKey;

    public String consumeApiKey() {
        return apiKey;
    }

    public DiscordCredentials(String apiKey) {
        this.apiKey = apiKey;
    }
}
