package com.example.da1_t6.Model;

public class DanhMuc {
    private int maDanhMuc;
    private String tenDanhmuc;

    public DanhMuc() {
    }

    public DanhMuc(int maDanhMuc, String tenDanhmuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhmuc = tenDanhmuc;
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhmuc() {
        return tenDanhmuc;
    }

    public void setTenDanhmuc(String tenDanhmuc) {
        this.tenDanhmuc = tenDanhmuc;
    }
}
