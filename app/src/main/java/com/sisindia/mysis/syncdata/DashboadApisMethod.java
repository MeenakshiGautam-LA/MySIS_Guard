package com.sisindia.mysis.syncdata;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sisindia.mysis.MainActivity;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SalesMaxx on 10/20/2016.
 */

public class DashboadApisMethod {

    static final String TAG = DashboadApisMethod.class.getName();
    private Context c;
    private String done = "false";
    //private ProgressDialog pDialog;

    private static String Logged_In_UserID = null;
    //private static String DEVICE_ID = null;
    // private static String IMEI_NO = null;
    private static String SERIAL_NO = null;

    SyncListener syncListener = null;

    private static DashboadApisMethod instance = null;


    public static DashboadApisMethod getInstance(Context ctx) {
        if (instance == null) {
            instance = new DashboadApisMethod(ctx);
        }
        return instance;
    }

    public DashboadApisMethod(Context c) {
        this.c = c;
        //  pDialog = new ProgressDialog(c);
        //  pDialog.setCanceledOnTouchOutside(false);

    }

    public void initSyncListener(SyncListener listener) {
        this.syncListener = listener;
    }


    public String getLoggedInUserID() {
        if (Logged_In_UserID == null)
            Logged_In_UserID = CSShearedPrefence.getInstance().getString("LoginUserID", "");
        return Logged_In_UserID;
    }

