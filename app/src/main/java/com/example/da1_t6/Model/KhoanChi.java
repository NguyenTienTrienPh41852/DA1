package com.example.da1_t6.Model;

public class KhoanChi {
    private int maKC;
    private int maDanhMuc;
    private String tenKC;
    private String tenDanhMuc;

    public KhoanChi() {
    }

    public KhoanChi(int maKC, int maDanhMuc, String tenKC, String tenDanhMuc) {
        this.maKC = maKC;
        this.maDanhMuc = maDanhMuc;
        this.tenKC = tenKC;
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getMaKC() {
        return maKC;
    }

    public void setMaKC(int maKC) {
        this.maKC = maKC;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenKC() {
        return tenKC;
    }

    public void setTenKC(String tenKC) {
        this.tenKC = tenKC;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
