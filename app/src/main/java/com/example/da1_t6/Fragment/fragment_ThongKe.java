package com.example.da1_t6.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.da1_t6.DAO.ThongKeDAO;
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
    ThongKeDAO thongKeDAO;
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
        // Khởi tạo DAO
        thongKeDAO = new ThongKeDAO(getContext());
        barChart_chitieu = view.findViewById(R.id.barchart_chitieu);
        barChart_thunhap = view.findViewById(R.id.barchart_thunhap);
        btnCTNgay = view.findViewById(R.id.btn_ct_ngay);
        btnCTTuan = view.findViewById(R.id.btn_ct_tuan);
        btnCTThang = view.findViewById(R.id.btn_ct_thang);
        btnTNNgay = view.findViewById(R.id.btn_tn_ngay);
        btnTNTuan = view.findViewById(R.id.btn_tn_tuan);
        btnTNThang = view.findViewById(R.id.btn_tn_thang);
        chiTieu = new ChiTieu();

        btnCTNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định ngày hiện tại
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();

                // Danh sách chứa 10 ngày gần nhất tính từ ngày hiện tại
                List<Date> recentDays = new ArrayList<>();

                // Tạo danh sách 10 ngày gần nhất tính từ ngày hiện tại
                for (int i = 9; i >= 0; i--) {
                    calendar.add(Calendar.DATE, -1); // Lùi ngày về 1 ngày
                    recentDays.add(calendar.getTime());
                }

                // Mảng để lưu trữ tổng chi tiêu của từng ngày
                float[] expenses = new float[10];

                // Lấy tổng chi tiêu của từng ngày trong danh sách 10 ngày gần nhất
                for (int i = 0; i < recentDays.size(); i++) {
                    float dailyExpense = thongKeDAO.getTongChiTieuTheoNgay(recentDays.get(i));
                    expenses[i] = dailyExpense;
                }

                // Cập nhật dữ liệu lên biểu đồ
                updateChartWithDailyExpenses(recentDays, expenses);
            }
        });

        btnCTTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định ngày hiện tại
                Calendar calendar = Calendar.getInstance();

                // Danh sách chứa 8 tuần gần nhất tính từ ngày hiện tại
                List<Date> recentWeeks = new ArrayList<>();

                // Lấy ngày hiện tại làm ngày kết thúc của tuần hiện tại
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Chuyển ngày hiện tại về thứ 2 của tuần
                recentWeeks.add(calendar.getTime()); // Ngày đầu tiên của tuần hiện tại

                // Tạo danh sách 8 tuần gần nhất tính từ tuần hiện tại
                for (int i = 1; i < 8; i++) {
                    calendar.add(Calendar.WEEK_OF_YEAR, -1); // Lùi về 1 tuần
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Chuyển về thứ 2 của tuần
                    recentWeeks.add(0, calendar.getTime()); // Thêm ngày đầu tiên của tuần vào đầu danh sách
                }

                // Mảng để lưu trữ tổng chi tiêu của từng tuần
                float[] weeklyExpenses = new float[8];

                // Lấy tổng chi tiêu của từng tuần trong danh sách 8 tuần gần nhất
                for (int i = 0; i < recentWeeks.size(); i++) {
                    float weeklyExpense = thongKeDAO.getTongChiTieuTheoTuan(recentWeeks.get(i));
                    weeklyExpenses[i] = weeklyExpense;
                }

                // Cập nhật dữ liệu lên biểu đồ
                updateChartWithWeeklyExpenses(recentWeeks, weeklyExpenses);
            }
        });

        btnCTThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức trong DAO để lấy tổng chi tiêu của 6 tháng gần nhất
                float totalExpensesForLast6Months = thongKeDAO.getTongChiTieuCua6ThangGanNhat();

                // Hiển thị dữ liệu lên biểu đồ
                updateChartWithMonthlyExpenses(totalExpensesForLast6Months);
            }
        });

        return view;
    }
    // Phương thức cập nhật dữ liệu lên biểu đồ
    private void updateChartWithDailyExpenses(List<Date> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng chi tiêu");
        dataSet.setColor(Color.rgb(0, 155, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng ngày/tháng cho trục X
        XAxis xAxis = barChart_chitieu.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getFormattedDateStrings(dates)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Đặt dữ liệu vào biểu đồ
        barChart_chitieu.setData(barData);

        // Hiển thị biểu đồ
        barChart_chitieu.invalidate();
    }

    // Phương thức để định dạng ngày/tháng từ danh sách ngày
    private ArrayList<String> getFormattedDateStrings(List<Date> dates) {
        ArrayList<String> formattedDates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
        for (Date date : dates) {
            formattedDates.add(sdf.format(date));
        }
        return formattedDates;
    }
    private void updateChartWithWeeklyExpenses(List<Date> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng chi tiêu");
        dataSet.setColor(Color.rgb(0, 155, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng ngày/tháng cho trục X
        XAxis xAxis = barChart_chitieu.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getFormattedWeekStrings(dates)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Đặt dữ liệu vào biểu đồ
        barChart_chitieu.setData(barData);

        // Hiển thị biểu đồ
        barChart_chitieu.invalidate();
    }

    // Phương thức để định dạng tuần từ danh sách ngày
    private ArrayList<String> getFormattedWeekStrings(List<Date> dates) {
        ArrayList<String> formattedWeeks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());

        // Xác định ngày đầu tuần và ngày cuối tuần từ danh sách ngày
        for (int i = 0; i < dates.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dates.get(i));
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Chuyển về thứ 2 của tuần
            Date mondayDate = calendar.getTime();
            calendar.add(Calendar.DATE, 6); // Thêm 6 ngày để đến Chủ Nhật
            Date sundayDate = calendar.getTime();

            String formattedWeek = sdf.format(mondayDate) + " - " + sdf.format(sundayDate);
            formattedWeeks.add(formattedWeek);
        }
        return formattedWeeks;
    }

    private void updateChartWithMonthlyExpenses(float totalExpensesForLast6Months) {
        // Tạo danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();

        // Tạo dữ liệu cho 6 tháng gần nhất
        for (int i = 1; i <= 6; i++) {
            entries.add(new BarEntry(i, totalExpensesForLast6Months)); // Thêm dữ liệu tổng chi tiêu của mỗi tháng
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng chi tiêu 6 tháng gần nhất");
        dataSet.setColor(Color.rgb(0, 155, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng tháng cho trục X
        XAxis xAxis = barChart_chitieu.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getFormattedMonths())); // Lấy danh sách số tháng
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelCount(6); // Số lượng tháng cần hiển thị

        // Đặt dữ liệu vào biểu đồ
        barChart_chitieu.setData(barData);

        // Hiển thị biểu đồ
        barChart_chitieu.invalidate();
    }

    // Phương thức để định dạng tháng từ danh sách số tháng
    private ArrayList<String> getFormattedMonths() {
        ArrayList<String> formattedMonths = new ArrayList<>();
        // Thêm các số tháng tương ứng vào danh sách
        for (int i = 1; i <= 6; i++) {
            formattedMonths.add(String.valueOf(i));
        }
        return formattedMonths;
    }
}