package com.sisindia.mysis.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LauncherActivity extends BaseActivity {
    private static int SPLASH_SCREEN_TIME_OUT = 3000;
    @BindView(R.id.versionTV)
    TextView versionTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CFUtil.screenShotDisableInApp(this); // Disable ScreenShot
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        CSApplicationHelper.application().setActivity(this);
        Log.e("MPIN",""+CSShearedPrefence.getMpin());
        setAppVersionName();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearDeviceData();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
    private void setAppVersionName(){
        try {
            String app_ver_code = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            versionTV.setText(app_ver_code);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void clearDeviceData()
    {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int mCurrentVersion = pInfo.versionCode;
            int last_version = CSShearedPrefence.getVersion();
            if(last_version==-1){
//                openActivity(CSShearedPrefence.isLoggedIn() ? MainActivity_Guard.class : SignInActivity.class);
                openActivity(CSShearedPrefence.isLoggedIn() ? MainActivity_Guard.class : Term_Condition_Page.class);
            }else {
                if(last_version != mCurrentVersion)
                {
                    //CSShearedPrefence.getInstance().edit().clear().apply();
                    //CSApplicationHelper.application().getDatabaseInstance().clearAllTables();
                }
//                openActivity(CSShearedPrefence.isLoggedIn() ? MainActivity_Guard.class : SignInActivity.class);
                openActivity(CSShearedPrefence.isLoggedIn() ? MainActivity_Guard.class : Term_Condition_Page.class);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getScreenName() {
        return R.string.app_name;
    }

    public void openActivity(Class target) {
        Intent intent = new Intent(CSApplicationHelper.application().getActivity(), target);
        CSApplicationHelper.application().getActivity().startActivity(intent);
        CSApplicationHelper.application().getActivity().finish();
    }
}
