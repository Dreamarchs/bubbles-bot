package com.mythic.madjayq;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.google.inject.AbstractModule;
import com.mythic.madjayq.aws.DynamoDBClientProvider;
import com.mythic.madjayq.aws.DynamoDBGuildDAOProvider;

public class GuildModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GuildMemberService.class).to(GuildMemberServiceImpl.class);
        bind(AmazonDynamoDB.class).toProvider(DynamoDBClientProvider.class);
        bind(GuildDAO.class).toProvider(DynamoDBGuildDAOProvider.class);
    }
}
