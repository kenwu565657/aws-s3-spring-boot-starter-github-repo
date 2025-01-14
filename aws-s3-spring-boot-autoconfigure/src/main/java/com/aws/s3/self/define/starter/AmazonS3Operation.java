package com.aws.s3.self.define.starter;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;
import java.util.Optional;

public interface AmazonS3Operation {

    List<Bucket> listBuckets();

    Optional<Bucket> getBucket(String bucketName);

    Bucket createBucket(String bucketName);

    void copyObject(String bucketName, String sourceKey, String destinationKey) throws AmazonS3Exception;


}
