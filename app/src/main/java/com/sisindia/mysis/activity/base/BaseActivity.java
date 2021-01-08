package com.sisindia.mysis.activity.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.sisindia.mysis.R;
import com.sisindia.mysis.activity.Application_Description_Screen;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.fragment.base.PermissionItem;
import com.sisindia.mysis.listener.GetLoctaionInterface;
import com.sisindia.mysis.listener.ResponseListener;
import com.sisindia.mysis.utils.CSAppUtil;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

abstract public class BaseActivity extends AppCompatActivity implements GetLoctaionInterface, View.OnClickListener, ResponseListener,
        GoogleApiClient.ConnectionCallbacks,
        ResultCallback<LocationSettingsResult>, GoogleApiClient.OnConnectionFailedListener {
    public GoogleApiClient mGoogleApiClient;
    public ProgressDialog progressDialog;
    private LocationRequest locationRequest;
    public int locationPermissionAllowed = 0;
    private CSHeaderFragmentFragment context;
    private List<PermissionItem> listpermissionNedded;
    private static final int REQUEST_CODE_MUTI = 1001;
    private static final int REQUEST_CODE_MUTI_SINGLE = 1002;
    private int mRePermissionIndex;
    public boolean permissionAllowed = false;
    private static final int REQUEST_SETTING = 1003;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setTheme(CSShearedPrefence.getThemeId());
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme

        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        ButterKnife.bind(this);
        CSApplicationHelper.application().setActivity(this);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
       /* int width = size.x;
        int height = size.y;*/


        int width = display.getWidth();
        int height = display.getHeight();
        Log.e("SCREEN_DIMEN",""+width+"height"+height);
    }

    @Override
    public void onGetResponse(String jsonObject, int requestCode) {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getCurrent_Location(Location location) {

    }

    public void setListener(View... params) {
        for (View view : params) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {

    }

    @Override
    public void onGetResponse(JSONArray jsonArray, int requestCode) {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onFail(String message) {
//        CSDialogUtil.showInfoDialog(this,message);
    }


    //For google anaylaytics
    abstract public int getScreenName();

    @Override
    public String toString() {
//        return CSStringUtil.getString(getScreenName()); // Working
        return "layout_dialog_box";
    }

    public void getCurrentLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                initializeSettingApi();
            }
        } else {
            initializeSettingApi();
        }

    }

    public void getCurrentLocationWithOutLoader(CSHeaderFragmentFragment context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                initializeSettingApiWithOut_Loader();
                stopGoogleClient();
            }
        } else {
            initializeSettingApiWithOut_Loader();
            stopGoogleClient();
        }

    }

    private void stopGoogleClient() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mGoogleApiClient != null) {
                    mGoogleApiClient.disconnect();
                }
            }
        }, 150000); // 3 minutes
    }

    private void initializeSettingApi() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Searching Location...");


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }

    private void initializeSettingApiWithOut_Loader() {


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    //GET CURRENT LOCATION
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2 * 1000);
        locationRequest.setFastestInterval(0);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            if (progressDialog != null) {
                progressDialog.show();
            }

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest
                , new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (location != null) {
                            getCurrent_Location(location);
                            locationPermissionAllowed=1;
                            CSShearedPrefence.setGeoLocation(location.getLatitude() + "," + location.getLongitude());

                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }

                    }
                });

        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:


                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {
                    status.startResolutionForResult(this, 1);


                } catch (IntentSender.SendIntentException e) {
                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_SETTING:
                Log.e("onActivityResult", "MEIN_AAYA>>>");
                checkPermission();
                if (listpermissionNedded.size() > 0) {
                    mRePermissionIndex = 0;
                    reRequestPermission(listpermissionNedded.get(mRePermissionIndex).Permission);
                } else {
                    permissionAllowed = true;
                    storeDeviceId();
                }
                break;
            case 1:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        locationPermissionAllowed = 1;

                        // All required changes were successfully made
                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        locationPermissionAllowed = 0;
                        CSDialogUtil.showInfoDialog(this,CSStringUtil.getString(R.string.Allowed_Location_permission),
                                "","Allowed","Cancel",false,12345,context,null);
//                        CSDialogUtil.showInfoDialog(this, CSStringUtil.getString(R.string.Allowed_Location_permission), context,
//                                12345, false);
                        // The user was asked to change settings, but chose not to
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }
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
        listpermissionNedded.add(new PermissionItem(Manifest.permission.CALL_PHONE, ""));
        listpermissionNedded.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, ""));
        listpermissionNedded.add(new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, ""));
        if (Build.VERSION.SDK_INT > 28) {
            listpermissionNedded.add(new PermissionItem(Manifest.permission.ACCESS_BACKGROUND_LOCATION, ""));
        }

        listpermissionNedded.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, ""));
        String str[] = getPermissionStrArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(str, REQUEST_CODE_MUTI);
        }
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
                        CSAppUtil.openActivity(Application_Description_Screen.class);
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
                    CSAppUtil.openActivity(Application_Description_Screen.class);
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
                CSShearedPrefence.saveDeviceToken(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
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


}
