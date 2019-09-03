package com.cipcipp.main.model;

public class User {
    private String username;
    private String useremail;

    public User(String username, String useremail) {
        this.useremail = useremail;
        this.username  = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }
}
