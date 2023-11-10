package com.example.da1_t6.Model;

public class KhoanThu {
    private int maKhoanThu;
    private int maVi;
    private String tenKhoanThu;
    private double soTienThu;
    private String thoiGianThu;
    private String ghiChu;

    public KhoanThu() {
    }

    public KhoanThu(int maKhoanThu, int maVi, String tenKhoanThu, double soTienThu, String thoiGianThu, String ghiChu) {
        this.maKhoanThu = maKhoanThu;
        this.maVi = maVi;
        this.tenKhoanThu = tenKhoanThu;
        this.soTienThu = soTienThu;
        this.thoiGianThu = thoiGianThu;
        this.ghiChu = ghiChu;
    }

    public int getMaKhoanThu() {
        return maKhoanThu;
    }

    public void setMaKhoanThu(int maKhoanThu) {
        this.maKhoanThu = maKhoanThu;
    }

    public int getMaVi() {
        return maVi;
    }

    public void setMaVi(int maVi) {
        this.maVi = maVi;
    }

    public String getTenKhoanThu() {
        return tenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu = tenKhoanThu;
    }

    public double getSoTienThu() {
        return soTienThu;
    }

    public void setSoTienThu(double soTienThu) {
        this.soTienThu = soTienThu;
    }

    public String getThoiGianThu() {
        return thoiGianThu;
    }

    public void setThoiGianThu(String thoiGianThu) {
        this.thoiGianThu = thoiGianThu;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
