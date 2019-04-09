package com.cipcipp.main.model;

public class Report  {
    private String provider;
    private String nominal;
    private String price;
    private String imageUri;
    private String timestamp;

    public Report(String provider, String nominal, String price, String imageUri, String timestamp) {
        this.provider = provider;
        this.nominal = nominal;
        this.price = price;
        this.imageUri = imageUri;
        this.timestamp = timestamp;
    }

    public Report() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
