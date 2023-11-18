package com.example.da1_t6.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.da1_t6.Adapter.DanhMucAdapter;
import com.example.da1_t6.Adapter.KhoanChiAdapter;
import com.example.da1_t6.DAO.DanhMucDAO;
import com.example.da1_t6.DAO.KhoanChiDAO;
import com.example.da1_t6.Model.DanhMuc;
import com.example.da1_t6.Model.KhoanChi;
import com.example.da1_t6.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_QuanLyKhoanChi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_QuanLyKhoanChi extends Fragment {
    RecyclerView recyclerView;
    List<DanhMuc> danhMucList;
    List<KhoanChi> khoanChiList;
    KhoanChi khoanChi;
    DanhMucAdapter danhMucAdapter;
    KhoanChiAdapter khoanChiAdapter;
    DanhMucDAO danhMucDAO;
    KhoanChiDAO khoanChiDAO;
    ImageButton imageButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_QuanLyKhoanChi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Quanlykhoanchi.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_QuanLyKhoanChi newInstance(String param1, String param2) {
        fragment_QuanLyKhoanChi fragment = new fragment_QuanLyKhoanChi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quanlykhoanchi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageButton = view.findViewById(R.id.add_QLKC);
        recyclerView = view.findViewById(R.id.rcvQLKC);
        danhMucDAO = new DanhMucDAO(getContext());
        khoanChiDAO = new KhoanChiDAO(getContext());
        danhMucList = danhMucDAO.layDanhSachDanhMuc();
        khoanChiList = khoanChiDAO.layDanhSachKhoanChi();
        danhMucAdapter = new DanhMucAdapter(view.getContext(), danhMucList, khoanChiList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(danhMucAdapter);
        danhMucAdapter.notifyDataSetChanged();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_them_khoan_chi, null);
                builder.setView(v);
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                EditText tenKhoanChi = v.findViewById(R.id.ed_ten_khoan_chi);
                Spinner spDanhMuc = v.findViewById(R.id.sp_chon_danh_muc);
                Button luuKhoanChi = v.findViewById(R.id.btn_luu_khoan_chi);
                danhMucDAO = new DanhMucDAO(requireContext());
                danhMucList = danhMucDAO.layDanhSachDanhMuc();
                List<String> listTenDanhMuc = new ArrayList<>();
                for (DanhMuc dm : danhMucList){
                    listTenDanhMuc.add(dm.getTenDanhmuc());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, listTenDanhMuc);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spDanhMuc.setAdapter(adapter);
                luuKhoanChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (tenKhoanChi.getText().toString().isEmpty()){
                            Toast.makeText(getContext(), "Vui lòng điền tên khoản chi!", Toast.LENGTH_SHORT).show();
                        } else if (spDanhMuc.getSelectedItem() == null){
                            Toast.makeText(getContext(), "Vui lòng chọn danh mục!", Toast.LENGTH_SHORT).show();
                        } else {
                            khoanChi = new KhoanChi();
                            khoanChi.setTenDanhMuc(spDanhMuc.getSelectedItem().toString());
                            khoanChi.setTenKC(tenKhoanChi.getText().toString());
                            long result = khoanChiDAO.themKhoanChi(khoanChi);
                            if (result > 0) {
                                // Xóa khoản chi mới từ danh sách hiện tại
                                khoanChiList.clear();
                                // Lấy danh sách khoản chi mới từ cơ sở dữ liệu
                                khoanChiList.addAll(khoanChiDAO.layDanhSachKhoanChi());
                                // Thông báo cho Adapter biết dữ liệu đã được thay đổi
                                danhMucAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog.show();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}