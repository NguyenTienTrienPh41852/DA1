package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;

import java.util.List;

public class QuanLyViAdapter extends RecyclerView.Adapter<QuanLyViAdapter.viewHolder>{
    Context context;
    List<ViTien> listVi;

    public QuanLyViAdapter(Context context, List<ViTien> listVi) {
        this.context = context;
        this.listVi = listVi;
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
        ViTien viTien = listVi.get(position);

        holder.tvTenVi.setText(viTien.getTenVi());
        holder.tvSoTien.setText(viTien.getSoDuBanDau()+"");

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
