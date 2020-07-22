package com.sict.mobile.vks.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.mobile.vks.R;
import com.sict.mobile.vks.item.ItemNotification;

import java.util.List;


public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolder> {
    List<ItemNotification> mListItemNofication;

    public AdapterNotification(List<ItemNotification> mListItemNofication) {
        this.mListItemNofication = mListItemNofication;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemNotification itemNotification = mListItemNofication.get(position);
        holder.txt_itemNotification_Name.setText(itemNotification.getNotificationName());
        holder.txt_itemNotification_Time.setText(itemNotification.getNotificationTime());
        holder.txt_itemNotification_Content.setText(itemNotification.getNotificationContent());
        holder.txt_itemNotification_Link.setText(itemNotification.getNotificationLink());
    }

    @Override
    public int getItemCount() {
        return mListItemNofication.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_itemNotification_Name;
        TextView txt_itemNotification_Time;
        TextView txt_itemNotification_Content;
        TextView txt_itemNotification_Link;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemNotification_Name = itemView.findViewById(R.id.txt_itemNotification_name);
            txt_itemNotification_Time = itemView.findViewById(R.id.txt_itemNotification_time);
            txt_itemNotification_Content = itemView.findViewById(R.id.txt_itemNotification_content);
            txt_itemNotification_Link = itemView.findViewById(R.id.txt_itemNotification_link);
        }
    }
}
