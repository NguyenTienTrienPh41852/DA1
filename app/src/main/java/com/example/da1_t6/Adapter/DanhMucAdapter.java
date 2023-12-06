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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.DAO.DanhMucDAO;
import com.example.da1_t6.DAO.IconDAO;
import com.example.da1_t6.DAO.KhoanChiDAO;
import com.example.da1_t6.Fragment.fragment_QuanLyKhoanChi;
import com.example.da1_t6.Model.DanhMuc;
import com.example.da1_t6.Model.Icon;
import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.R;

import java.util.ArrayList;
import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    Context context;
    List<DanhMuc> danhMucList;
    ImageView imgIcon;
    private List<Icon> iconList;
    IconDAO iconDAO;
    KhoanChiDAO khoanChiDAO;
    List<KhoanChi> khoanChiList;
    fragment_QuanLyKhoanChi fragmentQuanLyKhoanChi;
    public DanhMucAdapter(Context context, List<DanhMuc> danhMucList, List<KhoanChi> khoanChiList, fragment_QuanLyKhoanChi fragmentQuanLyKhoanChi) {
        this.context = context;
        this.danhMucList = danhMucList;
        this.khoanChiList = khoanChiList;
        this.fragmentQuanLyKhoanChi = fragmentQuanLyKhoanChi;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_danh_muc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc danhMuc = danhMucList.get(position);
        holder.tv_tenDanhMuc.setText(danhMuc.getTenDanhmuc());
        List<KhoanChi> khoanChiCon = new ArrayList<>();
        for (KhoanChi khoanChi : khoanChiList){
            if(khoanChi.getMaDanhMuc() == danhMuc.getMaDanhMuc()){
                khoanChiCon.add(khoanChi);
            }
        }
        KhoanChiAdapter khoanChiAdapter = new KhoanChiAdapter(context, khoanChiCon, new KhoanChiAdapter.onKhoanChiLongClick() {
            @Override
            public void onKhoanChiLongClick(int position) {
                // Xử lý sự kiện giữ vào item trong khoản chi ở đây
                // position là vị trí của item khoản chi trong danh sách khoản chi được giữ
                // Bạn có thể thực hiện việc chỉnh sửa hoặc xóa item khoản chi tại vị trí position ở đây
                KhoanChiDAO khoanChiDAO = new KhoanChiDAO(context);
                KhoanChi khoanChi = khoanChiCon.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String mang[] = new String[]{
                        "Chỉnh sửa khoản chi", "Xóa khoản chi"
                };
                builder.setTitle("Chọn chức năng");
                builder.setItems(mang, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                // Xử lý sự kiện chỉnh sửa khoản chi tại vị trí position
                                showEditKhoanChiDialog(khoanChi, Gravity.CENTER);
                                break;
                            case 1:
                                // Xử lý sự kiện xóa khoản chi tại vị trí position
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                builder1.setTitle("Thông báo");
                                builder1.setIcon(R.drawable.warning);
                                builder1.setMessage("Xác nhận muốn xóa");
                                builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int result = khoanChiDAO.xoaKhoanChi(khoanChi.getMaKC());
                                        if (result > 0) {
                                            khoanChiList.clear();
                                            khoanChiList.addAll(khoanChiDAO.layDanhSachKhoanChi());
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                                builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Toast.makeText(context, "Đã hủy thao tác", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder1.show();
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        holder.listViewKhoanChi.setAdapter(khoanChiAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.listViewKhoanChi.setLayoutManager(linearLayoutManager);
    }
    // Hiển thị dialog chỉnh sửa khoản chi
    private void showEditKhoanChiDialog(KhoanChi khoanChi,int gravity) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_khoan_chi);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = gravity;

        window.setAttributes(params);
        dialog.setCancelable(true);


        TextView tvTittle = dialog.findViewById(R.id.txtTittle);
        EditText tenKhoanChi = dialog.findViewById(R.id.ed_ten_khoan_chi);
        Spinner spDanhMuc = dialog.findViewById(R.id.sp_chon_danh_muc);
        Button luuKhoanChi = dialog.findViewById(R.id.btn_luu_khoan_chi);
        iconDAO = new IconDAO(context);
        imgIcon = dialog.findViewById(R.id.img_icon);
        imgIcon.setImageResource(iconDAO.icon(khoanChi.getMaIcon()).getIcon());
        spDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                khoanChi.setMaDanhMuc(danhMucList.get(i).getMaDanhMuc());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //icon
        iconDAO = new IconDAO(context);
        icon = iconDAO.icon(khoanChi.getMaIcon());
        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconList = iconDAO.layDSIcon();
                showDialogIcon(iconList);
            }
        });

        tvTittle.setText("Cập nhật khoản chi");
        // Set thông tin cho dialog từ đối tượng khoản chi
        tenKhoanChi.setText(khoanChi.getTenKC());

        KhoanChiDAO khoanChiDAO = new KhoanChiDAO(context);
        DanhMucDAO danhMucDAO = new DanhMucDAO(context);
        danhMucList = danhMucDAO.layDanhSachDanhMuc();
        List<String> listTenDanhMuc = new ArrayList<>();
        for (DanhMuc dm : danhMucList){
            listTenDanhMuc.add(dm.getTenDanhmuc());
        }
        // Tìm vị trí của danh mục được chọn
        int selectedDanhMucPosition = -1;
        for (int i = 0; i < danhMucList.size(); i++) {
            if (danhMucList.get(i).getMaDanhMuc() == khoanChi.getMaDanhMuc()) {
                selectedDanhMucPosition = i;
                break;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, listTenDanhMuc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDanhMuc.setAdapter(adapter);
        // Set danhMuc lên Spinner
        if (selectedDanhMucPosition != -1) {
            spDanhMuc.setSelection(selectedDanhMucPosition);
        }

        // Bắt sự kiện khi click vào nút lưu
        luuKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý lưu thông tin khoản chi đã chỉnh sửa vào cơ sở dữ liệu
                if (tenKhoanChi.getText().toString().isEmpty()){
                    Toast.makeText(context, "Vui lòng điền tên khoản chi!", Toast.LENGTH_SHORT).show();
                } else if (spDanhMuc.getSelectedItem() == null){
                    Toast.makeText(context, "Vui lòng chọn danh mục!", Toast.LENGTH_SHORT).show();
                } else {
                    khoanChi.setMaIcon(icon.getMaIcon());
                    khoanChi.setTenDanhMuc(spDanhMuc.getSelectedItem().toString());
                    khoanChi.setTenKC(tenKhoanChi.getText().toString());
                    long result = khoanChiDAO.capNhatKhoanChi(khoanChi);
                    if (result > 0) {
                        khoanChiList.clear();
                        khoanChiList.addAll(khoanChiDAO.layDanhSachKhoanChi());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog.show();
    }
    public void showDialogIcon(List<Icon> iconList){
       AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_icon,null);
        builder.setView(view);
        builder.setCancelable(true);
        Dialog dialog = builder.create();
        dialog.show();

        RecyclerView rcv = view.findViewById(R.id.rcv_icon);
        rcv.setLayoutManager(new GridLayoutManager(context,4));
        IconAdapter adapter = new IconAdapter(context, iconList,dialog,this);
        rcv.setAdapter(adapter);
    }
    Icon icon;


    public void setIcon(Icon icon) {
        this.icon = icon;
        imgIcon.setImageResource(icon.getIcon());
    }


    @Override
    public int getItemCount() {
        return danhMucList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenDanhMuc;
        RecyclerView listViewKhoanChi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenDanhMuc = itemView.findViewById(R.id.tv_tendanhmuc);
            listViewKhoanChi = itemView.findViewById(R.id.lv_khoanchi);
        }
    }
}
