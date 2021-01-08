package com.sisindia.mysis.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.utils.CFUtil;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Term_Condition_Page extends BaseActivity {
    @BindView(R.id.agreeBTN)
    AppCompatButton agreeBTN;
    @Override
    public int getScreenName() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CFUtil.screenShotDisableInApp(this);
        setContentView(R.layout.term_and_condition);
        ButterKnife.bind(this);
        agreeBTN.setOnClickListener(view -> {
            Log.e ("permissionAllowed",""+permissionAllowed);

            if(permissionAllowed){
                Log.e ("permissionAllowed","IF>>>"+permissionAllowed);
                CSAppUtil.openActivity(Application_Description_Screen.class);
                finish();
            }else {
                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (!permissionAllowed) {

                        checkPermissions();
                    }
                }else {
                    CSAppUtil.openActivity(Application_Description_Screen.class);
                    finish();
                }

            }


        });
    }
}
