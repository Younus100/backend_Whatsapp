package com.whatsapp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.backend.service.ChatService;
import com.whatsapp.backend.service.FirebaseService;
import com.whatsapp.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageController {

    FirebaseService firebaseService;
    UserService userService;

    ChatService chatService;

    public ImageController(FirebaseService firebaseService, UserService userService, ChatService chatService) {
        this.firebaseService = firebaseService;
        this.userService = userService;
        this.chatService = chatService;
    }


    @PostMapping("/updateProfilePic")
    public ResponseEntity<String> addProduct(@RequestParam("file") MultipartFile file) {
        try {

//             Upload the image to Firebase and get the image URL
            String imageUrl = firebaseService.uploadImage(file);
            System.out.println(imageUrl);

            userService.updateProfilePicofUser(imageUrl);

//             Save the imageUrl along with other product details
            return ResponseEntity.ok("Profile Pic updated successfully.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload pic");
        }
    }

    public ResponseEntity<String> GroupPhoto(@RequestParam("id") Long id,@RequestParam("file") MultipartFile file) {
        try {

//             Upload the image to Firebase and get the image URL
            String imageUrl = firebaseService.uploadImage(file);
            System.out.println(imageUrl);

           chatService.groupProfilePic(imageUrl,id);

//             Save the imageUrl along with other product details
            return ResponseEntity.ok("Profile Pic updated successfully.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload pic");
        }
    }

    @GetMapping("/v1")
    public String test(){
        return "Test";
    }
}
