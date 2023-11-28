package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_t6.DAO.BangDiemDAO;
import com.example.da1_t6.DAO.MonHocDAO;
import com.example.da1_t6.Model.BangDiem;
import com.example.da1_t6.R;

import java.util.List;

public class BangDiemAdapter extends BaseAdapter {
    Context context;
    List<BangDiem> listB;
    BangDiemDAO bangDiemDAO;

    public BangDiemAdapter(Context context, List<BangDiem> listB) {
        this.context = context;
        this.listB = listB;
    }

    @Override
    public int getCount() {
        return listB.size();
    }

    @Override
    public Object getItem(int position) {
        return listB.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null){
            v = View.inflate(context, R.layout.item_diem_mon_hoc,null);
        }else {
            v = convertView;
        }

        BangDiem bangDiem = listB.get(position);
        TextView tvTenDauDiem = v.findViewById(R.id.tv_ten_dau_diem);
        TextView tvTrongSo = v.findViewById(R.id.tv_trong_so);
        TextView tvDiem = v.findViewById(R.id.tv_diem);

        tvTenDauDiem.setText(bangDiem.getTenDauDiem());
        tvTrongSo.setText(bangDiem.getTrongSo()+"");
        tvDiem.setText(bangDiem.getDiem()+"");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogEdit(bangDiem,Gravity.CENTER);
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDialogDel(position);
                return false;
            }
        });

        return v;
    }
    private void openDialogEdit(BangDiem bangDiem,int gravity){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_diem_mon_hoc);

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

        EditText edTenDauDiem = dialog.findViewById(R.id.ed_ten_dau_diemm);
        EditText edTrongSo = dialog.findViewById(R.id.ed_trong_soo);
        EditText edDiem = dialog.findViewById(R.id.ed_diemm);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        edTenDauDiem.setText(bangDiem.getTenDauDiem());
        edTrongSo.setText(bangDiem.getTrongSo()+"");
        edDiem.setText(bangDiem.getDiem()+"");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDauDiem = edTenDauDiem.getText().toString();
                double trongSo = Double.parseDouble(edTrongSo.getText().toString());
                double diem = Double.parseDouble(edDiem.getText().toString());

                BangDiem bangDiem_new = new BangDiem(tenDauDiem,trongSo,diem);
                bangDiem_new.setMaBangDiem(bangDiem.getMaBangDiem());
                bangDiem_new.setIDmonHoc(bangDiem.getIDmonHoc());
                bangDiemDAO = new BangDiemDAO(context);

                int kq = bangDiemDAO.UpdateBangDiem(bangDiem_new);

                if (kq>0){

                    int IDmonHoc = bangDiem.getIDmonHoc();
                    List<BangDiem> updatedList = bangDiemDAO.layDanhSachBangDiemTheoMonHoc(IDmonHoc);

                    if (!updatedList.isEmpty()) {
                        Log.d("DanhSachTruocCapNhat", listB.toString());
                        listB.clear();
                        listB.addAll(updatedList);
                        Log.d("DanhSachSauCapNhat", listB.toString());
                    }
                    dialog.dismiss();
                    Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Khong thanh cong", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });
        dialog.show();
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
                int idDel = listB.get(i).getMaBangDiem();

                bangDiemDAO = new BangDiemDAO(context);
                int idMonHoc = bangDiemDAO.layIDMonHocTuBangDiem(idDel);
                int kqBangDiem = bangDiemDAO.DeleteBangDiem(idDel);
                if (idMonHoc != -1) {
                    if (kqBangDiem > 0) {
                        List<BangDiem> updatedList = bangDiemDAO.layDanhSachBangDiemTheoMonHoc(idMonHoc);

                        if (!updatedList.isEmpty()) {
                            Log.d("DanhSachTruocCapNhat", listB.toString());
                            listB.clear();
                            listB.addAll(updatedList);
                            Log.d("DanhSachSauCapNhat", listB.toString());
                        }
                        dialog.dismiss();
                        Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
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
