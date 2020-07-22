package com.sict.mobile.vks.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.mobile.vks.R;
import com.sict.mobile.vks.item.ItemSubjects;

import java.util.List;


public class AdapterSubjects extends RecyclerView.Adapter<AdapterSubjects.ViewHolder> {
    List<ItemSubjects> mListItemSubjects;

    public AdapterSubjects(List<ItemSubjects> mListItemSubjects) {
        this.mListItemSubjects = mListItemSubjects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subjects,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemSubjects itemSubjects= mListItemSubjects.get(position);
        holder.txt_itemSubjects_class.setText(itemSubjects.getClassSubjects());
        holder.txt_itemSubjects_name.setText(itemSubjects.getNameSubjects());
        holder.txt_itemSubjects_time.setText(itemSubjects.getTimeSubjects());
    }

    @Override
    public int getItemCount() {
        return mListItemSubjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_itemSubjects_name;
        TextView txt_itemSubjects_class;
        TextView txt_itemSubjects_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemSubjects_name = itemView.findViewById(R.id.txt_itemSubjects_name);
            txt_itemSubjects_class = itemView.findViewById(R.id.txt_itemSubjects_class);
            txt_itemSubjects_time= itemView.findViewById(R.id.txt_itemSubjects_time);
        }
    }
}
