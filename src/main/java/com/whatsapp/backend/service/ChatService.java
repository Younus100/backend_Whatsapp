package com.whatsapp.backend.service;

import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Chat;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.request.GroupRequest;

import java.util.List;

public interface ChatService {

    Chat createChat(User user, Long userid2) throws UserException;

    Chat findChatById(Long chatId) throws ChatException;

    List<Chat> findAllChatByUser(User user) throws UserException;

    Chat createGroup(GroupRequest request, User user) throws UserException;

    Chat addUserToGroup(Long chatId, Long userid2) throws UserException, ChatException;

    Chat renameGroup(Long chatId,String grpname) throws UserException, ChatException;

    void removeFromGrp(Long chatId, Long userid2) throws UserException, ChatException;

    void  deleteChat(Long chatId) throws ChatException, UserException;

    Chat isChatPresentByUser(Long userid1,Long userid2) throws  UserException;

    List<Chat> getChatwithSearch(String query) throws UserException;

    void groupProfilePic(String url,Long chatid)throws ChatException;





    // Other methods for updating, deleting, etc.
}
