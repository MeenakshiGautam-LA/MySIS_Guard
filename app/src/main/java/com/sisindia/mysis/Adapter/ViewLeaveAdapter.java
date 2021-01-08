package com.sisindia.mysis.Adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class ViewLeaveAdapter extends RecyclerView.Adapter<ViewLeaveAdapter.MyViewHolder> {

    private List<LeaveMaster> leaveMasterList;
    CSHeaderFragmentFragment context;
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

    public ViewLeaveAdapter(CSHeaderFragmentFragment context, List<LeaveMaster> conveyanceTravelDetailTimeLineAdapterList) {
        this.context = context;
        this.leaveMasterList = conveyanceTravelDetailTimeLineAdapterList;
    }

    public void setItemListener(ItemClciked itemClciked) {
        this.itemClciked = itemClciked;
    }


    @Override
    public ViewLeaveAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_leaving_status, parent, false);
        return new ViewLeaveAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewLeaveAdapter.MyViewHolder holder, int position) {
        LeaveMaster leaveMaster = leaveMasterList.get(position);
        holder.leaveReason.setText(
                CSApplicationHelper.application().getDatabaseInstance().getLeaveTypeList_dao().
                        singleLeave(String.valueOf(leaveMaster.getLEAVE_TYPE())).getLEAVE_TYPE());
//        holder.leaveReason.setText(CFUtil.returnLeaveReasonDescriptionText(String.valueOf(leaveMaster.getLEAVE_TYPE())));
        if (leaveMaster.getLEAVE_REQUEST_TYPE().equalsIgnoreCase("S")) { // use to validate Leave Request is Single mode OR Multiple Mode
            holder.leaveDate.setText(convertDateTo_DD_MM_Format(leaveMaster.getLeaveStartDate()));
        } else {
            holder.leaveDate.setText(convertDateTo_DD_MM_Format(leaveMaster.getLeaveStartDate()) + " - " +
                    convertDateTo_DD_MM_Format(leaveMaster.getLeaveEndDate()));
        }
        holder.leaveStatus.setText(CFUtil.returnLeaveStatusDescription(String.valueOf(leaveMaster.getLeaveStatus())));
        holder.totalLeaveDay.setText(leaveMaster.getCountLeaveDays() + " " + CSStringUtil.getString(R.string.days));

//        holder.totalLeaveDay.setText(leaveLandingModel.getLeave_days());

       /* switch (leaveLandingModel.getLeave_status()) {
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
                holder.leaveStatus.setText("Request Rejextes");
                holder.leaveStatus.setTextColor(context.getColor(R.color.leave_request_rejected));
                holder.leave_status_img.setBackground(context.getResources().getDrawable(R.drawable.icon_leaves));
                break;

        }
*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClciked.onItemClicked(holder.itemView, position);
                leaveMaster.setIschecked(true);
                context.onItemClick(holder.itemView, position);
            }
        });
        if (leaveMaster.isIschecked()) {
            holder.cancel_request_linear_layout.setVisibility(View.VISIBLE);

        } else {
            holder.cancel_request_linear_layout.setVisibility(View.GONE);
        }
        holder.cancel_request.setOnClickListener(view -> {
            itemClciked.onItemClicked(holder.cancel_request, position);

        });
    }

    public void removeAt(int position) {
        leaveMasterList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, leaveMasterList.size());
    }

    @Override
    public int getItemCount() {
        if(leaveMasterList!=null)
            return leaveMasterList.size();
        else
            return 0;
    }


    public interface ItemClciked {
        public void onItemClicked(View view, int postion);
    }

    private String convertDateTo_DD_MM_Format(String date) {
        String reuiredDate = "";
        Date convertToDate = DateUtils.getInstance().dateFromShiftDate(date);
        reuiredDate = DateUtils.getInstance().getDate_And_MonthWordFormat().format(convertToDate);
        Log.e("DATE_CONVERT>>>>", "" + "convertToDate" + convertToDate + "reuiredDate" + reuiredDate);

        return reuiredDate;
    }

}
