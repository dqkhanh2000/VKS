package com.sict.mobile.vks.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.mobile.vks.R;
import com.sict.mobile.vks.adapter.AdapterSubjects;
import com.sict.mobile.vks.item.ItemSubjects;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Spinner spn_date;
    private RecyclerView rcvSubjects;
    private AdapterSubjects adapterSubjects;
    private List<ItemSubjects> mListItemSubjects;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        rcvSubjects = root.findViewById(R.id.rcv_fragmentDashboard_subjects);
        mListItemSubjects = new ArrayList<>();
        rcvSubjects.setLayoutManager(new LinearLayoutManager(getActivity()));
        spn_date = root.findViewById(R.id.spn_fragmentDashboard_date);
        final ArrayList<String> arrayListDate = new ArrayList<String>();
        arrayListDate.add("Thứ 2");
        arrayListDate.add("Thứ 3");
        arrayListDate.add("Thứ 4");
        arrayListDate.add("Thứ 5");
        arrayListDate.add("Thứ 6");
        arrayListDate.add("Thứ 7");

        final ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,arrayListDate);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_date.setAdapter(arrayAdapter);

        spn_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choose = arrayListDate.get(position);
                if(choose.equals("Thứ 2")){
                    mListItemSubjects.clear();
                    for (int i=0;i<5;i++){
                        mListItemSubjects.add(new ItemSubjects("Lập trình di động","Tiết: 1-2","Phòng: B203"));
                    }
                    adapterSubjects = new AdapterSubjects(mListItemSubjects);
                    rcvSubjects.setAdapter(adapterSubjects);
                }else if(choose.equals("Thứ 3")){
                    mListItemSubjects.clear();
                    for (int i=0;i<5;i++){
                        mListItemSubjects.add(new ItemSubjects("Tiếng anh chuyên ngành và thực hành 3_3","Tiết: 1-2","Phòng: B203"));
                    }
                    adapterSubjects = new AdapterSubjects(mListItemSubjects);
                    rcvSubjects.setAdapter(adapterSubjects);
                }else if(choose.equals("Thứ 4")){
                    mListItemSubjects.clear();
                    for (int i=0;i<5;i++){
                        mListItemSubjects.add(new ItemSubjects("Môn thứ 4","Tiết: 1-2","Phòng: B203"));
                    }
                    adapterSubjects = new AdapterSubjects(mListItemSubjects);
                    rcvSubjects.setAdapter(adapterSubjects);
                }else if(choose.equals("Thứ 5")){
                    mListItemSubjects.clear();
                    for (int i=0;i<20;i++){
                        mListItemSubjects.add(new ItemSubjects("Môn thứ 5","Tiết: 1-2","Phòng: B203"));
                    }
                    adapterSubjects = new AdapterSubjects(mListItemSubjects);
                    rcvSubjects.setAdapter(adapterSubjects);
                }else if(choose.equals("Thứ 6")){
                    mListItemSubjects.clear();
                    for (int i=0;i<5;i++){
                        mListItemSubjects.add(new ItemSubjects("Môn thứ 6","Tiết: 1-2","Phòng: B203"));
                    }
                    adapterSubjects = new AdapterSubjects(mListItemSubjects);
                    rcvSubjects.setAdapter(adapterSubjects);
                }else if(choose.equals("Thứ 7")){
                    mListItemSubjects.clear();
                    for (int i=0;i<5;i++){
                        mListItemSubjects.add(new ItemSubjects("Môn thứ 7","Tiết: 1-2","Phòng: B203"));
                    }
                    adapterSubjects = new AdapterSubjects(mListItemSubjects);
                    rcvSubjects.setAdapter(adapterSubjects);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }
}
