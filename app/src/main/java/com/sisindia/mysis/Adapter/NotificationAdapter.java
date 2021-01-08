package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;

import java.util.List;

public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    List<String> list;
    Context mContext;
    boolean flag=false;
    public NotificationAdapter(Context mContext, List<String> list) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notification_date_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.employeeName.setText(list.get(position));
        holder.txt_notificationTilte.setText(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_notificationTilte, duty_status, dutyRank, leave_reason, leave_time, leave_date, leave_from_date, leave_from_month, leave_end_date, leave_end_month;
        ImageView employeeImg;
        CardView cardView1;
        LinearLayout linear_request;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_notificationTilte=itemView.findViewById(R.id.txt_notificationTilte);
//
//            dutyRank = itemView.findViewById(R.id.dutyRank);
        }
    }
}