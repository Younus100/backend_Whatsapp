package com.whatsapp.backend.config;

import com.whatsapp.backend.model.Message;
import com.whatsapp.backend.request.PayMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeChat {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public PayMessage receiveMessage(@Payload PayMessage message){
        simpMessagingTemplate.convertAndSend("/group"+String.valueOf(message.getId()),message);
        return message;
    }
}
