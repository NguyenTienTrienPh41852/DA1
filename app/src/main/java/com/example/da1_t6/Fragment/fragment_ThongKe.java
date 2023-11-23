package com.example.da1_t6.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.da1_t6.Model.ChiTieu;
import com.example.da1_t6.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class fragment_ThongKe extends Fragment {

    public fragment_ThongKe() {
        // Required empty public constructor
    }
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    BarChart barChart_chitieu;
    BarChart barChart_thunhap;
    Button btnCTNgay,btnCTTuan,btnCTThang;
    Button btnTNNgay,btnTNTuan,btnTNThang;
    ChiTieu chiTieu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_thongke, container, false);

        barChart_chitieu = view.findViewById(R.id.barchart_chitieu);
        barChart_thunhap = view.findViewById(R.id.barchart_thunhap);
        btnCTNgay = view.findViewById(R.id.btn_ct_ngay);
        btnCTTuan = view.findViewById(R.id.btn_ct_tuan);
        btnCTThang = view.findViewById(R.id.btn_ct_thang);
        btnTNNgay = view.findViewById(R.id.btn_tn_ngay);
        btnTNTuan = view.findViewById(R.id.btn_tn_tuan);
        btnTNThang = view.findViewById(R.id.btn_tn_thang);
        chiTieu = new ChiTieu();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        List<Date> dates = new ArrayList<>();
        String a = chiTieu.getThoiGianChi();

        try {
            Date date = simpleDateFormat.parse(a);
            dates.add(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Float> values = new ArrayList<>();
        // Điền dữ liệu mẫu vào lists dates và values
        values.add((float) chiTieu.getSoTienChi());
        // Khởi tạo List<BarEntry> để lưu trữ dữ liệu biểu đồ
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            entries.add(new BarEntry(i, values.get(i)));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Dữ liệu");
        dataSet.setColor(Color.rgb(0, 155, 0));

        // Tạo biểu đồ
        BarData barData = new BarData(dataSet);
        barChart_chitieu.setData(barData);

        // Cài đặt định dạng ngày/tháng cho trục x
        XAxis xAxis = barChart_chitieu.getXAxis();
        xAxis.setValueFormatter(new DateAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Hiển thị biểu đồ
        barChart_chitieu.invalidate();


        return view;
    }
    private static class DateAxisValueFormatter extends IndexAxisValueFormatter{
        private final List<Date> dates;

        private DateAxisValueFormatter(List<Date> dates) {
            this.dates = dates;
        }
        public String getFormattedValue(float value, AxisBase axis){
            int index = (int) value;
            if (index >= 0 && index < dates.size()) {
                Date date = dates.get(index);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                return sdf.format(date);
            }
            return super.getFormattedValue(value, axis);
        }
    }

}