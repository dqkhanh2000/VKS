package com.sict.mobile.vks.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.sict.mobile.vks.R;
import com.sict.mobile.vks.adapter.AdapterNotificationHome;
import com.sict.mobile.vks.adapter.AdapterToday;
import com.sict.mobile.vks.item.ItemNotificationHome;
import com.sict.mobile.vks.item.ItemToday;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView rcvNotificationHome;
    private AdapterNotificationHome adapterNotificationHome;
    private List<ItemNotificationHome> mListItemNotificationHomes;

    private  RecyclerView rcvToday;
    private AdapterToday adapterToday;
    private List<ItemToday> mListItemToday;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mListItemNotificationHomes  = new ArrayList<>();
        mListItemNotificationHomes.add(new ItemNotificationHome("Thông báo kế hoạch học Giáo dục Quốc phòng - An ninh trong học kì 2 năm học 2019-2020","03/07/2020 12:07"));
        mListItemNotificationHomes.add(new ItemNotificationHome("Thông báo kế hoạch Thực tập doanh nghiệp học kỳ 2 năm học 2019 - 2020","03/07/2020 12:07"));
        mListItemNotificationHomes.add(new ItemNotificationHome("Danh sách thi kết thúc học phần Học Kỳ 2 năm học 2019-2020 ","03/07/2020 12:07"));
        mListItemNotificationHomes.add(new ItemNotificationHome("Thông báo về việc nộp và bảo vệ đề án 6 đối với sinh viên khóa 17BA","03/07/2020 12:07"));
        mListItemNotificationHomes.add(new ItemNotificationHome("Thông báo kế hoạch học Giáo dục Quốc phòng - An ninh trong học kì 2 năm học 2019-2020","03/07/2020 12:07"));

        rcvNotificationHome = root.findViewById(R.id.rcv_fragmentHome_notification);
        rcvNotificationHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterNotificationHome = new AdapterNotificationHome(mListItemNotificationHomes);
        rcvNotificationHome.setAdapter(adapterNotificationHome);

        mListItemToday = new ArrayList<>();
        mListItemToday.add(new ItemToday("Lập trình di động(1)","ThS. Nguyễn Anh Tuấn","Phòng: B203","Tiết: 6->7"));
        mListItemToday.add(new ItemToday("Lập trình di động(1)","ThS. Nguyễn Anh Tuấn","Phòng: B203","Tiết: 6->7"));
        mListItemToday.add(new ItemToday("Lập trình di động(1)","ThS. Nguyễn Anh Tuấn","Phòng: B203","Tiết: 6->7"));
        mListItemToday.add(new ItemToday("Lập trình di động(1)","ThS. Nguyễn Anh Tuấn","Phòng: B203","Tiết: 6->7"));

        rcvToday = root.findViewById(R.id.rcv_fragmentHome_today);
        rcvToday.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterToday = new AdapterToday(mListItemToday, this);
        rcvToday.setAdapter(adapterToday);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AdapterToday.REQUEST_CODE_RECOG){
            if(data != null){
                boolean isMatch = data.getBooleanExtra("isMatch", false);
                int position = data.getIntExtra("POSITION", -1);
                if(isMatch && position != -1){
                    new KAlertDialog(getContext(), KAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Thành công")
                            .setContentText("Đã điểm danh "+mListItemToday.get(position).getSubjectTitle())
                            .setConfirmText("OK")
                            .show();
                }
                else
                    new KAlertDialog(getContext(), KAlertDialog.ERROR_TYPE)
                            .setTitleText("Lỗi")
                            .setContentText("Dữ liệu không khớp")
                            .setCancelText("OK")
                            .show();
            }

        }
    }
}
