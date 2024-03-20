package com.whatsapp.backend.service;


import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.MessageException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.SendMessageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;


public interface MessageService {
    Message createMessage(SendMessageRequest request) throws UserException, ChatException;

    List<Message> getChatMessages(Long chatId,User reguser) throws ChatException;

    void deleteMessage(Long msgid) throws MessageException;

    void deleteMessagesByIds(List<Long> msgids);

}
