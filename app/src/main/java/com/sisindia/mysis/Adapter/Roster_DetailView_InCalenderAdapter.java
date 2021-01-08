package com.sisindia.mysis.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Model.MapTableRosterAndShift;
import com.sisindia.mysis.R;
import com.sisindia.mysis.utils.CFUtil;

import java.util.List;

public class Roster_DetailView_InCalenderAdapter extends RecyclerView.Adapter<Roster_DetailView_InCalenderAdapter.Viewholder> {
    private List<MapTableRosterAndShift> mapTableRosterAndShifts;

    public Roster_DetailView_InCalenderAdapter(List<MapTableRosterAndShift> mapTableRosterAndShifts) {
        this.mapTableRosterAndShifts = mapTableRosterAndShifts;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_view_roster_in_calender, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
//        ShiftMasterModel shiftDetail = CSApplicationHelper.application().getDatabaseInstance().shifMaster_dao().getShiftDetail(mapTableRosterAndShifts.get(position).getSHIFT_ID());
//        if (shiftDetail != null) {
//            holder.shiftNameTV.setText(shiftDetail.getShiftName());
        holder.shiftNameTV.setText(CFUtil.getKeyDataFromJSON(mapTableRosterAndShifts.get(position).getJSON(), "SHIFT_NAME").toUpperCase());
        holder.unitNameTV.setText(CFUtil.getKeyDataFromJSON(mapTableRosterAndShifts.get(position).getJSON(), "UNIT_NAME").toUpperCase());
//            holder.unitNameTV.setText(mapTableRosterAndShifts.get(position).getUNIT_NAME().toUpperCase());
        holder.unitCodeTV.setText(mapTableRosterAndShifts.get(position).getUNIT_CODE().toUpperCase());//            holder.shiftStartTimeTV.setText(shiftDetail.getStartTime().substring(0, 5));
        holder.shiftStartTimeTV.setText(CFUtil.getKeyDataFromJSON(mapTableRosterAndShifts.get(position).getJSON(), "START_TIME").substring(0, 5));
//            holder.postNameTV.setText(CSApplicationHelper.application().getDatabaseInstance().duty_post_detail_dao().
//                    postName(mapTableRosterAndShifts.get(position).getDUTY_POST_ID()));
        holder.postNameTV.setText(CFUtil.getKeyDataFromJSON(mapTableRosterAndShifts.get(position).getJSON(), "POST_NAME").toUpperCase());
//        }
    }

    @Override
    public int getItemCount() {
        if (mapTableRosterAndShifts.size() > 0)
            return mapTableRosterAndShifts.size();
        else
            return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView shiftStartTimeTV, unitNameTV, postNameTV, shiftNameTV, unitCodeTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            shiftStartTimeTV = itemView.findViewById(R.id.shiftStartTimeTV);
            postNameTV = itemView.findViewById(R.id.postNameTV);
            unitNameTV = itemView.findViewById(R.id.unitNameTV);
            shiftNameTV = itemView.findViewById(R.id.shiftNameTV);
            unitCodeTV = itemView.findViewById(R.id.unitCodeTV);
        }
    }
}
