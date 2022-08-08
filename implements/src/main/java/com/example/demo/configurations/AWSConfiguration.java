package com.example.demo.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "mobysuite")
@Getter @Setter
public class AWSConfiguration {
    private Integer maxLoginAttempts;
    private String awsS3BucketName;
    private String awsS3BaseUrl;
    private String awsCredentialsAccessKey;
    private String awsCredentialsSecretKey;
    private String awsCredentialsRegion;
    private String customer;
    private Long customerId;
    private String jasperReportsSystemPath;
    private String twoStepApprovalUrl;
}
