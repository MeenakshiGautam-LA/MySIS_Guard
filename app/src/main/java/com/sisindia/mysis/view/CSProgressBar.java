package com.sisindia.mysis.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;


public class CSProgressBar extends ProgressDialog {
    int backPressCount = 0;
    private TextView progressLabel;
    private ProgressBar progressBar;
    public CSProgressBar(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_progress);
        if(this.getWindow()!=null)
            this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.setCanceledOnTouchOutside(false);
        this.progressLabel = this.findViewById(R.id.progressLabel);
        this.progressBar = this.findViewById(R.id.progressBar1);
//        this.progressBar.setIndeterminateDrawable(CSApplicationHelper.application().getDrawable(getIntermidiateDrawable()));
    }

    public void setProgressLabel(String progressLable) {
        if (this.progressLabel.getVisibility() == View.GONE) {
            this.progressLabel.setVisibility(View.VISIBLE);
        }
        this.progressLabel.setText(progressLable);
    }

    @Override
    public void show() {

        try {
            if(!CSApplicationHelper.application().getActivity().isFinishing()){
                super.show();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void dismiss() {
        try {
            if(progressBar!=null /*&& progressBar.getWindowToken()!=null*/ && this.isShowing())
                super.dismiss();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int getIntermidiateDrawable(){
        return R.drawable.cs_progress_drawable;
    }
}
