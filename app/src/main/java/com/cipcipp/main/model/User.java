package com.cipcipp.main.model;

public class User {
    private String username;
    private String useremail;
    private String photo_url;

    public User(String username, String useremail, String photo_url) {
        this.useremail = useremail;
        this.username  = username;
        this.photo_url = photo_url;
    }

    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getPhoto_url() { return photo_url; }
}
