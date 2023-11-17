package com.example.da1_t6.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_t6.Adapter.QuanLyViAdapter;
import com.example.da1_t6.DAO.ViTienDAO;
import com.example.da1_t6.Model.ViTien;
import com.example.da1_t6.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class fragment_QuanLyVi extends Fragment {
    LinearLayout linear_item_vi;
    QuanLyViAdapter viAdapter;
    List<ViTien> listVi;
    ViTienDAO viTienDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quan_li_vi,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linear_item_vi = view.findViewById(R.id.linear_item_vi);

        linear_item_vi.setOnClickListener(v -> openDialogChonVi());
    }

    private void openDialogChonVi(){
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_chon_vi,null);
        dialog.setContentView(v);

//        RecyclerView rv_chon_vi = v.findViewById(R.id.rc_vi);

        viTienDAO = new ViTienDAO(getContext());
//        listVi = viTienDAO.layDanhSachVi();
        viAdapter = new QuanLyViAdapter(getContext(),listVi);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
//        rv_chon_vi.setLayoutManager(manager);
//
//        rv_chon_vi.setAdapter(viAdapter);

//        AlertDialog dialog = builder.create();
//        dialog.show();
        dialog.show();
    }
}
