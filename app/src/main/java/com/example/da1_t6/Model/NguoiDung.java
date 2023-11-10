package com.example.da1_t6.Model;

public class NguoiDung {
    private String maNguoiDung;
    private String matKhau;
    private String hoTen;
    private byte[] anhDaiDien;

    public NguoiDung() {
    }

    public NguoiDung(String maNguoiDung, String matKhau, String hoTen, byte[] anhDaiDien) {
        this.maNguoiDung = maNguoiDung;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.anhDaiDien = anhDaiDien;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public byte[] getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(byte[] anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }
}
