package com.whatsapp.backend.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;


@Service
public class FirebaseService {
    private  Storage storage;
    String BUCKET_NAME  = "e-commerce-website-2cc5c.appspot.com";
    String FOLDER_NAME  = "whatsapp";

//    public FirebaseService() {
//        try {
//            // Initialize Firebase Admin SDK
//            InputStream serviceAccount = getClass().getResourceAsStream("config/serviceAccountKey.json");
//            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//            StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
//            this.storage = storageOptions.getService();
//        } catch (IOException e) {
//            // Handle the exception (log, set storage to null, etc.)
//            System.out.println("Failed to initialize FirebaseService"+ e);
//        }
//    }

    public String uploadImage(MultipartFile file) throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("config/firebase.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        StorageOptions storageOptions = StorageOptions.newBuilder().setCredentials(credentials).build();
        this.storage = storageOptions.getService();
        // Set your Firebase Storage bucket name


        // Generate a unique file name
        String fileName = generateUniqueFileName( file.getOriginalFilename());

        // Specify the storage location
        BlobId blobId = BlobId.of(BUCKET_NAME ,fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        // Upload the file to Firebase Storage
        Blob blob = storage.create(blobInfo, file.getBytes());

        // Get the public URL of the uploaded file
        return fileName;
//        return "";
    }

    private String generateUniqueFileName(String originalFileName) {
        // Implement logic to generate a unique file name
        // For simplicity, you can use a combination of timestamp and the original file name
        return  FOLDER_NAME + "%2F" +System.currentTimeMillis() + "_" + originalFileName;
    }
}
