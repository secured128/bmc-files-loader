package com.bmc.files_loader.services.files_generator;

public interface FilesGenerator {
    String generateFiles();
    void createFile(String inputDirectoryPath);
}
