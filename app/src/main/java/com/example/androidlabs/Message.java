package com.example.androidlabs;

import java.util.ArrayList;

public class Message {

    private String message;
    private int messageType;

    public Message(String message, int messageType) {

        this.message = message;
        this.messageType = messageType;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

}


