package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.da1_t6.Model.DanhMuc;
import com.example.da1_t6.R;

import java.util.List;

public class adapterSPDanhMuc extends BaseAdapter {
    List<DanhMuc> listDM;
    Context context;

    public adapterSPDanhMuc(List<DanhMuc> listDM, Context context) {
        this.listDM = listDM;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listDM.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return listDM.get(position).getMaDanhMuc();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.item_lvkhoanchi,parent,false);
        TextView tvDanhMuc = convertView.findViewById(R.id.tv_lvkhoanchi);

        tvDanhMuc.setText(listDM.get(position).getTenDanhmuc());

        return convertView;
    }
}
