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

public class faqHelpContentAdapter extends RecyclerView.Adapter<faqHelpContentAdapter.ViewHolder> {

    List<String> faqHelpContentList;
    Context context;

    public faqHelpContentAdapter(Context context, List<String> faqHelpContentList) {
        this.context = context;
        this.faqHelpContentList = faqHelpContentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_help_content_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.help_content.setText(faqHelpContentList.get(position));
    }

    @Override
    public int getItemCount() {
        return faqHelpContentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView help_content,help_full_content;
        ImageView drowpDown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            help_content = itemView.findViewById(R.id.help_content);
            drowpDown = itemView.findViewById(R.id.drowpDown);
            help_full_content = itemView.findViewById(R.id.help_full_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    help_full_content.setVisibility(View.VISIBLE);
                    Log.e("Tag", "onClick：in faq content adapter...   " + getAdapterPosition());
                }
            });
        }
    }
}
