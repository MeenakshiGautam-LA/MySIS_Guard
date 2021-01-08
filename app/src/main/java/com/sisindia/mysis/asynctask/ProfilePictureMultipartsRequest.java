package com.sisindia.mysis.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ashu Rajput on 11/24/2016.
 */

public class ProfilePictureMultipartsRequest<T> extends Request<T> {
    private static final String FILE_PART_NAME = "fileName";
    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
    private final File mImageFile;
    private ProgressDialog progressDialog = null;
    private ProfilePicUpdateListener profilePicUpdateListener = null;
    private Context context = null;
    private String fileName;
    private String documentID;
    private String documentTitle;
    private Map hashMap;
    private String apiCallResponse = "";

    //*****************************BELOW CONSTRUCTOR IS USED IN USER PROFILE ACTIVITY*****************************//
    public ProfilePictureMultipartsRequest(Context context, String url, ErrorListener errorListener, File imageFile, String fileName) {
        super(Method.POST, url, errorListener);
        this.context = context;
        setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("uploading image please wait...");
        progressDialog.show();

        profilePicUpdateListener = (ProfilePicUpdateListener) context;

        mImageFile = imageFile;
        this.fileName = fileName;
        buildMultipartEntity();
    }

    public ProfilePictureMultipartsRequest(Context context, String url, ErrorListener errorListener, ProfilePicUpdateListener profilePicUpdateListener,
                                           File imageFile, String fileName, Map hashMap) {
        super(Method.POST, url, errorListener);
        this.context = context;
        setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        this.profilePicUpdateListener = profilePicUpdateListener;

        mImageFile = imageFile;
        this.fileName = fileName;
        buildMultipartEntity();
    }

    public ProfilePictureMultipartsRequest(String url, ErrorListener errorListener, File imageFile, String fileName,
                                           ProfilePicUpdateListener profilePicUpdateListener,Map hashMap) {
        super(Method.POST, url, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(70 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Log.e("EmailBGService", "EmailProfiel Constructor");

        this.profilePicUpdateListener = profilePicUpdateListener;
        mImageFile = imageFile;
        this.fileName = fileName;
        this.hashMap = hashMap;
        this.documentTitle = documentTitle;
        buildMultipartEntity();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }

//        headers.put(HEADER_USERNAME, Commons.getInstance().getUserName(CSApplicationHelper.application()));
//        headers.put(HEADER_PASSWORD, Commons.getInstance().getPassword(CSApplicationHelper.application()));
//        headers.put(HEADER_DEVICEID, Commons.getInstance().getDeviceID());
//        headers.put(HEADER_AUTHTYPE, HEADER_AUTHTYPE_NAME);
//        headers.put("Accept", "application/json");
////        headers.put("Accept", "application/octet-stream");
//        headers.put("DOCUMENT_ID", documentID);
//        headers.put("DOCUMENT_TITLE", documentTitle);
//
//        Log.e("EmailBGService", "Header " + headers);

        return hashMap;
    }


    private void buildMultipartEntity() {
//        mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile, ContentType.create("multipart/form-data"), fileName);
        mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile, ContentType.create("image/jpg"), fileName);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("Z2f54fR5my4YD345dFmP1XQY609m4pAhDR1Zou").setCharset(Charset.forName("UTF-8"));
    }

    @Override
    public String getBodyContentType() {
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mBuilder.build().writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        apiCallResponse = new String(response.data);
        Log.e("Response", "ImageRes " + apiCallResponse);
        T result = (T) response;
        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {

        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

//        String responseID = "";
//        try {
//            JSONObject jsonObject = new JSONObject(apiCallResponse);
//            responseID = jsonObject.getString("d");
//        } catch (JSONException e) {
//            Crashlytics.logException(e);
//        }
        profilePicUpdateListener.onProfilePicUpdateSuccessfully(apiCallResponse);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);

        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

        if (context != null)
            Toast.makeText(context, "Unable to upload your profile pic", Toast.LENGTH_LONG).show();
    }

    public interface ProfilePicUpdateListener {
        void onProfilePicUpdateSuccessfully(String result);
    }

}
