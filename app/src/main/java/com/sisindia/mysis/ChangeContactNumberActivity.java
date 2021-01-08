package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class ChangeContactNumberActivity extends AppCompatActivity {

    Button raise_request_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_contact_number);

        raise_request_btn = findViewById(R.id.raise_request_btn);

        raise_request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  ViewDialog alert = new ViewDialog();
                 alert.showDialog("1234567890");
               // DialogClass cdd = new DialogClass(ChangeContactNumberActivity.this);
               // cdd.show();
            }
        });
    }


    public class ViewDialog {

        public void showDialog(String msg) {
            final Dialog dialog = new Dialog(ChangeContactNumberActivity.this);
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
