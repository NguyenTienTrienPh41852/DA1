package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.R;

import java.util.List;

public class QuanLyThuNhapAdapter extends RecyclerView.Adapter<QuanLyThuNhapAdapter.ViewHolder> {
    private Context context;
    private List<ThuNhap> list;

    ThuNhapDAO thuNhapDAO;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thunhap,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogUpdateThuNhap(list.get(holder.getAdapterPosition()));
                return true;
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa thu nhập");
                builder.setMessage("Bạn có chắc muốn xóa mục thu nhập này chứ ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = thuNhapDAO.xoaThuNhap(list.get(holder.getAdapterPosition()).getMaKhoanThu());
                        switch (check){
                            case 1:
                                loadData();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
//                            case -1:
//                                Toast.makeText(context, "Không xóa được sách này vì đang còn tồn tại trong phiếu mượn", Toast.LENGTH_SHORT).show();
//                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtLuong,txtSoTien,txtNgay,txtLoaiVi,txtGhiChu;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLuong = itemView.findViewById(R.id.tv_tn_luong);
            txtSoTien = itemView.findViewById(R.id.tv_tn_sotien);
            txtNgay = itemView.findViewById(R.id.tv_tn_ngay);
            txtLoaiVi = itemView.findViewById(R.id.tv_tn_loaivi);
            txtGhiChu = itemView.findViewById(R.id.tv_tn_ghichu);
            btnDelete = itemView.findViewById(R.id.btn_tn_delete);

        }
    }
    private void dialogUpdateThuNhap(ThuNhap thuNhap){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_updatethunhap,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText ed_sotien = view.findViewById(R.id.ud_tn_sotien);
        EditText ed_ghichu = view.findViewById(R.id.ud_tn_ghichu);
        Spinner spn_tenGD = view.findViewById(R.id.spn_ud_tn_tengd);
        LinearLayout li_ngay = view.findViewById(R.id.ud_tn_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_ud_tn_loaivi);
        Button btn_save = view.findViewById(R.id.btn_tn_update);



    }
    private void loadData(){
        list.clear();
        list = thuNhapDAO.layDanhSachThuNhap();
        notifyDataSetChanged();
    }
}
