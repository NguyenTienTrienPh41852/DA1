package com.example.da1_t6.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private boolean isCalculator = false;
    TextView tvResultNummber;
    EditText ed_sotien;
    ChiTieu chiTieu;
    AppCompatImageButton btnOK;
    List<KhoanChi> listKC;
    private String kqTinhToan = null;
    private StringBuilder numberBuilder = new StringBuilder();
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
        chiTieu = new ChiTieu();
        loadData();
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddChiTieu(Gravity.CENTER);
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


    private void dialogAddChiTieu(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_themchitieu);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = gravity;

        window.setAttributes(params);
        dialog.setCancelable(true);
        dialog.show();
        TextInputLayout in_errSoTien = dialog.findViewById(R.id.in_addSoTien);
        ed_sotien = dialog.findViewById(R.id.add_ct_sotien);
        EditText ed_ghichu = dialog.findViewById(R.id.add_ct_ghichu);
        Spinner spn_danhmuc = dialog.findViewById(R.id.spn_add_ct_danhmuc);
        Spinner spn_khoanchi = dialog.findViewById(R.id.spn_add_ct_khoanchi);
        LinearLayout li_ngay = dialog.findViewById(R.id.add_ct_ngay);
        TextView tvNgay = dialog.findViewById(R.id.tv_add_ct_ngay);
        Spinner spn_loaivi = dialog.findViewById(R.id.spn_add_ct_loaivi);
        Button btn_save = dialog.findViewById(R.id.btn_ct_add);
        listKC = new ArrayList<>();

        ed_sotien.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){
                    openDialogMayTinh();
                    tvResultNummber.setText(ed_sotien.getText().toString());
                    return true;
                }
                return false;
            }
        });

        List<DanhMuc> listDM = danhMucDAO.layDanhSachDanhMuc();
        adapterSPDanhMuc spDanhMuc = new adapterSPDanhMuc(listDM,getContext());
        spn_danhmuc.setAdapter(spDanhMuc);
        final adapterSPKhoanChi[] spKhoanChi = new adapterSPKhoanChi[1];
        int index_khoanchi = 0;
        int finalIndex_khoanchi = index_khoanchi;
        spn_khoanchi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //set lại mã khoản chi
                chiTieu.setMaKC(listKC.get(i).getMaKC());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chiTieu.setMaDM(listDM.get(position).getMaDanhMuc());
