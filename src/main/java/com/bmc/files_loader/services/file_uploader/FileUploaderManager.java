package com.bmc.files_loader.services.file_uploader;

import com.amazonaws.services.s3.AmazonS3;

import java.io.File;

public interface FileUploaderManager<T> {
    void uploadFile(T transferManager, String bucketName,File file);
}
