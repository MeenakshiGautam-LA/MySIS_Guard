package com.sisindia.mysis.fragment.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.listener.DialogCallback;
import com.sisindia.mysis.listener.RecyclerItemClickListener;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

abstract public class CSHeaderFragmentFragment extends CSFragment
        implements RecyclerItemClickListener.OnItemClickListener,
        DialogCallback,
        PopupMenu.OnMenuItemClickListener {

    public LinearLayout footerView;
    private List<PermissionItem> listpermissionNedded;
    private static final int REQUEST_CODE_MUTI = 1001;
    private static final int REQUEST_CODE_MUTI_SINGLE = 1002;
    private int mRePermissionIndex;
    public boolean permissionAllowed = false;
    private static final int REQUEST_SETTING = 1003;

    public int headerViewTitle() {
        return R.string.app_name;
    }

    public String headerTitle() {
        return CSStringUtil.getString(headerViewTitle());
    }

    @Override
    public void init() throws Exception {

    }


    public void setUpMainHeaderView() {
        footerView = csActivity.findViewById(R.id.footerBottomSheet_LY);

//        toolbar_backPressIV.setOnClickListener(headerItemClick);
        if (footerView != null) {
            main_Header_ToolbarVisibilty();  //working CODE BY ANKIT
//            setUpHeaderItem();
        }

    }


    public void main_Header_ToolbarVisibilty() {
        footerView.setVisibility(footerBottomSheetVisibility());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        closeSearch();
    }

    public int footerBottomSheetVisibility() {
        return View.VISIBLE;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
        }
        return false;
    }

    public class HeaderItemClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }
    }

    @Override
    public void dialog_onClick(int requestCode) {

    }

    @Override
    public void dialog_onCancel() {

    }

    public void updateImage(ImageView targetImageView, String valueUrl, int placeHolder) {

        Glide.with(csActivity)
                .load(placeHolder)
                /*  .centerInside()*/
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .into(targetImageView);

        if (CSStringUtil.isNonEmptyStr(valueUrl)) {
            Glide.with(csActivity)
                    .load(valueUrl)
                    .error(placeHolder)
                    .placeholder(placeHolder)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    /* .centerInside()*/
                    .into(targetImageView);
        }


    }

    @Override
    public void onItemLongPress(View view, int position) {

    }

    public void showToast(String s) {
        Toast t = Toast.makeText(csActivity
                , s, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    public void show_Log_Data(String s) {
        Log.e("Show_Log_Data", "" + s);
    }

    public void show_Log_Data(String TAG, String data) {
        Log.e(TAG, "" + data);
    }

    public void checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            if (!permissionAllowed) {

                checkandRequestPermission();
            }
        }

    }

    private boolean checkandRequestPermission() {
        listpermissionNedded = new ArrayList<PermissionItem>();
        listpermissionNedded.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE, ""));
        listpermissionNedded.add(new PermissionItem(Manifest.permission.CAMERA, ""));
        listpermissionNedded.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, ""));
        listpermissionNedded.add(new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, ""));
        if (Build.VERSION.SDK_INT > 28) {
            listpermissionNedded.add(new PermissionItem(Manifest.permission.ACCESS_BACKGROUND_LOCATION, ""));
        }

        listpermissionNedded.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, ""));
        String str[] = getPermissionStrArray();
        requestPermissions(str, REQUEST_CODE_MUTI);
        return true;

    }

    private String[] getPermissionStrArray() {
        String[] str = new String[listpermissionNedded.size()];
        for (int i = 0; i < listpermissionNedded.size(); i++) {
            str[i] = listpermissionNedded.get(i).Permission;
        }
        return str;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_MUTI_SINGLE:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                    Log.e("ONDENY>>>>", "MEIN_AAYA>>>");
                    //重新申请后再次拒绝
                    //弹框警告! haha
                    try {
//                        //permissions可能返回空数组，所以try-catchs
//                        String name = getPermissionItem(permissions[0]).PermissionName;
//                        String title = String.format(getString(R.string.permission_title), name);
//                        String msg = String.format(getString(R.string.permission_denied_with_naac), "", name, "");


                        showAlertDialog(getResources().getString(R.string.permission_title), getResources().getString(R.string.Permission_message), getResources().getString(R.string.permission_cancel),
                                getString(R.string.permission_setting), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            Uri packageURI = Uri.parse("package:" + CSApplicationHelper.application().getActivity().getPackageName());
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                            startActivityForResult(intent, REQUEST_SETTING);
                                        } catch (Exception e) {
                                            e.printStackTrace();
//                            onClose();
                                        }
                                    }
                                });
