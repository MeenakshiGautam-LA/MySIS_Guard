package com.sisindia.mysis.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.entity.LeaveTypeListModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;

import java.util.List;

public class View_Leave_typeList_Adapter extends RecyclerView.Adapter<View_Leave_typeList_Adapter.ViewHolder> {
    private List<LeaveTypeListModel> leaveTypeListModelList;
    private CSHeaderFragmentFragment csHeaderFragmentFragment;
    private int leaveTypeClickPosition = 0;

    public View_Leave_typeList_Adapter(CSHeaderFragmentFragment context, List<LeaveTypeListModel> leaveTypeListModelList,int leaveTypeClickPosition) {
        csHeaderFragmentFragment = context;
        this.leaveTypeListModelList = leaveTypeListModelList;
        this.leaveTypeClickPosition = leaveTypeClickPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_leave_type_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(leaveTypeClickPosition!=-1){
            holder.leave_type_RB.setChecked(leaveTypeClickPosition == position);
        }
        holder.leave_type_RB.setText(leaveTypeListModelList.get(position).getLEAVE_TYPE());
//        holder.itemView.setOnClickListener(view -> {
//            csHeaderFragmentFragment.onItemClick(holder.itemView,position);
//
//        });
    }

    @Override
    public int getItemCount() {
        if(leaveTypeListModelList!=null)
            return leaveTypeListModelList.size();
        else
            return 0;
    }

    public void update(Integer leaveTypeClickPosition) {
        this.leaveTypeClickPosition=leaveTypeClickPosition;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RadioButton leave_type_RB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leave_type_RB = itemView.findViewById(R.id.leave_type_RB);
        }
    }
}
