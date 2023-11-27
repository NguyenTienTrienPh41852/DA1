package com.example.da1_t6.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.da1_t6.R;

import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper (Context context){
        super(context,"QLCTSH", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE NGUOIDUNG (\n" +
                "    MAND       TEXT PRIMARY KEY,\n" +
                "    MATKHAU    TEXT NOT NULL,\n" +
                "    TENND      TEXT NOT NULL,\n" +
                "    ANHDAIDIEN BLOB\n" +
                ");\n");
        db.execSQL("CREATE TABLE DANHMUC (\n" +
                "    MADANHMUC  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    TENDANHMUC TEXT    NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE MONHOC (\n" +
                "    IDMONHOC  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MAMON     TEXT    NOT NULL,\n" +
                "    TENMON    TEXT    NOT NULL,\n" +
                "    TENLOP    TEXT    NOT NULL,\n" +
                "    DIEMTB    REAL    DEFAULT (0),\n" +
                "    TRANGTHAI INTEGER DEFAULT (0) \n" +
                ");\n");
        db.execSQL("CREATE TABLE VITIEN (\n" +
                "    MAVI   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MAND   TEXT    REFERENCES NGUOIDUNG (MAND),\n" +
                "    TENVI  TEXT    NOT NULL,\n" +
                "    SODUBD REAL    NOT NULL,\n" +
                "    SODUHT REAL    DEFAULT (0) \n" +
                ");");
        db.execSQL("create table ICON(\n" +
                "MAICON INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "ANH TEXT \n" +
                ")");
        db.execSQL("CREATE TABLE KHOANCHI (\n" +
                "    MAKC      INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MADANHMUC TEXT    REFERENCES DANHMUC (MADANHMUC),\n" +
                "    MAICON TEXT    REFERENCES ICON (MAICON),\n" +
                "    TENKC     TEXT    NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE THUNHAP (\n" +
                "    MATN     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MAVI     INTEGER REFERENCES VITIEN (MAVI),\n" +
                "    TENKT    TEXT    NOT NULL,\n" +
                "    SOTIEN   REAL    NOT NULL,\n" +
                "    THOIGIAN TEXT    NOT NULL,\n" +
                "    GHICHU   TEXT\n" +
                ");\n");

        db.execSQL("CREATE TABLE BANGDIEM (\n" +
                "    MABANGDIEM INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    IDMONHOC   INTEGER REFERENCES MONHOC (IDMONHOC),\n" +
                "    TENDD      TEXT    NOT NULL,\n" +
                "    TRONGSO    REAL    NOT NULL,\n" +
                "    DIEM       REAL    NOT NULL\n" +
                ");");
        db.execSQL("CREATE TABLE HOATDONG (\n" +
                "    MAHD       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MAND       TEXT    REFERENCES NGUOIDUNG (MAND),\n" +
                "    TENHD      TEXT    NOT NULL,\n" +
                "    MOTA       TEXT    NOT NULL,\n" +
                "    TGBATDAU   TEXT    NOT NULL,\n" +
                "    TGKETTHUC  TEXT    NOT NULL,\n" +
                "    TTHOATDONG INTEGER DEFAULT (0),\n" +
                "    NGAY       TEXT    NOT NULL\n" +
                ");\n");
        db.execSQL("CREATE TABLE CHITIEU (\n" +
                "    MACT        INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MAVI        INTEGER REFERENCES VITIEN (MAVI),\n" +
                "    MAKC        INTEGER REFERENCES KHOANCHI (MAKC),\n" +
                "    MADM        INTEGER REFERENCES DANHMUC (MADANHMUC),\n" +
                "    SOTIENCHI   REAL    NOT NULL,\n" +
                "    THOIGIANCHI TEXT    NOT NULL,\n" +
                "    GHICHU      TEXT    NOT NULL\n" +
                ");");

        db.execSQL("INSERT INTO DANHMUC VALUES (1, 'Thiết yếu')");
        db.execSQL("INSERT INTO DANHMUC VALUES (2, 'Hưởng thụ')");
        db.execSQL("INSERT INTO DANHMUC VALUES (3, 'Biếu tặng')");
        db.execSQL("INSERT INTO DANHMUC VALUES (4, 'Sức khỏe')");
        db.execSQL("INSERT INTO DANHMUC VALUES (5, 'Chi tiêu khác')");
        db.execSQL("insert into KHOANCHI values (1,1,2,'Ăn uống'),(2,1,1,'Đổ xăng'),(3,2,3,'Chơi game')");
        db.execSQL("INSERT INTO VITIEN VALUES (1,2,'Tiền Mặt',57,67),(2,2,'Chuyển khoản',57,67)");
        db.execSQL("INSERT INTO CHITIEU\n" +
                "VALUES (1,1,2,1,30000,'23-11-2023','abc'),\n" +
                "(2,1,3,2,50000,'20-10-2023','abcde')");

       String anh1 = (R.drawable.sandwich)+"";
       String anh2 = (R.drawable.bill)+"";
       String anh3 = (R.drawable.car)+"";
       String anh4 = (R.drawable.rent)+"";
       String anh5 = (R.drawable.market)+"";
       String anh6 = (R.drawable.maintenance)+"";
       String anh7 = (R.drawable.clapboard)+"";
       String anh8 = (R.drawable.dress)+"";
       String anh9 = (R.drawable.travel)+"";
       String anh10= (R.drawable.makeuppouch)+"";
       String anh11= (R.drawable.cocktail)+"";
       String anh12= (R.drawable.giftbox)+"";
       String anh13= (R.drawable.donation)+"";
       String anh14= (R.drawable.stethoscope)+"";
       String anh15= (R.drawable.sports)+"";
       String anh16= (R.drawable.medicalcase)+"";
       String anh17= (R.drawable.healthinsurance)+"";
       String anh18= (R.drawable.boy)+"";
       String anh19= (R.drawable.transaction)+"";
       String anh20= (R.drawable.money)+"";
       String anh21= (R.drawable.interestrate)+"";
       String anh22= (R.drawable.comment)+"";
       String [] listicon = new String[]{anh1,anh2,anh3,anh4,anh5,anh6,anh7,anh8,anh9,anh10,anh11,anh12,anh13,anh14,anh15,anh16,anh17,anh18,anh19,anh20,anh21,anh22};
      db.execSQL("INSERT INTO ICON VALUES (1,?),(2,?),(3,?),(4,?),(5,?),(6,?),(7,?),(8,?),(9,?),(10,?),(11,?),(12,?),(13,?),(14,?),(15,?),(16,?),(17,?),(18,?),(19,?),(20,?),(21,?),(22,?);",listicon);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1){
            db.execSQL("DROP TABLE IF EXISTS VITIEN");
            db.execSQL("DROP TABLE IF EXISTS DANHMUC");
            db.execSQL("DROP TABLE IF EXISTS KHOANCHI");
            db.execSQL("DROP TABLE IF EXISTS KHOANTHU");
            db.execSQL("DROP TABLE IF EXISTS MONHOC");
            db.execSQL("DROP TABLE IF EXISTS BANGDIEM");
            db.execSQL("DROP TABLE IF EXISTS HOATDONG");
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS ICON");
            onCreate(db);
        }
    }
}
