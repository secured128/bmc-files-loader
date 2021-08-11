package com.bmc.files_loader.services.files_generator;

import com.bmc.files_loader.utils.Utils;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Service
public class FilesGeneratorImpl implements FilesGenerator {
    @Value("${numberOfFilesToCreate}")
    int numOfFilesToCreate;
    @Value("${minFileSize}")
    int minFileSize;
    @Value("${maxFileSize}")
    int maxFileSize;
    @Value("${inputFilesPath}")
    String inputFilesPathPrefix;
    @Value("${filesPrefix}")
    String filesPrefix;
    @Autowired
    Faker faker;
    @Override
    public String  generateFiles() {
        String inputDirectoryPath = prepareInputDir();
        for(int i=0;i<numOfFilesToCreate;i++){
            createFile(inputDirectoryPath);
        }
        return inputDirectoryPath;
    }
    private String prepareInputDir() {
        String inputDirectoryPath = inputFilesPathPrefix+System.currentTimeMillis();
        System.out.println(inputDirectoryPath);
        inputDirectoryPath=inputDirectoryPath+"/";
        File directory = new File(inputDirectoryPath);
        if (!directory.exists()){
            directory.mkdir();
        }
        return inputDirectoryPath;
    }

    @SneakyThrows
    public void createFile(String inputDirectoryPath) {
        int fileSize = Utils.getRandomNumber(minFileSize, maxFileSize)*1000;
        int writtenNumberOfBytes = 0;
        String outputFileName = inputDirectoryPath+filesPrefix+System.currentTimeMillis();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName, true));
        while (writtenNumberOfBytes < fileSize) {
            String stringToWrite = faker.backToTheFuture().quote();
            bufferedWriter.write(stringToWrite);
            bufferedWriter.flush();
            writtenNumberOfBytes = writtenNumberOfBytes+stringToWrite.getBytes().length;
        }
        bufferedWriter.close();
    }


}
