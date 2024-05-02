package com.aeon.hadog.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String dir = "pet";

    public String uploadImage(MultipartFile file) throws Exception{
        String originfileName = file.getOriginalFilename();
        String filePath = dir + "/" + UUID.randomUUID() + originfileName.substring(originfileName.lastIndexOf("."));;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.addUserMetadata("originfilename", URLEncoder.encode(originfileName, StandardCharsets.UTF_8));
        PutObjectResult result = amazonS3Client.putObject(bucketName, filePath, file.getInputStream(), metadata);
        return filePath;
    }

}