//                onDeny(permissions[0], 0);
                    } catch (Exception e) {
                        e.printStackTrace();
//                onClose();
                    }
                } else {
//            onGuarantee(permissions[0], 0);
                    if (mRePermissionIndex < listpermissionNedded.size() - 1) {
                        //继续申请下一个被拒绝的权限
                        reRequestPermission(listpermissionNedded.get(++mRePermissionIndex).Permission);
                    } else {
                        //全部允许了
//                onFinish();

//                        editor.putBoolean("PERMISSION_ALLOWED", true);
//                        editor.apply();
                        permissionAllowed = true;
                        storeDeviceId();
                    }
                }
                break;
            case REQUEST_CODE_MUTI:
                for (int i = 0; i < grantResults.length; i++) {
                    //权限允许后，删除需要检查的权限
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        PermissionItem item = getPermissionItem(permissions[i]);
                        listpermissionNedded.remove(item);
//                    onGuarantee(permissions[i], i);
                    } else {
                        //权限拒绝
//                    onDeny(permissions[i], i);
                    }
                }
                if (listpermissionNedded.size() > 0) {
                    //User rejected one or more permissions and reapplied

                    reRequestPermission(listpermissionNedded.get(mRePermissionIndex).Permission);
                } else {
//                onFinish();
//                    editor.putBoolean("PERMISSION_ALLOWED", true);
//                    editor.apply();
                    permissionAllowed = true;
                    storeDeviceId();
                }
                break;


        }
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private void storeDeviceId() {

        if (CSStringUtil.isEmptyStr(CSShearedPrefence.getDeviceToken())) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                CSShearedPrefence.saveDeviceToken(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) csActivity.getSystemService(Context.TELEPHONY_SERVICE);
                CSShearedPrefence.saveDeviceToken(telephonyManager.getDeviceId());
            }
        }


    }

    public void showAlertDialog(String title, String msg, String positivelable, String cancelTxt, DialogInterface.OnClickListener posyiveClick) {

        AlertDialog alertDialog = new AlertDialog.Builder(CSApplicationHelper.application().getActivity())
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(positivelable, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        onClose();
                    }
                })
                .setPositiveButton(cancelTxt, posyiveClick).create();
        alertDialog.show();
    }

    private void reRequestPermission(final String permission) {
//        String alertTitle = String.format(getString(R.string.permission_title), permissionName);
//        String msg = String.format(getString(R.string.permission_denied), permissionName, "");
        showAlertDialog(getResources().getString(R.string.permission_title), getResources().getString(R.string.Permission_message), getString(R.string.permission_cancel), getString(R.string.permission_ensure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermission(new String[]{permission}, REQUEST_CODE_MUTI_SINGLE);
                    }
                });
    }

    private void requestPermission(String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode);
    }

    private PermissionItem getPermissionItem(String permission) {
        for (PermissionItem permissionItem : listpermissionNedded) {
            if (permissionItem.Permission.equals(permission))
                return permissionItem;
        }
        return null;
    }


    private void checkPermission() {

        ListIterator<PermissionItem> iterator = listpermissionNedded.listIterator();
        while (iterator.hasNext()) {
            int checkPermission = ContextCompat.checkSelfPermission(CSApplicationHelper.application().getActivity(), iterator.next().Permission);
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                iterator.remove();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTING) {
            Log.e("onActivityResult", "MEIN_AAYA>>>");
            checkPermission();
            if (listpermissionNedded.size() > 0) {
                mRePermissionIndex = 0;
                reRequestPermission(listpermissionNedded.get(mRePermissionIndex).Permission);
            } else {
                permissionAllowed = true;
                storeDeviceId();
            }
        }
    }

}