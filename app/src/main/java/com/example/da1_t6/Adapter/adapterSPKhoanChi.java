package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.da1_t6.DAO.IconDAO;
import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class adapterSPKhoanChi extends BaseAdapter {
    List<KhoanChi> listDM;
    Context context;

    public adapterSPKhoanChi(List<KhoanChi> listDM, Context context) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.item_khoan_chi,parent,false);
        TextView tvDanhMuc = convertView.findViewById(R.id.tv_lvkhoanchi);
        tvDanhMuc.setText(listDM.get(position).getTenKC());
        CircleImageView icon = convertView.findViewById(R.id.icon);
        IconDAO iconDAO = new IconDAO(context);
        icon.setImageResource(iconDAO.icon(listDM.get(position).getMaIcon()).getIcon());
        Log.e("TAG", "TONtai: "+listDM.get(position).getMaIcon());
        return convertView;
    }
}
