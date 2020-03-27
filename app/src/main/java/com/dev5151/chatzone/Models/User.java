package com.dev5151.chatzone.Models;

public class User {
    String email;
    String password;
    String username;
    String imgUrl;
    String uid;
    String status;
    String search;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getStatus() {
        return status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String email, String username, String imgUrl, String uid, String status, String search) {
        this.email = email;
        this.username = username;
        this.imgUrl = imgUrl;
        this.uid = uid;
        this.status = status;
        this.search = search;
    }
}
