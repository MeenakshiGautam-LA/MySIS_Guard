package com.sisindia.mysis.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.entity.LeaveTypeListModel;
import com.sisindia.mysis.entity.OtherDuty_WithQrCode.ShiftDetail;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.DateUtils;

import java.util.List;

public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.MyViewHolder> {

    private List<ShiftDetail> shiftDetail;
    CSHeaderFragmentFragment context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView shiftName_tv, shiftTime_tv;
        private RadioButton radioBTN;


        public MyViewHolder(View view) {
            super(view);
            shiftName_tv = view.findViewById(R.id.shiftName_tv);
            shiftTime_tv = view.findViewById(R.id.shiftTime_tv);
            radioBTN = view.findViewById(R.id.radioBTN);

        }
    }

    public ShiftAdapter(CSHeaderFragmentFragment context, List<ShiftDetail> shiftDetail) {
        this.context = context;
        this.shiftDetail = shiftDetail;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_shift_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(shiftDetail.get(position).isSelected()){
            holder.radioBTN.setChecked(true);
        }else {
            holder.radioBTN.setChecked(false);
        }
        holder.shiftName_tv.setText(shiftDetail.get(position).getSHIFTNAME());
        holder.shiftTime_tv.setText(DateUtils.convert24_Hrs_To12_HrsWith_AM_OR_PMFormat(shiftDetail.get(position).getSTARTTIME().split(" ")[1]));
        Log.e("ShiftAdapter", "" + shiftDetail.get(position).isSelected());
        holder.radioBTN.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                shiftDetail.get(position).setSelected(true);

            } else {
                shiftDetail.get(position).setSelected(false);

            }
            context.onItemClick(holder.radioBTN,position);
        });

    }

    @Override
    public int getItemCount() {
        if (shiftDetail != null)
            return shiftDetail.size();
        else
            return 0;
    }
}


