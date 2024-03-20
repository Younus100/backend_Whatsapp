package com.whatsapp.backend.response;

import com.whatsapp.backend.model.User;

public class AuthResponse {
    private String jwt;
    private String msg;

    private String fullName;

    private String profilepic;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AuthResponse(String jwt, String msg) {
        this.jwt = jwt;
        this.msg = msg;
    }

    public AuthResponse() {

    }
}
