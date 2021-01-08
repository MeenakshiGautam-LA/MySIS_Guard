package com.sisindia.mysis.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.R;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class CalenderViewAdapter extends RecyclerView.Adapter<CalenderViewAdapter.MyViewHolder> {

    private List<CalendarDayModel> calendarDayModelsList = new ArrayList<>();
    private TaskCalendarClickListener listener = null;
    private Context mContext;
    private Boolean _dateRange;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rv_day_tv, extra_duty_tv;
        private LinearLayout day_container;
        private FrameLayout frame_layout;
        private ImageView approved_leave_IV, applied_leave_IV;

        public MyViewHolder(View view) {
            super(view);
            rv_day_tv = view.findViewById(R.id.rv_day_tv);
            day_container = view.findViewById(R.id.day_container);
            frame_layout = view.findViewById(R.id.frame_layout);
            approved_leave_IV = view.findViewById(R.id.approved_leave_IV);
            extra_duty_tv = view.findViewById(R.id.extra_duty_tv);
            applied_leave_IV = view.findViewById(R.id.applied_leave_IV);
        }
    }

    public CalenderViewAdapter(Context context, List<CalendarDayModel> calendarDayModelsList, TaskCalendarClickListener listener, Boolean _dateRange) {
        this.mContext = context;
        this.calendarDayModelsList = calendarDayModelsList;
        this.listener = listener;
        this._dateRange = _dateRange;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_singal_day_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final CalendarDayModel calendarDayModel = calendarDayModelsList.get(position);
        if (Integer.parseInt(calendarDayModel.getDateSingleDigit()) > 0) {
            holder.rv_day_tv.setText(calendarDayModel.getDateSingleDigit());

            holder.day_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!CSStringUtil.isEmptyStr(calendarDayModel.getLEAVE_TYPE_ID())) {
                        if (calendarDayModel.getLEAVE_TYPE_ID().equalsIgnoreCase("p")) {
                            listener.taskClicked(Integer.parseInt(calendarDayModel.getDateSingleDigit()), "ATTENDANCE");
                        } else {
                            listener.taskClicked(Integer.parseInt(calendarDayModel.getDateSingleDigit()), "LEAVE");
                        }
                    }
                }
            });
            if (calendarDayModel.getLeaveSelected()) {
                holder.rv_day_tv.setBackground(mContext.getResources().getDrawable(R.drawable.day_dec_tour_leave));
                holder.rv_day_tv.setTextColor(Color.BLACK);
            } else if (calendarDayModel.getSelected()) {
                if (!CSStringUtil.isEmptyStr(calendarDayModel.getLEAVE_TYPE_ID())) {
                    setLeaveStatusOn_Calender(calendarDayModel, holder);
                }
            }
        } else {
            holder.rv_day_tv.setText("0");
            holder.rv_day_tv.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("ResourceType")
    private void setLeaveStatusOn_Calender(CalendarDayModel calendarDayModel, MyViewHolder holder) {
        if (calendarDayModel.getLEAVE_TYPE_ID().equalsIgnoreCase("P")) {
            if (calendarDayModel.getExtraDuty() > 1) {
                holder.extra_duty_tv.setVisibility(View.VISIBLE);
            } else {
                holder.extra_duty_tv.setVisibility(View.GONE);

            }
            if (!CSStringUtil.isEmptyStr(calendarDayModel.getUN_SYNC_DATA())) {
                if (calendarDayModel.getUN_SYNC_DATA().equalsIgnoreCase("1")) {

                    holder.frame_layout.setBackgroundDrawable(CFUtil.leaveReasonBackGround(Color.parseColor(mContext.
                            getString(R.color.un_sync_data))));
                } else {
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {

                        holder.frame_layout.setBackgroundDrawable(CFUtil.leaveReasonBackGround(calendarDayModel.getColorCode()));

                    } else {
                        holder.frame_layout.setBackground(CFUtil.leaveReasonBackGround(calendarDayModel.getColorCode()));


                    }
                }
            } else {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.frame_layout.setBackgroundDrawable(CFUtil.leaveReasonBackGround(calendarDayModel.getColorCode()));
                } else {
                    holder.frame_layout.setBackground(CFUtil.leaveReasonBackGround(calendarDayModel.getColorCode()));
                }
            }
            holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.present));
        } else {
            if (calendarDayModel.getLeaveStatus().equalsIgnoreCase("0")) {
                if (calendarDayModel.getUN_SYNC_DATA().equalsIgnoreCase("1")) {
                    holder.frame_layout.setBackgroundDrawable(CFUtil.leaveReasonBackGround(Color.parseColor(mContext.getString(R.color.un_sync_data))));
                }
                holder.approved_leave_IV.setVisibility(View.GONE);
                holder.applied_leave_IV.setVisibility(View.VISIBLE);
            } else {
                if (DateUtils.getInstance().compareDate_Nd_TimeFormat(DateUtils.getCurrentDate_format() + " " + "00:00:00",
                        calendarDayModel.getDate() + " " + "00:00:00")) {
                    holder.frame_layout.setBackgroundDrawable(CFUtil.leaveReasonBackGround(Color.parseColor(mContext.
                            getString(R.color.attendance_not_mark_backGround))));
                    holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.app_default_red_color));
                } else {
                }
                holder.approved_leave_IV.setVisibility(View.VISIBLE);
                holder.applied_leave_IV.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return calendarDayModelsList.size();
    }

    public interface TaskCalendarClickListener {
        // void taskClicked(int date, View view, String leaveReason, List<String> dateRange, Boolean dateRangeStatus, Boolean selectionStatus);
        void taskClicked(int date, String statusType);
    }

}

