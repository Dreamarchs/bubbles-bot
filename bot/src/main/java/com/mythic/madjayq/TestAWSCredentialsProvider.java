package com.mythic.madjayq;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.google.inject.Provider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestAWSCredentialsProvider implements Provider<AWSCredentialsProvider> {

    FileRead fileread = new FileRead();
    private final String accessKeyId = fileread.configRead(0, 7);
    private final String secretAccessKey = fileread.configRead(1, 8);

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
