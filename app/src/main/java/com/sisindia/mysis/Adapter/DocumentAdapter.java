package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {

    Context mContext;
    List<String> documentNameList;

    public DocumentAdapter(Context mContext, List<String> documentNameList) {
        this.documentNameList = documentNameList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DocumentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_document_layout, parent, false);
        return new DocumentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentAdapter.ViewHolder holder, int position) {
        holder.documentName.setText(documentNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return documentNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView documentName, documentNuber;
        ImageView documentImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            documentName = itemView.findViewById(R.id.document_name);
            documentNuber = itemView.findViewById(R.id.document_number);

        }
    }
}
