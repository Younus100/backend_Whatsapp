package com.whatsapp.backend.controller;

import com.whatsapp.backend.config.SecurityUtils;
import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.SendMessageRequest;
import com.whatsapp.backend.service.MessageService;
import com.whatsapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> createMessage(@RequestBody SendMessageRequest request) {
        try {
            Message createdMessage = messageService.createMessage(request);
            return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
        } catch (UserException | ChatException e) {
            System.out.println("zzzzz");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getChatMessages/{chatId}")
    public ResponseEntity<List<Message>> getChatMessages(@PathVariable Long chatId) {
        try {
            User user = SecurityUtils.getCurrentUser(userService);
            List<Message> messages = messageService.getChatMessages(chatId,user);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (ChatException | UserException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public void deleteMessagesByIds(@RequestParam List<Long> messageIds) {
        messageService.deleteMessagesByIds(messageIds);
    }
}

