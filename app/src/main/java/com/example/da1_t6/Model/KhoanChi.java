package com.example.da1_t6.Model;

public class KhoanChi {
    private int maKhoanChi;
    private int maDanhMuc;
    private int maVi;
    private String tenKhoanChi;
    private double soTienChi;
    private String thoiGianChi;
    private String ghiChu;

    public KhoanChi() {
    }

    public KhoanChi(int maKhoanChi, int maDanhMuc, int maVi, String tenKhoanChi, double soTienChi, String thoiGianChi, String ghiChu) {
        this.maKhoanChi = maKhoanChi;
        this.maDanhMuc = maDanhMuc;
        this.maVi = maVi;
        this.tenKhoanChi = tenKhoanChi;
        this.soTienChi = soTienChi;
        this.thoiGianChi = thoiGianChi;
        this.ghiChu = ghiChu;
    }

    public int getMaKhoanChi() {
        return maKhoanChi;
    }

    public void setMaKhoanChi(int maKhoanChi) {
        this.maKhoanChi = maKhoanChi;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public int getMaVi() {
        return maVi;
    }

    public void setMaVi(int maVi) {
        this.maVi = maVi;
    }

    public String getTenKhoanChi() {
        return tenKhoanChi;
    }

    public void setTenKhoanChi(String tenKhoanChi) {
        this.tenKhoanChi = tenKhoanChi;
    }

    public double getSoTienChi() {
        return soTienChi;
    }

    public void setSoTienChi(double soTienChi) {
        this.soTienChi = soTienChi;
    }

    public String getThoiGianChi() {
        return thoiGianChi;
    }

    public void setThoiGianChi(String thoiGianChi) {
        this.thoiGianChi = thoiGianChi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
