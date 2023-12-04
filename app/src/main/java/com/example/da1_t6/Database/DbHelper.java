package com.example.da1_t6.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.da1_t6.R;

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
        db.execSQL("CREATE TABLE ICON(\n" +
                "MAICON INTEGER PRIMARY KEY,\n" +
                "ANH TEXT UNIQUE\n" +
                ")");
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
                "    MAICON TEXT REFERENCES ICON (MAICON),\n"+
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
        db.execSQL("INSERT INTO VITIEN VALUES (1, 1, 'Ví tiền mặt', 0, 0), (2, 1, 'Chuyển khoản', 0, 0)");

        String anh1 = (R.drawable.sandwich)+"";
        String anh2 = (R.drawable.bill)+"";
        String anh3 = (R.drawable.car)+"";
        String anh4 = (R.drawable.rent)+"";
        String anh5 = (R.drawable.market)+"";
        String anh6 = (R.drawable.maintenance)+"";
        String anh7 = (R.drawable.clapboard)+"";
        String anh8 = (R.drawable.dress)+"";
        String anh9 = (R.drawable.travel)+"";
        String anh10 = (R.drawable.makeuppouch)+"";
        String anh11 = (R.drawable.cocktail)+"";
        String anh12 = (R.drawable.giftbox)+"";
        String anh13 = (R.drawable.donation)+"";
        String anh14 = (R.drawable.stethoscope)+"";
        String anh15 = (R.drawable.sports)+"";
        String anh16 = (R.drawable.medicalcase)+"";
        String anh17 = (R.drawable.healthinsurance)+"";
        String anh18 = (R.drawable.boy)+"";
        String anh19 = (R.drawable.transaction)+"";
        String anh20 = (R.drawable.money)+"";
        String anh21 = (R.drawable.interestrate)+"";
        String anh22 = (R.drawable.comment)+"";
        String [] listanh = new String[]{anh1,anh2,anh3,anh4,anh5,anh6,anh7,anh8,anh9,anh10,anh11,anh12,anh13,anh14,anh15,anh16,anh17,anh18,anh19,anh20,anh21,anh22};
        db.execSQL("INSERT INTO ICON VALUES (1,?),(2,?),(3,?),(4,?),(5,?),(6,?),(7,?),(8,?),(9,?),(10,?),(11,?),(12,?),(13,?),(14,?),(15,?),(16,?),(17,?),(18,?),(19,?),(20,?),(21,?),(22,?);",listanh);
        db.execSQL("INSERT INTO KHOANCHI VALUES (1, 1, 1, 'Ăn uống'), (2, 1, 2, 'Hóa đơn'), (3, 1, 3, 'Đi lại'), (4, 1, 4, 'Thuê nhà')" +
                ", (5, 1, 6, 'Bảo trì và Sửa chữa'), (6, 1, 5, 'Đi chợ & Siêu thị'), (7, 2, 7, 'Giải trí'), (8, 2, 8, 'Thời trang'), (9, 2, 9, 'Du lịch')" +
                ", (10, 2, 10, 'Làm đẹp'), (11, 2, 12, 'Liên Hoan'), (12, 3, 1, 'Quà tặng'), (13, 3, 13, 'Từ thiện'), (14, 4, 14, 'Bác sĩ'), (15, 4, 15, 'Thể thao')" +
                ", (16, 4, 17, 'Bảo hiểm'), (17, 5, 20, 'Bị mất'), (18, 5, 21, 'Phí & Lệ phí')");
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
