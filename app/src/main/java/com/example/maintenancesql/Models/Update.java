package com.example.maintenancesql.Models;

public class Update {

    String no,tanggalMaintenance,tanggalMaintenanceSelanjutnya,tindakan,keterangan;
    User user;
    int id;

    public Update() {
    }

    public Update(String no, String tanggalMaintenance, String tanggalMaintenanceSelanjutnya, String tindakan, String keterangan, User user, int id) {
        this.no = no;
        this.tanggalMaintenance = tanggalMaintenance;
        this.tanggalMaintenanceSelanjutnya = tanggalMaintenanceSelanjutnya;
        this.tindakan = tindakan;
        this.keterangan = keterangan;
        this.user = user;
        this.id = id;
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
}
