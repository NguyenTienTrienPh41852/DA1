package com.example.da1_t6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.da1_t6.DAO.NguoiDungDAO;
import com.example.da1_t6.Model.NguoiDung;

public class DangKy extends AppCompatActivity {
    EditText txtTenND, txtTaiKhoan, txtPass, txtRePass;
    ImageView imgAnhDaiDien;
    Button btnDangKy;
    NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        txtTenND = findViewById(R.id.ed_dk_tennd);
        txtTaiKhoan = findViewById(R.id.ed_dk_tentk);
        txtPass = findViewById(R.id.ed_dk_pass);
        txtRePass = findViewById(R.id.ed_dk_resetpass);
        imgAnhDaiDien = findViewById(R.id.avatarImageView);
        btnDangKy = findViewById(R.id.btn_dangky);
        nguoiDungDAO = new NguoiDungDAO(DangKy.this);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenND = txtTenND.getText().toString().trim();
                String taiKhoan = txtTaiKhoan.getText().toString().trim();
                String matKhau = txtPass.getText().toString().trim();
                String lapMatKhau = txtRePass.getText().toString().trim();
                if (tenND.isEmpty()){
                    Toast.makeText(DangKy.this, "Vui lòng điền tên người dùng!", Toast.LENGTH_SHORT).show();
                } else if (taiKhoan.isEmpty()){
                    Toast.makeText(DangKy.this, "Vui lòng điền tài khoản!", Toast.LENGTH_SHORT).show();
                } else if (matKhau.isEmpty()){
                    Toast.makeText(DangKy.this, "Vui lòng điền mật khẩu!", Toast.LENGTH_SHORT).show();
                } else if (lapMatKhau.isEmpty()){
                    Toast.makeText(DangKy.this, "Vui lòng nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
                } else {
                    NguoiDung nguoiDung = new NguoiDung();
                    if (matKhau.equals(lapMatKhau)){
                        nguoiDung.setMaNguoiDung(taiKhoan);
                        nguoiDung.setHoTen(tenND);
                        nguoiDung.setMatKhau(matKhau);
                        nguoiDungDAO.themNguoiDung(nguoiDung);
                        Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DangKy.this, "Mật khẩu lặp lại không khớp!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}