    public synchronized void getUpdatedVersion(final Context context, String versionCode, final AppCompatActivity activity) {
        // LogUtil.d(TAG, getFilterURLByUserID(RestConstant.GET_ALERT_LIST));
        String tag_string_req = "string_req";
        StringRequest request = new StringRequest(Request.Method.GET,
                Constants.GET_UPDATED_VERSION, new Response.Listener<String>() {
            @Override
            public void onResponse(final String str) {

                try {
                    JSONObject response = new JSONObject(str);
                    if (response.optString("status").equals("true")) {

                        JSONArray jsonArray = response.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String VER_ID = jsonObject.getString("VER_ID");
                            String fileVersion = jsonObject.getString("FILE_VERSION");
                            String name = jsonObject.getString("NAME");
                            String description = jsonObject.getString("DESCRIPTION");
                            String file_url = jsonObject.getString("FILE_URL");

                            checkAPPCodeAndDownlaod(context, fileVersion, name, description, file_url);
                        }
                        // // TODO: 07-Feb-18 here will be nudge popup
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                error(error);
            }

        });

        CSApplicationHelper.application().addToRequestQueue(request, tag_string_req);
    }

    public void checkAPPCodeAndDownlaod(Context context, String fileVersion, String name, String description, String file_url) {

        try {
            String app_ver_code = CSApplicationHelper.application().getPackageManager().getPackageInfo(CSApplicationHelper.application().getPackageName(), 0).versionName;
            Log.e("MainActivity", "app_ver_code = " + app_ver_code);
            //  float f = Float.parseFloat(app_ver_code);

            if (!TextUtils.isEmpty(fileVersion)) {
                if (!TextUtils.equals(app_ver_code, fileVersion)) {
                    if (context != null) {
                        if (!((MainActivity) context).isFinishing()) {
                            //show dialog
//                            UpdateAPKDialog updateApkDiloag = new UpdateAPKDialog(context, name, description, file_url);
//                            updateApkDiloag.show();
                        }
                      /*  else
                        {
                            UpdateAPKDialog updateApkDiloag = new UpdateAPKDialog(, name, description, file_url);
                            updateApkDiloag.show();
                        }*/
                    }
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
        }
    }



    /*public String getDeviceID() {
        if (DEVICE_ID == null)
            DEVICE_ID = getIMEINo();
        Log.e("DashboardApisMethod", "DEVICE_ID " + DEVICE_ID);
        return DEVICE_ID;
       // return "34:bb:26:43:a3:01";
    }

    String getIMEINo() {
        if (IMEI_NO == null) {
            TelephonyManager mngr = (TelephonyManager) AppController.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
            IMEI_NO = mngr.getDeviceId();
        }
        Log.e("DashboardApisMethod", "IMEI_NO " + IMEI_NO);
        return IMEI_NO;

    }*/

   /* String getSerialNo() {
        if (SERIAL_NO == null) {
            //   TelephonyManager mngr = (TelephonyManager)AppController.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
            SERIAL_NO = Build.SERIAL;

        }
        Log.e("DashboardApisMethod", "SERIAL_NO " + SERIAL_NO);
        return SERIAL_NO;

    }*/


    private String getFilterURLByUserID(String URLWithoutFilter) {

        String URLWithFilter;
        URLWithFilter = Uri.parse(URLWithoutFilter).buildUpon().appendQueryParameter("$filter", "ASSIGNED_USER_ID eq '" + getLoggedInUserID() + "'").build().toString();

        Log.d(TAG + "getFilterURLByUserID", URLWithFilter);
        return URLWithFilter;
    }

    private String getFilterURLByUserID(String URLWithoutFilter, String lastModifiedDate) {

        if (TextUtils.isEmpty(lastModifiedDate)) lastModifiedDate = "";
        String URLWithFilter;
        URLWithFilter = Uri.parse(URLWithoutFilter).buildUpon().appendQueryParameter("$filter", "ASSIGNED_USER_ID eq '" + getLoggedInUserID() + "' and date_modified gt '" + lastModifiedDate + "'").build().toString();

        Log.d(TAG + "getFilterURLByUserID", URLWithFilter);
        return URLWithFilter;
    }

    //***********
    private String getFilterURLByModuleName(String URLWithoutFilter, String lastModifiedDate) {

        if (TextUtils.isEmpty(lastModifiedDate)) lastModifiedDate = "";
        String URLWithFilter;
        URLWithFilter = Uri.parse(URLWithoutFilter).buildUpon().appendQueryParameter("$filter", "ASSIGNED_USER_ID eq '" + getLoggedInUserID() + "' and date_modified gt '" + lastModifiedDate + "'").build().toString();

        Log.d(TAG + "getFil", URLWithFilter);
        return URLWithFilter;
    }

    public String addQueryParam(String URL, String param) {

        if (param == null || TextUtils.isEmpty(param)) param = "";

        String url = null; //if null something issue in url

        if (URL.toLowerCase().contains("LastUpdate".toLowerCase())) {

            if (URL.toLowerCase().contains("?LastUpdateDatetime".toLowerCase())) {

                URL = URL.replace("?LastUpdateDatetime=", "");
                url = Uri.parse(URL).buildUpon().appendQueryParameter("LastUpdateDatetime", param).build().toString();
                Log.e("", "the s = " + URL + "url = " + url);

            } else if (URL.toLowerCase().contains("?LastUpdateDate".toLowerCase())) {

                URL = URL.replace("?LastUpdateDate=", "");
                url = Uri.parse(URL).buildUpon().appendQueryParameter("LastUpdateDate", param).build().toString();

                Log.e("", "the s = " + URL + "url = " + url);

            } else if (URL.toLowerCase().contains("?LastUpdatedate".toLowerCase())) {

                URL = URL.replace("?LastUpdatedate=", "");
                url = Uri.parse(URL).buildUpon().appendQueryParameter("LastUpdatedate", param).build().toString();

                Log.e("", "the s = " + URL + "url = " + url);

            } else if (URL.toLowerCase().contains("?LastUpdateddate".toLowerCase())) {

                URL = URL.replace("?LastUpdateddate=", "");
                URL = URL.replace("?LastUpdatedDate=", "");
                url = Uri.parse(URL).buildUpon().appendQueryParameter("LastUpdateddate", param).build().toString();

                Log.e("", "the s = " + URL + "url = " + url);

            } else if (URL.toLowerCase().contains("?LastUpdatedDate".toLowerCase())) {

                URL = URL.replace("?LastUpdatedDate=", "");
                URL = URL.replace("?LastUpdateddate=", "");
                url = Uri.parse(URL).buildUpon().appendQueryParameter("LastUpdatedDate", param).build().toString();

                Log.e("", "the s = " + URL + "url = " + url);
            } else if (URL.toLowerCase().contains("?Quotation_ID".toLowerCase())) {

                URL = URL.replace("?Quotation_ID=", "");
                URL = URL.replace("?Quotation_ID=", "");
                url = Uri.parse(URL).buildUpon().appendQueryParameter("Quotation_ID", param).build().toString();

                Log.e("", "the s = " + URL + "url = " + url);
            } else
                url = Uri.parse(URL).buildUpon().build().toString();

        } else if (URL.toLowerCase().contains("Quotation".toLowerCase())) {
            URL = URL.replace("?Quotation_ID=", "");
            url = Uri.parse(URL).buildUpon().appendQueryParameter("Quotation_ID", param).build().toString();
            Log.e("", "the s = " + URL + "url = " + url);
        } else {
            url = Uri.parse(URL).buildUpon().build().toString();
        }

        Log.d(TAG + "addQueryParam", url);
        return url;

    }

    void setProgressDialogMsg(String msg) {

        /*pDialog.setMessage(msg);
        if (pDialog != null) {
            if (!pDialog.isShowing())
                pDialog.show();
        }*/
    }


    // ASHU RAJPUT, PARSING RESPONSE GETTING FROM API FOR FAQ
    public synchronized void getFAQ(String lastModifiedDate) {
        //  setProgressDialogMsg("Loading FAQ ...");
        String tag_string_req = "string_req";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getFilterURLByUserID(
                addQueryParam(Constants.GETCUSTOMERDETAILS, lastModifiedDate)), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // save
                    }
                }).start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                error(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return getHeadersParams();
            }
        };

