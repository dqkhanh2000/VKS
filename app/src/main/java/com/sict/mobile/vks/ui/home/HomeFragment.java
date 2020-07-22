package com.sict.mobile.vks.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

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
        adapterToday = new AdapterToday(mListItemToday);
        rcvToday.setAdapter(adapterToday);
        return root;
    }
}
