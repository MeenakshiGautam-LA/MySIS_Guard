package com.sisindia.mysis.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Consultit on 16/01/2017.
 */

public class WebServiceBodyResponseViaPost extends AsyncTask<String, Void, String> {
    private String webResponse = null;
    private HttpURLConnection httpURLConnection = null;
    private InputStream is = null;
    private OutputStream os = null;
    private static String json = null, urlAppend = null;
    private ProgressDialog mProgressDialog;
    private Context activityContext;
    private boolean check = false;
    private AsyncRequestListenerViaPost asyncTaskListener;
    private SharedPreferences preferences;

    //String baseUrl = "https://cyberdukaan.com/mobi/dukaan/";
    //String baseUrl = "http://sfcc.sisersys.com:92/Rest.svc/";// CYBER DUKAAN BASE URL FOR BUILD SHOP
    String baseUrl = "http://sfcc.sisersys.com:92/Rest.svc/";

    public WebServiceBodyResponseViaPost(AsyncRequestListenerViaPost listener, Context activityContext
            , String apiParams, String urlAppend, String loaderName) {
        this.activityContext = activityContext;
        this.json = apiParams;
        this.urlAppend = urlAppend;
        this.asyncTaskListener = listener;
        mProgressDialog = new ProgressDialog(activityContext);
        mProgressDialog.setMessage(loaderName);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public WebServiceBodyResponseViaPost(AsyncRequestListenerViaPost listener, Context activityContext
            , String apiParams, String urlAppend, String loaderName, boolean check) {
        this.activityContext = activityContext;
        this.json = apiParams;
        this.check = check;
        this.urlAppend = urlAppend;
        this.asyncTaskListener = listener;
        mProgressDialog = new ProgressDialog(activityContext);
        mProgressDialog.setMessage(loaderName);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        if (mProgressDialog != null)
            mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... url) {
        // TODO Auto-generated method stub
        try {

            preferences = activityContext.getSharedPreferences("EapPrefFile", MODE_PRIVATE);
            URL baseURL = new URL(baseUrl + urlAppend);
            Log.e("webservice class", "url " + baseURL);


            String finalString = "";
            if (json.length() > 1000) {
                String st = "";
                String d = "";
                for (long i = 0; i < json.length(); i = i + 1000) {
                    if (i + 1000 <= json.length()) {
                        st = st + (json.substring((int) i, (int) i + 1000)) + "~";
                    } else {
                        d = d + (json.substring((int) i, json.length()));
                    }
                }

                finalString = st + d;
                if (finalString.endsWith("~")) {
                    finalString = st.substring(0, st.length() - 1);
                }

                Log.e("JSON", "JSON LINE ENCRYPTED " + finalString);

                st = null;
                d = null;
            } else {
            }

            httpURLConnection = (HttpURLConnection) baseURL.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(200000);
            httpURLConnection.setReadTimeout(200000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "text/plain");
            os = httpURLConnection.getOutputStream();

            DataOutputStream wr = new DataOutputStream(os);
            wr.writeBytes(finalString.toString());
            wr.flush();
            wr.close();
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

    @Override
    protected void onPostExecute(String receivedString) {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.cancel();
        Log.e("JSON", "JSON RESPONSE ENCRYPTED " + receivedString);
        if (receivedString != null && !receivedString.equals("Exception")) {
            if (check) {
                Log.e("JSON", "JSON RESPONSE ENCRYPTED2222222 " + (receivedString));
                asyncTaskListener.onRequestComplete((receivedString));
            } else {
                asyncTaskListener.onRequestComplete((receivedString));
            }
        } else {
            asyncTaskListener.onRequestComplete("");
        }


//        asyncTaskListener.onRequestComplete(receivedString);
    }
    // Introducing inner Interface to avoid "Principle of least privilege":

    public interface AsyncRequestListenerViaPost {

        public void onRequestComplete(String loadedString);
    }

    private void writeToFile(String data) {
        try {
            File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/BBBB");
            boolean b = folder.mkdir();
            Log.e("AAA", "AAA No" + b);

            // Create the file.
            File file = new File(folder, "config.txt");

            // Save your stream, don't forget to flush() it before closing it.
            boolean c = file.createNewFile();

            Log.e("AAA", "AAA No22" + c);
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
