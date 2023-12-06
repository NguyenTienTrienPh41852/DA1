package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.HoatDongDAO;
import com.example.da1_t6.Model.HoatDong;
import com.example.da1_t6.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class QuanLyHoatDongAdapter extends RecyclerView.Adapter<QuanLyHoatDongAdapter.viewHolder>{
    Context context;
    List<HoatDong> listHD;
    HoatDongDAO hoatDongDAO;
    private OnCheckedListener onCheckedListener;
    List<HoatDong> listFilter;


    public QuanLyHoatDongAdapter(Context context, List<HoatDong> listHD, HoatDongDAO hoatDongDAO) {
        this.context = context;
        this.listHD = listHD;
        this.hoatDongDAO = hoatDongDAO;
    }

    public void setOnCheckedListener(OnCheckedListener listener){
      this.onCheckedListener = listener;
    }

    public int getCountTienDoHoanThanh() {
        int count = 0;
        for (HoatDong hoatDong : listHD) {
            if (hoatDong.getTrangThaiHoatDong() == 1) {
                count++;
            }
        }
        return count;
    }
    public int getCountTienDoHoanThanhTheoNgay(List<HoatDong> hoatDongs) {
        int count = 0;
        for (HoatDong hoatDong : hoatDongs) {
            if (hoatDong.getTrangThaiHoatDong() == 1) {
                count++;
            }
        }
        return count;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_quan_ly_hoat_dong,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        HoatDong hoatDong = listHD.get(position);

        holder.tvIdHoatDong.setText(hoatDong.getMaHoatDong()+"");
        holder.tvTenHoatDong.setText(hoatDong.getTenHoatDong());
        holder.tvMoTa.setText("Mô tả: "+hoatDong.getMoTa());
        holder.tvThoiGian.setText("Thời gian: Từ "+hoatDong.getThoiGianBatDau()+" đến "+hoatDong.getThoiGianKetThuc());
        holder.tvNgayHoatDong.setText("Ngày: "+hoatDong.getNgay());


        if (listHD.get(position).getTrangThaiHoatDong() == 1){
            holder.cb_check_status.setChecked(true);
            holder.tvStatus.setText("Đã hoàn thành");
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green_dam));
        } else {
            holder.cb_check_status.setChecked(false);
            holder.tvStatus.setText("Chưa hoàn thành");
            holder.tvStatus.setTextColor(Color.RED);
        }



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDialogEdit(hoatDong, Gravity.CENTER);
                return true;
            }
        });

        holder.imgDel.setOnClickListener(v -> openDialogDel(position));

        holder.cb_check_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int id = listHD.get(holder.getAdapterPosition()).getMaHoatDong();
                boolean check = hoatDongDAO.UpdateStatus(id,holder.cb_check_status.isChecked());
                if (check) {
                    listHD.get(holder.getAdapterPosition()).setTrangThaiHoatDong(isChecked ? 1 : 0);
                    if (onCheckedListener != null) {
                        onCheckedListener.onCheckedChange(isChecked);
                    }

                    // bị lỗi khi cuộn
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            listHD.clear();
                            listHD.addAll(hoatDongDAO.layDanhSachHoatDong());
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    Toast.makeText(context, "zzzz", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        return listHD.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvTenHoatDong, tvMoTa, tvThoiGian, tvStatus, tvNgayHoatDong,tvIdHoatDong;
        ImageView imgDel;
        CheckBox cb_check_status;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenHoatDong = itemView.findViewById(R.id.tv_ten_hoat_dong);
            tvMoTa = itemView.findViewById(R.id.tv_mo_ta_hoat_dong);
            tvThoiGian = itemView.findViewById(R.id.tv_thoi_gian_hoat_dong);
            tvStatus = itemView.findViewById(R.id.tv_status_hoat_dong);
            imgDel = itemView.findViewById(R.id.img_delete);
            cb_check_status = itemView.findViewById(R.id.cb_status_hoat_dong);
            tvNgayHoatDong = itemView.findViewById(R.id.tv_ngay_hoat_dong);
            tvIdHoatDong = itemView.findViewById(R.id.tv_id);
        }
    }

    private void openDialogEdit(HoatDong hoatDong, int gravity) {
        final Dialog dialog = new Dialog(context);
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

        EditText edTenHoatDong = dialog.findViewById(R.id.ed_ten_hoat_dong);
        EditText edMoTa = dialog.findViewById(R.id.ed_mo_ta);
        TextView tvFromTime = dialog.findViewById(R.id.tv_from_time);
        TextView tvToTime = dialog.findViewById(R.id.tv_to_time);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        TextView tvNgayHoatDong = dialog.findViewById(R.id.tv_ngay_dialog_hoat_dong);
        CheckBox cb_status = dialog.findViewById(R.id.cb_status);

        edTenHoatDong.setText(hoatDong.getTenHoatDong());
        edMoTa.setText(hoatDong.getMoTa());
        tvFromTime.setText(hoatDong.getThoiGianBatDau()+"");
        tvToTime.setText(hoatDong.getThoiGianKetThuc()+"");
        tvNgayHoatDong.setText(hoatDong.getNgay()+"");
        cb_status.setChecked(hoatDong.getTrangThaiHoatDong() == 1);

        tvNgayHoatDong.setOnClickListener(v1 -> showDatePicker(tvNgayHoatDong));
        tvFromTime.setOnClickListener(v1 -> showDialogTimePicker(tvFromTime));
        tvToTime.setOnClickListener(v1 -> showDialogTimePicker(tvToTime));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edTenHoatDong.getText().toString();
                String moTa = edMoTa.getText().toString();
                String from = tvFromTime.getText().toString();
                String to = tvToTime.getText().toString();
                String ngay = tvNgayHoatDong.getText().toString();
                int newStatus = cb_status.isChecked() ? 1 : 0;

                HoatDongDAO hoatDongDAO1 = new HoatDongDAO(context);

                HoatDong hoatDong_edit = new HoatDong(ten,moTa,from,to,newStatus,ngay);
                hoatDong_edit.setMaHoatDong(hoatDong.getMaHoatDong());

                long kq = hoatDongDAO1.capNhatHoatDong(hoatDong_edit);
                if (kq != -1){
                    listHD.clear();
                    listHD.addAll(hoatDongDAO1.layDanhSachHoatDong());
                    notifyDataSetChanged();
                    Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    private void openDialogDel(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa mục này không?");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.baseline_close_24);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int idDel = listHD.get(i).getMaHoatDong();
                long kq = hoatDongDAO.xoaHoatDong(idDel);
                if (kq > 0) {
                    listHD.clear();
                    listHD.addAll(hoatDongDAO.layDanhSachHoatDong());
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
    public interface OnCheckedListener{
        void onCheckedChange(boolean isChecked);
    }
    private void showDialogTimePicker(final TextView textView){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String timeSelected = String.format("%02d:%02d",hourOfDay, minute);
                textView.setText(timeSelected);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showDatePicker(final TextView textView){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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
}
