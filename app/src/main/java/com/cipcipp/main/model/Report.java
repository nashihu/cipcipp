package com.cipcipp.main.model;

public class Report  {
    private String provider = "null";
    private String nominal = "null";
    private String price = "null";
    private String imageUri = "null";
    private String timestamp = "null";
    private String user_email = "null";

    public Report(String provider, String nominal, String price, String imageUri, String timestamp, String user_email) {
        this.provider = provider;
        this.nominal = nominal;
        this.price = price;
        this.imageUri = imageUri;
        this.timestamp = timestamp;
        this.user_email = user_email;
    }

    public Report() {

    }

    public String getTimestamp() {
        return timestamp;
    }



    public String getProvider() {
        return provider;
    }



    public String getNominal() {
        return nominal;
    }



    public String getPrice() {
        return price;
    }



    public String getImageUri() {
        return imageUri;
    }



    public String getUser_email() {
        return user_email;
    }


}
