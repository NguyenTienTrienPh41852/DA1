package com.example.da1_t6.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThongKeDAO {
    private SQLiteDatabase db;
    DbHelper dbHelper;

    public ThongKeDAO (Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public float getTongChiTieuTheoNgay(Date selectedDate) {
        float tongChiTieu = 0.0f;

        try {
            // Chuyển đổi selectedDate về định dạng ngày/tháng/năm để so sánh với cột THOIGIANCHI trong database
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate);

            // Truy vấn để lấy tổng chi tiêu của ngày được chọn từ bảng CHITIEU
            String query = "SELECT SUM(SOTIENCHI) AS TongChiTieu FROM CHITIEU WHERE THOIGIANCHI = ?";
            Cursor cursor = db.rawQuery(query, new String[]{formattedDate});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị tổng chi tiêu từ cột TongChiTieu
                tongChiTieu = cursor.getFloat(0);
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongChiTieu;
    }

    public float tinhTongChiTieu (String monday, String sunday) {
        float tongChiTieu = 0.0f;

        try {
            // Truy vấn để lấy tổng chi tiêu từ ngày thứ 2 đến ngày chủ nhật trong bảng CHITIEU
            String query = "SELECT SUM(SOTIENCHI) AS TongChiTieu FROM CHITIEU WHERE THOIGIANCHI BETWEEN ? AND ?";
            Cursor cursor = db.rawQuery(query, new String[]{monday, sunday});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị tổng chi tiêu từ cột TongChiTieu
                tongChiTieu = cursor.getFloat(0);
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongChiTieu;
    }

    public float getTongThuNhapTheoNgay(Date selectedDate) {
        float tongThuNhap = 0.0f;

        try {
            // Chuyển đổi selectedDate về định dạng ngày/tháng/năm để so sánh với cột THOIGIANCHI trong database
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate);

            // Truy vấn để lấy tổng chi tiêu của ngày được chọn từ bảng CHITIEU
            String query = "SELECT SUM(SOTIEN) AS TongThuNhap FROM THUNHAP WHERE THOIGIAN = ?";
            Cursor cursor = db.rawQuery(query, new String[]{formattedDate});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị tổng chi tiêu từ cột TongThuNhap
                tongThuNhap = cursor.getFloat(0);
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongThuNhap;
    }

    public float tinhTongThuNhap (String monday, String sunday) {
        float tongThuNhap = 0.0f;

        try {
            // Truy vấn để lấy tổng chi tiêu từ ngày thứ 2 đến ngày chủ nhật trong bảng CHITIEU
            String query = "SELECT SUM(SOTIEN) AS TongThuNhap FROM THUNHAP WHERE THOIGIAN BETWEEN ? AND ?";
            Cursor cursor = db.rawQuery(query, new String[]{monday, sunday});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị tổng chi tiêu từ cột TongThuNhap
                tongThuNhap = cursor.getFloat(0);
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongThuNhap;
    }

    public String chuyenDoiDMY(String ngayXuatStr) {
        String[] ngayThangNam = ngayXuatStr.split("-");
        String nam = ngayThangNam[0];
        String thang = ngayThangNam[1];
        String ngay = ngayThangNam[2];
        return ngay + "-" + thang + "-" + nam;
    }
}
