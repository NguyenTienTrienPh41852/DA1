package com.example.da1_t6.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_t6.Adapter.QuanLyChiTieuAdapter;
import com.example.da1_t6.Adapter.adapterSPDanhMuc;
import com.example.da1_t6.Adapter.adapterSPKhoanChi;
import com.example.da1_t6.Adapter.adapterSPLoaiVi;
import com.example.da1_t6.DAO.ChiTieuDAO;
import com.example.da1_t6.DAO.DanhMucDAO;
import com.example.da1_t6.DAO.KhoanChiDAO;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.DanhMuc;
import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class fragment_QuanLyChiTieu extends Fragment {

    public fragment_QuanLyChiTieu() {
        // Required empty public constructor
    }
    RecyclerView rcvQLCT;
    SearchView search;
    DatePickerDialog datePickerDialog;
    ImageButton fltAdd;
    List<ChiTieu> list;
    ChiTieuDAO chiTieuDAO;
    KhoanChiDAO khoanChiDAO;
    DanhMucDAO danhMucDAO;
    ViTienDAO viTienDAO;
    QuanLyChiTieuAdapter chiTieuAdapter;
    String ghiChu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quanlychitieu, container, false);
        rcvQLCT = view.findViewById(R.id.rcvQLCT);
        fltAdd = view.findViewById(R.id.add_QLCT);
        search = view.findViewById(R.id.sv_qlct);
        chiTieuDAO = new ChiTieuDAO(getContext());
        danhMucDAO = new DanhMucDAO(getContext());
        khoanChiDAO = new KhoanChiDAO(getContext());
        viTienDAO = new ViTienDAO(getContext());
        loadData();
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddChiTieu();
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    loadData();
                } else {
                    list.clear();
                    list.addAll(chiTieuDAO.timKiem("%" + newText + "%"));
                    chiTieuAdapter.notifyDataSetChanged();
                }
                return false;
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

        TextInputLayout in_errSoTien = view.findViewById(R.id.in_addSoTien);
        EditText ed_sotien = view.findViewById(R.id.add_ct_sotien);
        EditText ed_ghichu = view.findViewById(R.id.add_ct_ghichu);
        Spinner spn_danhmuc = view.findViewById(R.id.spn_add_ct_danhmuc);
        Spinner spn_khoanchi = view.findViewById(R.id.spn_add_ct_khoanchi);
        LinearLayout li_ngay = view.findViewById(R.id.add_ct_ngay);
        TextView tvNgay = view.findViewById(R.id.tv_add_ct_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_add_ct_loaivi);
        Button btn_save = view.findViewById(R.id.btn_ct_add);

        ed_sotien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString().trim();

                if ( !TextUtils.isDigitsOnly(input)) {
                    // Không phải số
                    in_errSoTien.setError("Vui lòng nhập một số hợp lệ");
                }else {
                    // Là số hợp lệ, không có lỗi
                    in_errSoTien.setError(null);
                }
            }
        });

        List<DanhMuc> listDM = danhMucDAO.layDanhSachDanhMuc();
        adapterSPDanhMuc spDanhMuc = new adapterSPDanhMuc(listDM,getContext());
        spn_danhmuc.setAdapter(spDanhMuc);
        final adapterSPKhoanChi[] spKhoanChi = new adapterSPKhoanChi[1];
        spn_danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<KhoanChi> listKC = khoanChiDAO.layDanhSachKhoanChiTheoDM(listDM.get(position).getMaDanhMuc()+"");
                spKhoanChi[0] = new adapterSPKhoanChi(listKC,getContext());
                spn_khoanchi.setAdapter( spKhoanChi[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        tvNgay.setText(sdf.format(c.getTime()));
        li_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvNgay.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        List<ViTien> listVT = viTienDAO.layDanhSachViTien();
        adapterSPLoaiVi spLoaiVi = new adapterSPLoaiVi(listVT,getContext());
        spn_loaivi.setAdapter(spLoaiVi);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_sotien.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền chi!", Toast.LENGTH_SHORT).show();
                } else if (tvNgay.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng chọn ngày chi tiêu!", Toast.LENGTH_SHORT).show();
                } else {
                    if (ed_ghichu.getText().toString().isEmpty()){
                        ghiChu = "Không";
                    } else {
                        ghiChu = String.valueOf(ed_ghichu.getText());
                    }
                    int soTien = Integer.parseInt(String.valueOf(ed_sotien.getText()));
                    int danhmuc = spn_danhmuc.getSelectedItemPosition()+1;
                    //               Log.e("TAG","dialog:"+danhmuc);
                    int chiMuc = spn_khoanchi.getSelectedItemPosition()+1;
                    //               Log.e("TAG","dialog2:"+chiMuc);
                    String ngay = tvNgay.getText().toString();
                    int loaiVi = spn_loaivi.getSelectedItemPosition()+1;
                    ChiTieu chiTieu = new ChiTieu();
                    String tenVi = listVT.get(spn_loaivi.getSelectedItemPosition()).getTenVi();
                    ViTien viTien = viTienDAO.layViTienTheoTen(tenVi);
                    if (viTien != null) {
                        int soTienChi = soTien;
                        double soDuHT = viTien.getSoDuHienTai();
                        if (soTienChi > soDuHT){
                            Toast.makeText(getContext(), "Số dư trong ví không đủ để thanh toán!", Toast.LENGTH_SHORT).show();
                        } else {
                            chiTieu.setSoTienChi(soTien);
                            chiTieu.setGhiChu(ghiChu);
                            chiTieu.setMaKC(chiMuc);
                            chiTieu.setThoiGianChi(ngay);
                            chiTieu.setMaVi(loaiVi);
                            chiTieu.setMaDM(danhmuc);
                            double soDuMoi = soDuHT - soTienChi;
                            viTien.setSoDuHienTai(soDuMoi);
                            boolean check = chiTieuDAO.themChiTieu(chiTieu);
                            if(check){
                                boolean updateResult = viTienDAO.capNhatSoDuViTien(viTien);
                                if (updateResult) {
                                    loadData();
                                    Toast.makeText(getContext(), "Thêm chi tiêu thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Cập nhật số dư thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getContext(), "Thêm chi tiêu thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Ví tiền không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void loadData(){
        list = chiTieuDAO.layDanhSachChiTieu();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcvQLCT.setLayoutManager(layoutManager);
        chiTieuAdapter = new QuanLyChiTieuAdapter(getContext(),list,chiTieuDAO);
        rcvQLCT.setAdapter(chiTieuAdapter);
        chiTieuAdapter.notifyDataSetChanged();
    }
}