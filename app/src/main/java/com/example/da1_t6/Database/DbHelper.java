package com.example.da1_t6.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


        db.execSQL("CREATE TABLE KHOANCHI (\n" +
                "    MAKC      INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    MADANHMUC TEXT    REFERENCES DANHMUC (MADANHMUC),\n" +
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
        db.execSQL("insert into KHOANCHI(MAKC,MADANHMUC,TENKC) values (1,1,'Ăn uống'),(2,1,'Đổ xăng'),(3,2,'Chơi game')");
        db.execSQL("INSERT INTO VITIEN VALUES (1,2,'Tiền Mặt',57,67),(2,2,'Chuyển khoản',57,67)");
        db.execSQL("INSERT INTO CHITIEU\n" +
                "VALUES (1,1,2,1,30000,'ngay','abc'),\n" +
                "(2,1,3,2,50000,'ngay2','abcde')");
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
            onCreate(db);
        }
    }
}
