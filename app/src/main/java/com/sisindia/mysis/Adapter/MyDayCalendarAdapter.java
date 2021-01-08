package com.sisindia.mysis.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sisindia.mysis.Model.MyDayModel;
import com.sisindia.mysis.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyDayCalendarAdapter extends RecyclerView.Adapter<MyDayCalendarAdapter.MyViewHolder> {

    private List<MyDayModel> modelList = new ArrayList<>();
    private TaskCalendarClickListener listener = null;
    private Context mContext;
    private boolean _dateRange;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rv_day_tv;
        LinearLayout day_container;

        public MyViewHolder(View view) {
            super(view);
            rv_day_tv = view.findViewById(R.id.rv_day_tv);
            day_container = view.findViewById(R.id.day_container);
        }
    }


    public MyDayCalendarAdapter(Context context, List<MyDayModel> modelList, TaskCalendarClickListener listener, boolean _dateRange) {
        this.mContext = context;
        this.modelList = modelList;
        this.listener = listener;
        this._dateRange = _dateRange;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_day_view, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyDayModel model = modelList.get(position);

        if (Integer.parseInt(model.getDateSingleDigit()) > 0) {

            holder.rv_day_tv.setText(model.getDateSingleDigit());
            if (model.getYear() > (Calendar.getInstance().get(Calendar.YEAR))) {
            } else if (model.getYear() == (Calendar.getInstance().get(Calendar.YEAR))) {
                if (model.getMonth() > (Calendar.getInstance().get(Calendar.MONTH))) {
                } else if (model.getMonth() == (Calendar.getInstance().get(Calendar.MONTH))) {
                    if (Integer.parseInt(model.getDateSingleDigit()) >= (Calendar.getInstance().get(Calendar.DAY_OF_MONTH))) {
                    } else {

                    }
                } else {
                }
            } else {
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.setSelected(true);
                    listener.taskClicked(position, model.getDate());
                }
            });

            if (model.isSelected()) {
                //holder.rv_day_tv.setBackground(mContext.getResources().getDrawable(R.drawable.selected_day_bg));
                ShapeDrawable sd = new ShapeDrawable();

                // Specify the shape of ShapeDrawable
                sd.setShape(new RectShape());
                sd.setIntrinsicWidth(48);
                // Specify the border color of shape
                sd.getPaint().setColor(Color.RED);

                // Set the border width
                sd.getPaint().setStrokeWidth(10f);

                // Specify the style is a Stroke
                sd.getPaint().setStyle(Paint.Style.STROKE);

                // Finally, add the drawable background to TextView
              holder.rv_day_tv.setBackground(sd);
           //     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(48dp, ViewGroup.LayoutParams.WRAP_CONTENT);
             //   params.setMargins(48,48,48,48);
               // params.set
              //  TextView textView = (TextView) findViewById(R.id.textView);
             // holder.rv_day_tv.setLayoutParams(params);
             //  holder.rv_day_tv.setLayoutParams(48,48);


                ShapeDrawable shape = new ShapeDrawable(new RectShape());
                shape.getPaint().setColor(Color.parseColor("#F7AB25"));
                shape.getPaint().setStyle(Paint.Style.STROKE);
                shape.getPaint().setStrokeWidth(10);
                holder.rv_day_tv.setBackground(shape);
                holder.rv_day_tv.setTextColor(Color.parseColor("#FF0000"));
               // TextView text = new TextView(this.context);


            }
          else if(holder.rv_day_tv.getText().toString().equals("7")){
                holder.rv_day_tv.setTextColor(Color.parseColor("#DCDDDE"));

            }
          else if(holder.rv_day_tv.getText().toString().equals("8")){
                holder.rv_day_tv.setTextColor(Color.parseColor("#DCDDDE"));

            }

            else if(holder.rv_day_tv.getText().toString().equals("18")){
                holder.rv_day_tv.setTextColor(Color.parseColor("#DCDDDE"));

            }
            else if(holder.rv_day_tv.getText().toString().equals("26")){
                holder.rv_day_tv.setTextColor(Color.parseColor("#FF0000"));

            }


            else if(holder.rv_day_tv.getText().toString().equals("10")){
                holder.rv_day_tv.setTextColor(Color.parseColor("#FF0000"));

            }

            else if(holder.rv_day_tv.getText().toString().equals("2")){
                holder.rv_day_tv.setTextColor(Color.parseColor("#FF0000"));

            }


            else if (model.getToday()) {
              //holder.rv_day_tv.setBackground(mContext.getResources().getDrawable(R.drawable.today_day_bg));



                holder.rv_day_tv.setTextColor(Color.parseColor("#000000"));
            }
            else {
               // holder.rv_day_tv.setBackground(mContext.getResources().getDrawable(R.drawable.all_day_bg));
                holder.rv_day_tv.setTextColor(Color.parseColor("#949598"));
            }
          //}

        } else {
            holder.rv_day_tv.setText("0");
            holder.rv_day_tv.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    // CREATING INTERFACE TO GET RESULT FROM ACTIVITY
    public interface TaskCalendarClickListener {
        void taskClicked(int selectedGUID, String type);
    }

}