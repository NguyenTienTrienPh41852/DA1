package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.HoatDong;

import java.util.ArrayList;
import java.util.List;

public class HoatDongDAO {
    private SQLiteDatabase db;
    DbHelper dbHelper;

    public HoatDongDAO (Context context) {
        dbHelper = new DbHelper(context);

        db = dbHelper.getWritableDatabase();
    }
    public long themHoatDong (HoatDong hoatDong) {
        db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("TENHD", hoatDong.getTenHoatDong());
        values.put("MOTA", hoatDong.getMoTa());
        values.put("TGBATDAU", hoatDong.getThoiGianBatDau());
        values.put("TGKETTHUC", hoatDong.getThoiGianKetThuc());
        values.put("TTHOATDONG", hoatDong.getTrangThaiHoatDong());
        values.put("NGAY", hoatDong.getNgay());
        long result = db.insert("HOATDONG", null, values);
        db.close();
        return result;
    }

    public long capNhatHoatDong (HoatDong hoatDong) {
        db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("TENHD", hoatDong.getTenHoatDong());
        values.put("MOTA", hoatDong.getMoTa());
        values.put("TGBATDAU", hoatDong.getThoiGianBatDau());
        values.put("TGKETTHUC", hoatDong.getThoiGianKetThuc());
        values.put("TTHOATDONG", hoatDong.getTrangThaiHoatDong());
        values.put("NGAY", hoatDong.getNgay());
        long result = db.update("HOATDONG", values, "MAHD = ?", new String[]{String.valueOf(hoatDong.getMaHoatDong())});
        db.close();
        return result;
    }

    public int xoaHoatDong (int maHoatDong) {
        db = dbHelper.getReadableDatabase();
        int result =  db.delete("HOATDONG", "MAHD = ?", new String[]{String.valueOf(maHoatDong)});
        db.close();
        return result;
    }

    public boolean UpdateStatus(Integer id, boolean check){
        db = dbHelper.getReadableDatabase();
        int statusValue = check ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put("TTHOATDONG",statusValue);
        String[] dieukien = new  String[]{String.valueOf(id)};
        long row = db.update("HOATDONG",values,"MAHD=?",dieukien);
        db.close();
        return row != -1;

    }

    public List<HoatDong> layDanhSachHoatDongTheoNgay (String ngay){
        List<HoatDong> list = new ArrayList<>();

        db = dbHelper.getReadableDatabase();

        Cursor c = null;
        try {
            c = db.rawQuery("SELECT * FROM HOATDONG WHERE NGAY = ?", new String[]{ngay});
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    HoatDong hd = new HoatDong(
                            c.getInt(0), c.getString(2), c.getString(3), c.getString(4),
                            c.getString(5), c.getInt(6), c.getString(7)
                    );
                    list.add(hd);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null && db.isOpen()){

            }
        }

        return list;
    }
    public List<HoatDong> layDanhSachHoatDong (){
        db = dbHelper.getReadableDatabase();
        List<HoatDong> list = new ArrayList<>();
        Cursor c = null;
        try {
            c = db.rawQuery("SELECT * FROM HOATDONG", null);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                do {
                    HoatDong hd = new HoatDong(
                            c.getInt(0), c.getString(2), c.getString(3), c.getString(4),
                            c.getString(5), c.getInt(6), c.getString(7)
                    );
                    list.add(hd);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }

        return list;
    }
}
