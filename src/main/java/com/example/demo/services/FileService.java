package com.example.demo.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;


@Service
@Slf4j
@RequiredArgsConstructor
/**Class for uploading files to S3 on AWS*/
public class FileService {

    private final AmazonS3 amazonS3;

    /**Uploads file to S3 bucket "gameweb" where avatars are stored*/
    public void uploadFile(byte[] file, String fileName) {
        File fileToSave = new File(fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileToSave)) {
            fileOutputStream.write(file);
            amazonS3.putObject(new PutObjectRequest("gameweb", fileName, fileToSave));
        } catch (IOException e) {
            log.error("Error during uploading file to S3", e);
        }
    }

    /**Uploads file to S3 bucket "gwnews" where news images are stored*/
    public void uploadFileToNews(byte[] file, String fileName) {
        File fileToSave = new File(fileName);
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
            fileOutputStream.write(file);
            fileOutputStream.close();
            amazonS3.putObject(new PutObjectRequest("gwnews", fileName, fileToSave));

        } catch (IOException e) {
            log.error("Error during uploading file to S3", e);
        }
    }

    /**Uploads file to S3 bucket "gwreviews" where reviews images are stored*/
    public void uploadFileToReview(byte[] file, String fileName) {
        File fileToSave = new File(fileName);
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileToSave) ) {
            fileOutputStream.write(file);
            amazonS3.putObject(new PutObjectRequest("gwreviews", fileName, fileToSave));
        } catch (IOException e) {
            log.error("Error during uploading file to S3", e);
        }
    }



}
