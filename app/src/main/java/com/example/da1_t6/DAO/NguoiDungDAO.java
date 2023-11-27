package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.NguoiDung;


public class NguoiDungDAO {
    private SQLiteDatabase db;


    public NguoiDungDAO (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean themNguoiDung (NguoiDung obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MAND",obj.getMaNguoiDung());
        contentValues.put("MATKHAU", obj.getMatKhau());
        contentValues.put("TENND",obj.getHoTen());
        contentValues.put("ANHDAIDIEN",obj.getAnhDaiDien());
        long check = db.insert("NGUOIDUNG",null,contentValues);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean capNhatNguoiDung (NguoiDung obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("MAND",obj.getMaNguoiDung());
        contentValues.put("MATKHAU", obj.getMatKhau());
        contentValues.put("TENND",obj.getHoTen());
        contentValues.put("ANHDAIDIEN",obj.getAnhDaiDien());
        long check =  db.update("NGUOIDUNG",contentValues,"MAND = ?",new String[]{String.valueOf(obj.getMaNguoiDung())});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkLogin(String MaND,String MK){
        Cursor cursor = db.rawQuery("select * from NGUOIDUNG where MAND = ? and MATKHAU = ?",new String[]{MaND, MK});
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

}
