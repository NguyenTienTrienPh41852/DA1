package com.example.da1_t6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;




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
        fg = getSupportFragmentManager();
        fg.beginTransaction().add(R.id.frameLayout,new fragment_quan_li_vi()).commit();
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuQLV){
                    fragment_quan_li_vi frgQLV = new fragment_quan_li_vi();
                    relaceFrg(frgQLV);
                    toolbar.setTitle("Quản lý ví");
                }else if(item.getItemId() == R.id.menuQLCT){
                    fragment_Quanlychitieu frgQLCT = new fragment_Quanlychitieu();
                    relaceFrg(frgQLCT);
                    toolbar.setTitle("Quản lý chi tiêu");
                }else if(item.getItemId() == R.id.menuQLTN){
                    fragment_Quanlythunhap frgQLTN =  new fragment_Quanlythunhap();
                    relaceFrg(frgQLTN);
                    toolbar.setTitle("Quản lý thu nhập");
                }else if(item.getItemId() == R.id.menuQLTGB){
                    fragment_thoi_gian_bieu frgQLTGB = new fragment_thoi_gian_bieu();
                    relaceFrg(frgQLTGB);
                    toolbar.setTitle("Thời gian biểu");
                }else if(item.getItemId() == R.id.menuBDTC){
                    fragment_Biendongtaichinh frgBDTC = new fragment_Biendongtaichinh();
                    relaceFrg(frgBDTC);
                    toolbar.setTitle("Biến động tài chính");
                }else if(item.getItemId() == R.id.menuTK){
                    fragment_Thongke frgTK = new fragment_Thongke();
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