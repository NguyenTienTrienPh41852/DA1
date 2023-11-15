package com.example.da1_t6.Model;

import java.util.List;

public class All_a {
    private List<ChiTieu> list_an;
    private List<ThuNhap> list_thu;

    public All_a() {
    }

    public All_a(List<ChiTieu> list_an, List<ThuNhap> list_thu) {
        this.list_an = list_an;
        this.list_thu = list_thu;
    }

    public List<ChiTieu> getList_an() {
        return list_an;
    }

    public void setList_an(List<ChiTieu> list_an) {
        this.list_an = list_an;
    }

    public List<ThuNhap> getList_thu() {
        return list_thu;
    }

    public void setList_thu(List<ThuNhap> list_thu) {
        this.list_thu = list_thu;
    }
}
