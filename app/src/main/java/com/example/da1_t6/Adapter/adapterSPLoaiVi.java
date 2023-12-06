package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.da1_t6.Model.DanhMuc;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;

import java.util.List;

public class adapterSPLoaiVi extends BaseAdapter {
    List<ViTien> listVi;
    Context context;

    public adapterSPLoaiVi(List<ViTien> listDM, Context context) {
        this.listVi = listDM;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listVi.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.item_chon_loai_vi,parent,false);
        TextView tvDanhMuc = convertView.findViewById(R.id.tv_chonLoaiVi);
        tvDanhMuc.setText(listVi.get(position).getTenVi());
        return convertView;
    }
}
