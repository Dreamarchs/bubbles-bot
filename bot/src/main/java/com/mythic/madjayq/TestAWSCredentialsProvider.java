package com.mythic.madjayq;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.google.inject.Provider;
import java.io.IOException;

public class TestAWSCredentialsProvider implements Provider<AWSCredentialsProvider> {

    private final ConfigFileRead fileRead = new ConfigFileRead();
    private final String accessKeyId = ConfigFileRead.configRead(0, 7);
    private final String secretAccessKey = ConfigFileRead.configRead(1, 8);

    public TestAWSCredentialsProvider() throws IOException {
    }

    private BasicAWSCredentials getCredentials() {
        return new BasicAWSCredentials(accessKeyId, secretAccessKey);
    }

    @Override
    public AWSCredentialsProvider get() {
        return new AWSStaticCredentialsProvider(getCredentials());
    }

}
