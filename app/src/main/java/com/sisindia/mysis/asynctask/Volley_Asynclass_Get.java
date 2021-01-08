package com.sisindia.mysis.asynctask;

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
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.view.CSProgressBar;

import java.util.HashSet;
import java.util.Map;

import static com.sisindia.mysis.utils.Constants.BASE_URL;


/**
 * Created by Ankit on 10/2/2017.
 */

public class Volley_Asynclass_Get {

    private String hostURL = BASE_URL;

    Context context;
    String appendTag = null;
    VolleyResponse volleyResponse;
    String param = null;
    Map<String, String> params1;
    private CSProgressBar mProgressDialog;
    String requestCode1 = null;

    public Volley_Asynclass_Get(Context context, String params, String tag) {
        this.context = context;
        volleyResponse = (VolleyResponse) context;
        this.param = params;
//        mProgressDialog = new ProgressDialog(context);
//        mProgressDialog.setMessage("");
//        mProgressDialog.setIndeterminate(false);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.show();
        Log.e("volleyAsyn", "params..." + param);
        api(tag);


    }

    public Volley_Asynclass_Get(Context context, Map params1, String tag, String requestCode) {
        volleyResponse = (VolleyResponse) context;
        Log.e("Volley_Asynclass_Get", "" + volleyResponse);
        this.params1 = params1;
        requestCode1 = requestCode;
//        mProgressDialog = new CSProgressBar(context);
//        mProgressDialog.setMessage(context.getResources().getString(R.string.please_wait));
//        mProgressDialog.setIndeterminate(false);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.show();
        Log.e("volleyAsyn", "params..." + params1 + "requestCode" + requestCode);
        api(tag);
    }


    public Volley_Asynclass_Get(VolleyResponse volleyResponse, Map params1, String tag, String requestCode) {
        this.volleyResponse = volleyResponse;
        Log.e("Volley_Asynclass_Get111", "params1>>>" + params1);
        this.params1 = params1;
        requestCode1 = requestCode;
//        mProgressDialog = new CSProgressBar(context);
//        mProgressDialog.setMessage(context.getResources().getString(R.string.please_wait));
//        mProgressDialog.setIndeterminate(false);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.show();
        Log.e("volleyAsyn", "params..." + params1 + "requestCode" + requestCode);
        api(tag);
    }



    public synchronized void api(String Tag) {

        hostURL = hostURL + Tag;
        Log.e("hosturl", "" + hostURL);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, hostURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("onRightResponse>>>>>>", "" + response);
                        volleyResponse.getDataFromVolleyInterFace(response, requestCode1);
//                        mProgressDialog.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mProgressDialog.dismiss();
Log.e("ErrorResponse",error.toString());
                        HashSet<String> set = CSApplicationHelper.application().getHashSet();
                        set.add(requestCode1);
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.no_internet));
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
//                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.no_internet));

                            //TODO
                        } else if (error instanceof NetworkError) {
                            //TODO
//                            CSDialogUtil.showInfoDialog(CSApplicationHelper.application().getActivity(), CSStringUtil.getString(R.string.no_internet));

                        } else if (error instanceof ParseError) {
                            //TODO
                        }


//                        volleyResponse.onFail("EXCEPTION",String.valueOf(error));
                    }
                }


        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return params1;
            }
        };

        CSApplicationHelper.application().addToRequestQueue(jsonObjectRequest, hostURL);
    }

    public interface VolleyResponse {
        void getDataFromVolleyInterFace(String loadedString, String regNO);
    }
}
