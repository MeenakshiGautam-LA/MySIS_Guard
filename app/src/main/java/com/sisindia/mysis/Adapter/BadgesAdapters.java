package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;

import java.util.List;

public class BadgesAdapters extends RecyclerView.Adapter<BadgesAdapters.ViewHolder> {
    private Context mContext;
    private List<String> list;

    public BadgesAdapters(Context c, List<String> list) {
        mContext = c;
        this.list = list;
        Log.d("log>>>>>", "BadgesAdapter:list size " + list.size());
    }


    @NonNull
    @Override
    public BadgesAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_badge_grid_layout, parent, false);
        return new BadgesAdapters.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgesAdapters.ViewHolder holder, int position) {
        holder.numberTxt.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView numberTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numberTxt = itemView.findViewById(R.id.nameTxt);
        }
    }
}
