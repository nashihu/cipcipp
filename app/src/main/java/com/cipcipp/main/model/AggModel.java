package com.cipcipp.main.model;

public class AggModel {
    private String nominal;
    private String price;
    private String provider_url;
    private String provider_id;

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

    public String getProvider_url() {
        return provider_url;
    }

    public void setProvider_url(String provider_url) {
        this.provider_url = provider_url;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public AggModel(String price, String provider_url, String provider_id) {
        this.nominal = "0";
        this.price = price;
        this.provider_url = provider_url;
        this.provider_id = provider_id;
    }
}
