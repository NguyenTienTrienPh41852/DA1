package com.example.da1_t6.Model;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;

public class NguoiDung {
    private String maNguoiDung;
    private String matKhau;
    private String hoTen;
    private byte[] anhDaiDien;

    public NguoiDung() {
    }

    public NguoiDung(String maNguoiDung, String matKhau, String hoTen, byte[] anhDaiDien) {
        this.maNguoiDung = maNguoiDung;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.anhDaiDien = anhDaiDien;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public byte[] getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(byte[] anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }
    public Uri hienthi(Context context) {
        byte[] imageData = getAnhDaiDien();// Mảng byte chứa dữ liệu hình ảnh
        String tempFileName = "temp_image.jpg";
        Uri uri;
        // Tạo đường dẫn tới tập tin ảnh tạm
        File tempFile = new File(context.getCacheDir(), tempFileName);
        // Ghi dữ liệu blob vào tập tin ảnh tạm
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(imageData);
            fileOutputStream.close();
            uri = Uri.fromFile(tempFile);
            return uri;
        } catch (Exception e) {
            return null;
        }
    }
}
