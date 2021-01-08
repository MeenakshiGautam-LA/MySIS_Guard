package com.sisindia.mysis.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sisindia.mysis.R;

public class SetDateTimeDialog extends Dialog {
    private Button updateAPPBtn;
    private Button laterUpdateBtn;
    private Context context;
    String name;
    private iSetDateTimeDialogListener listener;
    String description;
    private TextView titleTV;
    private TextView descTV;

    public SetDateTimeDialog(Context context, String description, iSetDateTimeDialogListener listener) {
        super(context);
        this.context = context;
        this.description = description;
        this.listener = listener;
        this.name = "Update Date/Time";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_date_time_dialog);
        titleTV = (TextView) findViewById(R.id.titleTV);
        descTV = (TextView) findViewById(R.id.descTV);
        titleTV.setText(name);
        descTV.setText(description);


        updateAPPBtn = (Button) findViewById(R.id.updateAPPBtn);
        laterUpdateBtn = (Button) findViewById(R.id.laterUpdateBtn);

        updateAPPBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.openDateSettings();
                dismiss();
            }
        });

        laterUpdateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
                dismiss();
            }
        });

    }

    public interface iSetDateTimeDialogListener {
        void openDateSettings();
    }
}
