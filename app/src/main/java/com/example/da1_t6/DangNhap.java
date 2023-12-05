package com.example.da1_t6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_t6.DAO.NguoiDungDAO;
import com.example.da1_t6.Fragment.fragment_QuanLyKhoanChi;
import com.google.android.material.textfield.TextInputLayout;

public class DangNhap extends AppCompatActivity {

    Button btn_login;
    EditText ed_user;
    EditText ed_pass;
    CheckBox chkSave;
    NguoiDungDAO nguoiDungDAO;
    TextInputLayout in_User,in_Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        ed_user = findViewById(R.id.ed_dn_tentk);
        ed_pass = findViewById(R.id.ed_dn_pass);
        in_User = findViewById(R.id.in_User);
        in_Pass = findViewById(R.id.in_Pass);
        btn_login = findViewById(R.id.btn_dangnhap);
        chkSave = findViewById(R.id.chkSave);
        nguoiDungDAO = new NguoiDungDAO(this);
        SharedPreferences pref = getSharedPreferences("User_File", MODE_PRIVATE);
        ed_user.setText(pref.getString("Username", ""));
        chkSave.setChecked(pref.getBoolean("Remember", false));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public  void login(){
        String user = ed_user.getText().toString();
        String pass = ed_pass.getText().toString();

        if (user.isEmpty() || pass.isEmpty()) {
            if (user.equals("")) {
                in_User.setError("Không được để trông tên đăng nhập");
            } else {
                in_User.setError(null);
            }
            if (pass.equals("")) {
                in_Pass.setError("Không được để trống mật khẩu");
            } else {
                in_Pass.setError(null);
            }
        } else {
            if (nguoiDungDAO.checkLogin(user, pass)) {
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(user, pass, chkSave.isChecked());
                Intent i = new Intent(DangNhap.this, MainActivity.class);
                i.putExtra("MAND", user);
                startActivity(i);
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("User_File", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            //xóa trắng dữ liệu trước đó
            edit.clear();
        } else {
            //lưu dữ liệu
            edit.putString("Username", u);
            edit.putString("Password", p);
            edit.putBoolean("Remember", status);
        }
        //lưu lại toàn bộ
        edit.commit();
    }
}