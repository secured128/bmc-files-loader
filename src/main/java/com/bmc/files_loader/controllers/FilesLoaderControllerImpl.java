package com.bmc.files_loader.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.bmc.files_loader.services.cloud_connector.CloudConnector;
import com.bmc.files_loader.services.file_uploader.FileUploaderManager;
import com.bmc.files_loader.services.files_generator.FilesGenerator;
import com.bmc.files_loader.services.print_manager.PrintManager;
import com.bmc.files_loader.services.transfer_manager.TransferManagerActivities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FilesLoaderControllerImpl implements Controller {
    @Value("${createFilesInd}")
    boolean createFilesInd;
    @Value("${inputFilesPath}")
    String inputFilesPath;
    @Value("${bucketNamePrefix}")
    String bucket;
    @Autowired
    FilesGenerator filesGenerator;
    @Autowired
    CloudConnector<AmazonS3> cloudConnector;
    @Autowired
    TransferManagerActivities<TransferManager, AmazonS3> transferManagerActivities;
    @Autowired
    FileUploaderManager<TransferManager> fileUploader;
    @Autowired
    PrintManager printManager;

    @Override
    public void controlFlow() {
        String directoryToProceed = createFilesInd ? filesGenerator.generateFiles() : inputFilesPath + "/";
        System.out.println(directoryToProceed);
        AmazonS3 s3Client = cloudConnector.createCloudConnection();
        TransferManager transferManager = transferManagerActivities.createTransferManager(s3Client);
        File dir = new File(directoryToProceed);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            uploadFiles(s3Client, transferManager, directoryListing);
        }
        transferManagerActivities.closeTransferManager(transferManager);
    }

    private void uploadFiles(AmazonS3 s3Client, TransferManager transferManager, File[] directoryListing) {
        String bucketName = bucket+ System.currentTimeMillis();
        s3Client.createBucket(new CreateBucketRequest(bucketName));
        StringBuilder stringToPrint = new StringBuilder("\n\n"+ directoryListing.length + " files are about to be transferred to bucket " + bucketName);
        printManager.printString(stringToPrint);
        for (File file : directoryListing) {
            fileUploader.uploadFile(transferManager,bucketName, file);
        }
    }
}
