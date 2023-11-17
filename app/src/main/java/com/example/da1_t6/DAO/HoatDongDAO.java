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
        ContentValues values = new ContentValues();

        values.put("TENHD", hoatDong.getTenHoatDong());
        values.put("MOTA", hoatDong.getMoTa());
        values.put("TGBATDAU", hoatDong.getThoiGianBatDau());
        values.put("TGKETTHUC", hoatDong.getThoiGianKetThuc());
        values.put("TTHOATDONG", hoatDong.getTrangThaiHoatDong());
        values.put("NGAY", hoatDong.getNgay());
        return db.insert("HOATDONG", null, values);
    }

    public long capNhatHoatDong (HoatDong hoatDong) {
        ContentValues values = new ContentValues();

        values.put("TENHD", hoatDong.getTenHoatDong());
        values.put("MOTA", hoatDong.getMoTa());
        values.put("TGBATDAU", hoatDong.getThoiGianBatDau());
        values.put("TGKETTHUC", hoatDong.getThoiGianKetThuc());
        values.put("TTHOATDONG", hoatDong.getTrangThaiHoatDong());
        values.put("NGAY", hoatDong.getNgay());
        return db.update("HOATDONG", values, "MAHD = ?", new String[]{String.valueOf(hoatDong.getMaHoatDong())});
    }

    public int xoaHoatDong (int maHoatDong) {
        return db.delete("HOATDONG", "MAHD = ?", new String[]{String.valueOf(maHoatDong)});
    }

    public boolean UpdateStatus(Integer id, boolean check){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int statusValue = check ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put("TTHOATDONG",statusValue);
        String[] dieukien = new  String[]{String.valueOf(id)};
        long row = database.update("HOATDONG",values,"MAHD=?",dieukien);
        return row != -1;

    }

    public List<HoatDong> layDanhSachHoatDong (){
        List<HoatDong> list = new ArrayList<>();
        Cursor c =db.rawQuery("SELECT * FROM HOATDONG", null);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                HoatDong hd = new HoatDong(
                        c.getInt(0), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6), c.getString(7)
                );
                list.add(hd);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }
}
