package com.whatsapp.backend.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;

   private  String fullName;
}
