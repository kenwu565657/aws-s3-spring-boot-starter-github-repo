package com.aws.s3.self.define.starter;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
import java.util.Optional;

public class AmazonS3Operator implements AmazonS3Operation {

    //private final AmazonS3Properties amazonS3Properties;
    //private final AmazonS3 amazonS3;
    private final AmazonS3 amazonS3Client;

    public AmazonS3Operator(AmazonS3Properties amazonS3Properties) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(amazonS3Properties.getAccessKey(), amazonS3Properties.getSecretKey());
        this.amazonS3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(amazonS3Properties.getRegion())
                .build();
    }

    @Override
    public List<Bucket> listBuckets() {
        return amazonS3Client.listBuckets();
    }

    @Override
    public Optional<Bucket> getBucket(String bucketName) {
        List<Bucket> buckets = listBuckets();
        for (Bucket bucket : buckets) {
            if (bucketName.equals(bucket.getName())) {
                return Optional.of(bucket);
            }
        }
        return Optional.empty();
    }

    @Override
    public Bucket createBucket(String bucketName) {
        return null;
    }

    @Override
    public void copyObject(String bucketName, String sourceKey, String destinationKey) throws AmazonS3Exception {

    }
}
