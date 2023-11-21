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
        values.put("MADM",chiTieu.getMaDM());
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

    public boolean capNhatChiTieu (ChiTieu chiTieu) {
        ContentValues values = new ContentValues();
        values.put("MAVI", chiTieu.getMaVi());
        values.put("MADM",chiTieu.getMaDM());
        values.put("MAKC", chiTieu.getMaKC());
        values.put("SOTIENCHI", chiTieu.getSoTienChi());
        values.put("THOIGIANCHI", chiTieu.getThoiGianChi());
        values.put("GHICHU", chiTieu.getGhiChu());
        long check = db.update("CHITIEU", values, "MACT = ?", new String[]{String.valueOf(chiTieu.getMaCT())});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public int xoaChiTieu (int maChiTieu) {
        return db.delete("CHITIEU", "MACT = ?", new String[]{String.valueOf(maChiTieu)});
    }

    public List<ChiTieu> layDanhSachChiTieu (){
        List<ChiTieu> list = new ArrayList<>();
        Cursor c =db.rawQuery("SELECT * FROM CHITIEU INNER JOIN VITIEN ON CHITIEU.MAVI = VITIEN.MAVI INNER JOIN KHOANCHI ON CHITIEU.MAKC = KHOANCHI.MAKC inner join DANHMUC ON CHITIEU.MADM=DANHMUC.MADANHMUC", null);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                ChiTieu ct = new ChiTieu(
                    c.getInt(0), c.getInt(1), c.getInt(2),c.getInt(3), c.getString(9), c.getString(14), c.getDouble(4), c.getString(5), c.getString(6)
                );
                list.add(ct);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public List<ChiTieu> timKiem (String name){
        List<ChiTieu> list = new ArrayList<>();
        String data[] = new String[]{name};
        Cursor c =db.rawQuery("SELECT * FROM CHITIEU INNER JOIN VITIEN ON CHITIEU.MAVI = VITIEN.MAVI INNER JOIN KHOANCHI ON CHITIEU.MAKC = KHOANCHI.MAKC INNER JOIN DANHMUC ON CHITIEU.MADM = DANHMUC.MADANHMUC WHERE KHOANCHI.TENKC LIKE ?", data);
        if (c!=null&&c.getCount()>0){
            c.moveToFirst();
            do{
                ChiTieu ct = new ChiTieu(
                        c.getInt(0), c.getInt(1), c.getInt(2),c.getInt(3), c.getString(9), c.getString(14), c.getDouble(4), c.getString(5), c.getString(6)
                );
                list.add(ct);
            } while (c.moveToNext());
            c.close();
        }
        return list;
    }

    public double getTongChiTieu(){
        String sql = "SELECT SUM(SOTIENCHI) FROM CHITIEU";
        Cursor c = db.rawQuery(sql,null);

        double tongChiTieu = 0;
        if (c.moveToFirst()){
            tongChiTieu = c.getDouble(0);
        }
        c.close();

        return tongChiTieu;
    }
}
