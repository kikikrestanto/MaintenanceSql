package com.example.maintenancesql.Models;

public class Post {
    String inventarisEdit, jangkaWaktu,jenisEdit,lokasiEdit,merkEdit,dateEdit;
    User user;
    int id,user_id,post_id;

    public Post(String inventarisEdit, String jangkaWaktu, String jenisEdit, String lokasiEdit, String merkEdit, User user, int id, String dateEdit,int user_id,int post_id) {
        this.inventarisEdit = inventarisEdit;
        this.jangkaWaktu = jangkaWaktu;
        this.jenisEdit = jenisEdit;
        this.lokasiEdit = lokasiEdit;
        this.merkEdit = merkEdit;
        this.user = user;
        this.id = id;
        this.dateEdit = dateEdit;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public Post() {
    }

    public String getInventarisEdit() {
        return inventarisEdit;
    }

    public void setInventarisEdit(String inventarisEdit) {
        this.inventarisEdit = inventarisEdit;
    }

    public String getJangkaWaktu() {
        return jangkaWaktu;
    }

    public void setJangkaWaktu(String jangkaWaktu) {
        this.jangkaWaktu = jangkaWaktu;
    }

    public String getJenisEdit() {
        return jenisEdit;
    }

    public void setJenisEdit(String jenisEdit) {
        this.jenisEdit = jenisEdit;
    }

    public String getLokasiEdit() {
        return lokasiEdit;
    }

    public void setLokasiEdit(String lokasiEdit) {
        this.lokasiEdit = lokasiEdit;
    }

    public String getMerkEdit() {
        return merkEdit;
    }

    public void setMerkEdit(String merkEdit) {
        this.merkEdit = merkEdit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(String dateEdit) {
        this.dateEdit = dateEdit;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
