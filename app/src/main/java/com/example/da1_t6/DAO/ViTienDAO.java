package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.ViTien;

import java.util.ArrayList;
import java.util.List;

public class ViTienDAO {
    private SQLiteDatabase db;

    public ViTienDAO (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long themVi (ViTien obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENVI",obj.getTenVi());
        contentValues.put("SODUBD",obj.getSoDuBanDau());
        contentValues.put("SODUHT",obj.getSoDuBanDau());
        return db.insert("VITIEN",null, contentValues);
    }

    public long capNhatVi (ViTien obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENVI",obj.getTenVi());
        contentValues.put("SODUBD",obj.getSoDuBanDau());
        contentValues.put("SODUHT",obj.getSoDuHienTai());
        return db.update("VITIEN",contentValues,"MAVI = ?",new String[]{String.valueOf(obj.getMaVi())});
    }

    public int xoaVi (String id) {
        return db.delete("VITIEN","MAVI = ?",new String[]{String.valueOf(id)});
    }
    public List<ViTien> layDanhSachViTien (){
        List<ViTien> list = new ArrayList<>();
        Cursor c =db.rawQuery("SELECT * FROM VITIEN", null);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                ViTien ct = new ViTien(
                        c.getInt(0),c.getString(2),c.getDouble(3),c.getDouble(4)
                );
                list.add(ct);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }
    public double tongSoDuHienTai() {
        double tongSoDuHienTai = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(SODUHT) FROM VITIEN", null);

        if (cursor != null && cursor.moveToFirst()) {
            tongSoDuHienTai = cursor.getDouble(0);
            cursor.close();
        }

        return tongSoDuHienTai;
    }
}
