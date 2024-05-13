package com.whatsapp.backend.response;

import com.whatsapp.backend.model.User;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AuthResponse {
    private String jwt;
    private String msg;

    private String fullName;

    private String profilepic;

    private Map<Long, Integer> unreadMessages;



}
