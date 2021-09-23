package com.mythic.madjayq;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.google.inject.Provider;

public class TestAWSCredentialsProvider implements Provider<AWSCredentialsProvider> {

    private final String accessKeyId = "<put-id-here>";
    private final String secretAccessKey = "<put-key-here>";

    private BasicAWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKeyId, secretAccessKey);
    }

    @Override
    public AWSCredentialsProvider get() {
        return new AWSStaticCredentialsProvider(getCredentials());
    }

}
