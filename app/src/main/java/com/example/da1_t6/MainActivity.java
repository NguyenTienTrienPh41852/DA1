package com.example.da1_t6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.da1_t6.Fragment.fragment_BienDongTaiChinh;
import com.example.da1_t6.Fragment.fragment_QuanLyChiTieu;
import com.example.da1_t6.Fragment.fragment_QuanLyKhoanChi;
import com.example.da1_t6.Fragment.fragment_QuanLyThuNhap;
import com.example.da1_t6.Fragment.fragment_ThongKe;
import com.example.da1_t6.Fragment.fragment_QuanLyVi;
import com.example.da1_t6.Fragment.fragment_QuanLyHoatDong;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FragmentManager fg;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Context context = this;
    FrameLayout frameLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        fg = getSupportFragmentManager();
        fg.beginTransaction().add(R.id.frameLayout,new fragment_QuanLyVi()).commit();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
                }else if(item.getItemId() == R.id.menuQLTGB){
                    fragment_QuanLyHoatDong frgQLTGB = new fragment_QuanLyHoatDong();
                    relaceFrg(frgQLTGB);
                    toolbar.setTitle("Thời gian biểu");
                }else if(item.getItemId() == R.id.menuBDTC){
                    fragment_BienDongTaiChinh frgBDTC = new fragment_BienDongTaiChinh();
                    relaceFrg(frgBDTC);
                    toolbar.setTitle("Biến động tài chính");
                }else if(item.getItemId() == R.id.menuTK){
                    fragment_ThongKe frgTK = new fragment_ThongKe();
                    relaceFrg(frgTK);
                    toolbar.setTitle("Thống kê");
                }else if(item.getItemId() == R.id.menuDX){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chắn muốn đăng xuất chứ!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy",null);
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
}