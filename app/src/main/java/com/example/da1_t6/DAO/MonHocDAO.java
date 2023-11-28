package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {
    private SQLiteDatabase db;
    DbHelper dbHelper;

    public MonHocDAO(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long AddMonHoc(MonHoc monHoc){
        ContentValues values = new ContentValues();
        values.put("MAMON",monHoc.getMaMonHoc());
        values.put("TENMON",monHoc.getTenMonHoc());
        values.put("TENLOP",monHoc.getTenLop());
        values.put("DIEMTB",monHoc.getDiemTrungBinh());
        values.put("TRANGTHAI",monHoc.getTrangThai());
        return db.insert("MONHOC",null,values);
    }
    public int UpdateMonHoc(MonHoc monHoc){
        ContentValues values = new ContentValues();
        values.put("MAMON",monHoc.getMaMonHoc());
        values.put("TENMON",monHoc.getTenMonHoc());
        values.put("TENLOP",monHoc.getTenLop());
        values.put("DIEMTB",monHoc.getDiemTrungBinh());
        values.put("TRANGTHAI",monHoc.getTrangThai());

        String[] Args = new String[]{String.valueOf(monHoc.getIDMonHoc())};
        return db.update("MONHOC",values,"IDMONHOC=?",Args);
    }
    public int DeleteMonHoc(int IDmonHoc){
        String[] Args = new String[]{String.valueOf(IDmonHoc)};
        return db.delete("MONHOC","IDMONHOC=?",Args);
    }

    public List<MonHoc> layDanhSachMonHoc(){
        List<MonHoc> listM = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM MONHOC",null);
        if (c != null && c.getCount()>0){
            c.moveToFirst();
            do {
                MonHoc monHoc = new MonHoc(
                  c.getInt(0) ,c.getString(1),c.getString(2),c.getString(3),c.getDouble(4),c.getInt(5)
                );
                listM.add(monHoc);
            }while (c.moveToNext());
            c.close();
        }

        return listM;
    }
}
