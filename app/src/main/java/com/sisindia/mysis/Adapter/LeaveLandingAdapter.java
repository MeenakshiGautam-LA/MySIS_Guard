package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Model.LeaveLandingModel;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;

import java.util.List;

public class LeaveLandingAdapter extends RecyclerView.Adapter<LeaveLandingAdapter.MyViewHolder> {

    private List<LeaveLandingModel> conveyanceTravelDetailTimeLineAdapterList;
    Context context;
    ItemClciked itemClciked;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leaveReason, totalLeaveDay, leaveStatus, leaveDate, cancel_request;
        ImageView leave_status_img;

        LinearLayout cancel_request_linear_layout;

        public MyViewHolder(View view) {
            super(view);
            leaveReason = view.findViewById(R.id.leave_reason);
            totalLeaveDay = view.findViewById(R.id.leave_days);
            leaveDate = view.findViewById(R.id.leave_date);
            leaveStatus = view.findViewById(R.id.leave_status);
            leave_status_img = view.findViewById(R.id.leave_status_img);
            cancel_request = view.findViewById(R.id.cancel_request);
            cancel_request_linear_layout = view.findViewById(R.id.cancel_request_linear_layout);

        }
    }
    public LeaveLandingAdapter(Context context, List<LeaveLandingModel> conveyanceTravelDetailTimeLineAdapterList) {
        this.context = context;
        this.conveyanceTravelDetailTimeLineAdapterList = conveyanceTravelDetailTimeLineAdapterList;
    }

   public void setItemListener(ItemClciked itemClciked){
        this.itemClciked = itemClciked;
   }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_leaving_status, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LeaveLandingModel leaveLandingModel = conveyanceTravelDetailTimeLineAdapterList.get(position);
//        holder.leaveReason.setText(CFUtil.returnLeaveReasonDescriptionText(leaveLandingModel.getLeave_reason()));
        holder.leaveReason.setText(CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao()
        .singleLeave(leaveLandingModel.getLeaveId()).getLEAVE_TYPE());
        holder.leaveDate.setText(leaveLandingModel.getLeave_date());
        holder.totalLeaveDay.setText(leaveLandingModel.getLeave_days());

        switch (leaveLandingModel.getLeave_status()) {
            case "0":
                holder.leaveStatus.setText("Pending Request");
                holder.leaveStatus.setTextColor(context.getColor(R.color.processing_color));
                holder.leave_status_img.setBackground(context.getResources().getDrawable(R.drawable.icon_leaves));
                break;
            case "1":
                holder.leaveStatus.setText("Approved Leave");
                // holder.leaveStatus.setTextColor(context.getColor(R.color.leave_request_approved));
//                holder.leave_status_img.setBackground(context.getResources().getDrawable(R.drawable.icon_leaves));
                break;
            case "2":
                holder.leaveStatus.setText("Request Reject");
                holder.leaveStatus.setTextColor(context.getColor(R.color.leave_request_rejected));
                holder.leave_status_img.setBackground(context.getResources().getDrawable(R.drawable.icon_leaves));
                break;

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClciked.onItemClicked(holder.itemView, position);
                leaveLandingModel.set_isClicked(true);
                //csFragment.onItemClick(holder.itemView, position);
            }
        });
        if (leaveLandingModel.get_isClicked()) {
            holder.cancel_request_linear_layout.setVisibility(View.VISIBLE);

        }else {
            holder.cancel_request_linear_layout.setVisibility(View.GONE);
        }
        holder.cancel_request.setOnClickListener(view -> {
//            CSAppUtil.showToast(leaveLandingModel.getLeaveId());
        });
    }


    @Override
    public int getItemCount() {
        return conveyanceTravelDetailTimeLineAdapterList.size();
    }


    public interface ItemClciked{
        public void onItemClicked(View view, int postion);
    }
}
