package com.sisindia.mysis.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;


public class CSUIUtil {

    public static void applyKeyBoardEvent(final View clickView, EditText applyOn) {
        applyOn.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                CSAppUtil.closeKeyboard(CSApplicationHelper.application(), v);
                clickView.performClick();
            }
            return false;
        });
    }

    public static void applyPasswordDrawable(EditText passwordEditText) {
        CustomTouchListener customTouchListener = new CustomTouchListener(passwordEditText);
        passwordEditText.setOnTouchListener(customTouchListener);
    }

    public static void applyDrwable(EditText target, int left) {
        applyDrwable(target, left, 0);

    }

    public static void applyDrwable(EditText target, int left, int right) {
        target.setCompoundDrawablesWithIntrinsicBounds(left, 0, right, 0);
    }

    static class CustomTouchListener implements View.OnTouchListener {

        EditText passwordEditText;

        public CustomTouchListener(EditText passwordEditText) {
            this.passwordEditText = passwordEditText;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    try {
                        if (CSUIUtil.setPasswordVisibility(passwordEditText)) {
//                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock, 0, R.drawable.ic_round_visibility_off, 0);
                        } else {
//                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock, 0, R.drawable.ic_round_visibility, 0);

                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public static boolean setPasswordVisibility(EditText pass) {
        if (pass.getTransformationMethod() instanceof HideReturnsTransformationMethod) {
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        pass.setSelection(pass.getText().length());
        return pass.getTransformationMethod() instanceof HideReturnsTransformationMethod;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static Drawable getDrawable(int drawableResId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return AppCompatResources.getDrawable(CSApplicationHelper.application(), drawableResId);
        } else {
            try {
                return CSApplicationHelper.application().getDrawable(drawableResId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    public static void setTextColor(final TextView... text) {
//        for (TextView editText : text) {
//            editText.setTextColor(CSApplicationHelper.application().getResources().getColor(getColorCode()));
//        }
//    }

    public static void applyEditTextListener(final EditText... editTextChange) {
        for (EditText editText : editTextChange) {
            applyEditTextListener(editText);
        }
    }

    public static void applyEditTextListener(final EditText editTextChange) {
        editTextChange.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if (str.length() > 0 && str.contains(" ")) {
                    editTextChange.setText(editTextChange.getText().toString().trim());
                    editTextChange.setSelection(editTextChange.getText().length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

//    public static void applySearchListener(final EditText editTextChange, final CSTileAdapter csTileAdapter, final RecyclerView recyclerView) {
//
//        editTextChange.setImeOptions(EditorInfo.IME_ACTION_NONE);
//        editTextChange.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                csTileAdapter.getFilter().filter(s);
//                /*try {
//                    if(csTileAdapter.getTempAsync()!=null){
//                        if(csTileAdapter.getTempAsync().getStatus() == AsyncTask.Status.RUNNING ){
//                            csTileAdapter.getTempAsync().cancel(true);
//                        }
//                        if(s!=null)
//                            csTileAdapter.getTempAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,s.toString());
//                    }
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }*/
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }


    public static void clearText(EditText view) {
        if (view != null) {
            view.setText("");
        }
    }
    /**
     * Making headerNotificationButton bar transparent
     */
    public static void changeStatusBarColor(BaseActivity csActivity) {
        Window window = csActivity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setDefault(ImageView... targetViews) {
        for (ImageView tagertImageView : targetViews) {
            if (tagertImageView != null)
                tagertImageView.setColorFilter(CSApplicationHelper.application().getResources().getColor(R.color.color_bg_alert_badge));
        }
    }

//    public static void setTint(ImageView... targetViews) {
//        for (ImageView tagertImageView : targetViews) {
//            tagertImageView.setColorFilter(CSApplicationHelper.application().getResources().getColor(getColorCode()));
//        }
//    }

//    public static void changeBackgroundTheme(View... list) {
//        int colorCode = getColorCode();
//        for (View view : list) {
//            view.setBackgroundColor(CSApplicationHelper.application().getResources().getColor(getColorCode()));
//        }
//    }
//
//    public static void changeBackgroundDownloadHeader(View... list) {
//        int colorCode = getColorCode();
//        for (View view : list) {
//            view.setBackgroundColor(CSApplicationHelper.application().getResources().getColor(R.color.download_header));
//        }
//    }


//    public static void changeBackgroundDownloadTab(View... list) {
//        int colorCode = getColorCode();
//        for (View view : list) {
//            view.setBackgroundColor(CSApplicationHelper.application().getResources().getColor(R.color.download_tab));
//        }
//    }
//
//
//    public static void changeBackgroundThemeAccent(View... list) {
//        int colorCode = getColorCode();
//        for (View view : list) {
//            view.setBackgroundColor(CSApplicationHelper.application().getResources().getColor(getAccentColorCode()));
//        }
//    }

//    public static int getColorCode() {
//        switch (CSShearedPrefence.getThemeId()) {
//            case R.style.AppThemeBlue:
//                return R.color.colorPrimary;
//            case R.style.AppThemeRed:
//                return R.color.redPrimaryColor;
//            case R.style.AppThemeTeal:
//                return R.color.greenPrimaryColor;
//            case R.style.AppThemePurple:
//                return R.color.purplePrimaryColor;
//            case R.style.AppThemeOrange:
//                return R.color.orangePrimaryColor;
//            case R.style.AppThemeMaroon:
//                return R.color.maroonPrimaryColor;
//            case R.style.AppThemePink:
//                return R.color.pinkPrimaryColor;
//            case R.style.AppThemeGray:
//                return R.color.lightBlackPrimaryColor;
//            case R.style.AppThemeGreen:
//                return R.color.darkGreenPrimaryColor;
//            case R.style.AppThemebrown:
//                return R.color.brownPrimaryColor;
//
//
//        }
//        return R.color.colorPrimary;
//    }
//
//    public static int getAccentColorCode() {
//        switch (CSShearedPrefence.getThemeId()) {
//            case R.style.AppThemeBlue:
//                return R.color.colorAccent;
//            case R.style.AppThemeRed:
//                return R.color.redPrimaryAccentColor;
//            case R.style.AppThemeTeal:
//                return R.color.greenAccentColor;
//            case R.style.AppThemePurple:
//                return R.color.purpleAccentColor;
//            case R.style.AppThemeOrange:
//                return R.color.orangeAccentColor;
//            case R.style.AppThemeMaroon:
//                return R.color.maroonAccentColor;
//            case R.style.AppThemePink:
//                return R.color.pinkAccentColor;
//            case R.style.AppThemeGray:
//                return R.color.lightBlackAccentColor;
//            case R.style.AppThemebrown:
//                return R.color.brownAccentColor;
//            case R.style.AppThemeGreen:
//                return R.color.darkGreenAccentColor;
//        }
//        return R.color.colorPrimary;
//    }


    public static int getCircleFillDrawableCode() {
        switch (CSShearedPrefence.getThemeId()) {
//            case R.style.AppThemeBlue :
//                return R.drawable.background_circle;
//            case R.style.AppThemeRed:
//                return R.drawable.background_circle_red;
//            case R.style.AppThemeTeal:
//                return R.drawable.background_circle_teal;
//            case R.style.AppThemePurple:
//                return R.drawable.background_circle_purple;
//            case R.style.AppThemeOrange:
//                return R.drawable.background_circle_orange;
//            case R.style.AppThemeMaroon:
//                return R.drawable.background_circle_maroon;
//            case R.style.AppThemePink:
//                return R.drawable.background_circle_pink;
//            case R.style.AppThemeGray:
//                return R.drawable.background_circle_gray;
//            case R.style.AppThemebrown:
//                return R.drawable.background_circle_browwn;
//            case R.style.AppThemeGreen:
//                return R.drawable.background_circle_dark_green;
//        }
//        return R.drawable.background_circle;
        }
        return 1;
    }

    public static int getRoundBorderDrawable() {
        switch (CSShearedPrefence.getThemeId()) {
//            case R.style.AppTheme :
//                return R.drawable.background_round;
//            case R.style.AppThemeRed:
//                return R.drawable.background_round_red;
//            case R.style.AppThemeTeal:
//                return R.drawable.background_round_teal;
//            case R.style.AppThemePurple:
//                return R.drawable.background_round_purple;
//            case R.style.AppThemeOrange:
//                return R.drawable.background_round_orange;
//            case R.style.AppThemeMaroon:
//                return R.drawable.background_round_maroon;
//            case R.style.AppThemePink:
//                return R.drawable.background_round_pink;
//            case R.style.AppThemeGray:
//                return R.drawable.background_round_gray;
//            case R.style.AppThemebrown:
//                return R.drawable.background_round_brown;
//            case R.style.AppThemeGreen:
//                return R.drawable.background_round_dark_green;
//        }
//        return R.drawable.background_round;
        }
        return 1;
    }

    public static void changeNightMode(int color) {
        try {
            CSShearedPrefence.setNightId(color);
//            CSAppUtil.showToast(R.string.setting_night_mode_update);
            CSApplicationHelper.application().getActivity().onBackPressed();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
