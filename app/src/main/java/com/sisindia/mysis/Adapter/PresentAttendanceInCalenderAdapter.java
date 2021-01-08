package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.CSUtil;
import com.sisindia.mysis.utils.DateUtils;

import org.json.JSONObject;

import java.util.List;

import static com.sisindia.mysis.utils.Constants.PARAM_KEY_DELETED;

public class PresentAttendanceInCalenderAdapter extends RecyclerView.Adapter<PresentAttendanceInCalenderAdapter.PresentViewHolder> {
    private List<DailyAttendanceDetail> dailyAttendanceDetailList;
    private Context context;

    public PresentAttendanceInCalenderAdapter(Context context, List<DailyAttendanceDetail> dailyAttendanceDetailList) {
        this.dailyAttendanceDetailList = dailyAttendanceDetailList;
        this.context = context;
    }

    @NonNull
    @Override
    public PresentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_present_attendance_calender, parent, false);
//                  .inflate(R.layout.single_row_present_attendance_calendar_new, parent, false);
        return new PresentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresentViewHolder holder, int position) {
        try {
            if (!CSStringUtil.isEmptyStr(dailyAttendanceDetailList.get(position).getUNITCODE())) {
                holder.unitCodeTV.setText(dailyAttendanceDetailList.get(position).getUNITCODE());

                if ((!dailyAttendanceDetailList.get(position).getUNITCODE().equalsIgnoreCase(CSShearedPrefence.getUnitcode()))) {// check if employee mark attendance in other unit then showother unit description
                    holder.unitNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().siteDetail_DAO().
                            getSiteName(dailyAttendanceDetailList.get(position).getUNITCODE()));
                    holder.unitFoundDesTV.setText(CSStringUtil.getString(R.string.attendance_From_other_Unit));
                } else {
                    holder.unitFoundDesTV.setVisibility(View.GONE);
                    holder.unitNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().siteDetail_DAO().
                            getSiteName(dailyAttendanceDetailList.get(position).getUNITCODE()));
                }
            }
            if(dailyAttendanceDetailList.get(position).getFlag().equalsIgnoreCase("1")){
                holder.un_sync_Des_LY.setVisibility(View.VISIBLE);
            }else {
                holder.un_sync_Des_LY.setVisibility(View.GONE);

            }
            if (!CSStringUtil.isEmptyStr(dailyAttendanceDetailList.get(position).getACTSTARTTIME())) {
                holder.dutyInTimeTV.setText(shiftTimeInHourAndMinutes(dailyAttendanceDetailList.get(position).getACTSTARTTIME().split(" ")[1]));
            } else {
                holder.dutyInTimeTV.setText(CSStringUtil.getString(R.string.not_applicable));
            }
            if(!CSStringUtil.isEmptyStr(dailyAttendanceDetailList.get(position).getJSON())){
                try{
                    JSONObject jsonObject=new JSONObject(dailyAttendanceDetailList.get(position).getJSON());
                    if (jsonObject.has("DUTY_POST_NAME")) {
                        holder.postNameTV.setText(jsonObject.getString("DUTY_POST_NAME"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if(!CSStringUtil.isEmptyStr(dailyAttendanceDetailList.get(position).getJSON())){
                try{
                    JSONObject jsonObject=new JSONObject(dailyAttendanceDetailList.get(position).getJSON());
                    if (jsonObject.has("SHIFT_NAME")) {
                        holder.shiftNameTV.setText(jsonObject.getString("SHIFT_NAME"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (!CSStringUtil.isEmptyStr(dailyAttendanceDetailList.get(position).getACTENDTIME())) {
                holder.dutyOutTimeTV.setText(shiftTimeInHourAndMinutes(dailyAttendanceDetailList.get(position).getACTENDTIME().split(" ")[1]));
            } else {
                holder.dutyOutTimeTV.setText(CSStringUtil.getString(R.string.not_applicable));
            }
            holder.rankName_Tv.setText(CSApplicationHelper.application().getDatabaseInstance().serviceDAO().getSymbol(dailyAttendanceDetailList.get(position)
                    .getEMPRANK()));
            holder.todayShiftStartTimeTV.setText(DateUtils.convert24_Hrs_To12_HrsWith_AM_OR_PMFormat(dailyAttendanceDetailList.get(position).
                    getSHIFTSTARTTIME().split(" ")[1]).split(" ")[0]);
            holder.todayTimePeroidTV.setText(DateUtils.convert24_Hrs_To12_HrsWith_AM_OR_PMFormat(dailyAttendanceDetailList.get(position).
                    getSHIFTSTARTTIME().split(" ")[1]).split(" ")[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String shiftTimeInHourAndMinutes(String time) {
        String hourNd_Minutes = "";

        try {
            hourNd_Minutes = time.split(":")[0] + ":" + time.split(":")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hourNd_Minutes;
    }

    @Override
    public int getItemCount() {
        if (dailyAttendanceDetailList.size() == 0)
            return 0;
        else
            return dailyAttendanceDetailList.size();
    }

    public class PresentViewHolder extends RecyclerView.ViewHolder {
        private TextView dutyOutTimeTV, dutyInTimeTV, unitNameTV, unitCodeTV, unitFoundDesTV, shiftNameTV, postNameTV, rankName_Tv, todayTimePeroidTV, todayShiftStartTimeTV;
        private LinearLayout un_sync_Des_LY;

        public PresentViewHolder(@NonNull View itemView) {
            super(itemView);
            dutyOutTimeTV = itemView.findViewById(R.id.dutyOutTimeTV);
            dutyInTimeTV = itemView.findViewById(R.id.dutyInTimeTV);
            unitNameTV = itemView.findViewById(R.id.unitNameTV);
            unitCodeTV = itemView.findViewById(R.id.unitCodeTV);
            unitFoundDesTV = itemView.findViewById(R.id.unitFoundDesTV);
            un_sync_Des_LY = itemView.findViewById(R.id.un_sync_Des_LY);
            shiftNameTV = itemView.findViewById(R.id.todayShiftNameTV);
            postNameTV = itemView.findViewById(R.id.postNameTV);
            rankName_Tv = itemView.findViewById(R.id.rankName_Tv);
            todayShiftStartTimeTV = itemView.findViewById(R.id.todayShiftStartTimeTV);
            todayTimePeroidTV = itemView.findViewById(R.id.todayTimePeroidTV);
        }
    }
}
