package com.bmc.files_loader.services.file_uploader;

import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.bmc.files_loader.services.print_manager.PrintManager;
import com.bmc.files_loader.services.transfer_manager.TransferManagerActivities;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.amazonaws.event.ProgressEventType.REQUEST_BYTE_TRANSFER_EVENT;

@Service
public class FileUploaderManagerImpl implements FileUploaderManager<TransferManager> {
    @Autowired
    PrintManager printManager;
    @Autowired
    TransferManagerActivities<TransferManager, AmazonS3> transferManagerActivities;
    @Value("${percentageProgressToPrint}")
    int percentageProgressToPrint;

    @SneakyThrows
    @Override
    public void uploadFile(TransferManager transferManager, String bucketName, File file) {

        Upload upload = transferManager.upload(bucketName, file.getName(), file);
        AtomicLong fileLength = new AtomicLong(file.length());
        AtomicLong transferredBytes = new AtomicLong(0);
        AtomicInteger prevPercentage = new AtomicInteger(0);
        StringBuilder printString = new StringBuilder("\n\nTransferring " + file.getName() + String.format("\nSize %,d ", fileLength.get()));
        printManager.printString(printString);
        upload.addProgressListener((ProgressListener) e -> {
            int pct = 0;
            if (e.getEventType() == REQUEST_BYTE_TRANSFER_EVENT) {
                transferredBytes.addAndGet(e.getBytesTransferred());
                pct = (int) (transferredBytes.get() * 100.0 / fileLength.get());
                if ((pct - prevPercentage.get()) >= percentageProgressToPrint) {
                    StringBuilder printStringTransfer = new StringBuilder(String.format("Transferred: %,d", transferredBytes.get()) + " bytes " + "Percent: " + pct);
                    printManager.printString(printStringTransfer);
                    prevPercentage.getAndSet(pct);
                }
            }
        });
        upload.waitForUploadResult();
    }
}
