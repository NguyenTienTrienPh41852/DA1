package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.ThuNhap;

import java.util.ArrayList;
import java.util.List;

public class ThuNhapDAO {
    private SQLiteDatabase db;

    public ThuNhapDAO (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public boolean themThuNhap (ThuNhap thuNhap) {
        ContentValues values = new ContentValues();
        values.put("MAVI", thuNhap.getMaVi());
        values.put("TENKT", thuNhap.getTenKhoanThu());
        values.put("SOTIEN", thuNhap.getSoTienThu());
        values.put("THOIGIAN", thuNhap.getThoiGianThu());
        values.put("GHICHU", thuNhap.getGhiChu());
        long check = db.insert("THUNHAP", null, values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean capNhatThuNhap (ThuNhap thuNhap) {
        ContentValues values = new ContentValues();
        values.put("MATN", thuNhap.getMaKhoanThu());
        values.put("MAVI", thuNhap.getMaVi());
        values.put("TENKT", thuNhap.getTenKhoanThu());
        values.put("SOTIEN", thuNhap.getSoTienThu());
        values.put("THOIGIAN", thuNhap.getThoiGianThu());
        values.put("GHICHU", thuNhap.getGhiChu());
        long check = db.update("THUNHAP", values, "MATN = ?", new String[]{String.valueOf(thuNhap.getMaKhoanThu())});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public int xoaThuNhap (int maThuNhap) {
        return db.delete("THUNHAP", "MATN = ?", new String[]{String.valueOf(maThuNhap)});
    }

    public List<ThuNhap> layDanhSachThuNhap (){
        List<ThuNhap> list = new ArrayList<>();
        Cursor c =db.rawQuery("SELECT * FROM THUNHAP INNER JOIN VITIEN ON THUNHAP.MAVI = VITIEN.MAVI", null);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                ThuNhap tn = new ThuNhap(
                    c.getInt(0), c.getInt(1), c.getString(8),c.getString(2), c.getDouble(3), c.getString(4), c.getString(5)
                );
                list.add(tn);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }
}
