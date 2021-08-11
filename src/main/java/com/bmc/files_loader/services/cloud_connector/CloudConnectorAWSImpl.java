package com.bmc.files_loader.services.cloud_connector;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CloudConnectorAWSImpl implements CloudConnector<AmazonS3>{
    @Value("${accessKey}")
    String accessKey;
    @Value("${secretKey}")
    String secretKey;
    @Value("${serviceEndPoint}")
    String serviceEndPoint;
    @Value("${region}")
    String signingRegion;

    @Override
    public AmazonS3 createCloudConnection() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey,
                secretKey);
        AwsClientBuilder.EndpointConfiguration endPoint = new AwsClientBuilder.EndpointConfiguration
                (serviceEndPoint, signingRegion);
        return AmazonS3ClientBuilder.standard()
                .withCredentials((new AWSStaticCredentialsProvider(awsCreds)))
                .withEndpointConfiguration(endPoint)
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
