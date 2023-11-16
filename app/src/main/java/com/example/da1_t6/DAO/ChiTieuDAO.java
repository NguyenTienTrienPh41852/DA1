package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.ChiTieu;

import java.util.ArrayList;
import java.util.List;

public class ChiTieuDAO {
    private SQLiteDatabase db;

    public ChiTieuDAO (Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public boolean themChiTieu (ChiTieu chiTieu) {
        ContentValues values = new ContentValues();
        values.put("MAVI", chiTieu.getMaVi());
        values.put("MAKC", chiTieu.getMaKC());
        values.put("SOTIENCHI", chiTieu.getSoTienChi());
        values.put("THOIGIANCHI", chiTieu.getThoiGianChi());
        values.put("GHICHU", chiTieu.getGhiChu());
        long check = db.insert("CHITIEU",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public long capNhatChiTieu (ChiTieu chiTieu) {
        ContentValues values = new ContentValues();
        values.put("MACT", chiTieu.getMaCT());
        values.put("MAVI", chiTieu.getMaVi());
        values.put("MAKC", chiTieu.getMaKC());
        values.put("SOTIENCHI", chiTieu.getSoTienChi());
        values.put("THOIGIANCHI", chiTieu.getThoiGianChi());
        values.put("GHICHU", chiTieu.getGhiChu());
        return db.update("CHITIEU", values, "MACT = ?", new String[]{String.valueOf(chiTieu.getMaCT())});
    }

    public int xoaChiTieu (int maChiTieu) {
        return db.delete("CHITIEU", "MACT = ?", new String[]{String.valueOf(maChiTieu)});
    }

    public List<ChiTieu> layDanhSachChiTieu (){
        List<ChiTieu> list = new ArrayList<>();
        Cursor c =db.rawQuery("SELECT * FROM CHITIEU INNER JOIN VITIEN ON CHITIEU.MAVI = VITIEN.MAVI INNER JOIN KHOANCHI ON CHITIEU.MAKC = KHOANCHI.MAKC", null);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                ChiTieu ct = new ChiTieu(
                    c.getInt(0), c.getInt(1), c.getInt(2), c.getString(8), c.getString(13), c.getDouble(3), c.getString(4), c.getString(5)
                );
                list.add(ct);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }
}
