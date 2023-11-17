package com.example.da1_t6.Model;

public class ChiTieu {
    private int maCT;
    private int maVi;
    private int maKC;
    private int maDM;
    private String tenVi;
    private String tenKC;
    private double soTienChi;
    private String thoiGianChi;
    private String ghiChu;

    public ChiTieu() {
    }

    public ChiTieu(int maCT, int maVi, int maKC, int maDM, String tenVi, String tenKC, double soTienChi, String thoiGianChi, String ghiChu) {
        this.maCT = maCT;
        this.maVi = maVi;
        this.maKC = maKC;
        this.maDM = maDM;
        this.tenVi = tenVi;
        this.tenKC = tenKC;
        this.soTienChi = soTienChi;
        this.thoiGianChi = thoiGianChi;
        this.ghiChu = ghiChu;
    }

    public ChiTieu(int maCT, int maVi, int maKC, String tenVi, String tenKC, double soTienChi, String thoiGianChi, String ghiChu) {
        this.maCT = maCT;
        this.maVi = maVi;
        this.maKC = maKC;
        this.tenVi = tenVi;
        this.tenKC = tenKC;
        this.soTienChi = soTienChi;
        this.thoiGianChi = thoiGianChi;
        this.ghiChu = ghiChu;
    }

    public int getMaDM() {
        return maDM;
    }

    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }

    public int getMaCT() {
        return maCT;
    }

    public void setMaCT(int maCT) {
        this.maCT = maCT;
    }

    public int getMaVi() {
        return maVi;
    }

    public void setMaVi(int maVi) {
        this.maVi = maVi;
    }

    public int getMaKC() {
        return maKC;
    }

    public void setMaKC(int maKC) {
        this.maKC = maKC;
    }

    public String getTenVi() {
        return tenVi;
    }

    public void setTenVi(String tenVi) {
        this.tenVi = tenVi;
    }

    public String getTenKC() {
        return tenKC;
    }

    public void setTenKC(String tenKC) {
        this.tenKC = tenKC;
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
