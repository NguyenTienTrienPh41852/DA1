package com.example.da1_t6.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.ThongKeDAO;
import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class QuanLyThuNhapAdapter extends RecyclerView.Adapter<QuanLyThuNhapAdapter.ViewHolder> {
    private Context context;
    private List<ThuNhap> list;
    private List<ViTien> listVT;
    ThuNhapDAO thuNhapDAO;
    ViTienDAO viTienDAO;
    ThongKeDAO thongKeDAO;
    DatePickerDialog datePickerDialog;
    EditText ed_sotien;
    private boolean isCalculator = false;
    private StringBuilder numberBuilder = new StringBuilder();
    private String number = null;
    TextView tvResultNummber;
    private String kqTinhToan = null;
    AppCompatImageButton btnOK;

    public QuanLyThuNhapAdapter(Context context, List<ThuNhap> list, ThuNhapDAO thuNhapDAO) {
        this.context = context;
        this.list = list;
        this.thuNhapDAO = thuNhapDAO;
        thongKeDAO = new ThongKeDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thu_nhap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtLuong.setText(String.valueOf(list.get(position).getTenKhoanThu()));
        holder.txtSoTien.setText(String.valueOf(list.get(position).getSoTienThu()));
        holder.txtNgay.setText(thongKeDAO.chuyenDoiDMY(list.get(position).getThoiGianThu()));
        holder.txtLoaiVi.setText(list.get(position).getTenVi());
        holder.txtGhiChu.setText(list.get(position).getGhiChu());
        viTienDAO = new ViTienDAO(context);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogUpdateThuNhap(list.get(holder.getAdapterPosition()), Gravity.CENTER);
                return true;
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa thu nhập");
                builder.setMessage("Bạn có chắc muốn xóa mục thu nhập này chứ ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = thuNhapDAO.xoaThuNhap(list.get(holder.getAdapterPosition()).getMaKhoanThu());
                        switch (check){
                            case 1:
                                loadData();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
//                            case -1:
//                                Toast.makeText(context, "Không xóa được sách này vì đang còn tồn tại trong phiếu mượn", Toast.LENGTH_SHORT).show();
//                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtLuong,txtSoTien,txtNgay,txtLoaiVi,txtGhiChu;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLuong = itemView.findViewById(R.id.tv_tn_luong);
            txtSoTien = itemView.findViewById(R.id.tv_tn_sotien);
            txtNgay = itemView.findViewById(R.id.tv_tn_ngay);
            txtLoaiVi = itemView.findViewById(R.id.tv_tn_loaivi);
            txtGhiChu = itemView.findViewById(R.id.tv_tn_ghichu);
            btnDelete = itemView.findViewById(R.id.btn_tn_delete);

        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void dialogUpdateThuNhap(ThuNhap thuNhap,int gravity){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cap_nhat_thu_nhap);

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

        ed_sotien = dialog.findViewById(R.id.ud_tn_sotien);
        EditText ed_ghichu = dialog.findViewById(R.id.ud_tn_ghichu);
        EditText ed_tenGD = dialog.findViewById(R.id.ud_tn_tengiaodich);
        LinearLayout li_ngay = dialog.findViewById(R.id.ud_tn_ngay);
        TextView tv_ngay = dialog.findViewById(R.id.ud_hienThi_ngay);
        Spinner spn_loaivi = dialog.findViewById(R.id.spn_ud_tn_loaivi);
        Button btn_save = dialog.findViewById(R.id.btn_tn_update);
        Button btn_huy = dialog.findViewById(R.id.btnCancelUdTn);

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ed_sotien.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP){

                    openDialogMayTinh();


                    return true;
                }
                return false;
            }
        });

        ed_sotien.setText(String.valueOf(thuNhap.getSoTienThu()));
        ed_ghichu.setText(thuNhap.getGhiChu());
        ed_tenGD.setText(thuNhap.getTenKhoanThu());
        tv_ngay.setText(thuNhap.getThoiGianThu());

        li_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDay = String.format(Locale.getDefault(), "%02d", dayOfMonth);
                        String formattedMonth = String.format(Locale.getDefault(), "%02d", (month + 1));
                        tv_ngay.setText(formattedDay + "-" + formattedMonth + "-" + year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        List<ViTien> listVi = viTienDAO.layDanhSachViTien();
        listVT = viTienDAO.layDanhSachViTien();
        adapterSPLoaiVi adapterSPLoaiVi = new adapterSPLoaiVi(listVi,context);
        spn_loaivi.setAdapter(adapterSPLoaiVi);
        int indexVi = 0;
        for (ViTien vi : listVi){
            if (thuNhap.getMaVi()==vi.getMaVi()){
                break;
            }
            indexVi++;
        }
        // hiển thị mã ví cũ lên spinner
        spn_loaivi.setSelection(indexVi);
        spn_loaivi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // set lại mã ví
                thuNhap.setMaVi(listVi.get(i).getMaVi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thuNhap.setSoTienThu(Double.parseDouble(ed_sotien.getText().toString()));
                thuNhap.setGhiChu(ed_ghichu.getText().toString());
                thuNhap.setTenKhoanThu(ed_tenGD.getText().toString());
                thuNhap.setMaVi(spn_loaivi.getSelectedItemPosition()+1);
                thuNhap.setThoiGianThu(tv_ngay.getText().toString());
                if (thuNhapDAO.capNhatThuNhap(thuNhap)){
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(thuNhapDAO.layDanhSachThuNhap());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void loadData(){
        list.clear();
        list = thuNhapDAO.layDanhSachThuNhap();
        notifyDataSetChanged();
    }
    public void openDialogMayTinh(){
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_may_tinh,null);
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
                        clearResult();
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
