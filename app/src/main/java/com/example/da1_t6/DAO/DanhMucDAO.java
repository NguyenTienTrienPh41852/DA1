package com.example.da1_t6.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.DanhMuc;

import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {
    private SQLiteDatabase db;

    public DanhMucDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<DanhMuc> layDanhSachDanhMuc() {
        List<DanhMuc> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM DANHMUC", null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                DanhMuc danhMuc = new DanhMuc(c.getInt(0), c.getString(2));
                list.add(danhMuc);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }
}
