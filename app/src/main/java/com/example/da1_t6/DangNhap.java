package com.example.da1_t6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.da1_t6.Fragment.fragment_QuanLyKhoanChi;

public class DangNhap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(DangNhap.this, fragment_QuanLyKhoanChi.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}