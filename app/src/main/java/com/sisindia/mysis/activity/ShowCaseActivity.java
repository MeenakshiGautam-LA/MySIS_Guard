package com.sisindia.mysis.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.sisindia.mysis.R;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

public class ShowCaseActivity extends AppCompatActivity {
    LinearLayout dutyInBTN,dutyOutBTN;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_attendance);
        dutyInBTN=findViewById(R.id.dutyInBTN);
        dutyOutBTN=findViewById(R.id.dutyOutBTN);
        dutyOutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialTapTargetSequence()
                        .addPrompt(new MaterialTapTargetPrompt.Builder(ShowCaseActivity.this)
                                .setTarget(findViewById(R.id.dutyOutBTN))
//                                .setPromptBackground(new RectanglePromptBackground())
//                                .setPromptFocal(new CirclePromptFocal())
                                .setFocalColour(getResources().getColor(R.color.purple))
                                .setAnimationInterpolator(new FastOutSlowInInterpolator())
//                        .setBackgroundColour(getResources().getColor(R.color.purple))
                                .setPrimaryText("Different shapes")
                                .setSecondaryText("Extend PromptFocal or PromptBackground to change the shapes")

//                        .setIcon(R.drawable.ic_search))
                        /*     .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                                     .setTarget(findViewById(R.id.setp_2_TV))
                                     .setPrimaryText("Step 2")
                                     .setSecondaryText("This will show till you press it")
                                     .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                                     .setFocalPadding(R.dimen.dp40))
                             .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                                     .setTarget(findViewById(R.id.setp_3_TV))
                                     .setPrimaryText("Step 3")
                                     .setSecondaryText("This will show till you press it")
                                     .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                                     .setFocalPadding(R.dimen.dp40))
                             .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                                     .setTarget(findViewById(R.id.setp_4_TV))
                                     .setPrimaryText("Step 4")
                                     .setSecondaryText("This will show till you press it")
                                     .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                                     .setFocalPadding(R.dimen.dp40))*/
                        .show());
            }
        });
        dutyInBTN.setOnClickListener(view -> {
            new MaterialTapTargetSequence()
                    .addPrompt(new MaterialTapTargetPrompt.Builder(ShowCaseActivity.this)
                            .setTarget(findViewById(R.id.dutyInBTN))
                            .setFocalColour(getResources().getColor(R.color.white))
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setPromptBackground(new RectanglePromptBackground())
                            .setPromptFocal(new RectanglePromptFocal())
//                        .setBackgroundColour(getResources().getColor(R.color.purple))
                            .setPrimaryText("Step 1")
                            .setSecondaryText("Click here to mark attendance")

//                        .setIcon(R.drawable.ic_search))
                            /*     .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                                         .setTarget(findViewById(R.id.setp_2_TV))
                                         .setPrimaryText("Step 2")
                                         .setSecondaryText("This will show till you press it")
                                         .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                                         .setFocalPadding(R.dimen.dp40))
                                 .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                                         .setTarget(findViewById(R.id.setp_3_TV))
                                         .setPrimaryText("Step 3")
                                         .setSecondaryText("This will show till you press it")
                                         .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                                         .setFocalPadding(R.dimen.dp40))
                                 .addPrompt(new MaterialTapTargetPrompt.Builder(this)
                                         .setTarget(findViewById(R.id.setp_4_TV))
                                         .setPrimaryText("Step 4")
                                         .setSecondaryText("This will show till you press it")
                                         .setAnimationInterpolator(new LinearOutSlowInInterpolator())
                                         .setFocalPadding(R.dimen.dp40))*/
                            .show());
        });

    }
}
