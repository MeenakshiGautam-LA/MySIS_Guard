package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;

import java.util.List;

public class FaqHelpAdapter extends RecyclerView.Adapter<FaqHelpAdapter.ViewHolder>{

    List<String> faqHelpList;
    List<String> faqHelpContentList;
    Context context;

    public FaqHelpAdapter(Context context, List<String> faqHelpList, List<String> faqHelpContentList) {
        this.context = context;
        this.faqHelpList = faqHelpList;
        this.faqHelpContentList = faqHelpContentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.faq_help_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.faq_header.setText(faqHelpList.get(position));
        holder.faq_content_RV.setAdapter(new faqHelpContentAdapter(context, faqHelpContentList));
    }

    @Override
    public int getItemCount() {
        return faqHelpList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView faq_header, faq_view_more;
        RecyclerView faq_content_RV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            faq_header = itemView.findViewById(R.id.faq_header);
            faq_view_more = itemView.findViewById(R.id.faq_view_more);
            faq_content_RV = itemView.findViewById(R.id.faq_content_RV);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            faq_content_RV.setLayoutManager(mLayoutManager);


        }
    }
}
