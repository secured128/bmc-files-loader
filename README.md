#Zenko files loader
Application which creates files upon request, loads files to ZENKO ORBIT sandbox storage under newly created bucket, and shows progress.
Set up and run instructions:
There is application.properties file which includes below parameters:

Parameter name  | Parameter description                        | Parameter example or  valid values
-------------   | -------------                                | -------------
createFilesInd  | option to generate files at local directory  | true / false
inputFilesPath  | in case that files should be generated locally - prefix of directory name where to generate files locally and from where to upload files to sandbox. In case that no files creation is required - directory where files to be uploaded exist| Example: data/filesToUpload
numberOfFilesToCreate  | relevant if  createFilesInd is true - number of files to be created | 10
minFileSize  | relevant if  createFilesInd is true - minimum size of file to be created in KB | 1
maxFileSize  | relevant if  createFilesInd is true - maximum size of file to be created in KB | 15000
filesPrefix  | relevant if  createFilesInd is true - files name prefix. File name will be concatination of prefix and System.currentTimeMillis() | bmc_
accessKey  | Access key to ZENKO | ABCD333
secretKey  | Secret key to ZENKO. In real situation it should not be kept here, or be encrypted | RTTTTAAA111
serviceEndPoint  | Service end point to ZENKO | https://a177c684-fa74-11eb-a99d-5a5f4303b399.sandbox.zenko.io
region  | Region at ZENKO | us-east-1
bucketNamePrefix  | Prefix for bucket name that data will be uploaded to in ZENKO. Full name of bucket will be concatination of bucket prefix and System.currentTimeMillis()  | bmcexercise
percentageProgressToPrint  | Percentage difference which should be reflected while printed transfer progress. Since transfer is done in chunks of 8192 bytes, for small files progress will be reflected as per number of transferred chunks. | 10

###Run application instructions 
Clone the project to the development environment (IntelliJ)  
Run locally FilesLoaderApplication.java under com/bmc/files_loader/FilesLoaderApplication
Right-click on the class and select Run "FilesLoaderApplication".
Transfer progress is printed on console

###Description of the solution:
In order to upload files to ZENKO, AWS SDK for Java 1.x. Note:  Java 2.x was not used since Transfer manager is still not there in Java 2.x.
Code was written at Java 11 ans springboot framework was used.
FilesLoaderControllerImpl class manages the whole flow.
Under services package there are all relevant services which are used in application. For each service interface is used and that 
for keeping modularity of code and ability to easily add/replace/remove services logic (For example, if in next sprint we would like to print progress
into file, and not into console, PrintManager interface can be implemented in different way so that data will be printed to file.)
Transfer progress reflection was implemented by adding ProgressListener to TransferManager.upload.
In case of generating local files to be uploaded - javafaker library was used in order to create files with text.

###Issues
When uploading several big files, connection to ZENKO is lost and files uploader is stopped in the middle.