package com.sict.mobile.vks.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.mobile.vks.R;
import com.sict.mobile.vks.item.ItemNotificationHome;

import java.util.List;


public class AdapterNotificationHome extends RecyclerView.Adapter<AdapterNotificationHome.ViewHolder> {
    List<ItemNotificationHome> mListItemNoficationHome;

    public AdapterNotificationHome(List<ItemNotificationHome> mListItemNoficationHome) {
            this.mListItemNoficationHome = mListItemNoficationHome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_home,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       ItemNotificationHome itemNotificationHome = mListItemNoficationHome.get(position);
        holder.txt_itemNotificationHome_Name.setText(itemNotificationHome.getNameNotification());
        holder.txt_itemNotificationHome_Time.setText(itemNotificationHome.getTimeNotification());
    }

    @Override
    public int getItemCount() {
        return mListItemNoficationHome.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_itemNotificationHome_Name;
        TextView txt_itemNotificationHome_Time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_itemNotificationHome_Name = itemView.findViewById(R.id.txt_itemNotificationHome_name);
            txt_itemNotificationHome_Time = itemView.findViewById(R.id.txt_itemNotificationHome_time);
        }
    }
}
