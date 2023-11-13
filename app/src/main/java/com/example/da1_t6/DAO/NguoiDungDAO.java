package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.NguoiDung;


public class NguoiDungDAO {
    private SQLiteDatabase db;

    public NguoiDungDAO (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long themNguoiDung (NguoiDung obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MAND",obj.getMaNguoiDung());
        contentValues.put("MATKHAU", obj.getMatKhau());
        contentValues.put("TENND",obj.getHoTen());
        contentValues.put("ANHDAIDIEN",obj.getAnhDaiDien());
        return db.insert("NGUOIDUNG",null,contentValues);
    }

    public long capNhatNguoiDung (NguoiDung obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MAND",obj.getMaNguoiDung());
        contentValues.put("MATKHAU", obj.getMatKhau());
        contentValues.put("TENND",obj.getHoTen());
        contentValues.put("ANHDAIDIEN",obj.getAnhDaiDien());
        return db.update("NGUOIDUNG",contentValues,"MAND = ?",new String[]{String.valueOf(obj.getMaNguoiDung())});
    }
}
