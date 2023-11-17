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
    private OnKhoanChiLongClickListener khoanChiLongClickListener;

    public KhoanChiAdapter(Context context, List<KhoanChi> list, OnKhoanChiLongClickListener listener) {
        this.context = context;
        this.list = list;
        this.khoanChiLongClickListener = listener;
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
        // Bắt sự kiện click vào item trong khoản chi
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (khoanChiLongClickListener != null) {
                    khoanChiLongClickListener.onKhoanChiLongClick(i); // Truyền vị trí của item được giữ
                    return true; // Trả về true để ngăn chặn sự kiện long click lan ra các view khác
                }
                return false;
            }
        });
        return view;
    }
    // Thêm một interface để bắt sự kiện click vào item khoản chi
    public interface OnKhoanChiLongClickListener {
        void onKhoanChiLongClick(int position);
    }
}
