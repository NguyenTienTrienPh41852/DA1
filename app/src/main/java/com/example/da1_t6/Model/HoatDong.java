package com.example.da1_t6.Model;

public class HoatDong {
    private int maHoatDong;
    private String tenHoatDong;
    private String moTa;
    private String thoiGianBatDau;
    private String thoiGianKetThuc;
    private int trangThaiHoatDong;
    private String ngay;

    public HoatDong() {
    }

    public HoatDong(int maHoatDong, String tenHoatDong, String moTa, String thoiGianBatDau, String thoiGianKetThuc, int trangThaiHoatDong, String ngay) {
        this.maHoatDong = maHoatDong;
        this.tenHoatDong = tenHoatDong;
        this.moTa = moTa;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.trangThaiHoatDong = trangThaiHoatDong;
        this.ngay = ngay;
    }

    public int getMaHoatDong() {
        return maHoatDong;
    }

    public void setMaHoatDong(int maHoatDong) {
        this.maHoatDong = maHoatDong;
    }

    public String getTenHoatDong() {
        return tenHoatDong;
    }

    public void setTenHoatDong(String tenHoatDong) {
        this.tenHoatDong = tenHoatDong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public int getTrangThaiHoatDong() {
        return trangThaiHoatDong;
    }

    public void setTrangThaiHoatDong(int trangThaiHoatDong) {
        this.trangThaiHoatDong = trangThaiHoatDong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
