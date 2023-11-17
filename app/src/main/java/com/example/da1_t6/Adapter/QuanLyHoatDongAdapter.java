package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Model.HoatDong;
import com.example.da1_t6.R;

import java.util.List;

public class QuanLyHoatDongAdapter extends RecyclerView.Adapter<QuanLyHoatDongAdapter.viewHolder>{
    Context context;
    List<HoatDong> listHD;
    public QuanLyHoatDongAdapter(Context context, List<HoatDong> listHD) {
        this.context = context;
        this.listHD = listHD;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_thoi_gian_bieu,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        HoatDong hoatDong = listHD.get(position);

        holder.tvTenHoatDong.setText(hoatDong.getTenHoatDong());
        holder.tvMoTa.setText("Mô tả: "+hoatDong.getMoTa());
        holder.tvThoiGian.setText("Thời gian: "+hoatDong.getThoiGianBatDau()+" đến "+hoatDong.getThoiGianKetThuc());

        if (hoatDong.getTrangThaiHoatDong() == 1){
            holder.tvStatus.setText("Đã hoàn thành");
            holder.tvStatus.setTextColor(Color.GREEN);
        } else {
            holder.tvStatus.setText("Chưa hoàn thành");
            holder.tvStatus.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return listHD.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvTenHoatDong, tvMoTa, tvThoiGian, tvStatus;
        ImageView imgDel;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenHoatDong = itemView.findViewById(R.id.tv_ten_hoat_dong);
            tvMoTa = itemView.findViewById(R.id.tv_mo_ta_hoat_dong);
            tvThoiGian = itemView.findViewById(R.id.tv_thoi_gian_hoat_dong);
            tvStatus = itemView.findViewById(R.id.tv_status_hoat_dong);
            imgDel = itemView.findViewById(R.id.img_delete);
        }
    }
}