//                Log.e("TAG","maDM: "+listDM.get(position).getMaDanhMuc());
                listKC.clear();
                listKC.addAll(khoanChiDAO.layDanhSachKhoanChiTheoDM(chiTieu.getMaDM()+""));
                spKhoanChi[0] = new adapterSPKhoanChi(listKC,getContext());
                spn_khoanchi.setAdapter(spKhoanChi[0]);
                spn_khoanchi.setSelection(finalIndex_khoanchi);
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
                        String formattedDay = String.format(Locale.getDefault(), "%02d", dayOfMonth);
                        String formattedMonth = String.format(Locale.getDefault(), "%02d", (month + 1));
                        tvNgay.setText(formattedDay + "-" + formattedMonth + "-" + year);
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
                    String ngay = tvNgay.getText().toString();
                    int loaiVi = spn_loaivi.getSelectedItemPosition()+1;
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
                            chiTieu.setThoiGianChi(chuyenDoiNgayPhuHop(ngay));
                            chiTieu.setMaVi(loaiVi);
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
    public void openDialogMayTinh(){
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View v = getLayoutInflater().inflate(R.layout.dialog_maytinh,null);
        dialog.setContentView(v);

        Button btnSo0 = dialog.findViewById(R.id.btn_so0);
        Button btnSo1 = dialog.findViewById(R.id.btn_so1);
        Button btnSo2 = dialog.findViewById(R.id.btn_so2);
        Button btnSo3 = dialog.findViewById(R.id.btn_so3);
        Button btnSo4 = dialog.findViewById(R.id.btn_so4);
        Button btnSo5 = dialog.findViewById(R.id.btn_so5);
        Button btnSo6 = dialog.findViewById(R.id.btn_so6);
        Button btnSo7 = dialog.findViewById(R.id.btn_so7);
        Button btnSo8 = dialog.findViewById(R.id.btn_so8);
        Button btnSo9 = dialog.findViewById(R.id.btn_so9);
        Button btnDauCham = dialog.findViewById(R.id.btn_daucham);

        Button btnXoaAll = dialog.findViewById(R.id.btn_xoahet);
        Button btn3so0 = dialog.findViewById(R.id.btn_3so0);
        Button btnDauTru = dialog.findViewById(R.id.btn_dautru);
        Button btnDauCong = dialog.findViewById(R.id.btn_daucong);
        Button btnDauNhan = dialog.findViewById(R.id.btn_daunhan);
        Button btnDauChia = dialog.findViewById(R.id.btn_dauchia);
        ImageButton btnXoa = dialog.findViewById(R.id.btn_xoa);
        btnOK = dialog.findViewById(R.id.btn_ok);

        tvResultNummber = dialog.findViewById(R.id.tv_hienthiso);

        btnXoa.setOnClickListener(v1 -> deleteButton());
        btnSo0.setOnClickListener(v1 -> onNumberClick("0"));
        btnSo1.setOnClickListener(v1 -> onNumberClick("1"));
        btnSo2.setOnClickListener(v1 -> onNumberClick("2"));
        btnSo3.setOnClickListener(v1 -> onNumberClick("3"));
        btnSo4.setOnClickListener(v1 -> onNumberClick("4"));
        btnSo5.setOnClickListener(v1 -> onNumberClick("5"));
        btnSo6.setOnClickListener(v1 -> onNumberClick("6"));
        btnSo7.setOnClickListener(v1 -> onNumberClick("7"));
        btnSo8.setOnClickListener(v1 -> onNumberClick("8"));
        btnSo9.setOnClickListener(v1 -> onNumberClick("9"));

        btn3so0.setOnClickListener(v1 -> onButtonClick(v1));

        btnDauCham.setOnClickListener(v1 -> onButtonClick(v1));
        btnDauCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ButtonCLick","Dau Cong");
                btnOK.setImageResource(R.drawable.img_7);
                isCalculator = true;
                onButtonClick(v);
            }
        });
        btnDauTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOK.setImageResource(R.drawable.img_7);
                isCalculator = true;
                onButtonClick(v);
            }
        });
        btnDauNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOK.setImageResource(R.drawable.img_7);
                isCalculator = true;
                onButtonClick(v);
            }
        });
        btnDauChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOK.setImageResource(R.drawable.img_7);
                isCalculator = true;
                onButtonClick(v);
            }
        });
        btnXoaAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearResult();

            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCalculator){
                    calculateResult();
                    isCalculator = false;
                    btnOK.setImageResource(R.drawable.iconv);
                } else {
                    if (ed_sotien != null){
                        ed_sotien.setText(kqTinhToan);
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.show();
    }
    private void onNumberClick(String view){
        numberBuilder.append(view);
        tvResultNummber.setText(numberBuilder.toString());
        isCalculator = true;
    }
    private void appendResult(String value){
        numberBuilder.append(value);
        tvResultNummber.setText(numberBuilder.toString());
        isCalculator = true;
    }
    private void onButtonClick(View view){
        if (view instanceof ImageButton){
            onButtonClick( (ImageButton) view) ;
        } else if (view instanceof Button){

            Button button = (Button) view;
            String btnText = button.getText().toString();

            if (isOperator(btnText)) {
                appendResult(btnText);
            } else {
                switch (btnText) {
                    case "C":
                        clearResult();
                        break;
                    case "000":
                        onNumberClick("000");
                        break;
                    case ".":
                        appendResult(".");
                        break;
                    default:
                        appendResult(btnText);
                        break;
                }
            }
        }
    }
    private boolean isOperator(String btnText){
        return btnText.equals("+") || btnText.equals("-") || btnText.equals("X") || btnText.equals("÷") ;
    }
    private void deleteButton(){
        if (isCalculator){
            String currentExpress = tvResultNummber.getText().toString();
            if (!currentExpress.isEmpty()){
                String newExpress = currentExpress.substring(0,currentExpress.length() - 1);
                tvResultNummber.setText(newExpress);
                numberBuilder.setLength(0);
                numberBuilder.append(newExpress);
            }
        }
    }
    private void clearResult(){
        tvResultNummber.setText("0");
        numberBuilder.setLength(0);
        btnOK.setImageResource(R.drawable.iconv);
    }


    private void calculateResult() {
        try {
            String expression = numberBuilder.toString();

            // Kiểm tra toán tử
            if (expression.contains("+") || expression.contains("-") ||
                    expression.contains("X") || expression.contains("÷")) {

                isCalculator = true;
                btnOK.setImageResource(R.drawable.img_7);

                // Tách số và toán tử
                String[] parts;
                if (expression.contains("+")) {
                    parts = expression.split("\\+");
                    // Thực hiện phép cộng
                    double result = Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
                    numberBuilder.setLength(0);
                    numberBuilder.append(result);
                    tvResultNummber.setText(String.valueOf(result));
                } else if (expression.contains("-")) {
                    parts = expression.split("-");
                    // Thực hiện phép trừ
                    if (parts.length == 2) {
                        double firstOperand = Double.parseDouble(parts[0]);
                        double secondOperand = Double.parseDouble(parts[1]);
                        double result = firstOperand - secondOperand;
                        numberBuilder.setLength(0);
                        numberBuilder.append(result);
                        tvResultNummber.setText(String.valueOf(result));
                    } else {
                        // Xử lý trường hợp không có đúng hai phần trong phép trừ
                        clearResult();
                    }
                } else if (expression.contains("X")) {
                    parts = expression.split("X");
                    // Thực hiện phép nhân
                    double result = Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
                    numberBuilder.setLength(0);
                    numberBuilder.append(result);
                    tvResultNummber.setText(String.valueOf(result));
                } else if (expression.contains("÷")) {
                    parts = expression.split("÷");
                    // Thực hiện phép chia
                    if (!parts[1].equals("0")) {
                        double result = Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
                        numberBuilder.setLength(0);
                        numberBuilder.append(result);
                        tvResultNummber.setText(String.valueOf(result));
                    } else {
                        // Xử lý trường hợp chia cho 0
                        clearResult();
                    }
                }
            } else {
                // Trường hợp không có toán tử
                isCalculator = false;
                btnOK.setImageResource(R.drawable.iconv);
                tvResultNummber.setText(expression);
            }

            kqTinhToan = tvResultNummber.getText().toString();
        } catch (NumberFormatException e) {
            clearResult();
        } catch (ArithmeticException e) {
            clearResult();
        } catch (Exception e) {
            clearResult();
        }

    }
    private String chuyenDoiNgayPhuHop(String ngayXuatStr) {
        String[] ngayThangNam = ngayXuatStr.split("-");
        String nam = ngayThangNam[2];
        String thang = ngayThangNam[1];
        String ngay = ngayThangNam[0];
        return nam + "-" + thang + "-" + ngay;
    }
}