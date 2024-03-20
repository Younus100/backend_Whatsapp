package com.whatsapp.backend.request;

import com.whatsapp.backend.model.Message;
import lombok.Data;

@Data
public class PayMessage {
    Message message;
    private  Long id;
}
