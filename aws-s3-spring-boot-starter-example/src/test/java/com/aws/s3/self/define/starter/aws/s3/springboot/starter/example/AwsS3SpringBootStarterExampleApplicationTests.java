package com.aws.s3.self.define.starter.aws.s3.springboot.starter.example;

import com.aws.s3.self.define.starter.AmazonS3Operation;
import com.aws.s3.self.define.starter.AmazonS3Operator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AwsS3SpringBootStarterExampleApplicationTests {
    @Autowired
    private AmazonS3Operation amazonS3Operation;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(amazonS3Operation);
        Assertions.assertTrue(amazonS3Operation instanceof AmazonS3Operator);
    }

    @Test
    void testListBuckets() {
        var bucketList = amazonS3Operation.listBuckets();
        Assertions.assertNotNull(bucketList);
    }
}
