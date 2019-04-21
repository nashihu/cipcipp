package com.cipcipp.main.model;

public class AggModel {
    private String nominal;
    private String price;
    private String provider_name;
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

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public AggModel(String price, String provider_name, String provider_id) {
        this.nominal = "0";
        this.price = price;
        this.provider_name = provider_name;
        this.provider_id = provider_id;
    }
}
