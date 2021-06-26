package com.example.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withRegion("eu-central-1")
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIA6QAFJQLAH26QLVKJ", "rMb9rTwR9KlKhAANOT0+ydRxPZzXIiDI+mZtFofA")))
                .build();
    }
}
