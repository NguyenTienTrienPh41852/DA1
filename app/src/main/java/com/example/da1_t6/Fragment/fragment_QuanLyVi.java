package com.example.da1_t6.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Adapter.CacGiaoDichViAdapter;
import com.example.da1_t6.Adapter.QuanLyViAdapter;
import com.example.da1_t6.DAO.ChiTieuDAO;
import com.example.da1_t6.DAO.ThuNhapDAO;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.Model.ThuNhap;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class fragment_QuanLyVi extends Fragment {
    LinearLayout linear_item_vi;
    QuanLyViAdapter viAdapter;
    List<ViTien> listVi;
    ViTienDAO viTienDAO;

    CacGiaoDichViAdapter giaoDichViAdapter;
    List<Object> listGD;
    RecyclerView rcCacGiaoDich;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_vi,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btnAddVi = view.findViewById(R.id.btn_add_vi);

        linear_item_vi = view.findViewById(R.id.linear_item_vi);
        linear_item_vi.setOnClickListener(v -> openDialogChonVi());

        btnAddVi.setOnClickListener(v -> openDialogAddVi(Gravity.CENTER));
        listGD = new ArrayList<>();
        listGD.addAll(listChiTieu());
        listGD.addAll(listThuNhap());

        rcCacGiaoDich = view.findViewById(R.id.rc_cac_giao_dich_thang);
        giaoDichViAdapter = new CacGiaoDichViAdapter(requireContext(),listGD);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcCacGiaoDich.setLayoutManager(manager);
        rcCacGiaoDich.setAdapter(giaoDichViAdapter);

        viTienDAO = new ViTienDAO(getContext());
        listVi = viTienDAO.layDanhSachViTien();
        viAdapter = new QuanLyViAdapter(getContext(),listVi);

        int tongTien = tinhTong();
        listVi.add(0, new ViTien("Tất cả ví", tongTien));
        ViTien viTong = listVi.get(0);
        updateViChon(viTong);
        viAdapter.setDefaultVi(0);
        int defaultSelectedVi = viAdapter.getDefaultSelectedVi();
        if (defaultSelectedVi != RecyclerView.NO_POSITION && defaultSelectedVi < listVi.size()) {
            ViTien defaultVi = listVi.get(defaultSelectedVi);
            updateViChon(defaultVi);
        }

        TextView tvTongChiTieuThang = view.findViewById(R.id.tv_chi_tieu_thang);
        TextView tvTongThuNhapThang = view.findViewById(R.id.tv_thu_nhap_thang);
        TextView tvSoDuThang = view.findViewById(R.id.tv_so_du_thang);

        ChiTieuDAO chiTieuDAO = new ChiTieuDAO(getContext());
        double tongChiTieu = chiTieuDAO.getTongChiTieu();
        tvTongChiTieuThang.setText(formatTienViet(tongChiTieu));

        ThuNhapDAO thuNhapDAO = new ThuNhapDAO(getContext());
        double tongThuNhap = thuNhapDAO.getTongThuNhap();
        tvTongThuNhapThang.setText(formatTienViet(tongThuNhap));

        double tongSoDu = viTienDAO.getTongSoDu();
        tvSoDuThang.setText(formatTienViet(tongSoDu));
    }

    private void openDialogChonVi(){
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_chon_vi,null);
        dialog.setContentView(v);

        RecyclerView rv_chon_vi = v.findViewById(R.id.rc_vi);
        viTienDAO = new ViTienDAO(getContext());
        listVi = viTienDAO.layDanhSachViTien();

        viAdapter = new QuanLyViAdapter(getContext(),listVi);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rv_chon_vi.setLayoutManager(manager);

        rv_chon_vi.setAdapter(viAdapter);

        int tongTien = tinhTong();
        listVi.add(0, new ViTien(0,"Tất cả ví",tongTien));

        viAdapter.setOnItemClickListener(new QuanLyViAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ViTien viTien = listVi.get(i);
                updateViChon(viTien);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void openDialogAddVi(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_vi);

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

        EditText edTenVi = dialog.findViewById(R.id.ed_ten_vi);
        EditText edSoDu = dialog.findViewById(R.id.ed_so_du);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        Spinner spChonIcon = dialog.findViewById(R.id.sp_chon_icon);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenVi = edTenVi.getText().toString();
                int soDuBanDau = Integer.parseInt(edSoDu.getText().toString());
                int soDuHienTai = Integer.parseInt(edSoDu.getText().toString());
                viTienDAO = new ViTienDAO(getContext());
                ViTien viTien = new ViTien(tenVi, soDuBanDau, soDuHienTai);
                long kq = viTienDAO.themVi(viTien);

                if (kq > 0){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    listVi.clear();
                    listVi.addAll(viTienDAO.layDanhSachViTien());
                    viAdapter.notifyDataSetChanged();
//                    linear_item_vi.notify();
                    if (!listVi.isEmpty()) {
                        ViTien viMoi = listVi.get(listVi.size() - 1); // Lấy ví mới thêm vào
                        updateViChon(viMoi); // Cập nhật thông tin hiển thị
                    }
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void updateViChon(ViTien viTien){
        TextView tvTenVi = getView().findViewById(R.id.tv_ten_vi);
        TextView tvSoDu = getView().findViewById(R.id.tv_so_tien);
        if (tvTenVi != null && tvSoDu != null && viTien != null){
            tvTenVi.setText(viTien.getTenVi());
            tvSoDu.setText(formatTienViet(viTien.getSoDuHienTai()));
        }
    }

    private int tinhTong(){
        int tongTien = 0;
        for (ViTien viTien : listVi){
            tongTien += viTien.getSoDuHienTai();
        }
        return tongTien;
    }
    public static String formatTienViet(double amount) {
        Locale locale = new Locale("vi", "VN"); // Locale cho tiền Việt Nam
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(amount);
    }

    private List<Object> listChiTieu(){
        List<Object> listCT = new ArrayList<>();
        ChiTieuDAO chiTieuDAO = new ChiTieuDAO(getContext());
        List<ChiTieu> list = chiTieuDAO.layDanhSachChiTieu();
        listCT.addAll(list);
        return listCT;

    }
    private List<Object> listThuNhap(){

        List<Object> listTN = new ArrayList<>();

        ThuNhapDAO thuNhapDAO = new ThuNhapDAO(getContext());

        List<ThuNhap> list = thuNhapDAO.layDanhSachThuNhap();

        listTN.addAll(list);

        return listTN;
    }
}
