package com.example.da1_t6.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Adapter.BangDiemAdapter;
import com.example.da1_t6.Adapter.MonHocAdapter;
import com.example.da1_t6.DAO.BangDiemDAO;
import com.example.da1_t6.Model.BangDiem;
import com.example.da1_t6.Model.MonHoc;
import com.example.da1_t6.R;

import java.util.ArrayList;
import java.util.List;

public class fragment_ChiTietDiem extends Fragment {
    BangDiemAdapter bangDiemAdapter;
    BangDiemDAO bangDiemDAO;
    List<BangDiem> listB;

    private int IDmonHoc;
    TextView tvTenMon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle newBundle = getArguments();
        if (newBundle != null){
            int selectedID = newBundle.getInt("IDMONHOC",-1);

            if (selectedID != -1){
                IDmonHoc = selectedID;
                bangDiemDAO = new BangDiemDAO(getContext());
                bangDiemDAO.layDanhSachBangDiemTheoMonHoc(selectedID);
            }

        }

        return inflater.inflate(R.layout.fragment_chi_tiet_diem,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lvBangDiem;
        ImageButton btnAdd;

        lvBangDiem = view.findViewById(R.id.lv_diem_mon_hoc);
        btnAdd = view.findViewById(R.id.btn_add_diem_mon_hoc);
        tvTenMon = view.findViewById(R.id.tv_ten_mon_hoc_chi_tiet);

        bangDiemDAO = new BangDiemDAO(getContext());
        String tenMonHoc = "";
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("IDMONHOC") || bundle.containsKey("TENMONHOC")) {
            int idMonHoc = bundle.getInt("IDMONHOC");
            tenMonHoc = bundle.getString("TENMONHOC");
            listB = bangDiemDAO.layDanhSachBangDiemTheoMonHoc(idMonHoc);
        } else {
        }

        tvTenMon.setText("Môn học: "+tenMonHoc);

        bangDiemAdapter = new BangDiemAdapter(getContext(),listB);
        lvBangDiem.setAdapter(bangDiemAdapter);

        btnAdd.setOnClickListener(v -> openDialogAddDiem(Gravity.CENTER));


    }
    private void openDialogAddDiem(int gravity){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_diem_mon_hoc);

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

        EditText edTenDauDiem = dialog.findViewById(R.id.ed_ten_dau_diem);
        EditText edTrongSo = dialog.findViewById(R.id.ed_trong_so);
        EditText edDiem = dialog.findViewById(R.id.ed_diem);
        Button btnSave = dialog.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenDauDiem = edTenDauDiem.getText().toString();
                if (tenDauDiem.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    double trongSo = Double.parseDouble(edTrongSo.getText().toString());
                    double diem = Double.parseDouble(edDiem.getText().toString());

                    BangDiem bangDiem = new BangDiem(tenDauDiem,trongSo,diem);
                    bangDiemDAO = new BangDiemDAO(getContext());
                    long kq = bangDiemDAO.AddDiem(bangDiem,IDmonHoc);
                    if (kq>0){
                        listB.clear();
                        listB.addAll(bangDiemDAO.layDanhSachBangDiemTheoMonHoc(IDmonHoc));
                        bangDiemAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e){
                    Toast.makeText(getContext(), "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();

                    return;
                }


            }
        });

        dialog.show();
    }

}
