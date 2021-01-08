package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sisindia.mysis.Model.CalendarDayModel;
import com.sisindia.mysis.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeCalendarAdapter extends RecyclerView.Adapter<EmployeeCalendarAdapter.MyViewHolder> {

    private List<CalendarDayModel> calendarDayModelsList = new ArrayList<>();
    private TaskCalendarClickListener listener = null;
    private Context mContext;
    private Boolean _dateRange;
    // LeaveEmployeeMaster leaveEmployeeMasters;
    List<String> rangeDate = new ArrayList<>();
    SelectCalendarClickListener selectCalendarClickListener;
    List<String> emptyRangeDate;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rv_day_tv;
        LinearLayout day_container;

        public MyViewHolder(View view) {
            super(view);
            rv_day_tv = view.findViewById(R.id.rv_day_tv);
            day_container = view.findViewById(R.id.day_container);
        }
    }

    public EmployeeCalendarAdapter(Context context, List<CalendarDayModel> calendarDayModelsList, TaskCalendarClickListener listener, Boolean _dateRange) {
        this.mContext = context;
        this.calendarDayModelsList = calendarDayModelsList;
        this.listener = listener;
        this._dateRange = _dateRange;
        rangeDate.clear();
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
                    Log.d("log>>>>>", "date range status....    " + calendarDayModel.get_dateRange());
                    Log.d("log>>>>>", "date getDateSingleDigit....    " + calendarDayModel.getDateSingleDigit());
                    Log.d("log>>>>>", " getMonth....    " + calendarDayModel.getMonth());
                    Log.d("log>>>>>", "rangeDate array....    " + rangeDate);
                    listener.taskClicked(Integer.parseInt(calendarDayModel.getDateSingleDigit()));
                    if (calendarDayModel.get_dateRange()) {
                        // listener.taskClicked(Integer.parseInt(calendarDayModel.getDateSingleDigit()), holder.day_container,calendarDayModel.getLEAVE_DESCRIPTION(),rangeDate,calendarDayModel.get_dateRange(),calendarDayModel.isSelected());
                    } else {
                        emptyRangeDate = new ArrayList<>();
                        // calendarDayModel.setSelected(true);
                        // listener.taskClicked(Integer.parseInt(calendarDayModel.getDateSingleDigit()), holder.day_container,calendarDayModel.getLEAVE_DESCRIPTION(),emptyRangeDate,calendarDayModel.get_dateRange(),calendarDayModel.isSelected());
                        // listener.taskClicked(position, holder.day_container,calendarDayModel.getLEAVE_DESCRIPTIONS(),emptyRangeDate,calendarDayModel.get_dateRange(),calendarDayModel.isSelected());
                    }
                }
            });


            if (calendarDayModel.get_dateRange()) {
                if (calendarDayModel.getDayNumber() == 1) {
                    holder.day_container.setBackground(mContext.getResources().getDrawable(R.drawable.start_date_selection_range));
                    holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    rangeDate.add(calendarDayModel.getDateSingleDigit());
                }

                if (calendarDayModel.getDayNumber() == 2) {
                    holder.day_container.setBackground(mContext.getResources().getDrawable(R.drawable.middle_selection_range));
                    holder.rv_day_tv.setBackground(null);
                    holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    rangeDate.add(calendarDayModel.getDateSingleDigit());
                }
                if (calendarDayModel.getDayNumber() == 3) {
                    holder.day_container.setBackground(mContext.getResources().getDrawable(R.drawable.end_date_selection_range));
                    holder.rv_day_tv.setBackground(null);
                    holder.rv_day_tv.setTextColor(mContext.getResources().getColor(R.color.white));
                    rangeDate.add(calendarDayModel.getDateSingleDigit());
                }
            } else {
                if (calendarDayModel.getLeaveSelected()){
                    holder.rv_day_tv.setBackground(mContext.getResources().getDrawable(R.drawable.day_dec_tour_leave));
                    holder.rv_day_tv.setTextColor(Color.BLACK);


                }else if (calendarDayModel.getSelected()){
                    holder.rv_day_tv.setBackground(mContext.getResources().getDrawable(R.drawable.day_dec_planned_leave));
                    holder.rv_day_tv.setTextColor(Color.WHITE);

                }

            }

        } else {
            holder.rv_day_tv.setText("0");
            holder.rv_day_tv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return calendarDayModelsList.size();
    }


    public interface TaskCalendarClickListener {
       // void taskClicked(int date, View view, String leaveReason, List<String> dateRange, Boolean dateRangeStatus, Boolean selectionStatus);
        void taskClicked(int date);
    }

    public interface SelectCalendarClickListener {
        void onCalendarClickListener(int date, String type);
    }
}
