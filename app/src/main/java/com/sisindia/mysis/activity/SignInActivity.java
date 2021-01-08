package com.sisindia.mysis.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.utils.CFUtil;

public class SignInActivity extends BaseActivity {
    @Override
    public int getScreenName() {
        return R.string.app_name;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        CFUtil.screenShotDisableInApp(this); // Disable ScreenShot
        setContentView(R.layout.sign_in_main_layout);
        CSApplicationHelper.application().setActivity(this);
    }
}
