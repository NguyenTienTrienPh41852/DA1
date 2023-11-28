package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.BangDiemDAO;
import com.example.da1_t6.DAO.HoatDongDAO;
import com.example.da1_t6.DAO.MonHocDAO;
import com.example.da1_t6.Model.HoatDong;
import com.example.da1_t6.Model.MonHoc;
import com.example.da1_t6.R;

import java.util.List;

public class MonHocAdapter extends RecyclerView.Adapter<MonHocAdapter.viewHolder>{
    Context context;
    List<MonHoc> listM;
    private OnItemClickListener listener;
    private MonHocDAO monHocDAO;

    public MonHocAdapter(Context context, List<MonHoc> listM) {
        this.context = context;
        this.listM = listM;
    }
    public void setListener (OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.item_mon_hoc,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MonHoc monHoc = listM.get(position);

        holder.tvTenMonHoc.setText(monHoc.getTenMonHoc()+"");
        holder.tvMaMonHoc.setText("("+monHoc.getMaMonHoc()+")");
        holder.tvTenLop.setText(" - "+monHoc.getTenLop());


        BangDiemDAO bangDiemDAO = new BangDiemDAO(context);

        double diemTB;
        diemTB = bangDiemDAO.getDiemTrungBinh(monHoc.getIDMonHoc());

        String diemTrungBinhFormatted = String.format("%.1f", diemTB);
        holder.tvDiemTrungBinh.setText(diemTrungBinhFormatted);

        if (diemTB >= 5.0){
            holder.tvTrangThai.setText("Passed");
            holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.green_dam));
        } else {
            holder.tvTrangThai.setText("Failed");
            holder.tvTrangThai.setTextColor(Color.RED);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDialogEdit(monHoc, Gravity.CENTER);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClick(listM.get(position).getIDMonHoc(),monHoc);
                }
            }
        });
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDel(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listM.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvIDmonHoc, tvTenMonHoc, tvMaMonHoc, tvTenLop, tvDiemTrungBinh, tvTrangThai;
        ImageView imgDel;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenLop = itemView.findViewById(R.id.tv_ten_lop_hoc);
            tvMaMonHoc = itemView.findViewById(R.id.tv_ma_mon_hoc);
            tvTenMonHoc = itemView.findViewById(R.id.tv_ten_mon_hoc);
            tvDiemTrungBinh = itemView.findViewById(R.id.tv_diem_trung_binh);
            tvTrangThai = itemView.findViewById(R.id.tv_status_mon_hoc);
            imgDel = itemView.findViewById(R.id.img_delete);
        }
    }
    private void openDialogDel(final int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xóa mục này không?");
        builder.setCancelable(true);
        builder.setIcon(R.drawable.baseline_close_24);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int idDel = listM.get(i).getIDMonHoc();

                monHocDAO = new MonHocDAO(context);
                int kqMonHoc = monHocDAO.DeleteMonHoc(idDel);

                BangDiemDAO bangDiemDAO = new BangDiemDAO(context);
                int kqBangDiem = bangDiemDAO.DeleteBangDiemTheoID(idDel);

                if (kqMonHoc > 0 || kqBangDiem > 0) {
                    listM.clear();
                    listM.addAll(monHocDAO.layDanhSachMonHoc());
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

    private void openDialogEdit(MonHoc monHoc,int gravity) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_mon_hoc);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = gravity;

        window.setAttributes(params);
        dialog.setCancelable(true);

        EditText edTenMonHoc = dialog.findViewById(R.id.ed_ten_mon);
        EditText edMaMon = dialog.findViewById(R.id.ed_ma_mon);
        EditText edTenLop = dialog.findViewById(R.id.ed_ten_lop);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        edTenMonHoc.setText(monHoc.getTenMonHoc());
        edMaMon.setText(monHoc.getMaMonHoc());
        edTenLop.setText(monHoc.getTenLop());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMon = edTenMonHoc.getText().toString();
                String maMon = edMaMon.getText().toString();
                String tenLop = edTenLop.getText().toString();

                MonHoc monHoc_new = new MonHoc(maMon,tenMon,tenLop);
                monHoc_new.setIDMonHoc(monHoc.getIDMonHoc());

                monHocDAO = new MonHocDAO(context);

                int kq = monHocDAO.UpdateMonHoc(monHoc_new);

                if (kq>0){
                    listM.clear();
                    listM.addAll(monHocDAO.layDanhSachMonHoc());
                    Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(context, "khong thanh cong", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });


        dialog.show();
    }

    public interface OnItemClickListener{
        void onItemClick(int IDmonHoc, MonHoc monHoc);
    }

}
