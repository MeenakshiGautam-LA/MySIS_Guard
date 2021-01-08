package com.sisindia.mysis.asynctask;

/**
 * Created by Consultit on 23/05/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class WebServiceResponseViaPost extends AsyncTask<String, Void, String>
{
    private String webResponse = null;
    private HashMap<String, String> apiParams;
    private AsyncRequestListenerViaPost asyncTaskListener;
    private HttpURLConnection httpURLConnection = null;
    private InputStream is = null;
    private OutputStream os = null;
    private static String json = null,loaderName=null, urlAppend=null;
    private ProgressDialog mProgressDialog;
    private Context activityContext;
    private boolean check=true;
    int tagNumber =0;
    private SharedPreferences preferences;

    String baseUrl2 = "https://cyberdukaan.com/mobi/dukaan/"; // CYBER DUKAAN BASE URL
    String baseUrl = "http://sfcc.sisersys.com:92/Rest.svc/";


    public WebServiceResponseViaPost(AsyncRequestListenerViaPost listener, String apiParams) {
//        this.activityContext = activityContext;
        this.json = apiParams;
        asyncTaskListener=listener;
    }

    public WebServiceResponseViaPost(AsyncRequestListenerViaPost listener, Context activityContext, String apiParams,
                                     String loaderName) {
        this.activityContext = activityContext;
        this.json = apiParams;
        this.loaderName = loaderName;
        asyncTaskListener=listener;
        mProgressDialog = new ProgressDialog(activityContext);
        mProgressDialog.setMessage(loaderName);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public WebServiceResponseViaPost(AsyncRequestListenerViaPost listener, Context activityContext
            , String apiParams, String urlAppend, String loaderName, boolean check) {
        this.activityContext = activityContext;
        this.json = apiParams;
        this.loaderName = loaderName;
        this.check=check;
        this.urlAppend = urlAppend;
        asyncTaskListener=listener;
        mProgressDialog = new ProgressDialog(activityContext);
        mProgressDialog.setMessage(loaderName);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public WebServiceResponseViaPost(AsyncRequestListenerViaPost listener, Context activityContext,
                                     String apiParams, String urlAppend, String loaderName,
                                     boolean check, int tagNumber) {
        this.activityContext = activityContext;
        this.json = apiParams;
        this.loaderName = loaderName;
        this.check=check;
        this.tagNumber = tagNumber;
        this.urlAppend = urlAppend;
        asyncTaskListener=listener;
        mProgressDialog = new ProgressDialog(activityContext);
        mProgressDialog.setMessage(loaderName);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public WebServiceResponseViaPost(AsyncRequestListenerViaPost listener, Context activityContext, String apiParams, String urlAppend, String loaderName) {
        this.activityContext = activityContext;
        this.json = apiParams;
        this.loaderName = loaderName;
        this.urlAppend = urlAppend;
        asyncTaskListener=listener;
        mProgressDialog = new ProgressDialog(activityContext);
        mProgressDialog.setMessage(loaderName);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }



    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub

        if(loaderName !=null)
        {
//            if(mProgressDialog!=null)
//                mProgressDialog.show();
        }

    }

    @Override
    protected String doInBackground(String... url) {
        // TODO Auto-generated method stub

        URL baseURL =null;

        preferences= activityContext.getSharedPreferences("EapPrefFile", MODE_PRIVATE);

//        baseUrl = "http://"+preferences.getString("APP_DOMAIN","cyberdukaan.in")+"/apex/cyberduaan/ep/";
//        baseUrl2 = "http://"+preferences.getString("APP_DOMAIN","cyberdukaan.in")+"/apex/cyberduaan/";

        try {
                baseURL  = new URL(baseUrl2 + urlAppend + "/");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {

            Log.e("JSON", "JUDO URL formed  " + baseURL);
//            Log.e("JSON", "JSON " + json);
            Log.e("JSON REQUEST", "JSON REQUEST " + "a=" + json);

            String encryptedData;
            if(tagNumber ==0){
                encryptedData = Uri.encode((json));
                Log.e("TAG NUMBER 0", "TAG NUMBER 0  " + encryptedData);
            }
            else if(tagNumber ==2){
                encryptedData  = Uri.encode(json);
                Log.e("TAG NUMBER 2", "TAG NUMBER 2  " + encryptedData);
            }
            else if(tagNumber ==3){
                encryptedData = Uri.encode((json));
                Log.e("TAG NUMBER 3", "TAG NUMBER 3  " + encryptedData);
            }else if(tagNumber ==5){
                encryptedData = (json);
                Log.e("TAG NUMBER 3", "TAG NUMBER 3  " + encryptedData);
            }else if(tagNumber ==4){
                encryptedData  = json;
            }
            else
                encryptedData  = json;

//            Log.e("JSON", "JSON ENCRYPTED-- " +encryptedData);
//            Log.e("JSON", "JSON ENCRYPTED " + (json));
//            Log.e("JSON", "JSON DECRYPTED " + (Uri.decode(encryptedData)));

            httpURLConnection = (HttpURLConnection) baseURL.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            if(urlAppend ==null) {
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }else if(tagNumber ==5){
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            else
            {
                if(urlAppend !=null) {
                    if (tagNumber == 0){
                        Log.e("urlAppend ","TAG NUMBER 0 : ");
                        httpURLConnection.setRequestProperty("a", (json));
                    }else if (tagNumber == 3){
                        Log.e("urlAppend ","TAG NUMBER 3 : ");
                        httpURLConnection.setRequestProperty("a", (json));
                    }else if (tagNumber == 5){
                        Log.e("urlAppend ","TAG NUMBER 5 : ");
                        httpURLConnection.setRequestProperty("a", (json));
                    }
                    else
                        httpURLConnection.setRequestProperty("a", json);
                }
                else
                {
                    httpURLConnection.setRequestProperty("a", json);
                }
            }

            os = httpURLConnection.getOutputStream();

            if(urlAppend ==null)
            {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write("a="+encryptedData);
                bw.flush();
                bw.close();

            }

            os.close();
            httpURLConnection.connect();


            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = httpURLConnection.getInputStream();// is is inputstream
            } else {
                is = httpURLConnection.getErrorStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                webResponse = sb.toString();
                Log.e("webResponse>>>>",""+webResponse);
//                is.close();
            }
            is.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            webResponse = "Exception";
        } catch (IOException ioe) {
            ioe.printStackTrace();
            webResponse = "Exception";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            webResponse = "Exception";
        }

        return webResponse;
    }

    // Sets the Bitmap returned by doInBackground
    @Override
    protected void onPostExecute(String receivedString) {

        Log.e("POST RESPONSE","POST RESPONSE : "+receivedString);

        if(loaderName !=null)
        {
//            if(mProgressDialog!=null && mProgressDialog.isShowing())
//                mProgressDialog.dismiss();
        }

//        Log.e("JSON postexecute", "JSON RESPONSE ENCRYPTED : " + receivedString);
//        Log.e("JSON postexecute", "JSON RESPONSE DECRYPTED : " + (Uri.decode(receivedString)));

        urlAppend =null;
        loaderName = null;

        if(!check)
        {
            Log.e("WITH","DECRYPTION"+(receivedString));
//            asyncTaskListener.onRequestComplete(receivedString);
            try{
                asyncTaskListener.onRequestComplete((receivedString));

            }catch (Exception e){
                Log.e("Exception","WebServiceResponseViaPost...."+ String.valueOf(e));
            }

        }
        else
        {
            if(tagNumber == 2){
                Log.e("WITHOUT DECRYPTION","TAG NUMBER 2 : "+receivedString);
                asyncTaskListener.onRequestComplete(receivedString);
            }else if(tagNumber == 3){
                Log.e("WITHOUT DECRYPTION","TAG NUMBER 3 : "+receivedString);
                asyncTaskListener.onRequestComplete(receivedString);
            }else if(tagNumber == 4){
                Log.e("WITHOUT DECRYPTION","TAG NUMBER 3 : "+receivedString);
                asyncTaskListener.onRequestComplete(receivedString);
            }else if(tagNumber == 5){
                Log.e("WITHOUT DECRYPTION","TAG NUMBER 3 : "+receivedString);
                asyncTaskListener.onRequestComplete(receivedString);
            }else if(tagNumber == 6){
                Log.e("WITHOUT DECRYPTION","TAG NUMBER 6 : "+receivedString);
                asyncTaskListener.onRequestComplete(receivedString);
            }
            else
                asyncTaskListener.onRequestComplete((receivedString));
        }

    }

// Introducing inner Interface to avoid "Principle of least privilege":

    public interface AsyncRequestListenerViaPost {

        public void onRequestComplete(String loadedString);
    }
}
