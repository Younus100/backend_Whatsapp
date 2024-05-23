package com.whatsapp.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    @Column(length = 512)
    private String profilePicture;

    private String bio;

    @ElementCollection
    @CollectionTable(name = "unread_messages",
            joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "chat_id") // Use @MapKeyJoinColumn for non-basic key types
    @Column(name = "unread_count")
    private Map<Long, Integer> unreadMessages = new HashMap<>();

}