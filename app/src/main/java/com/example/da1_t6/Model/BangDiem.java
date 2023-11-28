package com.example.da1_t6.Model;

public class BangDiem {
    private int maBangDiem;
    private int IDmonHoc;
    private String tenDauDiem;
    private double trongSo;
    private double diem;
    private String tenMon;

    public BangDiem() {
    }

    public BangDiem(int maBangDiem, int IDmonHoc, String tenDauDiem, double trongSo, double diem, String tenMon) {
        this.maBangDiem = maBangDiem;
        this.IDmonHoc = IDmonHoc;
        this.tenDauDiem = tenDauDiem;
        this.trongSo = trongSo;
        this.diem = diem;
        this.tenMon = tenMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public BangDiem(String tenDauDiem, double trongSo, double diem) {
        this.tenDauDiem = tenDauDiem;
        this.trongSo = trongSo;
        this.diem = diem;
    }

    public BangDiem(int IDmonHoc, String tenDauDiem, double trongSo, double diem) {
        this.IDmonHoc = IDmonHoc;
        this.tenDauDiem = tenDauDiem;
        this.trongSo = trongSo;
        this.diem = diem;

    }

    public int getMaBangDiem() {
        return maBangDiem;
    }

    public void setMaBangDiem(int maBangDiem) {
        this.maBangDiem = maBangDiem;
    }

    public int getIDmonHoc() {
        return IDmonHoc;
    }

    public void setIDmonHoc(int IDmonHoc) {
        this.IDmonHoc = IDmonHoc;
    }

    public String getTenDauDiem() {
        return tenDauDiem;
    }

    public void setTenDauDiem(String tenDauDiem) {
        this.tenDauDiem = tenDauDiem;
    }

    public double getTrongSo() {
        return trongSo;
    }

    public void setTrongSo(double trongSo) {
        this.trongSo = trongSo;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }
}
