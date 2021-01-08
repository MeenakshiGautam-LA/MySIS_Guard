package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sisindia.mysis.R;
import java.util.List;



public class SyncDataAdapter extends RecyclerView.Adapter<SyncDataAdapter.ViewHolder> {
    List<String> syncDataList;
    List<String> syncDataHeadingList;
    Context context;

    public SyncDataAdapter(Context context, List<String> syncDataList,List<String> syncDataHeadingList) {
        this.context = context;
        this.syncDataList = syncDataList;
        this.syncDataHeadingList = syncDataHeadingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_sync_data_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTV.setText(syncDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return syncDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.nameTV);
        }
    }
}
