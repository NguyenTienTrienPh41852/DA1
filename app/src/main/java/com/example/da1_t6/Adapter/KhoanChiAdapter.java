package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.R;

import java.util.List;

public class KhoanChiAdapter extends BaseAdapter {
    Context context;
    List<KhoanChi> list;
    public KhoanChiAdapter(Context context, List<KhoanChi> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getMaKC();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        view = inflater.inflate(R.layout.item_lvkhoanchi, viewGroup, false);
        TextView khoanChi = view.findViewById(R.id.tv_lvkhoanchi);
        KhoanChi kc = list.get(i);
        khoanChi.setText(kc.getTenKC());
        return view;
    }
}
