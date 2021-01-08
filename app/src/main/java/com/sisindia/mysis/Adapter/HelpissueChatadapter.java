package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;

import java.util.ArrayList;
import java.util.List;

public class HelpissueChatadapter extends RecyclerView.Adapter<HelpissueChatadapter.MyViewHolder>
{

    private List<String> conveyanceTravelDetailTimeLineAdapterList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txt;
        public MyViewHolder(View view) {
            super(view);
            txt=view.findViewById(R.id.txt1);
        }
    }


    public HelpissueChatadapter(Context context, ArrayList<String> conveyanceTravelDetailTimeLineAdapterList) {
        this.context = context;
        this.conveyanceTravelDetailTimeLineAdapterList = conveyanceTravelDetailTimeLineAdapterList;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_send_mssg_adapter_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String  invetoryAdapterModel = conveyanceTravelDetailTimeLineAdapterList.get(position);
        holder.txt.setText(invetoryAdapterModel);



    }


    @Override
    public int getItemCount() {
        return conveyanceTravelDetailTimeLineAdapterList.size();
    }}
