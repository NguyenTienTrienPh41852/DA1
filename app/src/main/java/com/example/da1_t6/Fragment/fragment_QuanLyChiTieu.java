package com.example.da1_t6.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import com.example.da1_t6.DAO.ChiTieuDAO;
import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.R;

import java.util.List;

public class fragment_QuanLyChiTieu extends Fragment {

    public fragment_QuanLyChiTieu() {
        // Required empty public constructor
    }
    RecyclerView rcvQLCT;
    SearchView search;

    ImageButton fltAdd;
    List<ChiTieu> list;
    ChiTieuDAO chiTieuDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__quanlychitieu, container, false);
        rcvQLCT = view.findViewById(R.id.rcvQLCT);
        fltAdd = view.findViewById(R.id.add_QLCT);
        search = view.findViewById(R.id.sv_qlct);
        chiTieuDAO = new ChiTieuDAO(getContext());
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddChiTieu();
            }
        });
        return view;
    }
    private void dialogAddChiTieu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themchitieu,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText ed_sotien = view.findViewById(R.id.add_ct_sotien);
        EditText ed_ghichu = view.findViewById(R.id.add_ct_ghichu);
        Spinner spn_danhmuc = view.findViewById(R.id.spn_add_ct_danhmuc);
        Spinner spn_khoanchi = view.findViewById(R.id.spn_add_ct_khoanchi);
        LinearLayout li_ngay = view.findViewById(R.id.add_ct_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_add_ct_loaivi);
        Button btn_save = view.findViewById(R.id.btn_ct_add);



    }
}