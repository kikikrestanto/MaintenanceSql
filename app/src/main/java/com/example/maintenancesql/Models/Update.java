package com.example.maintenancesql.Models;

public class Update {

    String no,tanggalMaintenance,tanggalMaintenanceSelanjutnya,tindakan,keterangan;
    int post_id;
    User user;
    int id,user_id;

    public Update() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTanggalMaintenance() {
        return tanggalMaintenance;
    }

    public void setTanggalMaintenance(String tanggalMaintenance) {
        this.tanggalMaintenance = tanggalMaintenance;
    }

    public String getTanggalMaintenanceSelanjutnya() {
        return tanggalMaintenanceSelanjutnya;
    }

    public void setTanggalMaintenanceSelanjutnya(String tanggalMaintenanceSelanjutnya) {
        this.tanggalMaintenanceSelanjutnya = tanggalMaintenanceSelanjutnya;
    }

    public String getTindakan() {
        return tindakan;
    }

    public void setTindakan(String tindakan) {
        this.tindakan = tindakan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
