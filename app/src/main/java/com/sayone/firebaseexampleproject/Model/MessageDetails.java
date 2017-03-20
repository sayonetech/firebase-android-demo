package com.sayone.firebaseexampleproject.Model;

/**
 * Created by sayone on 16/3/17.
 */

public class MessageDetails {

    private String userName;
    private String message;

    public MessageDetails() {

    }

    public MessageDetails(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }
}
