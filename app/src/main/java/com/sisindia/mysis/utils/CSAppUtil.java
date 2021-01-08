package com.sisindia.mysis.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.sisindia.mysis.MainActivity;
import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.application.CSApplicationHelper;


public class CSAppUtil {

    public static void openKeyBoard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static void closeKeyboard(final BaseActivity context, final View view) {

        try {

            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getApplicationWindowToken(), 0);
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void closeKeyboard(final Context context, final View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void showToast(int resId) {
        try {
            Toast toast = Toast.makeText(CSApplicationHelper.application().getActivity(), resId, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception ex) {
            Log.d("TAG", "showToast: ");
            ex.printStackTrace();
            Toast.makeText(CSApplicationHelper.application().getActivity(), resId, Toast.LENGTH_LONG).show();
        }
    }

    public static void showToast(String message) {
        Toast toast = Toast.makeText(CSApplicationHelper.application().getActivity(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (!CSApplicationHelper.application().getActivity().isFinishing())
            toast.show();
    }

    public static void showError(EditText editText, int resId) {
        editText.setError(CSStringUtil.getString(resId));
        editText.requestFocus();
    }

    public static void setSnackBar(View coordinatorLayout, String snackTitle) {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, snackTitle, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.ok, view -> snackbar.dismiss());
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = view.findViewById(R.id.snackbar_text);
        txtv.setMaxLines(5);  // show multiple line
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);

    }

    public static void setUpHeader(int id) {
//        ((TextView)(CSApplicationHelper.application().getActivity()).findViewById(R.id.header_text)).setText(CSApplicationHelper.application().getResources().getString(id));
    }

    public static void openFragmentNoAnim(Fragment fragment) {

        FragmentManager fragmentManager = CSApplicationHelper.application().getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }

        fragmentTransaction.replace(R.id.container, fragment).commit();
    }

    public static void openAddFragmentNoAnim(Fragment fragment) {
        FragmentTransaction fragmentTransaction = CSApplicationHelper.application().getActivity().
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack("").commit();
    }

    public static void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = CSApplicationHelper.application().getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container, fragment).commit();
    }

    public static void openAddFragment(Fragment fragment, String TAG) {
        FragmentTransaction fragmentTransaction = CSApplicationHelper.application().getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(TAG).commit();
    }

