package com.dev5151.chatzone.Models;

public class User {
    String email;
    String password;
    String username;
    String imgUrl;
    String uid;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String email, String password, String username, String imgUrl, String uid) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.imgUrl = imgUrl;
        this.uid = uid;
    }
}
