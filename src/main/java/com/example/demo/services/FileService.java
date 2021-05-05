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
public class FileService {

    private final AmazonS3 amazonS3;

    public void uploadFile(byte[] file, String fileName) {
        File fileToSave = new File(fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileToSave)) {
            fileOutputStream.write(file);
            amazonS3.putObject(new PutObjectRequest("gameweb", fileName, fileToSave));
        } catch (IOException e) {
            log.error("Error during uploading file to S3", e);
        }
    }

    public void uploadFileToNews(byte[] file, String fileName) {
        File fileToSave = new File(fileName);
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileToSave) ) {
            fileOutputStream.write(file);
            amazonS3.putObject(new PutObjectRequest("gwnews", fileName, fileToSave));
        } catch (IOException e) {
            log.error("Error during uploading file to S3", e);
        }
    }

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
