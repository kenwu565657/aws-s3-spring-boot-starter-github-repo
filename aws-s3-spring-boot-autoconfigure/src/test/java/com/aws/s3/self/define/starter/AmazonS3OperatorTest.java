package com.aws.s3.self.define.starter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;
import software.amazon.awssdk.services.s3.model.Bucket;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableConfigurationProperties
@SpringBootTest(classes = {AmazonS3OperatorTestConfiguration.class})
public class AmazonS3OperatorTest {

    private final Logger logger = LoggerFactory.getLogger(AmazonS3OperatorTest.class);

    @Autowired
    private AmazonS3Operator amazonS3Operator;

    private static final String TEST_FIlE_CLASS_PATH = "classpath:testingFile.txt";
    private static final String TEST_BUCKET_NAME_TEMPLATE = "my-test-bucket-{0}";
    private static final String TEST_FILE_NAME_TEMPLATE = "my-test-file-{0}";
    private int numberOfBuckets = 0;
    private String testingBucketName = null;
    private String testingFileName = null;

    @BeforeAll
    void setUp() {
        String randomNumber = UUID.randomUUID().toString();
        testingBucketName = MessageFormat.format(TEST_BUCKET_NAME_TEMPLATE, randomNumber);
        testingFileName = MessageFormat.format(TEST_FILE_NAME_TEMPLATE, randomNumber);
    }

    @Order(1)
    @Test
    void testInitListBuckets() {
        var bucketList = amazonS3Operator.listBuckets();
        Assertions.assertNotNull(bucketList);
        numberOfBuckets = bucketList.size();
    }

    @Order(2)
    @Test
    void testCreateBucket() {
        Assertions.assertDoesNotThrow(() -> amazonS3Operator.createBucket(testingBucketName));
        numberOfBuckets++;
    }

    @Order(3)
    @Test
    void testGetBucket() {
        var bucket = amazonS3Operator.getBucket(testingBucketName);
        Assertions.assertTrue(bucket.isPresent());
    }

    @Order(4)
    @Test
    void testAgainListBuckets() {
        var bucketList = amazonS3Operator.listBuckets();
        List<String> bucketNames = bucketList.stream().map(Bucket::name).toList();
        Assertions.assertEquals(numberOfBuckets, bucketNames.size());
        Assertions.assertTrue(bucketNames.contains(testingBucketName));
    }

    @Order(5)
    @Test
    void testUploadFile() {
        try {
            File testingFile = ResourceUtils.getFile(TEST_FIlE_CLASS_PATH);
            Assertions.assertDoesNotThrow(() -> amazonS3Operator.uploadFile(testingBucketName, testingFileName, testingFile));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Order(6)
    @Test
    void testDownloadFile() {
        byte[] byteArray = amazonS3Operator.downloadFile(testingBucketName, testingFileName);
        Assertions.assertNotNull(byteArray);
    }

    @Order(7)
    @Test
    void testDeleteFile() {
        Assertions.assertDoesNotThrow(() -> amazonS3Operator.deleteFile(testingBucketName, testingFileName));
    }

    @Order(Integer.MAX_VALUE)
    @Test
    void testDeleteBucket() {
        Assertions.assertDoesNotThrow(() -> amazonS3Operator.deleteBucket(testingBucketName));
    }
}
