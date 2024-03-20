package com.whatsapp.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private String chat_name;

    @Column(length = 512)
   private String chatImage;

   private boolean isGroup;

   @ManyToOne
    private User createdBy;

   @ManyToMany
    private Set<User> users = new HashSet<>();

   @OneToMany(mappedBy = "chat", cascade = CascadeType.REMOVE)
    private List<Message> messages= new ArrayList<>();

}
