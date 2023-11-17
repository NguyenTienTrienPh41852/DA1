package com.example.da1_t6.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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

import com.example.da1_t6.DAO.ChiTieuDAO;
import com.example.da1_t6.DAO.DanhMucDAO;
import com.example.da1_t6.DAO.KhoanChiDAO;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.DanhMuc;
import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QuanLyChiTieuAdapter extends RecyclerView.Adapter<QuanLyChiTieuAdapter.ViewHolder> {
    private Context context;
    private List<ChiTieu> list;
    private List<DanhMuc> listDM;
    private List<KhoanChi> listKC;
    private List<ViTien> listVT;
    DatePickerDialog datePickerDialog;
    ChiTieuDAO chiTieuDAO;
    ChiTieu chiTieu;
    DanhMucDAO danhMucDAO;
    KhoanChiDAO khoanChiDAO;
    ViTienDAO viTienDAO;
    QuanLyChiTieuAdapter chiTieuAdapter;


    public QuanLyChiTieuAdapter(Context context, List<ChiTieu> list, ChiTieuDAO chiTieuDAO) {
        this.context = context;
        this.list = list;
        this.chiTieuDAO = chiTieuDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_chitieu, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtChiMuc.setText(String.valueOf(list.get(position).getTenKC()));
        holder.txtSoTien.setText(String.valueOf(list.get(position).getSoTienChi()));
        holder.txtNgay.setText(list.get(position).getThoiGianChi());
        holder.txtLoaiVi.setText(list.get(position).getTenVi());
        holder.txtGhiChu.setText(list.get(position).getGhiChu());
        danhMucDAO = new DanhMucDAO(context);
        viTienDAO = new ViTienDAO(context);
        khoanChiDAO = new KhoanChiDAO(context);
        listKC = new ArrayList<>();
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
                        switch (check) {
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
                builder.setNegativeButton("Hủy", null);
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtChiMuc, txtSoTien, txtNgay, txtLoaiVi, txtGhiChu;
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

    private void dialogUpdateChiTieu(ChiTieu chiTieu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_updatechitieu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText ed_sotien = view.findViewById(R.id.ud_ct_sotien);
        EditText ed_ghichu = view.findViewById(R.id.ud_ct_ghichu);
        Spinner spn_chondanhmuc = view.findViewById(R.id.spn_ud_ct_danhmuc);
        Spinner spn_chonkhoanchi = view.findViewById(R.id.spn_ud_ct_khoanchi);
        LinearLayout li_ngay = view.findViewById(R.id.ud_ct_ngay);
        TextView tvNgay = view.findViewById(R.id.tv_add_ct_ngay);
        Spinner spn_loaivi = view.findViewById(R.id.spn_ud_ct_loaivi);
        Button btn_save = view.findViewById(R.id.btn_ct_update);

        ed_sotien.setText(String.valueOf(chiTieu.getSoTienChi()));
        ed_ghichu.setText(chiTieu.getGhiChu());
        List<DanhMuc> listDM = danhMucDAO.layDanhSachDanhMuc();
        listVT = viTienDAO.layDanhSachViTien();
        adapterSPLoaiVi spLoaiVi = new adapterSPLoaiVi(listVT,context);
        adapterSPDanhMuc spDanhMuc = new adapterSPDanhMuc(listDM, context);
        spn_loaivi.setAdapter(spLoaiVi);
        spn_chondanhmuc.setAdapter(spDanhMuc);
        final adapterSPKhoanChi[] spKhoanChi = new adapterSPKhoanChi[1];

        spn_chondanhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if (position >=0 && position <listDM.size()){
                   listKC = khoanChiDAO.layDanhSachKhoanChiTheoDM(listDM.get(position).getMaDanhMuc() + "");
                   spKhoanChi[0] = new adapterSPKhoanChi(listKC, context);
                   spn_chonkhoanchi.setAdapter(spKhoanChi[0]);
                   int index_khoanchi = 0;
                   for (KhoanChi itemKC : listKC) {
                       if (itemKC.getMaKC() == chiTieu.getMaKC()) {
//                Log.e("TAG","for:"+itemKC.getMaKC());
                           break;
                       }
                       index_khoanchi++;
                   }
                   if (index_khoanchi >= 0 && index_khoanchi < listKC.size()) {
                       spn_chonkhoanchi.setSelection(index_khoanchi);
                   }
               }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int index_danhmuc = 0;
        for (DanhMuc itemDM : listDM) {
            if (itemDM.getMaDanhMuc() == chiTieu.getMaDM()) {
//                Log.e("TAG", "dialogUpdateChiTieu: " + chiTieu.getMaDM());
                break;
            }
            index_danhmuc++;
        }
        spn_chondanhmuc.setSelection(index_danhmuc);

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
                        tvNgay.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

        int index_vitien=0;
        for(ViTien itemVT : listVT){
            if(itemVT.getMaVi() == chiTieu.getMaVi()){
                Log.e("TAG", "dialogUpdateChiTieu: "+itemVT.getMaVi());
                break;
            }
            index_vitien ++;
        }
        spn_loaivi.setSelection(index_vitien);

        tvNgay.setText(chiTieu.getThoiGianChi());


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float soTien = Float.parseFloat(ed_sotien.getText().toString());
                String ghiChu = String.valueOf(ed_ghichu.getText());
                int danhmuc = spn_chondanhmuc.getSelectedItemPosition()+1;
//                Log.e("TAG","dialog:"+danhmuc);
                if (danhmuc == 1){
                    int chiMuc = spn_chonkhoanchi.getSelectedItemPosition()+1;
                    chiTieu.setMaKC(chiMuc);
                }else if(danhmuc ==2){
                    int chiMuc = listKC.size()+(spn_chonkhoanchi.getSelectedItemPosition()+2);
                    chiTieu.setMaKC(chiMuc);
                }

//                Log.e("TAG","dialog2:"+chiMuc);
                String ngay = tvNgay.getText().toString();
                int loaiVi = (spn_loaivi.getSelectedItemPosition()+1);
                chiTieu.setSoTienChi(soTien);
                chiTieu.setGhiChu(ghiChu);
                chiTieu.setMaDM(danhmuc);

                chiTieu.setThoiGianChi(ngay);
                chiTieu.setMaVi(loaiVi);
                boolean check = chiTieuDAO.capNhatChiTieu(chiTieu);
                if(check){
                    loadData();
                    Toast.makeText(context, "Update thành công mục chi tiêu", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(context, "Update không thành công ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadData() {
        list.clear();
        list = chiTieuDAO.layDanhSachChiTieu();
        notifyDataSetChanged();
    }
}
