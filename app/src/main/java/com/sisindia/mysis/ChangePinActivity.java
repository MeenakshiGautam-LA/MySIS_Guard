package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ChangePinActivity extends AppCompatActivity {

    Button submit_pin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        submit_pin_btn = findViewById(R.id.submit_pin_btn);

        submit_pin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewChangePINDialog alert = new ViewChangePINDialog();
                alert.showDialog("1234567890");
            }
        });
    }

    public class ViewChangePINDialog {

        public void showDialog(String msg) {
            final Dialog dialog = new Dialog(ChangePinActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.change_contact_successfully_request);

            TextView text = dialog.findViewById(R.id.mobile_number);
            text.setText(msg);


            Button dialogButton = dialog.findViewById(R.id.contact_change_btn);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }
}
