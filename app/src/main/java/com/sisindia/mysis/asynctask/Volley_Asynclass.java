package com.sisindia.mysis.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sisindia.mysis.R;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.listener.ResponseListener;
import com.sisindia.mysis.utils.CSDialogUtil;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.view.CSProgressBar;

import java.util.HashMap;
import java.util.Map;

import static com.sisindia.mysis.utils.Constants.BASE_URL;

/**
 * Created by Ankit on 10/2/2017.
 */

public class Volley_Asynclass {

    private String hostURL = BASE_URL;
    Context context;
    String appendTag = null;
    ResponseListener volleyResponse;
    String param = null,listner=null;
    private CSProgressBar mProgressDialog;
    Map<String, String> params1;
    private Integer requestCode = null;

    public Volley_Asynclass(Context context, String params, String tag) {
        this.context = context;
        this.param = params;
        mProgressDialog = new CSProgressBar(context);
        mProgressDialog.setMessage("");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        Log.e("volleyAsyn", "params..." + param);
        api(tag);

    }

    public Volley_Asynclass(Context context, ResponseListener listner, Map params1, String tag, Integer requestCode) {
        volleyResponse =  listner;
        this.params1 = params1;
        this.requestCode = requestCode;
        mProgressDialog = new CSProgressBar(context);
        mProgressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Log.e("volleyAsyn", "params..." + params1 + "requestCode" + requestCode);
        api(tag);
    }

    public Volley_Asynclass(Context context, ResponseListener listner, String tag, Integer requestCode) {
        volleyResponse = (ResponseListener) listner;
        this.requestCode = requestCode;
        this.params1 = new HashMap<>();
/*
        mProgressDialog = new CSProgressBar(context);
        mProgressDialog.setMessage(context.getResources().getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
*/
        Log.e("volleyAsyn", "params..." + params1 + "requestCode" + requestCode);
        api(tag);
    }

    public Volley_Asynclass(Context context, ResponseListener listner, Map params1, String tag,
                            Integer requestCode, String waitingTag) {
        volleyResponse = (ResponseListener) listner;
        this.params1 = params1;
        this.requestCode = requestCode;
        mProgressDialog = new CSProgressBar(context);
        mProgressDialog.setMessage(waitingTag);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Log.e("volleyAsyn", "params..." + params1 + "requestCode" + requestCode);
        api(tag);
    }


    public Volley_Asynclass(Context context, String params, String tag, String message) {
        this.context = context;
        this.param = params;
        mProgressDialog = new CSProgressBar(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
//        mProgressDialog.show();
        api(tag);
        volleyResponse = (ResponseListener) context;
    }

    public synchronized void api(String Tag) {

        hostURL = hostURL + Tag;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, hostURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("synchronizedAPI", "onResponse>>>" + response);
                        if (mProgressDialog != null)
                            mProgressDialog.dismiss();
                        volleyResponse.onGetResponse(response, requestCode);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mProgressDialog != null)
                            mProgressDialog.dismiss();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.no_internet));
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.no_internet));

                            //TODO
                        } else if (error instanceof NetworkError) {
                            //TODO
                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.no_internet));

                        } else if (error instanceof ParseError) {
                            //TODO
                        }
                        volleyResponse.onFail(error.getMessage());
                    }
                }


        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> obj = new HashMap<>();
//                obj.put("VREGNO", "pat087392");
//                obj.put("DEVICEID", "869501024232693");
//                obj.put("VRESULT", "a");
//                return obj;
                return params1;
            }
        };

        CSApplicationHelper.application().addToRequestQueue(jsonObjectRequest, hostURL);

    }

}
