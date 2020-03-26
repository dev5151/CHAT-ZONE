package com.dev5151.chatzone.Models;

public class ChatRoomModel {
    String sender;
    String message;

    public ChatRoomModel() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatRoomModel(String sender, String receiver, String message, String isChatRoom) {
        this.sender = sender;
        this.message = message;
    }


}
