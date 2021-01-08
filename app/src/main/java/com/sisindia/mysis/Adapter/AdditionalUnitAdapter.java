package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.entity.UnitDetailModel;

import java.util.List;

public class AdditionalUnitAdapter extends RecyclerView.Adapter<AdditionalUnitAdapter.ViewHolder> {

    List<UnitDetailModel> unitDetailModelList;
    Context context;

    public AdditionalUnitAdapter(Context context, List<UnitDetailModel> unitDetailModelList) {
        this.context = context;
        this.unitDetailModelList = unitDetailModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_additional_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.unit_code_tv.setText(unitDetailModelList.get(position).getUNIT_CODE());
        holder.unitNameTV.setText(unitDetailModelList.get(position).getSITE_NAME());
        if (unitDetailModelList.size() - 1 == position) {
            holder.viewBorder.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {

        return unitDetailModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView unitNameTV, unit_code_tv;
        private View viewBorder;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unitNameTV = itemView.findViewById(R.id.unitNameTV);
            unit_code_tv = itemView.findViewById(R.id.unit_code_tv);
            viewBorder = itemView.findViewById(R.id.viewBorder);


        }
    }
}
