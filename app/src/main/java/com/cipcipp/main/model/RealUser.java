package com.cipcipp.main.model;

public class RealUser {
    private String username = "null";
    private String kelamin = "null";
    private String ttl = "null";
    private String province = "null";
    private String kota = "null";
    private String email = "null";

    public RealUser(String username, String kelamin, String ttl, String province, String kota,String email) {
        this.username = username;
        this.kelamin = kelamin;
        this.ttl = ttl;
        this.province = province;
        this.kota = kota;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RealUser() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
