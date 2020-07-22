package com.sict.mobile.vks.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sict.mobile.vks.MainActivity;
import com.sict.mobile.vks.R;
import com.sict.mobile.vks.adapter.AdapterTodo;
import com.sict.mobile.vks.item.ItemTodo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TodoFragment extends Fragment {
    private RecyclerView rcvTodo;
    private AdapterTodo adapterTodo;
    private List<ItemTodo> mListItemTodo;
    private FloatingActionButton floatingActionButton;
    private EditText  etxt_activityNewTodo_date ;
    private  EditText etxt_activityNewTodo_content;
    private  EditText etxt_activityNewTodo_time;
    private Button btn_activityNewTodo_cancer;
    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_todo, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        View toolbar = LayoutInflater.from(container.getContext()).inflate(R.layout.toolbar_add, null);


        ((MainActivity) getActivity()).getSupportActionBar().setCustomView(toolbar);

//        View Add = LayoutInflater.from(container.getContext()).inflate(R.layout.activity_newtodo, null);


        mListItemTodo = new ArrayList<>();
        for (int i=0;i<17;i++){
            mListItemTodo.add(new ItemTodo("Làm Con **** , nhớ","Deadline: 29/7/2020"));
        }

        rcvTodo = root.findViewById(R.id.rcv_fragmentTodo_todo);
        rcvTodo.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterTodo = new AdapterTodo(mListItemTodo);
        rcvTodo.setAdapter(adapterTodo);

        floatingActionButton = toolbar.findViewById(R.id.abtn_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddNew();
            }
        });
        return root;


    }

//    public void showAlertDialogButtonClicked(View view) {
//        // create an alert builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Name");
//        // set the custom layout
//        final View customLayout = getLayoutInflater().inflate(R.layout.activity_newtodo, null);
//        builder.setView(customLayout);
//        // add a button
//
//        // create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }


    public  void DialogAddNew(){
//        Intent intent = new Intent(getActivity(), AddNewTodo.class);
//        startActivity(intent);
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activity_newtodo);
        dialog.show();
        btn_activityNewTodo_cancer = dialog.findViewById(R.id.btn_activityNewTodo_cancer);
        btn_activityNewTodo_cancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        etxt_activityNewTodo_date = dialog.findViewById(R.id.etxt_activityNewTodo_date);
        etxt_activityNewTodo_content = dialog.findViewById(R.id.etxt_activityNewTodo_content);
        etxt_activityNewTodo_time = dialog.findViewById(R.id.etxt_activityNewTodo_time);
        etxt_activityNewTodo_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        etxt_activityNewTodo_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });
    }

    public  void selectDate(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam= calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                etxt_activityNewTodo_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }

    public void selectTime(){
        final Calendar calendar = Calendar.getInstance();
        final int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0,0,0,hourOfDay,minute);
                SimpleDateFormat simpletimeFormat = new SimpleDateFormat("HH:mm:ss");
                etxt_activityNewTodo_time.setText(simpletimeFormat.format(calendar.getTime()));
            }
        },gio,phut,true);
        timePickerDialog.show();
    }
}
