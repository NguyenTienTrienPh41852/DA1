package com.example.da1_t6.Fragment;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import java.util.Collections;
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
        hienThiBieuDoCT();
        hienThiBieuDoTN();

        btnCTNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định ngày hiện tại
                Calendar calendar = Calendar.getInstance();

                // Danh sách chứa 10 ngày gần nhất tính từ ngày hiện tại
                List<Date> list8Day = new ArrayList<>();

                // Tạo danh sách 8 ngày gần nhất tính từ ngày hiện tại
                for (int i = 1; i < 9; i++) {
                    list8Day.add(calendar.getTime());
                    calendar.add(Calendar.DATE, -1); // Lùi ngày về 1 ngày
                }

                Collections.reverse(list8Day);

                // Mảng để lưu trữ tổng chi tiêu của từng ngày
                float[] mangCT = new float[8];

                // Lấy tổng chi tiêu của từng ngày trong danh sách 10 ngày gần nhất
                for (int i = 0; i < list8Day.size(); i++) {
                    float chiTieu = thongKeDAO.getTongChiTieuTheoNgay(list8Day.get(i));
                    mangCT[i] = chiTieu;
                }

                // Cập nhật dữ liệu lên biểu đồ
                capNhatBieuDoCT(list8Day, mangCT);
            }
        });

        btnCTTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định ngày hiện tại
                Calendar currentCalendar = Calendar.getInstance();
                Calendar mondayCalendar = Calendar.getInstance();
                Calendar sundayCalendar = Calendar.getInstance();
                Calendar calendar = Calendar.getInstance();

                List<String> listMonday = new ArrayList<>();
                List<String> listSunday = new ArrayList<>();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                listMonday.add(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                calendar.add(Calendar.DATE, 6);
                listSunday.add(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                // Lặp qua 6 tuần gần nhất
                for (int i = 0; i < 6; i++) {
                    // Đặt ngày trong tuần là chủ nhật
                    sundayCalendar.setTime(currentCalendar.getTime());
                    sundayCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    listSunday.add(new SimpleDateFormat("yyyy-MM-dd").format(sundayCalendar.getTime()));
                    // Tính ngày thứ hai của tuần trước đó
                    mondayCalendar.setTime(sundayCalendar.getTime());
                    mondayCalendar.add(Calendar.DAY_OF_WEEK, -6);
                    listMonday.add(new SimpleDateFormat("yyyy-MM-dd").format(mondayCalendar.getTime()));
                    // Đặt ngày hiện tại là chủ nhật của tuần trước
                    currentCalendar.setTime(sundayCalendar.getTime());
                    currentCalendar.add(Calendar.DAY_OF_WEEK, -7);
                }

                // Đảo ngược danh sách
                Collections.reverse(listMonday);
                Collections.reverse(listSunday);

                // Xóa ngày dư
                listMonday.remove(0);
                listSunday.remove(0);

                // Mảng để lưu trữ tổng chi tiêu của từng tuần
                float[] mangChiTieuTuan = new float[6];
                List<String> chuyenNgay = new ArrayList<>();

                // Lấy tổng chi tiêu của từng tuần trong danh sách 6 tuần gần nhất
                for (int i = 0; i < 6; i++) {
                    float chiTieuTuan = thongKeDAO.tinhTongChiTieu(listMonday.get(i), listSunday.get(i));
                    chuyenNgay.add(thongKeDAO.chuyenDoiDMY(listMonday.get(i)));
                    mangChiTieuTuan[i] = chiTieuTuan;
                }

                // Cập nhật dữ liệu lên biểu đồ
                capNhatBieuDoTuanCT(chuyenNgay, mangChiTieuTuan);
            }
        });

        btnCTThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo đối tượng Calendar để lấy thời điểm hiện tại
                Calendar calendar = Calendar.getInstance();

                // List để lưu trữ ngày đầu tiên và ngày cuối cùng của các tháng
                List<String> ngayDauThang = new ArrayList<>();
                List<String> ngayCuoiThang = new ArrayList<>();

                // Tính ngày đầu tiên và ngày cuối cùng của 4 tháng gần nhất bao gồm cả tháng hiện tại
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                for (int i = 0; i < 4; i++) {
                    // Lấy ngày đầu tiên của tháng
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    String firstDay = dateFormat.format(calendar.getTime());
                    ngayDauThang.add(firstDay);

                    // Lấy ngày cuối cùng của tháng
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String lastDay = dateFormat.format(calendar.getTime());
                    ngayCuoiThang.add(lastDay);

                    // Di chuyển đến tháng trước đó
                    calendar.add(Calendar.MONTH, -1);
                }

                // Đảo ngược danh sách
                Collections.reverse(ngayDauThang);
                Collections.reverse(ngayCuoiThang);

                // Mảng để lưu trữ tổng chi tiêu của từng tháng
                float[] mangChiTieuThang = new float[4];

                // Lấy tổng chi tiêu của từng tháng trong danh sách 4 tháng gần nhất
                for (int i = 0; i < 4; i++) {
                    float chiTieuThang = thongKeDAO.tinhTongChiTieu(ngayDauThang.get(i), ngayCuoiThang.get(i));
                    mangChiTieuThang[i] = chiTieuThang;
                }

                // Cập nhật dữ liệu lên biểu đồ
                capNhatBieuDoThangCT(lay4Thang(), mangChiTieuThang);
            }
        });

        btnTNNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định ngày hiện tại
                Calendar calendar = Calendar.getInstance();

                // Danh sách chứa 10 ngày gần nhất tính từ ngày hiện tại
                List<Date> list8Day = new ArrayList<>();

                // Tạo danh sách 8 ngày gần nhất tính từ ngày hiện tại
                for (int i = 1; i < 9; i++) {
                    list8Day.add(calendar.getTime());
                    calendar.add(Calendar.DATE, -1); // Lùi ngày về 1 ngày
                }

                Collections.reverse(list8Day);

                // Mảng để lưu trữ tổng chi tiêu của từng ngày
                float[] mangTN = new float[8];

                // Lấy tổng chi tiêu của từng ngày trong danh sách 10 ngày gần nhất
                for (int i = 0; i < list8Day.size(); i++) {
                    float thuNhap = thongKeDAO.getTongThuNhapTheoNgay(list8Day.get(i));
                    mangTN[i] = thuNhap;
                }

                // Cập nhật dữ liệu lên biểu đồ
                capNhatBieuDoTN(list8Day, mangTN);
            }
        });

        btnTNTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định ngày hiện tại
                Calendar currentCalendar = Calendar.getInstance();
                Calendar mondayCalendar = Calendar.getInstance();
                Calendar sundayCalendar = Calendar.getInstance();
                Calendar calendar = Calendar.getInstance();

                List<String> listMonday = new ArrayList<>();
                List<String> listSunday = new ArrayList<>();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                listMonday.add(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                calendar.add(Calendar.DATE, 6);
                listSunday.add(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                // Lặp qua 6 tuần gần nhất
                for (int i = 0; i < 6; i++) {
                    // Đặt ngày trong tuần là chủ nhật
                    sundayCalendar.setTime(currentCalendar.getTime());
                    sundayCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    listSunday.add(new SimpleDateFormat("yyyy-MM-dd").format(sundayCalendar.getTime()));
                    // Tính ngày thứ hai của tuần trước đó
                    mondayCalendar.setTime(sundayCalendar.getTime());
                    mondayCalendar.add(Calendar.DAY_OF_WEEK, -6);
                    listMonday.add(new SimpleDateFormat("yyyy-MM-dd").format(mondayCalendar.getTime()));
                    // Đặt ngày hiện tại là chủ nhật của tuần trước
                    currentCalendar.setTime(sundayCalendar.getTime());
                    currentCalendar.add(Calendar.DAY_OF_WEEK, -7);
                }

                // Đảo ngược danh sách
                Collections.reverse(listMonday);
                Collections.reverse(listSunday);

                // Xóa ngày dư
                listMonday.remove(0);
                listSunday.remove(0);

                // Mảng để lưu trữ tổng chi tiêu của từng tuần
                float[] mangThuNhapTuan = new float[6];
                List<String> chuyenNgay = new ArrayList<>();

                // Lấy tổng chi tiêu của từng tuần trong danh sách 6 tuần gần nhất
                for (int i = 0; i < 6; i++) {
                    float thuNhapTuan = thongKeDAO.tinhTongThuNhap(listMonday.get(i), listSunday.get(i));
                    chuyenNgay.add(thongKeDAO.chuyenDoiDMY(listMonday.get(i)));
                    mangThuNhapTuan[i] = thuNhapTuan;
                }

                // Cập nhật dữ liệu lên biểu đồ
                capNhatBieuDoTuanTN(chuyenNgay, mangThuNhapTuan);
            }
        });

        btnTNThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo đối tượng Calendar để lấy thời điểm hiện tại
                Calendar calendar = Calendar.getInstance();

                // List để lưu trữ ngày đầu tiên và ngày cuối cùng của các tháng
                List<String> ngayDauThang = new ArrayList<>();
                List<String> ngayCuoiThang = new ArrayList<>();

                // Tính ngày đầu tiên và ngày cuối cùng của 4 tháng gần nhất bao gồm cả tháng hiện tại
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                for (int i = 0; i < 4; i++) {
                    // Lấy ngày đầu tiên của tháng
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    String firstDay = dateFormat.format(calendar.getTime());
                    ngayDauThang.add(firstDay);

                    // Lấy ngày cuối cùng của tháng
                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String lastDay = dateFormat.format(calendar.getTime());
                    ngayCuoiThang.add(lastDay);

                    // Di chuyển đến tháng trước đó
                    calendar.add(Calendar.MONTH, -1);
                }

                // Đảo ngược danh sách
                Collections.reverse(ngayDauThang);
                Collections.reverse(ngayCuoiThang);

                // Mảng để lưu trữ tổng chi tiêu của từng tháng
                float[] mangThuNhapThang = new float[4];

                // Lấy tổng chi tiêu của từng tháng trong danh sách 4 tháng gần nhất
                for (int i = 0; i < 4; i++) {
                    float chiTieuThang = thongKeDAO.tinhTongThuNhap(ngayDauThang.get(i), ngayCuoiThang.get(i));
                    mangThuNhapThang[i] = chiTieuThang;
                }

                // Cập nhật dữ liệu lên biểu đồ
                capNhatBieuDoThangTN(lay4Thang(), mangThuNhapThang);
            }
        });
        return view;
    }
    // Phương thức cập nhật dữ liệu lên biểu đồ
    private void capNhatBieuDoCT (List<Date> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng chi tiêu 8 ngày");
        dataSet.setColor(Color.rgb(255, 0, 0)); // Màu của cột trong biểu đồ

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
    private void capNhatBieuDoTuanCT (List<String> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < expenses.length; i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng chi tiêu 6 tuần");
        dataSet.setColor(Color.rgb(255, 0, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng ngày/tháng cho trục X
        XAxis xAxis = barChart_chitieu.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Đặt dữ liệu vào biểu đồ
        barChart_chitieu.setData(barData);

        // Hiển thị biểu đồ
        barChart_chitieu.invalidate();
    }
    private void capNhatBieuDoThangCT (List<String> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < expenses.length; i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng chi tiêu 4 tháng");
        dataSet.setColor(Color.rgb(255, 0, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng ngày/tháng cho trục X
        XAxis xAxis = barChart_chitieu.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Đặt dữ liệu vào biểu đồ
        barChart_chitieu.setData(barData);

        // Hiển thị biểu đồ
        barChart_chitieu.invalidate();
    }

    private void capNhatBieuDoTN (List<Date> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng thu nhập 8 ngày");
        dataSet.setColor(Color.rgb(0, 255, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng ngày/tháng cho trục X
        XAxis xAxis = barChart_thunhap.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getFormattedDateStrings(dates)));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Đặt dữ liệu vào biểu đồ
        barChart_thunhap.setData(barData);

        // Hiển thị biểu đồ
        barChart_thunhap.invalidate();
    }

    private void capNhatBieuDoTuanTN(List<String> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < expenses.length; i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng thu nhập 6 tuần");
        dataSet.setColor(Color.rgb(0, 255, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng ngày/tháng cho trục X
        XAxis xAxis = barChart_thunhap.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Đặt dữ liệu vào biểu đồ
        barChart_thunhap.setData(barData);

        // Hiển thị biểu đồ
        barChart_thunhap.invalidate();
    }
    private void capNhatBieuDoThangTN (List<String> dates, float[] expenses) {
        // Xác định danh sách BarEntry cho dữ liệu của biểu đồ
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < expenses.length; i++) {
            entries.add(new BarEntry(i, expenses[i]));
        }

        // Tạo dataset và đặt màu
        BarDataSet dataSet = new BarDataSet(entries, "Tổng thu nhập 4 tháng");
        dataSet.setColor(Color.rgb(0, 255, 0)); // Màu của cột trong biểu đồ

        // Tạo dữ liệu biểu đồ từ dataset
        BarData barData = new BarData(dataSet);

        // Cài đặt định dạng ngày/tháng cho trục X
        XAxis xAxis = barChart_thunhap.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setLabelRotationAngle(45);

        // Đặt dữ liệu vào biểu đồ
        barChart_thunhap.setData(barData);

        // Hiển thị biểu đồ
        barChart_thunhap.invalidate();
    }

    public List<String> lay4Thang() {
        List<String> bonThangGanNhat = new ArrayList<>();
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        // Lấy tháng hiện tại
        bonThangGanNhat.add(monthFormat.format(calendar.getTime()));

        // Lấy 3 tháng trước đó
        for (int i = 0; i < 3; i++) {
            calendar.add(Calendar.MONTH, -1);
            bonThangGanNhat.add(0, monthFormat.format(calendar.getTime()));
        }

        return bonThangGanNhat;
    }
    public void hienThiBieuDoCT(){
        // Xác định ngày hiện tại
        Calendar calendar = Calendar.getInstance();

        // Danh sách chứa 10 ngày gần nhất tính từ ngày hiện tại
        List<Date> list8Day = new ArrayList<>();

        // Tạo danh sách 8 ngày gần nhất tính từ ngày hiện tại
        for (int i = 1; i < 9; i++) {
            list8Day.add(calendar.getTime());
            calendar.add(Calendar.DATE, -1); // Lùi ngày về 1 ngày
        }

        Collections.reverse(list8Day);

        // Mảng để lưu trữ tổng chi tiêu của từng ngày
        float[] mangCT = new float[8];

        // Lấy tổng chi tiêu của từng ngày trong danh sách 10 ngày gần nhất
        for (int i = 0; i < list8Day.size(); i++) {
            float chiTieu = thongKeDAO.getTongChiTieuTheoNgay(list8Day.get(i));
            mangCT[i] = chiTieu;
        }

        // Cập nhật dữ liệu lên biểu đồ
        capNhatBieuDoCT(list8Day, mangCT);
    }
    public void hienThiBieuDoTN (){
        // Xác định ngày hiện tại
        Calendar calendar = Calendar.getInstance();

        // Danh sách chứa 10 ngày gần nhất tính từ ngày hiện tại
        List<Date> list8Day = new ArrayList<>();

        // Tạo danh sách 8 ngày gần nhất tính từ ngày hiện tại
        for (int i = 1; i < 9; i++) {
            list8Day.add(calendar.getTime());
            calendar.add(Calendar.DATE, -1); // Lùi ngày về 1 ngày
        }

        Collections.reverse(list8Day);

        // Mảng để lưu trữ tổng chi tiêu của từng ngày
        float[] mangTN = new float[8];

        // Lấy tổng chi tiêu của từng ngày trong danh sách 10 ngày gần nhất
        for (int i = 0; i < list8Day.size(); i++) {
            float thuNhap = thongKeDAO.getTongThuNhapTheoNgay(list8Day.get(i));
            mangTN[i] = thuNhap;
        }

        // Cập nhật dữ liệu lên biểu đồ
        capNhatBieuDoTN(list8Day, mangTN);
    }
}