package com.example.da1_t6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_t6.DAO.NguoiDungDAO;
import com.example.da1_t6.Fragment.fragment_BienDongTaiChinh;
import com.example.da1_t6.Fragment.fragment_QuanLyChiTieu;
import com.example.da1_t6.Fragment.fragment_QuanLyHocTap;
import com.example.da1_t6.Fragment.fragment_QuanLyKhoanChi;
import com.example.da1_t6.Fragment.fragment_QuanLyThuNhap;
import com.example.da1_t6.Fragment.fragment_QuanLyVi;
import com.example.da1_t6.Fragment.fragment_QuanLyHoatDong;

import com.example.da1_t6.Fragment.fragment_ThongKe;
import com.example.da1_t6.Model.NguoiDung;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FragmentManager fg;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Context context = this;
    FrameLayout frameLayout ;
    View header;
    List<NguoiDung> list;
    NguoiDungDAO nguoiDungDAO;
    CircleImageView avatar;
    TextView tenND;
    LinearLayout linearLayout;
    Uri selectedImage;
    byte[] anh;
    ImageView anhDaiDien;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == DangKy.RESULT_OK && data != null) {
            selectedImage = data.getData();
            anhDaiDien.setImageURI(selectedImage);
            Toast.makeText(MainActivity.this, "Chọn ảnh thành công", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        list = new ArrayList<>();
        fg = getSupportFragmentManager();
        fg.beginTransaction().add(R.id.frameLayout,new fragment_QuanLyVi()).commit();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        header = navigationView.getHeaderView(0);
        nguoiDungDAO = new NguoiDungDAO(MainActivity.this);
        list = nguoiDungDAO.layDanhSachNguoiDung();
        Log.e("Tag", ""+list);
        avatar = header.findViewById(R.id.headerImage);
        tenND = header.findViewById(R.id.header_name);
        avatar.setImageURI(list.get(0).hienthi(MainActivity.this));
        tenND.setText(list.get(0).getHoTen());
        linearLayout = header.findViewById(R.id.editThongTin);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialog_sua_thong_tin,null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                EditText txtTenHienThi = view1.findViewById(R.id.txtTenHienThi);
                anhDaiDien = view1.findViewById(R.id.avatarImageViewCNTT);
                Button btnHuy = view1.findViewById(R.id.btnHuyCNTT);
                Button btnLuu = view1.findViewById(R.id.btnSaveCNTT);
                NguoiDung nguoiDung = list.get(0);
                txtTenHienThi.setText(nguoiDung.getHoTen());
                anhDaiDien.setImageURI(list.get(0).hienthi(MainActivity.this));
                 btnHuy.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         dialog.dismiss();
                     }
                 });
                int drawableResourceId = R.drawable.imageht;
                Uri drawableUri = new Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(getResources().getResourcePackageName(drawableResourceId))
                        .appendPath(getResources().getResourceTypeName(drawableResourceId))
                        .appendPath(getResources().getResourceEntryName(drawableResourceId))
                        .build();
                selectedImage = drawableUri;
                anhDaiDien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                });
                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenHienThi = txtTenHienThi.getText().toString().trim();
                        anh = getAnh(selectedImage);
                        if (tenHienThi.isEmpty()){
                            Toast.makeText(MainActivity.this, "Vui lòng điền tên hiển thị", Toast.LENGTH_SHORT).show();
                        } else {
                            nguoiDung.setHoTen(tenHienThi);
                            nguoiDung.setAnhDaiDien(anh);
                            boolean check = nguoiDungDAO.capNhatNguoiDung(nguoiDung);
                            if (check){
                                // Cập nhật ảnh mới lên header
                                avatar.setImageURI(selectedImage);
                                // Cập nhật tên hiển thị mới lên header
                                tenND.setText(tenHienThi);
                                Toast.makeText(MainActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(MainActivity.this, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuQLV){
                    fragment_QuanLyVi frgQLV = new fragment_QuanLyVi();
                    relaceFrg(frgQLV);
                    toolbar.setTitle("Quản lý ví");
                }else if(item.getItemId() == R.id.menuQLKC){
                    fragment_QuanLyKhoanChi frgQLKC = new fragment_QuanLyKhoanChi();
                    relaceFrg(frgQLKC);
                    toolbar.setTitle("Quản lý khoản chi");
                }else if(item.getItemId() == R.id.menuQLCT){
                    fragment_QuanLyChiTieu frgQLCT = new fragment_QuanLyChiTieu();
                    relaceFrg(frgQLCT);
                    toolbar.setTitle("Quản lý chi tiêu");
                }else if(item.getItemId() == R.id.menuQLTN){
                    fragment_QuanLyThuNhap frgQLTN =  new fragment_QuanLyThuNhap();
                    relaceFrg(frgQLTN);
                    toolbar.setTitle("Quản lý thu nhập");
                }else if(item.getItemId() == R.id.menuQLHD){
                    fragment_QuanLyHoatDong frgQLTGB = new fragment_QuanLyHoatDong();
                    relaceFrg(frgQLTGB);
                    toolbar.setTitle("Quản lý hoạt động");

                }else if (item.getItemId() == R.id.menuQLHT){
                    fragment_QuanLyHocTap fragmentQuanLyHocTap = new fragment_QuanLyHocTap();
                    relaceFrg(fragmentQuanLyHocTap);
                    toolbar.setTitle("Quản lý học tập");
                }else if(item.getItemId() == R.id.menuTK){
                    fragment_ThongKe frgTK = new fragment_ThongKe();
                    relaceFrg(frgTK);
                    toolbar.setTitle("Thống kê");
                }else if (item.getItemId() == R.id.menuDMK) {
                    dialog_UpDatePass();
                }else if(item.getItemId() == R.id.menuDX){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chắn muốn đăng xuất chứ!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });


    }
    public void relaceFrg(Fragment frg){
        fg.beginTransaction().replace(R.id.frameLayout,frg).commit();
    }
    public void dialog_UpDatePass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_doi_mat_khau, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        //ánh xạ
        EditText txtMatKhauCu = view.findViewById(R.id.ed_matKhauCu);
        EditText txtMatKhauMoi = view.findViewById(R.id.ed_matKhauMoi);
        EditText txtMatKhauMoixn = view.findViewById(R.id.ed_nhapLaiMk);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);
        //
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mkCu = txtMatKhauCu.getText().toString();
                String mkMoi = txtMatKhauMoi.getText().toString();
                String nhapLaiMkMoi = txtMatKhauMoixn.getText().toString();
                if (mkCu.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                } else if (mkMoi.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
                } else if (nhapLaiMkMoi.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
                } else {
                    if (mkCu.equals(list.get(0).getMatKhau())){
                        if (mkMoi.equals(nhapLaiMkMoi)){
                            NguoiDung nguoiDung = list.get(0);
                            nguoiDung.setMatKhau(nhapLaiMkMoi);
                            nguoiDungDAO.capNhatNguoiDung(nguoiDung);
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    public byte[] getAnh(Uri selectedImage) {
        // Max allowed size in bytes
        int maxSize = 1024 * 1024; // 1MB
        try {
            InputStream inputStream = MainActivity.this.getContentResolver().openInputStream(selectedImage);
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
            inputStream = MainActivity.this.getContentResolver().openInputStream(selectedImage);
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