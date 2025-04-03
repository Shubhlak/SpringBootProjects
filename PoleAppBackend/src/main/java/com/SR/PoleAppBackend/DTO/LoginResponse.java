package com.SR.PoleAppBackend.DTO;

import com.SR.PoleAppBackend.entity.User;

public class LoginResponse {
    private String message;
    private User user;

    public LoginResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }
}