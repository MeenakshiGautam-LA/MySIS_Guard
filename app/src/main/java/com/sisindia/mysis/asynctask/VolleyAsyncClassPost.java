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
import com.android.volley.toolbox.JsonObjectRequest;
import com.sisindia.mysis.application.CSApplicationHelper;
import com.sisindia.mysis.view.CSProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Map;

import static com.sisindia.mysis.utils.Constants.BASE_URL;

/**
 * Created by Ankit on 10/2/2017.
 */

public class VolleyAsyncClassPost {

    private String hostURL = BASE_URL;

    Context context;
    String appendTag = null;
    VolleyPostResponse volleyResponse;
    String param = null;
    Map<String, String> postDataParam;
    private CSProgressBar mProgressDialog;
    Integer requestCode = null;
    String requestCode1 = null;

    public VolleyAsyncClassPost(Context context, String params, String tag) {
        this.context = context;
        volleyResponse = (VolleyPostResponse) context;
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

    public VolleyAsyncClassPost(Context context, Map params1, String tag, String requestCode) {
        volleyResponse = (VolleyPostResponse) context;
        Log.e("Volley_Asynclass_Get", "" + volleyResponse);
        this.postDataParam = params1;
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


    public VolleyAsyncClassPost(VolleyPostResponse volleyResponse, Map params1, String tag, String requestCode, String API, String tableName) {
        this.volleyResponse = volleyResponse;
        Log.e("Volley_Asynclass_Get111", "params1>>>" + params1);
        this.postDataParam = params1;
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

    private void api(String tag) {

    }

    public VolleyAsyncClassPost(VolleyPostResponse volleyPostResponse, JSONArray jsonArray, String API, String tableName) {
        this.volleyResponse = volleyPostResponse;
        postData(jsonArray, API, tableName);
    }

    public VolleyAsyncClassPost(VolleyPostResponse volleyPostResponse,Map postDataParam ,
                                JSONObject jsonObject, String APITag, String tableName) {
        this.volleyResponse = volleyPostResponse;
        this.postDataParam=postDataParam;
        Log.e("postDataParam",""+postDataParam);
        postData(jsonObject, APITag, tableName);
    }


    private synchronized void postData(JSONArray jsonArray, String API, String tableName) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("data", jsonArray);
            Log.e("JSON ", "postData: " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        hostURL = hostURL + API;
        Log.e("hosturl", "" + hostURL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, hostURL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("onResponse>>>>>>", "" + response);
//                        if (response.optString("status").equals("true")) {

                            volleyResponse.postDataFromVolleyInterface(response, tableName);
//                        mProgressDialog.dismiss();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mProgressDialog.dismiss();

                        HashSet<String> set = CSApplicationHelper.application().getHashSet();
                        set.add(requestCode1);
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        } else if (error instanceof AuthFailureError) {
                            //TODO
                        } else if (error instanceof ServerError) {
                            //TODO
                        } else if (error instanceof NetworkError) {
                            //TODO

                        } else if (error instanceof ParseError) {
                            //TODO
                        }
//                        volleyResponse.onFail("EXCEPTION",String.valueOf(error));
                    }
                }


        ) {
            @Override
            public String getBodyContentType() {
                //return super.getBodyContentType();
                return "text";
//                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return CSApplicationHelper.application().getPostDataParam();
            }
        };

        CSApplicationHelper.application().addToRequestQueue(jsonObjectRequest, hostURL);
    }

    private synchronized void postData(JSONObject jsonObject, String APITag, String tableName) {
        hostURL = hostURL + APITag;
        Log.e("hosturl", "" + hostURL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, hostURL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("onResponse>>>>>>", "" + response);
                        if (response.optString("status").equals("true")) {

                            volleyResponse.postDataFromVolleyInterface(response, tableName);
//                        mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mProgressDialog.dismiss();

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
            public String getBodyContentType() {
                //return super.getBodyContentType();
                return "text";
//                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return postDataParam;
            }
        };

        CSApplicationHelper.application().addToRequestQueue(jsonObjectRequest, hostURL);
    }


    public interface VolleyPostResponse {
        void postDataFromVolleyInterface(JSONObject response, String tableName);
    }


    private synchronized void postDocumentData(JSONArray json, String API, String tableName, Context context) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("data", json);
            Log.d("JSON ", "postData: " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        hostURL = hostURL + API;
        Log.e("hosturl", "" + hostURL);


    }
}