    public static void openAddFragmentNoStack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = CSApplicationHelper.application().getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.container, fragment).commit();
    }

    public static void openFragmentAfterLoginNoAnim(Fragment fragment) {
        if (!openLogin())
            CSAppUtil.openFragmentNoAnim(fragment);
    }

    public static void openAddFragmentAfterLoginNoAnim(Fragment fragment) {
        if (!openLogin())
            CSAppUtil.openAddFragmentNoAnim(fragment);
    }


    public static void openFragmentAfterLoginWithAnim(Fragment fragment) {
        if (!openLogin())
            CSAppUtil.openFragment(fragment);
    }

    public static boolean openLogin() {
        if (!CSShearedPrefence.isLoggedIn()) {
            CSAppUtil.openActivity(MainActivity.class, false);
            return true;
        }
        return false;
    }

    public static void openActivity(boolean isClearStack, Class target) {
        Intent intent = new Intent(CSApplicationHelper.application().getActivity(), target);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CSApplicationHelper.application().getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        if (isClearStack) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        CSApplicationHelper.application().getActivity().startActivity(intent);

    }

    public static void openActivity(Class target, boolean isCurrentFinish) {
        Intent intent = new Intent(CSApplicationHelper.application().getActivity(), target);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CSApplicationHelper.application().getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        if (isCurrentFinish) {
            CSApplicationHelper.application().getActivity().finish();
        }
        CSApplicationHelper.application().getActivity().startActivity(intent);

    }

    public static void openActivity(Class target) {
        Intent intent = new Intent(CSApplicationHelper.application().getActivity(), target);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            CSApplicationHelper.application().getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
//        CSApplicationHelper.application().getActivity().finish();
        CSApplicationHelper.application().getActivity().startActivity(intent);
    }

    public static void openActivity(Intent intent) {
        CSApplicationHelper.application().getActivity().startActivityForResult(intent, 0);
    }

    public static boolean isNetworkAvailable(Context context, boolean isShowToast) {
        ConnectivityManager cm = null;
        if (context != null) {
            cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        } else {
            if (CSApplicationHelper.application().getApplicationContext() != null) {
                cm = (ConnectivityManager) CSApplicationHelper.application().getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
            }
        }
        if (cm != null) {
            NetworkInfo netInfo;
            try {
                netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                    return true;
                } else if (isShowToast) {
                    CSAppUtil.showToast(R.string.internet_connection);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean isNetworkNotAvailable(Context context, boolean isShowToast) {
        return !isNetworkAvailable(context, isShowToast);
    }

    public static void openFragmentByNavigation(View view, int actionId) {
        NavHostFragment navHostFragment = (NavHostFragment) CSApplicationHelper.application().getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
//        NavController navController = Navigation.findNavController(view);
        navController.navigate(actionId);

    }

    public static Fragment returnnavController(View view, BaseActivity mContext) {
        NavHostFragment navHostFragment = (NavHostFragment) mContext.getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        return navHostFragment.getChildFragmentManager().getFragments().get(0);
    }

    public static void openFragmentByNavigationClearStack(View view, int actionId) {
        NavHostFragment navHostFragment = (NavHostFragment) CSApplicationHelper.application().getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
//        NavController controller = Navigation.findNavController(view);
        navController.popBackStack(actionId, true);

    }

    public static void openBottomSheetfragment(BaseActivity mContext, int actionId) {
//        NavHostFragment navHostFragment = (NavHostFragment) mContext.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment) CSApplicationHelper.application().getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navCo = navHostFragment.getNavController();
        navCo.navigate(actionId);

    }
    public static void openbottomSheetfragmentWithBundle(BaseActivity mContext, int actionId, Bundle bundle) {
//        NavHostFragment navHostFragment = (NavHostFragment) mContext.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment) CSApplicationHelper.application().getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navCo = navHostFragment.getNavController();
        navCo.navigate(actionId,bundle);

    }

    public static void openBottomSheetfragmentWithStack(BaseActivity mContext, int actionId, boolean stack) {
//        NavHostFragment navHostFragment = (NavHostFragment) mContext.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavHostFragment navHostFragment = (NavHostFragment) CSApplicationHelper.application().getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navCo = navHostFragment.getNavController();
        navCo.popBackStack(actionId, stack);
        navCo.navigate(actionId);

    }

    public static void openFragmentByNavigationNotAddInStack(View view, int actionId) {

        NavController navController = Navigation.findNavController(view);
        navController.navigate(actionId);
        navController.popBackStack(actionId, false);
    }

    public static void openFragmentByNavi_Pass_Bundle(View view, int actionId, Bundle bundle) {
        NavHostFragment navHostFragment = (NavHostFragment)CSApplicationHelper.application().getActivity(). getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
//        NavController navController = Navigation.findNavController(view);
        navController.popBackStack(actionId, false);
        navController.navigate(actionId, bundle);


    }

    public static void openFragmentByNavigationWithStackClear(View view, int actionId) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(actionId, null, new NavOptions.Builder()
                .setPopUpTo(R.id.signActivity,
                        false).build());

    }

    public static String getScanLocationQR(String qrString) {
        String resultQr = qrString.trim();
        try {
            if (resultQr.trim().length() > 0) {
                if (isNumeric(resultQr)) {
                    return resultQr;
                } else {
                    resultQr = qrString.split("QRCODE")[1];
                    resultQr = resultQr.split("Vendor")[0];
                    if (resultQr.contains(":"))
                        resultQr = resultQr.replace(":", "").trim();
                    return resultQr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrString;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
