package com.example.da1_t6.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Adapter.CacGiaoDichViAdapter;
import com.example.da1_t6.Adapter.QuanLyViAdapter;
import com.example.da1_t6.DAO.ChiTieuDAO;
import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class fragment_QuanLyVi extends Fragment {
    LinearLayout linear_item_vi;
    QuanLyViAdapter viAdapter;
    List<ViTien> listVi;
    ViTienDAO viTienDAO;
    EditText edSoDu;

    CacGiaoDichViAdapter giaoDichViAdapter;
    List<Object> listGD;
    RecyclerView rcCacGiaoDich;
    private boolean isCalculator = false;
    private StringBuilder numberBuilder = new StringBuilder();
    private String number = null;
    TextView tvResultNummber;
    private String kqTinhToan = null;
    AppCompatImageButton btnOK;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_vi,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnAddVi = view.findViewById(R.id.btn_add_vi);

//        btnAddVi.setOnClickListener(v -> openDialogMayTinh());

        linear_item_vi = view.findViewById(R.id.linear_item_vi);
        linear_item_vi.setOnClickListener(v -> openDialogChonVi());

        btnAddVi.setOnClickListener(v -> openDialogAddVi(Gravity.CENTER));
        listGD = new ArrayList<>();
        listGD.addAll(listChiTieu());
        listGD.addAll(listThuNhap());

        rcCacGiaoDich = view.findViewById(R.id.rc_cac_giao_dich_thang);
        giaoDichViAdapter = new CacGiaoDichViAdapter(requireContext(),listGD);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcCacGiaoDich.setLayoutManager(manager);
        rcCacGiaoDich.setAdapter(giaoDichViAdapter);

        viTienDAO = new ViTienDAO(getContext());
        listVi = viTienDAO.layDanhSachViTien();
        viAdapter = new QuanLyViAdapter(getContext(),listVi);

        double tongTien = tinhTong();
        listVi.add(0, new ViTien("Tất cả ví", tongTien));
        ViTien viTong = listVi.get(0);
        updateViChon(viTong);
        viAdapter.setDefaultVi(0);
        int defaultSelectedVi = viAdapter.getDefaultSelectedVi();
        if (defaultSelectedVi != RecyclerView.NO_POSITION && defaultSelectedVi < listVi.size()) {
            ViTien defaultVi = listVi.get(defaultSelectedVi);
            updateViChon(defaultVi);
        }

        TextView tvTongChiTieuThang = view.findViewById(R.id.tv_chi_tieu_thang);
        TextView tvTongThuNhapThang = view.findViewById(R.id.tv_thu_nhap_thang);
        TextView tvSoDuThang = view.findViewById(R.id.tv_so_du_thang);

        ChiTieuDAO chiTieuDAO = new ChiTieuDAO(getContext());
        double tongChiTieu = chiTieuDAO.getTongChiTieu();
        tvTongChiTieuThang.setText(formatTienViet(tongChiTieu));

        ThuNhapDAO thuNhapDAO = new ThuNhapDAO(getContext());
        double tongThuNhap = thuNhapDAO.getTongThuNhap();
        tvTongThuNhapThang.setText(formatTienViet(tongThuNhap));

        double tongSoDu = viTienDAO.getTongSoDu();
        tvSoDuThang.setText(formatTienViet(tongSoDu));

    }

    private void openDialogChonVi(){
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_chon_vi,null);
        dialog.setContentView(v);

        RecyclerView rv_chon_vi = v.findViewById(R.id.rc_vi);
        viTienDAO = new ViTienDAO(getContext());
        listVi = viTienDAO.layDanhSachViTien();

        viAdapter = new QuanLyViAdapter(getContext(),listVi);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv_chon_vi.setLayoutManager(manager);

        rv_chon_vi.setAdapter(viAdapter);

        double tongTien = tinhTong();
        listVi.add(0, new ViTien(0,"Tất cả ví",tongTien));

        viAdapter.setOnItemClickListener(new QuanLyViAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ViTien viTien = listVi.get(i);
                updateViChon(viTien);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void openDialogAddVi(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_vi);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = gravity;

        window.setAttributes(params);
        dialog.setCancelable(true);

        EditText edTenVi = dialog.findViewById(R.id.ed_ten_vi);
        edSoDu = dialog.findViewById(R.id.ed_so_du);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        Spinner spChonIcon = dialog.findViewById(R.id.sp_chon_icon);

        edSoDu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){

                    openDialogMayTinh();

                    tvResultNummber.setText(edSoDu.getText().toString());
                    return true;
                }
                return false;
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenVi = edTenVi.getText().toString();
                int soDuBanDau = Integer.parseInt(edSoDu.getText().toString());
                int soDuHienTai = Integer.parseInt(edSoDu.getText().toString());

                if (tenVi.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }

                viTienDAO = new ViTienDAO(getContext());
                ViTien viTien = new ViTien(tenVi, soDuBanDau, soDuHienTai);
                long kq = viTienDAO.themVi(viTien);

                if (kq > 0){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    listVi.clear();
                    listVi.addAll(viTienDAO.layDanhSachViTien());
                    viAdapter.notifyDataSetChanged();
//                    linear_item_vi.notify();
                    if (!listVi.isEmpty()) {
                        ViTien viMoi = listVi.get(listVi.size() - 1); // Lấy ví mới thêm vào
                        updateViChon(viMoi); // Cập nhật thông tin hiển thị
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void updateViChon(ViTien viTien){
        TextView tvTenVi = getView().findViewById(R.id.tv_ten_vi);
        TextView tvSoDu = getView().findViewById(R.id.tv_so_tien);
        if (tvTenVi != null && tvSoDu != null && viTien != null){
            tvTenVi.setText(viTien.getTenVi());
            tvSoDu.setText(formatTienViet(viTien.getSoDuHienTai()));
        }
    }

    private double tinhTong(){
        double tongTien = 0;
        for (ViTien viTien : listVi){
            tongTien += viTien.getSoDuHienTai();
        }
        return tongTien;
    }
    public static String formatTienViet(double amount) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        String formattedAmount = currencyFormatter.format(Math.abs(amount));

        if (amount < 0) {
            return "-" + formattedAmount;
        } else {
            return formattedAmount;
        }
    }

    private List<Object> listChiTieu(){
        List<Object> listCT = new ArrayList<>();
        ChiTieuDAO chiTieuDAO = new ChiTieuDAO(getContext());
        List<ChiTieu> list = chiTieuDAO.layDanhSachChiTieu();
        listCT.addAll(list);
        return listCT;

    }
    private List<Object> listThuNhap(){

        List<Object> listTN = new ArrayList<>();

        ThuNhapDAO thuNhapDAO = new ThuNhapDAO(getContext());

        List<ThuNhap> list = thuNhapDAO.layDanhSachThuNhap();

        listTN.addAll(list);

        return listTN;
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
                    if (edSoDu != null){
                        edSoDu.setText(kqTinhToan);
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
    private String appendResult(String value){
        numberBuilder.append(value);
        tvResultNummber.setText(numberBuilder.toString());
        isCalculator = true;
        return value;
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
}

