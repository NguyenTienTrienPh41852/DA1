package com.example.da1_t6.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Adapter.QuanLyHoatDongAdapter;
import com.example.da1_t6.DAO.HoatDongDAO;
import com.example.da1_t6.Model.HoatDong;
import com.example.da1_t6.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class fragment_QuanLyHoatDong extends Fragment {
    List<HoatDong> listHD;
    QuanLyHoatDongAdapter hoatDongAdapter;
    HoatDongDAO hoatDongDAO;
    private Context mContext;
    TextView tvTienDo;
    RecyclerView rcHoatDong;
    List<HoatDong> listFilter = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_hoat_dong,container,false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnAddHoatDong;
        TextView tvNgay;
        ImageView imgNgayHoatDong;

        btnAddHoatDong = view.findViewById(R.id.btn_add_thoi_gian_bieu);
        rcHoatDong = view.findViewById(R.id.rc_thoi_gian_bieu);
        tvNgay = view.findViewById(R.id.tv_ngay_thoi_gian_bieu);
        tvTienDo = view.findViewById(R.id.tv_tien_do);
        imgNgayHoatDong = view.findViewById(R.id.img_ngay_thoi_gian_bieu);

        hoatDongDAO = new HoatDongDAO(getContext());
        listHD = hoatDongDAO.layDanhSachHoatDong();

        hoatDongAdapter = new QuanLyHoatDongAdapter(getContext(),listHD,hoatDongDAO);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcHoatDong.setLayoutManager(manager);

        rcHoatDong.setAdapter(hoatDongAdapter);

        btnAddHoatDong.setOnClickListener(v -> openDialogAdd(Gravity.CENTER));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        tvNgay.setText(sdf.format(c.getTime()));


        imgNgayHoatDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerImg(tvNgay);

            }
        });
        updateTienDo();

        hoatDongAdapter.setOnCheckedListener(new QuanLyHoatDongAdapter.OnCheckedListener() {
            @Override
            public void onCheckedChange(boolean isChecked) {
                updateTienDo();
            }
        });

    }
    private void showDataForDate(String ngay){
        listFilter.clear();
        listFilter.addAll(hoatDongDAO.layDanhSachHoatDongTheoNgay(ngay));
        hoatDongAdapter = new QuanLyHoatDongAdapter(getContext(),listFilter,hoatDongDAO);
        rcHoatDong.setAdapter(hoatDongAdapter);
        hoatDongAdapter.setOnCheckedListener(new QuanLyHoatDongAdapter.OnCheckedListener() {
            @Override
            public void onCheckedChange(boolean isChecked) {
                updateTienDoTheoNgay();
            }
        });
        updateTienDoTheoNgay();
        hoatDongAdapter.notifyDataSetChanged();
    }

    private void openDialogAdd(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_thoi_gian_bieu);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = gravity;

        window.setAttributes(params);
        dialog.setCancelable(true);

        EditText edTenHoatDong = dialog.findViewById(R.id.ed_ten_hoat_dong);
        EditText edMoTa = dialog.findViewById(R.id.ed_mo_ta);
        TextView tvFromTime = dialog.findViewById(R.id.tv_from_time);
        TextView tvToTime = dialog.findViewById(R.id.tv_to_time);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        TextView tvNgayHoatDong = dialog.findViewById(R.id.tv_ngay_dialog_hoat_dong);
        Spinner sp_status = dialog.findViewById(R.id.sp_status);

        tvFromTime.setOnClickListener(v1 -> showDialogTimePicker(tvFromTime));
        tvToTime.setOnClickListener(v1 -> showDialogTimePicker(tvToTime));

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        tvNgayHoatDong.setText(sdf.format(c.getTime()));

        tvNgayHoatDong.setOnClickListener(v1 -> showDatePicker(tvNgayHoatDong));


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenHoatDong = edTenHoatDong.getText().toString();
                String moTa = edMoTa.getText().toString();
                String fromTime = tvFromTime.getText().toString();
                String toTime = tvToTime.getText().toString();
                String ngayHoatDong = tvNgayHoatDong.getText().toString();
                int status = (int) sp_status.getSelectedItemId();

                if (tenHoatDong.isEmpty() || ngayHoatDong.isEmpty()){
                    Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                } else if (fromTime.isEmpty() || toTime.isEmpty()){
                    Toast.makeText(getContext(), "Bạn chưa chọn khung giờ", Toast.LENGTH_SHORT).show();
                    return;
                }

                HoatDong hoatDong = new HoatDong(tenHoatDong,moTa,fromTime,toTime,status,ngayHoatDong);

                long kq = hoatDongDAO.themHoatDong(hoatDong);

                if (kq != -1 ){
                    Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    listHD.clear();
                    listHD.addAll(hoatDongDAO.layDanhSachHoatDong());
                    hoatDongAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    updateTienDo();
                }
                else {
                    Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                }


            }
        });
        dialog.show();
    }
    private void showDialogTimePicker(final TextView textView){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeSelected = String.format("%02d:%02d",hourOfDay, minute);
                textView.setText(timeSelected);
            }
        }, hour, minute, true);

        timePickerDialog.show();

    }

    private void showDatePickerImg(final TextView textView){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                calendar.set(year, month,dayOfMonth);

                String selected_ngay = sdf.format(calendar.getTime());

                textView.setText(selected_ngay);

                showDataForDate(selected_ngay);
            }
        },year, month, day);
        datePickerDialog.show();

    }
    private void showDatePicker(final TextView textView){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                calendar.set(year, month,dayOfMonth);
                String selected_ngay = sdf.format(calendar.getTime());
                textView.setText(selected_ngay);

            }
        },year, month, day);
        datePickerDialog.show();

    }
    private void updateTienDo(){

        int tienDoHoanThanh = hoatDongAdapter.getCountTienDoHoanThanh();
        int itemHD = hoatDongAdapter.getItemCount();
        int progress = (int) ((float) tienDoHoanThanh / itemHD * 100);

        tvTienDo.setText("Tiến độ: "+progress+" %");
    }
    private void updateTienDoTheoNgay(){

        int tienDoHoanThanh = hoatDongAdapter.getCountTienDoHoanThanhTheoNgay(listFilter);
        int itemHD = listFilter.size();
        int progress = (int) ((float) tienDoHoanThanh / itemHD * 100);

        tvTienDo.setText("Tiến độ: "+progress+" %");
    }

}
