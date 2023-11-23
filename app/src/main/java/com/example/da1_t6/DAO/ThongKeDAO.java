package com.example.da1_t6.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;

import java.text.SimpleDateFormat;
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedDate);

            // Truy vấn để lấy tổng chi tiêu của ngày được chọn từ bảng CHITIEU
            String query = "SELECT SUM(SOTIENCHI) AS TongChiTieu FROM CHITIEU WHERE THOIGIANCHI = ?";
            Cursor cursor = db.rawQuery(query, new String[]{formattedDate});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị tổng chi tiêu từ cột TongChiTieu
                tongChiTieu = cursor.getFloat(cursor.getColumnIndexOrThrow("TongChiTieu"));
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongChiTieu;
    }

    public float getTongChiTieuTheoTuan(Date startDate) {
        float tongChiTieu = 0.0f;

        try {
            // Chuyển đổi startDate về định dạng ngày/tháng/năm để so sánh với cột THOIGIANCHI trong database
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            // Lấy ngày đầu tiên của tuần từ ngày được chọn
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Chuyển về thứ 2 của tuần
            Date mondayDate = calendar.getTime();
            String formattedStartDate = dateFormat.format(mondayDate);

            // Lấy ngày cuối cùng của tuần từ ngày được chọn
            calendar.add(Calendar.DATE, 6); // Thêm 6 ngày để đến Chủ Nhật
            Date sundayDate = calendar.getTime();
            String formattedEndDate = dateFormat.format(sundayDate);

            // Truy vấn để lấy tổng chi tiêu của tuần được chọn từ bảng CHITIEU
            String query = "SELECT SUM(SOTIENCHI) AS TongChiTieu FROM CHITIEU WHERE THOIGIANCHI BETWEEN ? AND ?";
            Cursor cursor = db.rawQuery(query, new String[]{formattedStartDate, formattedEndDate});

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị tổng chi tiêu từ cột TongChiTieu
                tongChiTieu = cursor.getFloat(cursor.getColumnIndexOrThrow("TongChiTieu"));
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongChiTieu;
    }

    public float getTongChiTieuCua6ThangGanNhat() {
        float tongChiTieu = 0.0f;

        try {
            // Lấy ngày hiện tại
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            // Tạo danh sách 6 tháng gần nhất tính từ tháng hiện tại
            List<Date> recentMonths = new ArrayList<>();

            // Lấy ngày đầu tiên của tháng hiện tại
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            for (int i = 0; i < 6; i++) {
                recentMonths.add(calendar.getTime());
                calendar.add(Calendar.MONTH, -1); // Lùi về tháng trước đó
            }

            // Lấy tổng chi tiêu của 6 tháng gần nhất
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            String[] formattedMonths = new String[6];

            for (int i = 0; i < recentMonths.size(); i++) {
                formattedMonths[i] = dateFormat.format(recentMonths.get(i));
            }

            // Truy vấn để lấy tổng chi tiêu của 6 tháng gần nhất từ bảng CHITIEU
            String query = "SELECT SUM(SOTIENCHI) AS TongChiTieu FROM CHITIEU WHERE strftime('%m/%Y', THOIGIANCHI) IN (?, ?, ?, ?, ?, ?)";
            Cursor cursor = db.rawQuery(query, formattedMonths);

            if (cursor != null && cursor.moveToFirst()) {
                // Lấy giá trị tổng chi tiêu từ cột TongChiTieu
                tongChiTieu = cursor.getFloat(cursor.getColumnIndexOrThrow("TongChiTieu"));
            }

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongChiTieu;
    }
}
