package com.sisindia.mysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HelpContactAdapter extends
        RecyclerView.Adapter<HelpContactAdapter.ViewHolder> {
    HelpContactUsActivity context;
    ArrayList<HelpContactEntity> attendancearrayList;
    /*public HelpContactAdapter(HelpIssueActivity faqLandingActivity, ArrayList<AttendanceEntity> attendancearrayList) {
        context=faqLandingActivity;
        this.attendancearrayList=attendancearrayList;

    }*/

    public HelpContactAdapter(HelpContactUsActivity helpContactUsActivity, ArrayList<HelpContactEntity> attendancearrayList) {

        context = helpContactUsActivity;
        this.attendancearrayList = attendancearrayList;


    }

    // ... constructor and member variables

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.single_row_item_help_contact, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        /*Contact contact = mContacts.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(contact.getName());*/
       /* Button button = viewHolder.messageButton;
        button.setText(contact.isOnline() ? "Message" : "Offline");
        button.setEnabled(contact.isOnline());*/


        viewHolder.designation_txt.setText(attendancearrayList.get(position).getDesignation().toString());
        viewHolder.number_txt.setText(attendancearrayList.get(position).getNumber().toString());
        viewHolder.name_txt.setText(attendancearrayList.get(position).getName().toString());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return attendancearrayList.size();
    }
//}

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView designation_txt, name_txt, number_txt;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

        /*    designation_txt = (TextView) itemView.findViewById(R.id.designation_txt);
            name_txt = (TextView) itemView.findViewById(R.id.name_txt);
            number_txt = (TextView) itemView.findViewById(R.id.number_txt);*/

        }
    }
}

