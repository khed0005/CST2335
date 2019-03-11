package com.example.androidlabs;

import java.util.ArrayList;

public class Message {

    private String message;
    private int messageType;
    private long id;

    public Message(String message, int messageType, long id) {

        this.message = message;
        this.messageType = messageType;
        this.id = id;
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

    public long getId( long id){
        return id;
     }

}


