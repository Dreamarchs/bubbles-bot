package com.mythic.madjayq.aws;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mythic.madjayq.GuildDAO;

public class DynamoDBGuildDAOProvider implements Provider<GuildDAO> {

    private final AmazonDynamoDB client;
    @Inject
    public DynamoDBGuildDAOProvider(AmazonDynamoDB client) {
        this.client = client;
    }

    @Override
    public GuildDAO get() {
        return new DynamoDBGuildDAO(client);
    }
}
