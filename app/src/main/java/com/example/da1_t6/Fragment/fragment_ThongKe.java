package com.example.da1_t6.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view = inflater.inflate(R.layout.fragment__thongke, container, false);

        BarChart barChart = view.findViewById(R.id.barchart_chitieu);
        ArrayList<BarEntry> visitor = new ArrayList<>();
        XAxis xAxis = barChart.getXAxis();
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

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(2000);


        return view;
    }
}