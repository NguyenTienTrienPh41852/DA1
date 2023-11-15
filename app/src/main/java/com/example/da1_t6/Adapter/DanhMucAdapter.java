package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Model.DanhMuc;
import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.R;

import java.util.ArrayList;
import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    Context context;
    List<DanhMuc> danhMucList;
    List<KhoanChi> khoanChiList;
    public DanhMucAdapter(Context context, List<DanhMuc> danhMucList, List<KhoanChi> khoanChiList) {
        this.context = context;
        this.danhMucList = danhMucList;
        this.khoanChiList = khoanChiList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khoanchi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc danhMuc = danhMucList.get(position);
        holder.tv_tenDanhMuc.setText(danhMuc.getTenDanhmuc());
        List<KhoanChi> khoanChiCon = new ArrayList<>();
        for (KhoanChi khoanChi : khoanChiList){
            if(khoanChi.getMaDanhMuc() == danhMuc.getMaDanhMuc()){
                khoanChiCon.add(khoanChi);
            }
        }
        KhoanChiAdapter khoanChiAdapter = new KhoanChiAdapter(context, khoanChiCon);
        holder.listViewKhoanChi.setAdapter(khoanChiAdapter);
    }

    @Override
    public int getItemCount() {
        return danhMucList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenDanhMuc;
        ListView listViewKhoanChi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenDanhMuc = itemView.findViewById(R.id.tv_tendanhmuc);
            listViewKhoanChi = itemView.findViewById(R.id.lv_khoanchi);
        }
    }
}
