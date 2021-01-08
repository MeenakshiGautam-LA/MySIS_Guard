package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sisindia.mysis.Adapter.DocumentAdapter;
import com.sisindia.mysis.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

    RecyclerView document_recycle_view;
    DocumentAdapter documentAdapter;
    List<String> documentList = new ArrayList<>();
    ImageView profile_logo;
    Button contact_change_btn, pin_change_btn;
    LinearLayout badgesLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_screen);

        documentList.add("Pan Card");
        documentList.add("Adhar Card");
        documentList.add("Voter Card");
        document_recycle_view = findViewById(R.id.document_recycle_view);
        profile_logo = findViewById(R.id.profile_logo);
        contact_change_btn = findViewById(R.id.contact_change_btn);
        pin_change_btn = findViewById(R.id.pin_change_btn);
        badgesLinear = findViewById(R.id.badgesLinear);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        document_recycle_view.setLayoutManager(mLayoutManager);

        documentAdapter = new DocumentAdapter(this, documentList);
        document_recycle_view.setAdapter(documentAdapter);
         document_recycle_view.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        documentAdapter.notifyDataSetChanged();

        profile_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, YourBadge.class));
            }
        });

        contact_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ChangeContactNumberActivity.class));
            }
        });

        pin_change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ChangePinActivity.class));
            }
        });


        badgesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, BadgeActivity.class));
            }
        });




    }

    @Override
    public void onItemClick(View view, int position) {
        ViewDocumentImageDialog alert = new ViewDocumentImageDialog();
        alert.showDialog("1234567890");
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


    public class ViewDocumentImageDialog {

        public void showDialog(String msg) {
            final Dialog dialog = new Dialog(ProfileActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_document_image_layout);

            ImageView dialogButton = dialog.findViewById(R.id.cancelImg);
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
