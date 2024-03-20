package com.whatsapp.backend.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SendMessageRequest {
        Long userId;
    Long chatId;
    String content;
}
