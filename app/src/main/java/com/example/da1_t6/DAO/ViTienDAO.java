package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.ViTien;

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
        return db.insert("VITIEN",null, contentValues);
    }

    public long capNhatVi (ViTien obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TENVI",obj.getTenVi());
        contentValues.put("SODUBD",obj.getSoDuBanDau());
        return db.update("VITIEN",contentValues,"MAVI = ?",new String[]{String.valueOf(obj.getMaVi())});
    }

    public int xoaVi (String id) {
        return db.delete("VITIEN","MAVI = ?",new String[]{String.valueOf(id)});
    }
}
