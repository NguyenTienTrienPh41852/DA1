package com.example.da1_t6.Model;

public class KhoanChi {
    private int maKC;
    private int maDanhMuc;

    private int maIcon;
    private String tenKC;
    private String tenDanhMuc;

    public KhoanChi() {
    }

    public KhoanChi(int maKC, int maIcon) {
        this.maKC = maKC;
        this.maIcon = maIcon;
    }

    public KhoanChi(int maKC, int maDanhMuc, int maIcon, String tenKC, String tenDanhMuc) {
        this.maKC = maKC;
        this.maDanhMuc = maDanhMuc;
        this.maIcon = maIcon;
        this.tenKC = tenKC;
        this.tenDanhMuc = tenDanhMuc;
    }

    public KhoanChi(int maKC, int maDanhMuc, String tenKC, String tenDanhMuc) {
        this.maKC = maKC;
        this.maDanhMuc = maDanhMuc;
        this.tenKC = tenKC;
        this.tenDanhMuc = tenDanhMuc;
    }

    public int getMaIcon() {
        return maIcon;
    }

    public void setMaIcon(int maIcon) {
        this.maIcon = maIcon;
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