        CSApplicationHelper.application().addToRequestQueue(request, tag_string_req);

    }


    public String trimMessage(String paramString1, String paramString2) {
        try {
            String str = new JSONObject(paramString1).getString(paramString2);
            return str;
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
//            FirebaseCrash.report(localJSONException);
        }
        return null;
    }

    /**
     * @param lastModifiedDate
     * @author AshuRajput
     * @Defining method to parse Email Details from API and Insert into local DB
     */


    //  EMD - Dhiraj
    public synchronized void getEMD(String lastModifiedDate) {
        //setProgressDialogMsg("Loading Contacts...");

        String tag_string_req = "string_req";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getFilterURLByUserID(addQueryParam(Constants.GET_REASON_DETAIL, lastModifiedDate), lastModifiedDate), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d("getEMD", response.toString());
                        // Go to next activity
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }).start();

                        if (syncListener != null) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    syncListener.OnSyncGetDataCompleted();
                                }
                            }, 28000);
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                error(error);


                if (syncListener != null) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
//                            syncListener.OnSyncGetDataCompleted();
                        }
                    }, 28000);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return getHeadersParams();
            }
        };

        CSApplicationHelper.application().addToRequestQueue(request, tag_string_req);
    }

    //Upload data to Server.

    public synchronized void uploadQuaotationSummary(JSONObject fullQuotationObject, final String quotationID, final Context context) {

        JsonObjectRequest getdocJsonRequest = new JsonObjectRequest(Request.Method.POST, Constants.DAILY_ATTENDANCEDETAIL, fullQuotationObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("senddata_response", response.toString());
                        //update dirty flag.
                        ContentValues cv = new ContentValues();
                        cv.put("dirty_flag", 0);

                        // update dirty flag
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        error(error);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return getHeadersParams();
            }

            @Override
            public String getBodyContentType() {
                return "text/plain";
            }
        };
        CSApplicationHelper.application().addToRequestQueue(getdocJsonRequest, "GETDOCID");
    }


    public interface SyncListener {
        void OnSyncGetDataCompleted();

        void OnSyncPostDataCompleted();

        void OnSyncError(VolleyError error);
    }

    void error(VolleyError error) {
        if (syncListener != null) {
            syncListener.OnSyncError(error);
        }
    }

    private HashMap<String, String> getHeadersParams() {
//        Log.e("Header","Header"+ Commons.getInstance().getHeadersParams(c));
        return new HashMap<>();
    }

}
