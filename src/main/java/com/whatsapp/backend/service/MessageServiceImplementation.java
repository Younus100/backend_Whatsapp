package com.whatsapp.backend.service;

import com.whatsapp.backend.config.SecurityUtils;
import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.MessageException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Chat;
import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.repository.ChatRepository;
import com.whatsapp.backend.repository.MessageRepository;
import com.whatsapp.backend.request.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @Autowired
    ChatRepository chatRepository;

    @Override
    public Message createMessage(SendMessageRequest request) throws UserException, ChatException {
        User user = SecurityUtils.getCurrentUser(userService);
        if(user == null) { throw  new UserException("User Not Found"); }
        Chat chat = chatService.findChatById(request.getChatId());
        if(chat==null) { throw  new ChatException("Chat not found"); }
        Message message=new Message();
        message.setContent(request.getContent());
        message.setUser(user);
        message.setChat(chat);
        message.setTimeStamp(LocalDateTime.now());
        messageRepository.save(message);
        chat.getMessages().add(message);
        chatRepository.save(chat);
        return  message;
    }

    @Override
    public List<Message> getChatMessages(Long chatId,User reguser) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        if(chat==null) { throw  new ChatException("Chat not found");  }
        if(!chat.getUsers().contains(reguser)) {  throw new ChatException("Not the member  of Chat");  }
        List<Message> messages = messageRepository.findMessagesByChatId(chatId);
        return messages;
    }

    @Override
    public void deleteMessage(Long msgid) throws MessageException {

    }

    @Override
    public void deleteMessagesByIds(List<Long> msgids) {
        messageRepository.deleteByIdIn(msgids);
    }
}
