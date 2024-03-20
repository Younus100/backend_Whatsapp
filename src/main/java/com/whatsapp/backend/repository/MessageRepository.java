package com.whatsapp.backend.repository;

import com.whatsapp.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId")
    List<Message> findMessagesByChatId(@Param("chatId") Long chatId);

    void deleteByIdIn(List<Long> msgids);
}
