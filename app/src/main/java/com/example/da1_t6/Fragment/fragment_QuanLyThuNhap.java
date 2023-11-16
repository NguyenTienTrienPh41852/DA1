package com.example.da1_t6.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.da1_t6.Adapter.QuanLyThuNhapAdapter;
import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class fragment_QuanLyThuNhap extends Fragment {

    public fragment_QuanLyThuNhap() {
        // Required empty public constructor
    }

    RecyclerView rcvQLTN;
    SearchView search;

    ImageButton fltAdd;
    List<ThuNhap> list;
    ThuNhapDAO thuNhapDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__quanlythunhap, container, false);
        rcvQLTN = view.findViewById(R.id.rcvQLTN);
        fltAdd = view.findViewById(R.id.add_QLTN);
        search = view.findViewById(R.id.sv_qltn);
        thuNhapDAO = new ThuNhapDAO(getContext());
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddThuNhap();
            }
        });
        return view;
    }
    private void dialogAddThuNhap(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themthunhap,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText ed_sotien = view.findViewById(R.id.add_tn_sotien);
        EditText ed_ghichu = view.findViewById(R.id.add_tn_ghichu);
        Spinner spn_tenGD = view.findViewById(R.id.spn_add_tn_tengd);
        LinearLayout li_ngay = view.findViewById(R.id.add_tn_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_add_tn_loaivi);
        Button btn_save = view.findViewById(R.id.btn_tn_add);



    }
}