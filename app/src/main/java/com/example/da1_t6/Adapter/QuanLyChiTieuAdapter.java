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

import com.example.da1_t6.DAO.ChiTieuDAO;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.R;

import java.util.List;

public class QuanLyChiTieuAdapter extends RecyclerView.Adapter<QuanLyChiTieuAdapter.ViewHolder> {
    private Context context;
    private List<ChiTieu> list;

    ChiTieuDAO chiTieuDAO;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_chitieu,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogUpdateChiTieu(list.get(holder.getAdapterPosition()));
                return true;
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa chi tiêu");
                builder.setMessage("Bạn có chắc muốn xóa mục chi tiêu này chứ ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = chiTieuDAO.xoaChiTieu(list.get(holder.getAdapterPosition()).getMaCT());
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
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtChiMuc,txtSoTien,txtNgay,txtLoaiVi,txtGhiChu;
        ImageButton btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtChiMuc = itemView.findViewById(R.id.tv_ct_chimuc);
            txtSoTien = itemView.findViewById(R.id.tv_ct_sotien);
            txtNgay = itemView.findViewById(R.id.tv_ct_ngay);
            txtLoaiVi = itemView.findViewById(R.id.tv_ct_loaivi);
            txtGhiChu = itemView.findViewById(R.id.tv_ct_ghichu);
            btnDelete = itemView.findViewById(R.id.btn_ct_delete);
            btnDelete = itemView.findViewById(R.id.btn_ct_delete);
        }
    }
    private void dialogUpdateChiTieu(ChiTieu chiTieu){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_updatechitieu,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText ed_sotien = view.findViewById(R.id.ud_ct_sotien);
        EditText ed_ghichu = view.findViewById(R.id.ud_ct_ghichu);
        Spinner spn_chondanhmuc = view.findViewById(R.id.spn_ud_ct_danhmuc);
        Spinner spn_chonkhoanchi = view.findViewById(R.id.spn_ud_ct_khoanchi);
        LinearLayout li_ngay = view.findViewById(R.id.ud_ct_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_ud_ct_loaivi);
        Button btn_save = view.findViewById(R.id.btn_ct_update);
    }
    private void loadData(){
        list.clear();
        list = chiTieuDAO.layDanhSachChiTieu();
        notifyDataSetChanged();
    }
}
