package com.sisindia.mysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SulabhLoanActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    List<String> purposeLoanList = new ArrayList<>();
    Spinner purpose_loan_sppinner;
    Button send_request;
    //AppCompatButton send_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sulabh_loan);

        purposeLoanList.add("Medical reason");
        purposeLoanList.add("Family function");
        purposeLoanList.add("Children education");
        purposeLoanList.add("Personal reason");

        purpose_loan_sppinner = findViewById(R.id.purpose_loan_sppinner);
        send_request = findViewById(R.id.send_request);
        purpose_loan_sppinner.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,purposeLoanList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purpose_loan_sppinner.setAdapter(aa);

        send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(SulabhLoanActivity.this,Reminder_thirtyMinute.class));
                startActivity(new Intent(SulabhLoanActivity.this,LoanRequested.class));
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),purposeLoanList.get(position) , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
