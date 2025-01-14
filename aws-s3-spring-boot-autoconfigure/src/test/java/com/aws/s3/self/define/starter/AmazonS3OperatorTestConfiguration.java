package com.aws.s3.self.define.starter;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.aws.s3.self.define.starter"})
public class AmazonS3OperatorTestConfiguration {

    @Bean
    AmazonS3Properties amazonS3Properties() {
        return new AmazonS3Properties();
    }
}
