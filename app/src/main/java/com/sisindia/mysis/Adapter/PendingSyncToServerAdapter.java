package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sisindia.mysis.R;
import com.sisindia.mysis.entity.DailyAttendanceDetail;

import java.util.List;



public class PendingSyncToServerAdapter extends RecyclerView.Adapter<PendingSyncToServerAdapter.ViewHolder> {
    List<DailyAttendanceDetail> dailyAttendanceDetailList;
    Context context;

    public PendingSyncToServerAdapter(Context context, List<DailyAttendanceDetail> dailyAttendanceDetails) {
        this.context = context;
        dailyAttendanceDetailList = dailyAttendanceDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_sync_data_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTV.setText(dailyAttendanceDetailList.get(position).getREGNO());
    }

    @Override
    public int getItemCount() {
        return dailyAttendanceDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
        }
    }
}
