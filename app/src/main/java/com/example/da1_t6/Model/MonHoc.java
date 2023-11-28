package com.example.da1_t6.Model;

import java.io.Serializable;
import java.util.List;

public class MonHoc {
    private int IDMonHoc;
    private String maMonHoc;
    private String tenMonHoc;
    private String tenLop;
    private double diemTrungBinh;
    private int trangThai;

    public MonHoc() {
    }

    public MonHoc(int IDMonHoc, String maMonHoc, String tenMonHoc, String tenLop, double diemTrungBinh, int trangThai) {
        this.IDMonHoc = IDMonHoc;
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.tenLop = tenLop;
        this.diemTrungBinh = diemTrungBinh;
        this.trangThai = trangThai;
    }

    public MonHoc(String maMonHoc, String tenMonHoc, String tenLop) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.tenLop = tenLop;
    }

    public int getIDMonHoc() {
        return IDMonHoc;
    }

    public void setIDMonHoc(int IDMonHoc) {
        this.IDMonHoc = IDMonHoc;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
