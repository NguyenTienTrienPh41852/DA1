package com.example.da1_t6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.da1_t6.DAO.NguoiDungDAO;
import com.example.da1_t6.Model.NguoiDung;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class DangKy extends AppCompatActivity {
    EditText txtTenND, txtTaiKhoan, txtPass, txtRePass;
    ImageView imgAnhDaiDien;
    Button btnDangKy;
    TextInputLayout in_tennd, in_tentk, in_pass, in_resetpass;
    NguoiDungDAO nguoiDungDAO;
    Uri selectedImage;
    byte[] anh;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == DangKy.RESULT_OK && data != null) {
            selectedImage = data.getData();
            imgAnhDaiDien.setImageURI(selectedImage);
            Toast.makeText(DangKy.this, "Chọn ảnh thành công", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        txtTenND = findViewById(R.id.ed_dk_tennd);
        txtTaiKhoan = findViewById(R.id.ed_dk_tentk);
        txtPass = findViewById(R.id.ed_dk_pass);
        txtRePass = findViewById(R.id.ed_dk_resetpass);
        in_tennd = findViewById(R.id.in_tennd);
        in_tentk = findViewById(R.id.in_tentk);
        in_pass = findViewById(R.id.in_pass);
        in_resetpass = findViewById(R.id.in_resetpass);
        imgAnhDaiDien = findViewById(R.id.avatarImageView);
        btnDangKy = findViewById(R.id.btn_dangky);
        nguoiDungDAO = new NguoiDungDAO(DangKy.this);
        // Gán ảnh
        int drawableResourceId = R.drawable.imageht;
        Uri drawableUri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(drawableResourceId))
                .appendPath(getResources().getResourceTypeName(drawableResourceId))
                .appendPath(getResources().getResourceEntryName(drawableResourceId))
                .build();
        selectedImage = drawableUri;
        imgAnhDaiDien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        // Kiểm tra trạng thái đăng ký từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean daDangKy = sharedPreferences.getBoolean("daDangKy", false);

        if (daDangKy) {
            Intent intent = new Intent(DangKy.this, DangNhap.class);
            startActivity(intent);
            finish(); // Đóng màn hình hiện tại để không quay lại màn hình đăng ký
        }
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenND = txtTenND.getText().toString().trim();
                String taiKhoan = txtTaiKhoan.getText().toString().trim();
                String matKhau = txtPass.getText().toString().trim();
                String lapMatKhau = txtRePass.getText().toString().trim();
                anh = getAnh(selectedImage);
                if (tenND.equals("")) {
                    in_tennd.setError("Vui lòng không để trống tên người dùng!");
                } else {
                    in_tennd.setError(null);
                }
                if (taiKhoan.equals("")) {
                    in_tentk.setError("Vui lòng không để trống tên tài khoản!");
                } else {
                    in_tentk.setError(null);
                }
                if (matKhau.equals("")) {
                    in_pass.setError("Vui lòng không để trống mật khẩu!");
                } else {
                    in_pass.setError(null);
                }
                if (lapMatKhau.equals("")) {
                    in_resetpass.setError("Vui lòng không để trống reset mật khẩu!");
                } else {
                    if (!lapMatKhau.equals(matKhau)) {
                        in_resetpass.setError("Mật khẩu không khớp nhau!");
                    } else {
                        in_resetpass.setError(null);
                        if (in_tennd.getError() == null && in_tentk.getError() == null && in_pass.getError() == null && in_resetpass.getError() == null) {
                            NguoiDung nguoiDung = new NguoiDung();
                            nguoiDung.setMaNguoiDung(taiKhoan);
                            nguoiDung.setHoTen(tenND);
                            nguoiDung.setMatKhau(matKhau);
                            nguoiDung.setAnhDaiDien(anh);
                            boolean check = nguoiDungDAO.themNguoiDung(nguoiDung);
                            if (check){
                                Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                // Lưu trạng thái đã đăng ký vào SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("daDangKy", true);
                                editor.apply();
                                // Mở màn hình đăng nhập
                                Intent i = new Intent(DangKy.this, DangNhap.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(DangKy.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(DangKy.this, "Chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    public byte[] getAnh(Uri selectedImage) {
        // Max allowed size in bytes
        int maxSize = 1024 * 1024; // 1MB
        try {
            InputStream inputStream = DangKy.this.getContentResolver().openInputStream(selectedImage);
            // Đọc ảnh vào một đối tượng Bitmap
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            // Tính toán tỷ lệ nén cần áp dụng để đảm bảo kích thước không vượt quá maxSize
            int scale = 1;
            while ((options.outWidth * options.outHeight) * (1 / Math.pow(scale, 2)) > maxSize) {
                scale++;
            }
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            inputStream.close();
            // Đọc ảnh lại với tỷ lệ nén
            inputStream = DangKy.this.getContentResolver().openInputStream(selectedImage);
            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, options);
            // Chuyển đổi Bitmap thành byte array
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteBuffer); // Thay đổi định dạng và chất lượng nén tùy theo nhu cầu
            byte[] imageData = byteBuffer.toByteArray();
            return imageData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}