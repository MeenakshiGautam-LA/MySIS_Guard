package com.sisindia.mysis.fragment;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

import com.sisindia.mysis.ApiCalling_Class;
import com.sisindia.mysis.R;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.syncdata.DashboadApisMethod;
import com.sisindia.mysis.syncdata.GetDataFromServer;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSStringUtil;

import butterknife.BindView;
import cdflynn.android.library.checkview.CheckView;
import pl.droidsonroids.gif.GifImageView;

public class SynServer extends CSHeaderFragmentFragment {

    @BindView(R.id.synServerMainRL)
    RelativeLayout synServerMainRL;
    @BindView(R.id.check)
    CheckView check;
    @BindView(R.id.syncDesTV)
    TextView syncDesTV;
    @BindView(R.id.synServerGIF)
    GifImageView synServerGIF;
    @BindView(R.id.crossIV)
    AppCompatImageView crossIV;
    @Override
    public int layoutResource() {
        return R.layout.syn_server_layout;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        crossIV.setOnClickListener(view -> {
            csActivity.onBackPressed();
        });
        if(CSAppUtil.isNetworkNotAvailable(csActivity,false)){
            CSDialogUtil.showInfoDialog(csActivity,CSStringUtil.getString(R.string.no_internet));
        }else {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void run() {
                    ApiCalling_Class calling_class=new  ApiCalling_Class(csActivity);
                    calling_class.push_All_Apis();
                  /*  DashboadApisMethod db = DashboadApisMethod.getInstance(csActivity);
                    GetDataFromServer.getInstance().getData(db, csActivity, null, false);*/
                }
            }, 1500);
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void run() {
                    synServerGIF.setVisibility(View.GONE);
                    check.setVisibility(View.VISIBLE);
                    check.check();
                    syncDesTV.setText(CSStringUtil.getString(R.string.sync_complete));
                }
            }, 15000);
        }
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }


}
