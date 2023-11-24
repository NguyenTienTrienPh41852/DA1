package com.example.da1_t6.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class QuanLyViAdapter extends RecyclerView.Adapter<QuanLyViAdapter.viewHolder>{
    Context context;
    List<ViTien> listVi;

    int defaultSelectedVi = -1;
    ViTienDAO viTienDAO;
    private OnItemClickListener onItemClickListener;

    public QuanLyViAdapter(Context context, List<ViTien> listVi) {
        this.context = context;
        this.listVi = listVi;
    }

    public void setDefaultVi(int i) {
        if (i >= 0 && i < listVi.size()) {
            defaultSelectedVi = i;
            notifyDataSetChanged();
        }
    }
    public int getDefaultSelectedVi(){
        return defaultSelectedVi;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
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

        ViTien viTien = listVi.get(holder.getAdapterPosition());

        holder.tvTenVi.setText(viTien.getTenVi());
        holder.tvSoTien.setText(formatTienViet(viTien.getSoDuHienTai()));

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDel(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
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
    public interface OnItemClickListener{
        void onItemClick(View view, int i);
    }
    public static String formatTienViet(double amount) {
        Locale locale = new Locale("vi", "VN"); // Locale cho tiền Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(amount);
    }

    private void openDialogEdit(){

    }
    private void openDialogDel(final int i){

        if (viTienDAO == null){
            viTienDAO = new ViTienDAO(context);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa mục này không?");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.baseline_close_24);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int idDel = listVi.get(i).getMaVi();
                long kq = viTienDAO.xoaVi(String.valueOf(idDel));
                if (kq > 0) {
                    listVi.clear();
                    listVi.addAll(viTienDAO.layDanhSachViTien());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Khong thanh cong", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

