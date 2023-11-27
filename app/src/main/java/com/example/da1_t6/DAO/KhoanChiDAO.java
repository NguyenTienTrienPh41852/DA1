package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.Icon;
import com.example.da1_t6.Model.KhoanChi;

import java.util.ArrayList;
import java.util.List;

public class KhoanChiDAO {
    private SQLiteDatabase db;

    public KhoanChiDAO (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long themKhoanChi (KhoanChi khoanChi) {
        ContentValues values = new ContentValues();
        values.put("MADANHMUC", khoanChi.getMaDanhMuc());
        values.put("MAICON", khoanChi.getMaIcon());
        values.put("TENKC", khoanChi.getTenKC());
        return db.insert("KHOANCHI", null, values);
    }

    public long capNhatKhoanChi (KhoanChi khoanChi) {
        ContentValues values = new ContentValues();
        values.put("MADANHMUC", khoanChi.getMaDanhMuc());
        values.put("MAICON", khoanChi.getMaIcon());
        values.put("TENKC", khoanChi.getTenKC());
        return db.update("KHOANCHI", values, "MAKC = ?", new String[]{String.valueOf(khoanChi.getMaKC())});
    }

    public int xoaKhoanChi (int maKhoanChi) {
        return db.delete("KHOANCHI", "MAKC = ?", new String[]{String.valueOf(maKhoanChi)});
    }

    public List<KhoanChi> layDanhSachKhoanChi (){
        List<KhoanChi> list = new ArrayList<>();
        Cursor c =db.rawQuery("SELECT * FROM KHOANCHI INNER JOIN DANHMUC ON KHOANCHI.MADANHMUC = DANHMUC.MADANHMUC JOIN ICON ON KHOANCHI.MAICON = ICON.MAICON", null);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                KhoanChi kc = new KhoanChi(c.getInt(0), c.getInt(4), c.getInt(6), c.getString(3), c.getString(5));
                list.add(kc);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }
    public KhoanChi layIcon(int maKC){
        KhoanChi list = new KhoanChi();
        Cursor c = db.rawQuery("SELECT * FROM KHOANCHI WHERE MAKC = ?",new String[]{maKC+""});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            list.setMaKC(c.getInt(0));
            list.setMaIcon(c.getInt(2));
        }
        return list;
    }
    public List<KhoanChi> layDanhSachKhoanChiTheoDM (String maDM){
        List<KhoanChi> list = new ArrayList<>();
        String[] ma = new String[]{maDM};
        Cursor c =db.rawQuery("SELECT * FROM KHOANCHI INNER JOIN DANHMUC ON KHOANCHI.MADANHMUC = DANHMUC.MADANHMUC WHERE KHOANCHI.MADANHMUC = ?", ma);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                KhoanChi kc = new KhoanChi(c.getInt(0), c.getInt(1), c.getInt(2),c.getString(3), c.getString(5));
                list.add(kc);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }
}
