package com.example.da1_t6.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class QuanLyViAdapter extends RecyclerView.Adapter<QuanLyViAdapter.viewHolder>{
    Context context;
    List<ViTien> listVi;

    int defaultSelectedVi = -1;
    ViTienDAO viTienDAO;
    private OnItemClickListener onItemClickListener;
    EditText edSoDu;
    private boolean isCalculator = false;
    private StringBuilder numberBuilder = new StringBuilder();
    private String number = null;
    TextView tvResultNummber;
    private String kqTinhToan = null;
    AppCompatImageButton btnOK;

    public QuanLyViAdapter(Context context, List<ViTien> listVi) {
        this.context = context;
        this.listVi = listVi;
    }

    public void setDefaultVi(int i) {
        if (i >= 0 && i < listVi.size()) {
            defaultSelectedVi = i;
            notifyDataSetChanged();
        }
    }
    public int getDefaultSelectedVi(){
        return defaultSelectedVi;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_chon_vi,parent,false);

        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ViTien viTien = listVi.get(holder.getAdapterPosition());

        holder.tvTenVi.setText(viTien.getTenVi());
        holder.tvSoTien.setText(formatTienViet(viTien.getSoDuHienTai()));

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDel(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listVi.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvTenVi, tvSoTien;
        ImageView imgIcon, imgDel;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgDel = itemView.findViewById(R.id.img_delete);
            tvTenVi = itemView.findViewById(R.id.tv_ten_vi);
            tvSoTien = itemView.findViewById(R.id.tv_so_tien);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int i);
    }
    public static String formatTienViet(double amount) {
        Locale locale = new Locale("vi", "VN"); // Locale cho tiền Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(amount);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void openDialogEdit(ViTien viTien,int gravity){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cap_nhat_vi);

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

        edTenVi.setText(viTien.getTenVi()+"");
        edSoDu.setText(viTien.getSoDuHienTai()+"");


        edSoDu.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP){
                    numberBuilder.delete(0,numberBuilder.length());
                    openDialogMayTinh();



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

                ViTienDAO viTienDAO1 = new ViTienDAO(context);
                ViTien viTien_new = new ViTien(tenVi,soDuBanDau,soDuHienTai);
                viTien_new.setMaVi(viTien.getMaVi());

                long kq = viTienDAO1.capNhatVi(viTien_new);

                if (kq != -1 ){
                    listVi.clear();
                    listVi.addAll(viTienDAO.layDanhSachViTien());

                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });
        dialog.show();

    }
    private void openDialogDel(final int i){

        if (viTienDAO == null){
            viTienDAO = new ViTienDAO(context);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa mục này không?");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.baseline_close_24);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int idDel = listVi.get(i).getMaVi();
                long kq = viTienDAO.xoaVi(String.valueOf(idDel));
                if (kq > 0) {
                    listVi.clear();
                    listVi.addAll(viTienDAO.layDanhSachViTien());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Khong thanh cong", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

