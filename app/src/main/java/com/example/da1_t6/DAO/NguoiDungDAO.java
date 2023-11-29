package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.NguoiDung;
import com.example.da1_t6.Model.ViTien;

import java.util.ArrayList;
import java.util.List;


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
        if(check < 0){
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
        Cursor cursor = db.rawQuery("SELECT * FROM NGUOIDUNG WHERE MAND = ? AND MATKHAU = ?",new String[]{MaND, MK});
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }
    public List<NguoiDung> layDanhSachNguoiDung (){
        List<NguoiDung> list = new ArrayList<>();
        Cursor c =db.rawQuery("SELECT * FROM NGUOIDUNG", null);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                NguoiDung nguoiDung = new NguoiDung(
                        c.getString(0),c.getString(1),c.getString(2),c.getBlob(3)
                );
                list.add(nguoiDung);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

}
