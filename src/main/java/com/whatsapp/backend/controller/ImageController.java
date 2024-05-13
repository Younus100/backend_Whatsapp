package com.whatsapp.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.request.SendMessageRequest;
import com.whatsapp.backend.service.ChatService;
import com.whatsapp.backend.service.FirebaseService;
import com.whatsapp.backend.service.MessageService;
import com.whatsapp.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageController {

    FirebaseService firebaseService;
    UserService userService;

    ChatService chatService;

    MessageService messageService;


    public ImageController(FirebaseService firebaseService, UserService userService, ChatService chatService, MessageService messageService) {
        this.firebaseService = firebaseService;
        this.userService = userService;
        this.chatService = chatService;
        this.messageService = messageService;
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

    @PostMapping("/imageChat/{chatId}")
    public ResponseEntity<Message> handleImageUpload(@RequestParam("file") MultipartFile file, @PathVariable Long chatId) throws IOException {
        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body(new Message("Please select a file to upload."));
            System.out.println("Hhhhh");
        }
        try {
            // Upload the image to Firebase and get the image URL
            String imageUrl = firebaseService.uploadImage(file);
            Message message =  messageService.createImgMessage(imageUrl,chatId);
            // Once saved, you can return the file path or any other response
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (IOException | UserException | ChatException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

        @GetMapping("/v1")
    public String test(){
        return "Test";
    }
}
