package com.example.da1_t6.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_t6.Database.DbHelper;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.Icon;

import java.util.ArrayList;
import java.util.List;

public class IconDAO {
    private SQLiteDatabase db;

    public IconDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
   public List<Icon> layDSIcon(){
        List<Icon> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM ICON",null);
       if (c != null && c.getCount() > 0) {
           c.moveToFirst();
           do {
               list.add(new Icon(c.getInt(0),c.getInt(1)));
           }while (c.moveToNext()) ;
       }
       return list;
   }
    public Icon icon(int maIcon){
        Icon list = new Icon();
        Cursor c = db.rawQuery("SELECT * FROM ICON WHERE MAICON = ?",new String[]{maIcon+""});
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
         list.setMaIcon(c.getInt(0));
         list.setIcon(c.getInt(1));
        }
        return list;
    }
}
