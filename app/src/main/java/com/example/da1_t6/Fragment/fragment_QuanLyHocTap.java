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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Adapter.MonHocAdapter;
import com.example.da1_t6.DAO.MonHocDAO;
import com.example.da1_t6.Model.MonHoc;
import com.example.da1_t6.R;

import java.util.List;

public class fragment_QuanLyHocTap extends Fragment implements MonHocAdapter.OnItemClickListener{
    MonHocAdapter monHocAdapter;
    List<MonHoc> listM;
    MonHocDAO monHocDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hoc_tap,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rcMonHoc;
        ImageButton btnAdd;

        btnAdd = view.findViewById(R.id.btn_add_mon_hoc);
        rcMonHoc = view.findViewById(R.id.rc_hoc_tap);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcMonHoc.setLayoutManager(manager);

        monHocDAO = new MonHocDAO(getContext());
        listM = monHocDAO.layDanhSachMonHoc();
        monHocAdapter = new MonHocAdapter(getContext(),listM);
        monHocAdapter.setListener(this);

        rcMonHoc.setAdapter(monHocAdapter);


        btnAdd.setOnClickListener(v -> openDialogAddMonHoc(Gravity.CENTER));
    }

    private void openDialogAddMonHoc(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_mon_hoc);

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
        EditText edMaMonHoc = dialog.findViewById(R.id.ed_ma_mon);
        EditText edTenLop = dialog.findViewById(R.id.ed_ten_lop);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        TextView tvError = dialog.findViewById(R.id.tv_erro);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenMon = edTenMonHoc.getText().toString();
                String maMon = edMaMonHoc.getText().toString();
                String tenLop = edTenLop.getText().toString();

                if (tenMon.isEmpty() || maMon.isEmpty() || tenLop.isEmpty()){
                    Toast.makeText(getContext(), "Hãy nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
                    tvError.setText("Hãy nhập đầy đủ dữ liệu!");
                    return;
                }
                MonHoc monHoc = new MonHoc(maMon,tenMon,tenLop);
                monHocDAO = new MonHocDAO(getContext());
                long kq = monHocDAO.AddMonHoc(monHoc);

                if (kq > 0){
                    listM.clear();
                    listM.addAll(monHocDAO.layDanhSachMonHoc());
                    monHocAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onItemClick(int IDmonHoc, MonHoc monHoc) {
        fragment_ChiTietDiem fragmentChiTietDiem = new fragment_ChiTietDiem();
        Bundle bundle = new Bundle();
        bundle.putInt("IDMONHOC", IDmonHoc);
        bundle.putString("TENMONHOC",monHoc.getTenMonHoc());
        fragmentChiTietDiem.setArguments(bundle);

        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.frameLayout, fragmentChiTietDiem);

        transaction.addToBackStack("Quay Lai");
        transaction.commit();
        Toast.makeText(getContext(), "Đây là ID Môn Học: "+IDmonHoc, Toast.LENGTH_SHORT).show();
    }

}
