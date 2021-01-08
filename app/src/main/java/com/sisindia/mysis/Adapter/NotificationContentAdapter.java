package com.sisindia.mysis.Adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.entity.NotificationModel;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.DateUtils;

import org.json.JSONObject;

import java.util.List;

public class NotificationContentAdapter extends RecyclerView.Adapter<NotificationContentAdapter.ViewHolder> {

    List<NotificationModel> notificationContentList;
    CSHeaderFragmentFragment context;

    public NotificationContentAdapter(CSHeaderFragmentFragment context, List<NotificationModel> notificationContentList) {
        this.notificationContentList = notificationContentList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationContentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_notification_content_layout, parent, false);
        return new NotificationContentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationContentAdapter.ViewHolder holder, int position) {
        if(notificationContentList.get(position).getReadStatus()==0){
            holder.notification_heading.setTypeface(null,Typeface.BOLD);
            holder.notification_description.setTypeface(null,Typeface.BOLD);
        }else {
            holder.notification_heading.setTypeface(null,Typeface.NORMAL);
            holder.notification_description.setTypeface(null,Typeface.NORMAL);
        }
        holder.cardview.setOnClickListener(view -> {
            if(notificationContentList.get(position).getReadStatus()==0){
                CSApplicationHelper.application().getDatabaseInstance().notification_dao().updateReadStatus(notificationContentList.get(position).getId(),
                        DateUtils.getCurrentDate_TIME_format()
                        ,updateJson(notificationContentList.get(position).getJson()));
                notificationContentList.get(position).setReadStatus(1);
                notifyDataSetChanged();
            }

        });
        holder.detailTV.setOnClickListener(view -> {
            showDetailOnDialog(context,position);
        });
        holder.notification_heading.setText(notificationContentList.get(position).getTitle());
        holder.notification_description.setText(notificationContentList.get(position).getDescr());
        holder.timePeriodTV.setText(DateUtils.getInstance().timeLaps(notificationContentList.get(position).getCreatedOn()));
    }
    private String updateJson(String json){
        String returnJson=null;
        try{
            JSONObject jsonObject=new JSONObject(json);
            if(jsonObject.has("ReadStatus")){
                jsonObject.put("ReadStatus",1);
            }

            if(jsonObject.has("DateRead")){
                jsonObject.put("DateRead",DateUtils.getCurrentDate_TIME_format());
            }
            returnJson=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  returnJson;
    }
    @Override
    public int getItemCount() {
        if (notificationContentList.size() > 0)
            return notificationContentList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView notificationImg, soundImg;
        TextView notification_heading, notification_description,detailTV,timePeriodTV;
        private LinearLayout main_layoutLY;
        private CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notification_heading = itemView.findViewById(R.id.notification_heading);
            timePeriodTV = itemView.findViewById(R.id.timePeriodTV);
            main_layoutLY = itemView.findViewById(R.id.main_layoutLY);
            cardview = itemView.findViewById(R.id.cardview);
            detailTV = itemView.findViewById(R.id.detailTV);
            notification_description = itemView.findViewById(R.id.notification_description);

        }
    }
    public  void showDetailOnDialog(CSHeaderFragmentFragment csHeaderFragmentFragment, int position){
        AlertDialog.Builder ad = new AlertDialog.Builder(csHeaderFragmentFragment.csActivity, R.style.DialogBox);
        LayoutInflater inflater = csHeaderFragmentFragment.csActivity.getLayoutInflater();
        ad.setInverseBackgroundForced(true);

        final View dialog = inflater.inflate(R.layout.notification_detail_ondialog, null);
        ad.setView(dialog);
        TextView descriptionTextTV = dialog.findViewById(R.id.descriptionTextTV);
        TextView headerTV = dialog.findViewById(R.id.headerTV);
        TextView ok_action = dialog.findViewById(R.id.ok_action);
        headerTV.setText(notificationContentList.get(position).getTitle());
        descriptionTextTV.setText(notificationContentList.get(position).getDescr());


        final AlertDialog alertDialog = ad.create();
//    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(false);
        ok_action.setOnClickListener(view -> {

            if (alertDialog != null)
                alertDialog.dismiss();
        });
        alertDialog.show();
    }
}
