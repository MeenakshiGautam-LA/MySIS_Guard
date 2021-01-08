package com.sisindia.mysis.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDrawable;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSUIUtil;

import java.util.Timer;
import java.util.TimerTask;

public class Application_Description_Screen extends BaseActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private View btnSkip;
    private Timer timer;
    private Button finishBTN;
    private int count = 0;
    private TextView stepNo;


    int[] layoutDrawable = {
            R.layout.welcome_screen_1,
            R.layout.welcome_screen_2,
            R.layout.welcome_screen_3,
            R.layout.welcome_screen_4,
            R.layout.welcome_screen_5,
            R.layout.welcome_screen_6};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making headerNotificationButton bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_app_tour);


        viewPager = findViewById(R.id.view_pager);
        finishBTN = findViewById(R.id.finishBTN);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        stepNo = findViewById(R.id.step_no_tv);


       /* for (int i = 0; i < layoutDrawable.length; i++) {

       stepNo.setText(String.valueOf(i));
        }
*/
        // adding bottom dots
        addBottomDots(0);
        CSUIUtil.changeStatusBarColor(this);
        // making headerNotificationButton bar transparent
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);

        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        timer.cancel();
                        return false; //This is important, if you return TRUE the action of swipe will not take place.
                    case MotionEvent.ACTION_DOWN:

                        timer.cancel();
                        break;
                    case MotionEvent.ACTION_UP:
                        push();
                }
                return false;
            }
        });
        push();
        btnSkip.setOnClickListener(this);
        finishBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_screen1_BTN:
                viewPager.setCurrentItem(1, true);
             //   stepNo.setText("1");
                break;
            case R.id.welcome_screen2_BTN:
                viewPager.setCurrentItem(getItem(+1), true);
             //   stepNo.setText("2");
                break;
            case R.id.welcome_screen3_BTN:
                viewPager.setCurrentItem(getItem(+1), true);
              //  stepNo.setText("3");
                break;
            case R.id.welcome_screen4_BTN:
                viewPager.setCurrentItem(getItem(+1), true);
             //   stepNo.setText("4");
                break;
            case R.id.welcome_screen5_BTN:
                viewPager.setCurrentItem(getItem(+1), true);
              //  stepNo.setText("5");
                break;
            case R.id.button_tour:
                launchHomeScreen();
                break;

            case R.id.finishBTN:
                int size = layoutDrawable.length;
                Log.d("jdescr", String.valueOf(size));

                //    for (int i = 0; i < layoutDrawable.length; i++) {


                  /*  if (count >= layoutDrawable.length - 1) {
                        launchHomeScreen();
                    }
*/
                if (count >= 5) {
                    launchHomeScreen();
                //    stepNo.setText("6");
                } else if (count == 0) {
                    viewPager.setCurrentItem(1, true);
                 //   stepNo.setText("1");
                    stepNo.setText("fgdegg");
                } else if (count == 1) {
                    viewPager.setCurrentItem(2, true);
                   // stepNo.setText("2");
                } else if (count == 2) {
                    viewPager.setCurrentItem(3, true);
                   // stepNo.setText("3");
                } else if (count == 3) {
                    viewPager.setCurrentItem(4, true);
                    //stepNo.setText("4");
                } else if (count == 4) {
                    viewPager.setCurrentItem(5, true);
                    //stepNo.setText("5");
                }

                    /*else if (count == i) {
                        viewPager.setCurrentItem( i+ 1, true);

                    }*/

                break;


                 /*  if(layoutDrawable.length>=layoutDrawable[i])
                   {
                   launchHomeScreen();
                   }
                   else if(count==layoutDrawable[i])
                   {
                       viewPager.setCurrentItem(layoutDrawable[i]+1, true);
                   }*/
            //      }


        }

    }

    @Override
    public int getScreenName() {
        return 0;
    }

    private void addBottomDots(int currentPage) {
        ImageView[] dots = new ImageView[layoutDrawable.length];
        Log.d("Amit", "addBottomDots: " + layoutDrawable.length);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    (int) getResources().getDimension(R.dimen.eight_dp),
                    (int) getResources().getDimension(R.dimen.eight_dp));
            layoutParams.setMargins((int) getResources().getDimension(R.dimen.sevenDp),
                    (int) getResources().getDimension(R.dimen.two_dp),
                    (int) getResources().getDimension(R.dimen.two_dp),
                    (int) getResources().getDimension(R.dimen.two_dp));


            dots[i].setLayoutParams(layoutParams);
            dots[i].setBackground(CSDrawable.getDrawable(R.drawable.background_circle_line_thik));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[currentPage].setBackground(CSDrawable.getDrawable(R.drawable.background_circle_line_thin));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        CSAppUtil.openActivity(CSShearedPrefence.isAlreadyLaunch() ? MainActivity_Guard.class : SignInActivity.class);
        finish();

    }

    private void switchCondition(View view, int position) {
        switch (position) {
            case 0:
                Button welcome_screen1_BTN = view.findViewById(R.id.welcome_screen1_BTN);
                welcome_screen1_BTN.setOnClickListener(Application_Description_Screen.this);
                break;
            case 1:
                Button welcome_screen2_BTN = view.findViewById(R.id.welcome_screen2_BTN);
                welcome_screen2_BTN.setOnClickListener(Application_Description_Screen.this);

                break;
            case 2:
                Button welcome_screen3_BTN = view.findViewById(R.id.welcome_screen3_BTN);
                welcome_screen3_BTN.setOnClickListener(Application_Description_Screen.this);
                break;
            case 3:
                Button welcome_screen4_BTN = view.findViewById(R.id.welcome_screen4_BTN);
                welcome_screen4_BTN.setOnClickListener(Application_Description_Screen.this);
                break;
            case 4:
                Button welcome_screen5_BTN = view.findViewById(R.id.welcome_screen5_BTN);
                welcome_screen5_BTN.setOnClickListener(Application_Description_Screen.this);
                break;

            case 5:
                Button finishBTN = view.findViewById(R.id.button_tour);
                finishBTN.setOnClickListener(Application_Description_Screen.this);

                break;

        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(final int position) {
            count = position;

            addBottomDots(position);
//            btnSkip.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            Log.d("TAG", "onPageScrolled: ");
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    private void push() {
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (viewPager.getCurrentItem() == layoutDrawable.length - 1) {
                /*viewPager.setCurrentItem(0);*/
            } else
                viewPager.setCurrentItem(getItem(1), true);
        };

        timer = new Timer();
        long DELAY_MS = 3000;
        long PERIOD_MS = 3000;
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.e("instantiateItem>>>", "" + position);
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layoutDrawable[position], container, false);

//            LinearLayout linearLayout = view.findViewById(R.id.ll_layout);
            switchCondition(view, position);


            container.addView(view);

//            if (position == layoutDrawable.length - 1) {
//                View start = view.findViewById(R.id.button_tour);
//                if (start != null) {
//                    start.setOnClickListener(v -> switchCondition(position));
//                }
//            }
            return view;
        }


        @Override
        public int getCount() {
            return layoutDrawable.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
