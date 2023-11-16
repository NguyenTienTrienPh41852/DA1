package com.example.da1_t6.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.da1_t6.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class fragment_ThongKe extends Fragment {

    public fragment_ThongKe() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    BarChart barChart_chitieu;
    BarChart barChart_thunhap;
    Button btnCTNgay,btnCTTuan,btnCTThang;
    Button btnTNNgay,btnTNTuan,btnTNThang;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment__thongke, container, false);

        barChart_chitieu = view.findViewById(R.id.barchart_chitieu);
        barChart_thunhap = view.findViewById(R.id.barchart_thunhap);
        btnCTNgay = view.findViewById(R.id.btn_ct_ngay);
        btnCTTuan = view.findViewById(R.id.btn_ct_tuan);
        btnCTThang = view.findViewById(R.id.btn_ct_thang);
        btnTNNgay = view.findViewById(R.id.btn_tn_ngay);
        btnTNTuan = view.findViewById(R.id.btn_tn_tuan);
        btnTNThang = view.findViewById(R.id.btn_tn_thang);



        ArrayList<BarEntry> visitor = new ArrayList<>();
        XAxis xAxis = barChart_chitieu.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        visitor.add(new BarEntry(2014,657));
        visitor.add(new BarEntry(2015,687));
        visitor.add(new BarEntry(2016,697));
        visitor.add(new BarEntry(2017,737));
        visitor.add(new BarEntry(2018,767));
        visitor.add(new BarEntry(2019,857));
        visitor.add(new BarEntry(2020,457));

        BarDataSet barDataSet = new BarDataSet(visitor, "Chi tiêu");
        barDataSet.setColor(Color.RED);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart_chitieu.setFitBars(true);
        barChart_chitieu.setData(barData);
        barChart_chitieu.animateY(2000);


        return view;
    }
}