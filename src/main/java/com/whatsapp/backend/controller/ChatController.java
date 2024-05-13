package com.whatsapp.backend.controller;

import com.whatsapp.backend.config.SecurityUtils;
import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Chat;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.GroupRequest;
import com.whatsapp.backend.service.ChatService;
import com.whatsapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Chat> createChatHandler( @RequestParam("receiver") Long userId2) throws UserException {
        User user = SecurityUtils.getCurrentUser(userService);
        Chat createdChat = chatService.createChat(user, userId2);
        return new ResponseEntity<>(createdChat, HttpStatus.CREATED);
    }

    @PostMapping("/getChat/{userid1}")
    public ResponseEntity<Chat> createChatIfNotPresentHandler(@PathVariable Long userid1 ) throws UserException {
        User user = SecurityUtils.getCurrentUser(userService);
        Chat chat = chatService.isChatPresentByUser(user.getId(), userid1);
        return new  ResponseEntity<>(chat,HttpStatus.CREATED);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChatById(@PathVariable Long chatId) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Chat>> getAllChatsByUserId() throws UserException {
        User user = SecurityUtils.getCurrentUser(userService);
        List<Chat> chats = chatService.findAllChatByUser(user);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }



    @PostMapping("/createGroup")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupRequest request) throws UserException {
        User user = SecurityUtils.getCurrentUser(userService);
        if(user==null ) {
            System.out.println("Nululll");
        }
        Chat createdGroup = chatService.createGroup(request, user);

        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @PostMapping("/addMember/{chatId}/{userId}")
    public ResponseEntity<Chat> addUserToGroup(@PathVariable Long chatId, @PathVariable Long userId) throws ChatException, UserException {
        Chat updatedGroup = chatService.addUserToGroup(chatId, userId);
        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);

    }

    @PutMapping("/renameGroup/{chatId}")
    public ResponseEntity<Chat> renameGroup(@PathVariable Long chatId, @RequestParam String groupName) throws ChatException, UserException {
        Chat updatedGroup = chatService.renameGroup(chatId, groupName);
        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<Chat>> searchChats(@PathVariable String query) throws UserException {
        List<Chat> chats = chatService.getChatwithSearch(query);
        return  new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
        try {
            chatService.deleteChat(chatId);
            return new ResponseEntity<>("Chat deleted successfully", HttpStatus.OK);
        } catch (ChatException | UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

