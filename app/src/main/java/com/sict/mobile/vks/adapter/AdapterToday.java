package com.sict.mobile.vks.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.sict.mobile.vks.R;
import com.sict.mobile.vks.RecognitionActivity;
import com.sict.mobile.vks.item.ItemToday;
import com.sict.mobile.vks.utils.UserUtils;

import java.util.List;


public class AdapterToday extends RecyclerView.Adapter<AdapterToday.ViewHolder> {
    public static int REQUEST_CODE_RECOG = 101;
    List<ItemToday> mListItemToday;
    private Context context;
    private View parent;
    private Fragment parentFragment;

    public AdapterToday(List<ItemToday> mListItemToday) {
        this.mListItemToday = mListItemToday;
    }

    public AdapterToday(List<ItemToday> mListItemToday, Fragment parentFragment) {
        this.mListItemToday = mListItemToday;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        this.parent = parent;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemToday itemToday = mListItemToday.get(position);
        holder.txt_itemToday_subjectTitle.setText(itemToday.getSubjectTitle());
        holder.txt_itemToday_teacher.setText(itemToday.getTeacher());
        holder.txt_itemToday_class.setText(itemToday.getClassName());
        holder.txt_itemToday_lesson.setText(itemToday.getLesson());
        holder.btnAttendance.setOnClickListener(v -> {
            if(!UserUtils.isHasLogin()) {
                Navigation.findNavController(parent).navigate(R.id.navigation_user);
            }
            else{
                Intent intent = new Intent(context, RecognitionActivity.class);
                intent.putExtra("SUBJECT", itemToday.getSubjectTitle());
                intent.putExtra("POSITION", position);
                if(UserUtils.isHasData())
                    intent.putExtra("MODE", "recognition");
                else
                    intent.putExtra("MODE", "add");

                parentFragment.startActivityForResult(intent, REQUEST_CODE_RECOG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItemToday.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_itemToday_subjectTitle;
        TextView txt_itemToday_class;
        TextView txt_itemToday_teacher;
        TextView txt_itemToday_lesson;
        Button btnAttendance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemToday_subjectTitle = itemView.findViewById(R.id.txt_itemToday_subjectTitle);
            txt_itemToday_class = itemView.findViewById(R.id.txt_itemToday_class);
            txt_itemToday_teacher = itemView.findViewById(R.id.txt_itemToday_teacher);
            txt_itemToday_lesson = itemView.findViewById(R.id.txt_itemToday_lesson);
            btnAttendance = itemView.findViewById(R.id.btn_itemToday_attendance);
        }
    }

}
