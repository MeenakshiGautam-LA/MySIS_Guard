package com.sisindia.mysis.activity.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sisindia.mysis.R;


public class ApplicationDescriptionSecond extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guided_screen5);


    }

    @Override
    public int getScreenName() {
        return 0;
    }
}
