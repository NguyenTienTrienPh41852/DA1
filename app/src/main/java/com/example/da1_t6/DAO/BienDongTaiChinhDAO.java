package com.example.da1_t6.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;

public class BienDongTaiChinhDAO {
    private SQLiteDatabase db;

    public BienDongTaiChinhDAO (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

}
