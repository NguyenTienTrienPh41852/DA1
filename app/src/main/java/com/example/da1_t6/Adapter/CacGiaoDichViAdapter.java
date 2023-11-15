package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.R;

import java.util.List;
import java.util.Objects;

public class CacGiaoDichViAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    static final int TYPE_CHI_TIEU = 0;
    static final int TYPE_THU_NHAP = 1;
    Context context;
    List<Objects> list;

    public CacGiaoDichViAdapter(Context context, List<Objects> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        Object object = list.get(position);
        if (object instanceof ChiTieu){
            return TYPE_CHI_TIEU;
        } else if (object instanceof ThuNhap){
            return TYPE_THU_NHAP;
        }


        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();

        switch (viewType){
            case TYPE_CHI_TIEU:
                View chiTieuView = inflater.inflate(R.layout.item_giao_dich_thang_chi_chi_tieu,parent,false);
                return new ChiTieuViewHolder(chiTieuView);

            case TYPE_THU_NHAP:
                View thuNhapView = inflater.inflate(R.layout.item_giao_dich_thang_thu_nhap,parent,false);
                return new ChiTieuViewHolder(thuNhapView);

            default:
                throw new IllegalArgumentException("Khong xac dinh");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object obj = list.get(position);
        switch (holder.getItemViewType()){
            case TYPE_CHI_TIEU:
                ((ChiTieuViewHolder) holder).bindChiTieu((ChiTieu) obj);
                break;
            case TYPE_THU_NHAP:
                ((ThuNhapViewHolder) holder).bindThuNhap((ThuNhap) obj);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ChiTieuViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenGiaoDichChiTieu, tvGiaChiTieu;
        public ChiTieuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenGiaoDichChiTieu = itemView.findViewById(R.id.tv_ten_giao_dich_chi_tieu);
            tvGiaChiTieu = itemView.findViewById(R.id.tv_gia_giao_dich_chi_tieu);
        }
        public void bindChiTieu(ChiTieu chiTieu){
            tvTenGiaoDichChiTieu.setText(chiTieu.getTenKC());
            tvGiaChiTieu.setText(chiTieu.getSoTienChi()+"");
        }
    }

    public static class ThuNhapViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenGiaoDichThuNhap, tvGiaThuNhap;
        public ThuNhapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenGiaoDichThuNhap = itemView.findViewById(R.id.tv_ten_giao_dich_thu_nhap);
            tvGiaThuNhap = itemView.findViewById(R.id.tv_gia_giao_dich_thu_nhap);
        }
        public void bindThuNhap(ThuNhap thuNhap){
            tvTenGiaoDichThuNhap.setText(thuNhap.getTenKhoanThu());
            tvGiaThuNhap.setText(thuNhap.getSoTienThu()+"");
        }
    }
}
