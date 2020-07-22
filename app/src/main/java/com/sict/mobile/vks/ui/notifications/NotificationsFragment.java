package com.sict.mobile.vks.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.mobile.vks.R;
import com.sict.mobile.vks.adapter.AdapterNotification;
import com.sict.mobile.vks.item.ItemNotification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private RecyclerView rcvNotification;
    private AdapterNotification adapterNotification;
    private List<ItemNotification> mListitemNotifications;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        mListitemNotifications = new ArrayList<>();
        for (int i=0;i<5;i++){
            mListitemNotifications.add(new ItemNotification("Thông báo kế hoạch học Giáo dục Quốc phòng - An ninh trong học kì 2 năm học 2019-2020","03/07/2020 12:07","Công ty TNHH Mothersbaby Việt Nam aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","Công ty TNHH Mothersbaby Việt Nam aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        }
        rcvNotification = root.findViewById(R.id.rcv_fragmentNotification_notification);
        rcvNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterNotification = new AdapterNotification(mListitemNotifications);
        rcvNotification.setAdapter(adapterNotification);
        return root;
    }
}
