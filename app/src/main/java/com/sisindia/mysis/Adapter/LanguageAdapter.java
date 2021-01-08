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



public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder>{

    List<String> langList;
    Context context;

    public LanguageAdapter(Context context, List<String> langList) {
        this.context = context;
        this.langList = langList;
    }


    @NonNull
    @Override
    public LanguageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_lang_item, parent, false);
        return new LanguageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageAdapter.ViewHolder holder, int position) {


       String item = langList.get(position);
       holder.rv_item_lang_tv.setText(item);



    }

    @Override
    public int getItemCount() {
        return langList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rv_item_lang_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_item_lang_tv=itemView.findViewById(R.id.rv_item_lang_tv);



        }
    }
}
