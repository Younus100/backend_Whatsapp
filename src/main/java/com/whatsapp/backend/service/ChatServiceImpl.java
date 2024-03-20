package com.whatsapp.backend.service;

import com.whatsapp.backend.config.SecurityUtils;
import com.whatsapp.backend.exception.ChatException;
import com.whatsapp.backend.exception.UserException;
import com.whatsapp.backend.model.Chat;
import com.whatsapp.backend.model.User;
import com.whatsapp.backend.repository.ChatRepository;
import com.whatsapp.backend.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User user, Long userid2) throws UserException {
        User receiver = userService.findUserById(userid2);
        if(receiver==null) {  throw  new UserException("Receiver Not Found"); }

        Chat ischatExisted = chatRepository.finSingleChatByUserIds(user,receiver);

        if(ischatExisted!=null) { return  ischatExisted; }

        Chat chat = new Chat();
        chat.setCreatedBy(user);
        chat.getUsers().add(receiver);
        chat.getUsers().add(user);
        chat.setGroup(false);
        chatRepository.save(chat);
        return  chat;
    }

    @Override
    public Chat findChatById(Long chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()) { return chat.get(); }
        else { throw new ChatException("Chat is not present eiwith chatId"+ chatId);  }
    }

    @Override
    public List<Chat> findAllChatByUser(User user) throws UserException {
        List<Chat> chats = chatRepository.findChatByUser(user);
        return chats;
    }

    @Override
    public Chat createGroup(GroupRequest request, User user) throws UserException {
        Chat chat = new Chat();
        chat.setCreatedBy(user);
        chat.getUsers().add(user);
        chat.setGroup(true);
        chat.setChat_name(request.getGroupName());
        for(Long member_id : request.getMember_ids()) {
            Long m = Long.valueOf(member_id);
            User member = userService.findUserById(m);
            if(member!=null) {
                chat.getUsers().add(member);
            }
        }
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public Chat addUserToGroup(Long chatId, Long userid2) throws UserException, ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(!chat.isPresent()){ throw new ChatException("Group is not present"); }
        User member = userService.findUserById(userid2);
        if(member==null) { throw new UserException("USr is not present") ; }
        Chat c = chat.get();
        c.getUsers().add(member);
        chatRepository.save(c);
        return c;
    }

    @Override
    public Chat renameGroup(Long chatId, String grpname) throws UserException, ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(!chat.isPresent()){ throw new ChatException("Group is not present"); }
        Chat c = chat.get();
        c.setChat_name(grpname);
        chatRepository.save(c);
        return c;
    }

    @Override
    public void removeFromGrp(Long chatId, Long userid2) throws UserException, ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        User user = userService.findUserById(userid2);
        if(user==null) { throw  new UserException("User Not Found"); }
        if(chat.isEmpty()){  throw  new ChatException("Chat Not Found"); }
        chat.get().getUsers().remove(user);
        chatRepository.save(chat.get());
    }

    @Override
    public void deleteChat(Long chatId) throws ChatException, UserException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isEmpty()) { throw new ChatException("ChatNot Found Exception"); }
        chatRepository.deleteById(chatId);
        }

    @Override
    public Chat isChatPresentByUser(Long reguser, Long userid2) throws UserException {
        User user =userService.findUserById(reguser);
        User user1 = userService.findUserById(userid2);
        if(user==null || user1==null) { throw  new UserException("Users  Not Found"); }
        Optional<Chat> chat = chatRepository.findByUsersIdAndIsGroupFalse(user,user1);
        if(chat.isEmpty()) {
            Chat chat1 = new Chat();
            chat1.setCreatedBy(user);
            chat1.getUsers().add(user);
            chat1.getUsers().add(user1);
            chat1.setGroup(false);
            chatRepository.save(chat1);
            return  chat1;
        } else {
            return chat.get();
        }
    }

    @Override
    public List<Chat> getChatwithSearch(String query) throws UserException {
        User user = SecurityUtils.getCurrentUser(userService);
        query = "%"+query+"%";
        List<Chat> chats = chatRepository.findChatByUsersContainingAndUsersEmailLikeIgnoreCaseOrUsersFullNameLikeIgnoreCase(user,query,query);
        return chats;
    }

    @Override
    public void groupProfilePic(String url, Long chatid) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatid);
        if(chat.isEmpty()) { throw  new ChatException(""); }
        Chat c = chat.get();
        c.setChatImage(url);
        chatRepository.save(c);
    }
}
