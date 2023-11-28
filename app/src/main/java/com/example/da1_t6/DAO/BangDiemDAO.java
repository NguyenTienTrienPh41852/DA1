package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.BangDiem;
import com.example.da1_t6.Model.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class BangDiemDAO {
    private SQLiteDatabase db;
    DbHelper dbHelper;

    public BangDiemDAO(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long AddDiem(BangDiem bangDiem, int IDmonHoc){
        ContentValues values = new ContentValues();
        values.put("TENDD",bangDiem.getTenDauDiem());
        values.put("IDMONHOC",IDmonHoc);
        values.put("TRONGSO",bangDiem.getTrongSo());
        values.put("DIEM",bangDiem.getDiem());

        return db.insert("BANGDIEM",null,values);
    }
    public int UpdateBangDiem(BangDiem bangDiem){
        ContentValues values = new ContentValues();
        values.put("TENDD",bangDiem.getTenDauDiem());
        values.put("IDMONHOC",bangDiem.getIDmonHoc());
        values.put("TRONGSO",bangDiem.getTrongSo());
        values.put("DIEM",bangDiem.getDiem());

        String[] Args = new String[]{String.valueOf(bangDiem.getMaBangDiem())};
        return db.update("BANGDIEM",values,"MABANGDIEM=?",Args);
    }

    public int DeleteBangDiemTheoID(int IDmonHoc){
        String[] Args = new String[]{String.valueOf(IDmonHoc)};
        return db.delete("BANGDIEM","IDMONHOC=?",Args);
    }
    public int DeleteBangDiem(int maBangDiem){
        String[] Args = new String[]{String.valueOf(maBangDiem)};
        return db.delete("BANGDIEM","MABANGDIEM=?",Args);
    }

    public List<BangDiem> layDanhSachBangDiemTheoMonHoc(int idMonHoc) {
        List<BangDiem> listB = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT BANGDIEM.*, MONHOC.TENMON FROM BANGDIEM INNER JOIN MONHOC ON BANGDIEM.IDMONHOC = MONHOC.IDMONHOC WHERE BANGDIEM.IDMONHOC = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idMonHoc)});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                BangDiem bangDiem = new BangDiem(
                        cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getDouble(3), cursor.getDouble(4),cursor.getString(5)
                );
                listB.add(bangDiem);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return listB;
    }
    public int layIDMonHocTuBangDiem(int maBangDiem) {

        int idMonHoc = -1;

        Cursor cursor = null;
        try {
            String query = "SELECT IDMONHOC FROM BANGDIEM WHERE MABANGDIEM = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(maBangDiem)});

            if (cursor.moveToFirst()) {
                idMonHoc = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        }

        return idMonHoc;
    }
    public double getDiemTrungBinh(int idMonHoc){
        String sql = "SELECT AVG((TRONGSO + DIEM)/2) FROM BANGDIEM WHERE IDMONHOC = ?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(idMonHoc)});

        double diemTB = 0;
        if (c.moveToFirst()){
            diemTB = c.getDouble(0);
        }
        c.close();

        return diemTB;
    }

}
