package com.whatsapp.backend.repository;

import com.whatsapp.backend.model.Chat;
import com.whatsapp.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat c where :user member of c.users")
    List<Chat> findChatByUser(@Param("user") User user);

    @Query("select c from Chat c where c.isGroup = false and :user member of c.users and :reqUser member of c.users")
    Chat finSingleChatByUserIds(@Param("user") User user, @Param("reqUser") User reqUser);

    @Query("SELECT c FROM Chat c WHERE c.isGroup = false AND :userId1 MEMBER OF c.users AND :userId2 MEMBER OF c.users")
    Optional<Chat> findByUsersIdAndIsGroupFalse(@Param("userId1") User userId1, @Param("userId2") User userId2);
    List<Chat> findChatByUsersContainingAndUsersEmailLikeIgnoreCaseOrUsersFullNameLikeIgnoreCase(User user,String email,String fullname);
}
