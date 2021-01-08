package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Model.LeaveLandingModel;
import com.sisindia.mysis.R;

import java.util.List;


public class LeaveHistoryAdapter extends RecyclerView.Adapter<LeaveHistoryAdapter.MyViewHolder> {

    private List<LeaveLandingModel> conveyanceTravelDetailTimeLineAdapterList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView leave_reason, leave_days, leave_date;

        public MyViewHolder(View view) {
            super(view);
            leave_reason = view.findViewById(R.id.leave_reason);
            leave_days = view.findViewById(R.id.leave_days);
            leave_date = view.findViewById(R.id.leave_date);
        }
    }


    public LeaveHistoryAdapter(Context context, List<LeaveLandingModel> conveyanceTravelDetailTimeLineAdapterList) {
        this.context = context;
        this.conveyanceTravelDetailTimeLineAdapterList = conveyanceTravelDetailTimeLineAdapterList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_leave_history_adapter_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LeaveLandingModel leaveLandingModel = conveyanceTravelDetailTimeLineAdapterList.get(position);
        holder.leave_reason.setText(leaveLandingModel.getLeave_reason());
        holder.leave_date.setText(leaveLandingModel.getLeave_date());
        holder.leave_days.setText(leaveLandingModel.getLeave_days());
    }


    @Override
    public int getItemCount() {
        return conveyanceTravelDetailTimeLineAdapterList.size();
    }
}
