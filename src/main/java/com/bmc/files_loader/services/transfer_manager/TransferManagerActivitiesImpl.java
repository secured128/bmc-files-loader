package com.bmc.files_loader.services.transfer_manager;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.stereotype.Service;

@Service
public class TransferManagerActivitiesImpl implements TransferManagerActivities<TransferManager, AmazonS3> {
    @Override
    public TransferManager createTransferManager(AmazonS3 s3Client) {
        return TransferManagerBuilder.standard().withS3Client(s3Client).build();
    }

    @Override
    public void closeTransferManager(TransferManager transferManager) {
        transferManager.shutdownNow();
    }
}
