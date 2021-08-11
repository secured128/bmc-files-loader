package com.bmc.files_loader.services.transfer_manager;

public interface TransferManagerActivities<T,G> {
    T createTransferManager(G client);
     void closeTransferManager(T transferManager);
}
