package com.sict.mobile.vks.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.mobile.vks.R;
import com.sict.mobile.vks.item.ItemTodo;

import java.util.List;


public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.ViewHolder> {
    List<ItemTodo> mListItemTodo;

    public AdapterTodo(List<ItemTodo> mListItemTodo) {
        this.mListItemTodo = mListItemTodo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemTodo itemTodo = mListItemTodo.get(position);
        holder.txt_itemTodo_Name.setText(itemTodo.getNameTodo());
        holder.txt_itemTodo_Time.setText(itemTodo.getTimetodo());
    }

    @Override
    public int getItemCount() {
        return mListItemTodo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_itemTodo_Name;
        TextView txt_itemTodo_Time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemTodo_Name = itemView.findViewById(R.id.txt_itemTodo_name);
            txt_itemTodo_Time = itemView.findViewById(R.id.txt_itemTodo_time);
        }
    }
}
