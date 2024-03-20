package com.whatsapp.backend.request;

import lombok.Data;

import java.sql.Blob;

@Data
public class UserRequest {
    private String full_name;
    private String profile_picture;
    private String password;
}
