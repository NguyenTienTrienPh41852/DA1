package com.example.da1_t6.Model;

public class ViTien {
    private int maVi;
    private String tenVi;
    private double soDuBanDau;
    private double soDuHienTai;

    public ViTien() {
    }

    public ViTien(int maVi, String tenVi, double soDuBanDau, double soDuHienTai) {
        this.maVi = maVi;
        this.tenVi = tenVi;
        this.soDuBanDau = soDuBanDau;
        this.soDuHienTai = soDuHienTai;
    }
    public ViTien(int maVi, String tenVi, double soDuHienTai) {
        this.maVi = maVi;
        this.tenVi = tenVi;
        this.soDuHienTai = soDuHienTai;
    }
    public ViTien(String tenVi, double soDuHienTai) {
        this.tenVi = tenVi;
        this.soDuHienTai = soDuHienTai;
    }
    public ViTien(String tenVi, double soDuBanDau, double soDuHienTai) {
        this.tenVi = tenVi;
        this.soDuBanDau = soDuBanDau;
        this.soDuHienTai = soDuHienTai;
    }

    public int getMaVi() {
        return maVi;
    }

    public void setMaVi(int maVi) {
        this.maVi = maVi;
    }

    public String getTenVi() {
        return tenVi;
    }

    public void setTenVi(String tenVi) {
        this.tenVi = tenVi;
    }

    public double getSoDuBanDau() {
        return soDuBanDau;
    }

    public void setSoDuBanDau(double soDuBanDau) {
        this.soDuBanDau = soDuBanDau;
    }

    public double getSoDuHienTai() {
        return soDuHienTai;
    }

    public void setSoDuHienTai(double soDuHienTai) {
        this.soDuHienTai = soDuHienTai;
    }
}
