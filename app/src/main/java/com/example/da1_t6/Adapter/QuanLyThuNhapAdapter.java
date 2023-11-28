package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.KhoanChiDAO;
import com.example.da1_t6.DAO.ThongKeDAO;
import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class QuanLyThuNhapAdapter extends RecyclerView.Adapter<QuanLyThuNhapAdapter.ViewHolder> {
    private Context context;
    private List<ThuNhap> list;
    private List<ViTien> listVT;
    ThuNhapDAO thuNhapDAO;
    ViTienDAO viTienDAO;
    ThongKeDAO thongKeDAO;
    DatePickerDialog datePickerDialog;

    public QuanLyThuNhapAdapter(Context context, List<ThuNhap> list, ThuNhapDAO thuNhapDAO) {
        this.context = context;
        this.list = list;
        this.thuNhapDAO = thuNhapDAO;
        thongKeDAO = new ThongKeDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thunhap,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtLuong.setText(String.valueOf(list.get(position).getTenKhoanThu()));
        holder.txtSoTien.setText(String.valueOf(list.get(position).getSoTienThu()));
        holder.txtNgay.setText(thongKeDAO.chuyenDoiDMY(list.get(position).getThoiGianThu()));
        holder.txtLoaiVi.setText(list.get(position).getTenVi());
        holder.txtGhiChu.setText(list.get(position).getGhiChu());
        viTienDAO = new ViTienDAO(context);

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
        EditText ed_tenGD = view.findViewById(R.id.ud_tn_tengiaodich);
        LinearLayout li_ngay = view.findViewById(R.id.ud_tn_ngay);
        TextView tv_ngay = view.findViewById(R.id.tv_add_ct_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_ud_tn_loaivi);
        Button btn_save = view.findViewById(R.id.btn_tn_update);

        ed_sotien.setText(String.valueOf(thuNhap.getSoTienThu()));
        ed_ghichu.setText(thuNhap.getGhiChu());
        ed_tenGD.setText(thuNhap.getTenKhoanThu());
        tv_ngay.setText(thuNhap.getThoiGianThu());

        li_ngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDay = String.format(Locale.getDefault(), "%02d", dayOfMonth);
                        String formattedMonth = String.format(Locale.getDefault(), "%02d", (month + 1));
                        tv_ngay.setText(formattedDay + "-" + formattedMonth + "-" + year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        List<ViTien> listVi = viTienDAO.layDanhSachViTien();
        listVT = viTienDAO.layDanhSachViTien();
        adapterSPLoaiVi adapterSPLoaiVi = new adapterSPLoaiVi(listVi,context);
        spn_loaivi.setAdapter(adapterSPLoaiVi);
        int indexVi = 0;
        for (ViTien vi : listVi){
            if (thuNhap.getMaVi()==vi.getMaVi()){
                break;
            }
            indexVi++;
        }
        // hiển thị mã ví cũ lên spinner
        spn_loaivi.setSelection(indexVi);
        spn_loaivi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // set lại mã ví
                thuNhap.setMaVi(listVi.get(i).getMaVi());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thuNhap.setSoTienThu(Double.parseDouble(ed_sotien.getText().toString()));
                thuNhap.setGhiChu(ed_ghichu.getText().toString());
                thuNhap.setTenKhoanThu(ed_tenGD.getText().toString());
                thuNhap.setMaVi(spn_loaivi.getSelectedItemPosition()+1);
                thuNhap.setThoiGianThu(tv_ngay.getText().toString());
                if (thuNhapDAO.capNhatThuNhap(thuNhap)){
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(thuNhapDAO.layDanhSachThuNhap());
                    notifyDataSetChanged();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    private void loadData(){
        list.clear();
        list = thuNhapDAO.layDanhSachThuNhap();
        notifyDataSetChanged();
    }
}
