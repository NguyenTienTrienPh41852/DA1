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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_t6.Adapter.QuanLyThuNhapAdapter;
import com.example.da1_t6.Adapter.adapterSPLoaiVi;
import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class fragment_QuanLyThuNhap extends Fragment {

    public fragment_QuanLyThuNhap() {
        // Required empty public constructor
    }

    RecyclerView rcvQLTN;
    SearchView search;

    ImageButton fltAdd;
    List<ThuNhap> list;
    ThuNhapDAO thuNhapDAO;
    DatePickerDialog datePickerDialog;
    QuanLyThuNhapAdapter thuNhapAdapter;
    ViTienDAO viTienDAO;
    String ghiChu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quanlythunhap, container, false);
        rcvQLTN = view.findViewById(R.id.rcvQLTN);
        fltAdd = view.findViewById(R.id.add_QLTN);
        search = view.findViewById(R.id.sv_qltn);
        thuNhapDAO = new ThuNhapDAO(getContext());
        viTienDAO = new ViTienDAO(getContext());
        loadData();
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddThuNhap();
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
                    list.addAll(thuNhapDAO.timKiem("%" + newText + "%"));
                    thuNhapAdapter.notifyDataSetChanged();
                }
                return false;
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
        TextInputLayout in_errSoTien = view.findViewById(R.id.in_addSoTien);
        EditText ed_sotien = view.findViewById(R.id.add_tn_sotien);
        EditText ed_ghichu = view.findViewById(R.id.add_tn_ghichu);
        EditText ed_tenGD = view.findViewById(R.id.add_tn_tengiaodich);
        LinearLayout li_ngay = view.findViewById(R.id.add_tn_ngay);
        TextView tvNgay = view.findViewById(R.id.tv_add_ct_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_add_tn_loaivi);
        Button btn_save = view.findViewById(R.id.btn_tn_add);
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
                    Toast.makeText(getContext(), "Vui lòng điền số tiền", Toast.LENGTH_SHORT).show();
                } else if (ed_tenGD.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền tên giao dịch!", Toast.LENGTH_SHORT).show();
                } else if (tvNgay.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng chọn ngày giao dịch!", Toast.LENGTH_SHORT).show();
                } else {
                    int soTien = Integer.parseInt(String.valueOf(ed_sotien.getText()));
                    if (ed_ghichu.getText().toString().isEmpty()){
                        ghiChu = "Không";
                    } else {
                        ghiChu = String.valueOf(ed_ghichu.getText());
                    }
                    String tenGD = String.valueOf(ed_tenGD.getText());
                    String ngay = tvNgay.getText().toString();
                    int loaiVi = spn_loaivi.getSelectedItemPosition()+1;
                    ThuNhap thuNhap = new ThuNhap();
                    thuNhap.setTenKhoanThu(tenGD);
                    thuNhap.setSoTienThu(soTien);
                    thuNhap.setThoiGianThu(ngay);
                    thuNhap.setGhiChu(ghiChu);
                    thuNhap.setMaVi(loaiVi);
                    boolean check = thuNhapDAO.themThuNhap(thuNhap);
                    if(check){
                        String tenVi = listVT.get(spn_loaivi.getSelectedItemPosition()).getTenVi();
                        ViTien viTien = viTienDAO.layViTienTheoTen(tenVi);
                        if (viTien != null) {
                            // Cập nhật số tiền hiện tại sau khi thêm khoản thu nhập mới
                            int soTienMoi = soTien; // Thay thế soTien bằng số tiền thu thực tế
                            double soDuHienTai = viTien.getSoDuHienTai();
                            double soDuMoi = soDuHienTai + soTienMoi;
                            viTien.setSoDuHienTai(soDuMoi);
                            // Cập nhật lại số dư mới vào cơ sở dữ liệu
                            boolean updateResult = viTienDAO.capNhatSoDuViTien(viTien);
                            if (updateResult) {
                                loadData();
                                Toast.makeText(getContext(), "Thêm thu nhập thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Cập nhật số dư thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Ví tiền không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Thêm thu nhập thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void loadData(){
        list = thuNhapDAO.layDanhSachThuNhap();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcvQLTN.setLayoutManager(layoutManager);
        thuNhapAdapter = new QuanLyThuNhapAdapter(getContext(),list,thuNhapDAO);
        rcvQLTN.setAdapter(thuNhapAdapter);
        thuNhapAdapter.notifyDataSetChanged();
    }
}