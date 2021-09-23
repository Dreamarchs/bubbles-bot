package com.mythic.madjayq.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.s3.model.Region;
import com.google.inject.Inject;
import com.google.inject.Provider;


public class DynamoDBClientProvider implements Provider<AmazonDynamoDB> {

    private final AWSCredentialsProvider credentialsProvider;
    @Inject
    public DynamoDBClientProvider(AWSCredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    @Override
    public AmazonDynamoDB get() {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(credentialsProvider)
                .build();
    }
}
