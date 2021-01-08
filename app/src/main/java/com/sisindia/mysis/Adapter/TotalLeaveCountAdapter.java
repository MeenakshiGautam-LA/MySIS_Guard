package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.R;

import java.util.List;

public class TotalLeaveCountAdapter extends RecyclerView.Adapter<TotalLeaveCountAdapter.ViewHolder> {

    Context context;
    List<CalendarDayModel> leaveDayList;

    public TotalLeaveCountAdapter(Context context, List<CalendarDayModel> leaveDayList) {
        this.leaveDayList = leaveDayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singal_total_leave_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarDayModel calendarDayModel = leaveDayList.get(position);
        holder.day_txt.setText(calendarDayModel.getLEAVE_DATE().split(" ")[0]);
        holder.leave_reason.setText("Reason :"+calendarDayModel.getLEAVE_TYPE_ID());
    }

    @Override
    public int getItemCount() {
        return leaveDayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day_txt,leave_reason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day_txt = itemView.findViewById(R.id.day_txt);
            leave_reason = itemView.findViewById(R.id.leave_reason);
        }
    }
}
