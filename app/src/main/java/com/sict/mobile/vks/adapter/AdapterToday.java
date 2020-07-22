package com.sict.mobile.vks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.mobile.vks.R;
import com.sict.mobile.vks.item.ItemToday;

import java.util.List;


public class AdapterToday extends RecyclerView.Adapter<AdapterToday.ViewHolder> {
    List<ItemToday> mListItemToday;

    public AdapterToday(List<ItemToday> mListItemToday) {
        this.mListItemToday = mListItemToday;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemToday_subjectTitle = itemView.findViewById(R.id.txt_itemToday_subjectTitle);
            txt_itemToday_class = itemView.findViewById(R.id.txt_itemToday_class);
            txt_itemToday_teacher = itemView.findViewById(R.id.txt_itemToday_teacher);
            txt_itemToday_lesson = itemView.findViewById(R.id.txt_itemToday_lesson);


        }
    }
}
