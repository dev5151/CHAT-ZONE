package com.dev5151.chatzone.Models;

import android.support.v4.app.INotificationSideChannel;

public class Chat {
    String sender;
    String receiver;
    String message;

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat(String sender, String receiver, String message, String isChatRoom) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

}
