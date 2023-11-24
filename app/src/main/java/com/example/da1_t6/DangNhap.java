package com.example.da1_t6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.da1_t6.DAO.NguoiDungDAO;
import com.example.da1_t6.Fragment.fragment_QuanLyKhoanChi;

public class DangNhap extends AppCompatActivity {

    Button btn_login;
    EditText ed_user;
    EditText ed_pass;
    CheckBox chkSave;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        ed_user = findViewById(R.id.ed_dn_tentk);
        ed_pass = findViewById(R.id.ed_dn_pass);
        btn_login = findViewById(R.id.btn_dangnhap);
        chkSave = findViewById(R.id.chkSave);


        SharedPreferences pref = getSharedPreferences("User_File", MODE_PRIVATE);
        ed_user.setText(pref.getString("Username", ""));
        ed_pass.setText(pref.getString("Password", ""));
        chkSave.setChecked(pref.getBoolean("Remember", false));

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(DangNhap.this, fragment_QuanLyKhoanChi.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
    }
}