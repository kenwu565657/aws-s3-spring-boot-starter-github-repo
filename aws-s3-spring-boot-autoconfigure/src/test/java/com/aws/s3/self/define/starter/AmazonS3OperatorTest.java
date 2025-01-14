package com.aws.s3.self.define.starter;

import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@EnableConfigurationProperties
@SpringBootTest(classes = {AmazonS3OperatorTestConfiguration.class})
public class AmazonS3OperatorTest {
    @Autowired
    private AmazonS3Operator amazonS3Operator;

    @Test
    void testListBuckets() {
        List<Bucket> bucketList = amazonS3Operator.listBuckets();
        List<String> bucketNames = bucketList.stream().map(Bucket::getName).toList();
        Assertions.assertTrue(bucketNames.size() > 0);
        Assertions.assertEquals(1, bucketNames.size());
    }
}
