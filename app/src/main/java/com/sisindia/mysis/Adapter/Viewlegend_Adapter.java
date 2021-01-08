package com.sisindia.mysis.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.entity.LeaveTypeListModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSStringUtil;

import java.util.List;

public class Viewlegend_Adapter extends RecyclerView.Adapter<Viewlegend_Adapter.MyViewHolder> {

    private List<LeaveTypeListModel> leaveMasterList;
    CSHeaderFragmentFragment context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leaveColorTV, leaveTypeNameTV;


        public MyViewHolder(View view) {
            super(view);
            leaveColorTV = view.findViewById(R.id.leaveColorTV);
            leaveTypeNameTV = view.findViewById(R.id.leaveTypeNameTV);


        }
    }

    public Viewlegend_Adapter(CSHeaderFragmentFragment context, List<LeaveTypeListModel> conveyanceTravelDetailTimeLineAdapterList) {
        this.context = context;
        this.leaveMasterList = conveyanceTravelDetailTimeLineAdapterList;
    }

    @Override
    public Viewlegend_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_view_legend, parent, false);
        return new Viewlegend_Adapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(Viewlegend_Adapter.MyViewHolder holder, int position) {
        holder.leaveTypeNameTV.setText(leaveMasterList.get(position).getLEAVE_TYPE());
        if(!CSStringUtil.isEmptyStr(leaveMasterList.get(position).getCOLOR_CODE())){
            holder.leaveColorTV.setBackgroundColor(Color.parseColor(leaveMasterList.get(position).getCOLOR_CODE()));

        }
    }
    @Override
    public int getItemCount() {
        if(leaveMasterList!=null)
        return leaveMasterList.size();
        else
            return 0;
    }
}

