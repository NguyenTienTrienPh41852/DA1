package com.example.da1_t6.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.IconDAO;
import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KhoanChiAdapter extends RecyclerView.Adapter<KhoanChiAdapter.ViewHolder> {
    Context context;
    List<KhoanChi> list;
    onKhoanChiLongClick onKhoanChiLongClick;

    public KhoanChiAdapter(Context context, List<KhoanChi> list, KhoanChiAdapter.onKhoanChiLongClick onKhoanChiLongClick) {
        this.context = context;
        this.list = list;
        this.onKhoanChiLongClick = onKhoanChiLongClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((Activity)context).
                getLayoutInflater().inflate(R.layout.item_khoan_chi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        IconDAO iconDAO = new IconDAO(context);
        holder.icon.setImageResource(iconDAO.icon(list.get(position).getMaIcon()).getIcon());
        holder.khoanChi.setText(list.get(position).getTenKC());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onKhoanChiLongClick != null){
                    onKhoanChiLongClick.onKhoanChiLongClick(position);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView khoanChi;
        CircleImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            khoanChi = itemView.findViewById(R.id.tv_lvkhoanchi);
            icon = itemView.findViewById(R.id.icon);
        }
    }
    public interface onKhoanChiLongClick {
        void onKhoanChiLongClick(int posittion);
    }
}
