package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class AddLeaveCalendarAdapter extends RecyclerView.Adapter<AddLeaveCalendarAdapter.MyViewHolder> {

    private List<CalendarDayModel> calendarDayModelsList = new ArrayList<>();
    private CalendarOnDateClickListener listener = null;
    private Context mContext;
    private Boolean _dateRange;
    List<String> rangeDate = new ArrayList<>();
    //AddLeaveCalendarAdapter.SelectCalendarClickListener selectCalendarClickListener;
    List<String> emptyRangeDate;
    private String startDate = "", endDate = "";

    public AddLeaveCalendarAdapter(Context context, List<CalendarDayModel> calendarDayModelsList, CalendarOnDateClickListener listener,
                                   Boolean _dateRange, String startDate, String endDate) {
        this.mContext = context;
        this.calendarDayModelsList = calendarDayModelsList;
        this.listener = listener;
        this._dateRange = _dateRange;
        this.startDate = startDate;
        this.endDate = endDate;
        Log.e("AddLeaveCalendarAdapter", "endDate" + endDate);
        Log.e("AddLeaveCalendarAdapter", "startDate" + startDate);
        rangeDate.clear();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_singal_day_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CalendarDayModel calendarDayModel = calendarDayModelsList.get(position);
        if (Integer.parseInt(calendarDayModel.getDateSingleDigit()) > 0) {
            if (DateUtils.compareDate(DateUtils.getCurrentDate_format(), calendarDayModel.getDate(), true)) {

                holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.black));
                if (calendarDayModel.isAlreadyLeaveApply()) {
                    holder.frame_layout.setBackgroundDrawable(CFUtil.leaveReasonBackGround(calendarDayModel.getColorCode()));
                    holder.rv_day_tv.setTextColor(Color.WHITE);
                }
                holder.day_container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (DateUtils.compareDate(DateUtils.getCurrentDate_format(), calendarDayModel.getDate())) {
                            calendarDayModel.setSelected(true);
                            if (CSApplicationHelper.application().getDatabaseInstance().leaveTransactionDetail_dao().checkRecordInDB(calendarDayModel.getDate(),
                                    CSShearedPrefence.getUser())) {
                                CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.leave_assign_for_day));

                            } else {
                                listener.clickOnDate(position, calendarDayModel.getDate());
                            }
                        } else {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.Date_Not_Allowed));

                        }

                    }

                });
            } else {
                if (calendarDayModel.isAlreadyLeaveApply()) {
                    holder.frame_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.background_border_red_app_bg));
                    holder.rv_day_tv.setTextColor(Color.WHITE);
                }
                holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.gray));
                holder.day_container.setOnClickListener(view -> CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.past_date_not_raise_Leave)));

            }
            holder.rv_day_tv.setText(calendarDayModel.getDateSingleDigit());


            if (_dateRange) {
                if (calendarDayModel.getDayNumber() == 1) {
                    holder.day_container.setBackground(mContext.getResources().getDrawable(R.drawable.start_date_selection_range));
                    holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    //  rangeDate.add(calendarDayModel.getDateSingleDigit());
                    holder.rv_day_tv.setBackground(null);
                }

                if (calendarDayModel.getDayNumber() == 2) {
                    holder.day_container.setBackground(mContext.getResources().getDrawable(R.drawable.middle_selection_range));
                    holder.rv_day_tv.setBackground(null);
                    holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    // rangeDate.add(calendarDayModel.getDateSingleDigit());
                }
                if (calendarDayModel.getDayNumber() == 3) {
                    holder.day_container.setBackground(mContext.getResources().getDrawable(R.drawable.end_date_selection_range));
                    holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    //  rangeDate.add(calendarDayModel.getDateSingleDigit());
                    holder.rv_day_tv.setBackground(null);
                }
            }
            if (calendarDayModel.getSelected()) {
                holder.frame_layout.setBackground(mContext.getResources().getDrawable(R.drawable.day_dec_planned_leave));
//                holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.purple));
                holder.rv_day_tv.setTextColor(Color.WHITE);
            }
            /*else if (calendarDayModel.getToday()) {
                holder.rv_day_tv.setBackground(mContext.getResources().getDrawable(R.drawable.day_dec_transfer_leave));
                holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.transfer_color));
            } */

        } else {
            holder.rv_day_tv.setText("0");
            holder.rv_day_tv.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return calendarDayModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rv_day_tv;
        private LinearLayout day_container;
        private FrameLayout frame_layout;

        public MyViewHolder(View view) {
            super(view);
            rv_day_tv = view.findViewById(R.id.rv_day_tv);
            day_container = view.findViewById(R.id.day_container);
            frame_layout = view.findViewById(R.id.frame_layout);
        }
    }


    public interface CalendarOnDateClickListener {
        void clickOnDate(int date, String type);
    }

}
