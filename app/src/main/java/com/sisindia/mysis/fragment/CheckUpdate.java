package com.sisindia.mysis.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.asynctask.Volley_Asynclass;
import com.sisindia.mysis.fragment.base.CSHeaderFragmentFragment;
import com.sisindia.mysis.utils.CSStringUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;

import static com.sisindia.mysis.utils.Constants.GET_CHECK_UPDATE;

public class CheckUpdate extends CSHeaderFragmentFragment {
    @BindView(R.id.avaliableDesTV)
    TextView avaliableDesTV;
    @BindView(R.id.appVersionTV)
    TextView appVersionTV;
    @BindView(R.id.checkUpdateBTN)
    Button checkUpdateBTN;
    @BindView(R.id.downloadUpdateBTN)
    Button downloadUpdateBTN;
    @BindView(R.id.backPressLY)
    LinearLayout backPressLY;

    @Override
    public int layoutResource() {
        return R.layout.check_update;
    }

    @Override
    public void setUpMainHeaderView() {
        super.setUpMainHeaderView();
        setAppVersionName();
        checkAppVersionOnServer();
        checkUpdateBTN.setOnClickListener(view -> {
            checkUpdateBTN.setAlpha(0.5f);
            checkUpdateBTN.setEnabled(false);
            checkAppVersionOnServer();
        });
        backPressLY.setOnClickListener(view -> csActivity.onBackPressed());
    }

    private void setAppVersionName() {
        try {
            String app_ver_code = csActivity.getPackageManager().getPackageInfo(csActivity.getPackageName(), 0).versionName;
            appVersionTV.setText(app_ver_code);
            Log.e("LocationDetector", "Location AREA 333333" + app_ver_code);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void checkAppVersionOnServer() {
        avaliableDesTV.setText(CSStringUtil.getString(R.string.checking));
        new Volley_Asynclass(csActivity, this, CSApplicationHelper.application().getPostDataParam(), GET_CHECK_UPDATE, 000);
    }

    @Override
    public void onGetResponse(String loadedString, int requestCode) {
        try {
            JSONObject response = new JSONObject(loadedString);
            if (response.optString("status").equals("true")) {

                JSONArray jsonArray = response.getJSONArray("data");
                if (jsonArray.length() > 0) {
                    checkUpdateBTN.setAlpha(1.0f);
                    checkUpdateBTN.setEnabled(true);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String VER_ID = jsonObject.getString("VER_ID");
                    String APP_TYPE = jsonObject.getString("APP_TYPE");
                    String fileVersion = jsonObject.getString("FILE_VERSION");
                    String name = jsonObject.getString("NAME");
                    String description = jsonObject.getString("DESCRIPTION");
                    String file_url = jsonObject.getString("FILE_URL");
                    String app_ver_code = csActivity.getPackageManager().getPackageInfo(csActivity.getPackageName(), 0).versionName;
                    if (!app_ver_code.equalsIgnoreCase(fileVersion)) {
                        avaliableDesTV.setText(CSStringUtil.getString(R.string.update_available));
                        appVersionTV.setText(CSStringUtil.getString(R.string.versionname)+" " + app_ver_code);
                        checkUpdateBTN.setVisibility(View.GONE);
                        downloadUpdateBTN.setVisibility(View.VISIBLE);
                        downloadUpdateBTN.setOnClickListener(view -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sisindia.guardattendance"));
                            startActivity(intent);
                        });
                    } else {
                        avaliableDesTV.setText(CSStringUtil.getString(R.string.No_update_available));
                        appVersionTV.setText(CSStringUtil.getString(R.string.versionname) + app_ver_code);
                        checkUpdateBTN.setVisibility(View.VISIBLE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int footerBottomSheetVisibility() {
        return View.GONE;
    }
}
