package com.sisindia.mysis.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

/**
 * Created by cirmac3 on 4/9/16.
 */
public class LoadResultViaPostMethod extends AsyncTask<String, Void, String> {

    private ProgressDialog mProgressDialog;
    String webResponse="";
    Context activityContext;
    private AsyncTaskCompleteListenerForPostMethod asyncTaskListener;
    HttpURLConnection httpURLConnection = null;
    InputStream is = null;
    OutputStream os = null;
    String params=null, tag = null;
    String appendTag;
    Map<String, String>  params1;
    String hostURL="https://cyberdukaan.com/mobi/dukaan/"; //  PRODUCTION VERSION URL
    String baseUrl = "http://sfcc.sisersys.com:92/Rest.svc/";

//    http://cyberdukaan.in/apex/cyberduaan/hr/getDocProfile/

    public LoadResultViaPostMethod(Context activityContext, String appendTag, Map params1)
    {
        Log.e("PARAM","param... "+params);
        this.activityContext=activityContext;
        this.appendTag = appendTag;
        this.params = params;
        this.asyncTaskListener=(AsyncTaskCompleteListenerForPostMethod) activityContext;
    }

    public LoadResultViaPostMethod(AsyncTaskCompleteListenerForPostMethod asyncTaskListener
            , Context activityContext, String appendTag, String params)
    {
        this.tag=tag;
        this.params = params;
        this.appendTag = appendTag;
        this.asyncTaskListener=asyncTaskListener;
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
//        if(mProgressDialog!=null)
//            mProgressDialog.show();
    }
    @Override
    protected String doInBackground(String... url)
    {
        // TODO Auto-generated method stub
        try
        {
            webResponse=sendPostRequest();
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            webResponse="Exception";
        }

        return webResponse;
    }

    @Override
    protected void onPostExecute(String receivedString)
    {
        Log.e("POST","Post Result "+receivedString);
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.cancel();

        if(receivedString==null)
            asyncTaskListener.onRequestComplete("");
            else
        asyncTaskListener.onRequestComplete(receivedString);

//        asyncTaskListener.onRequestComplete(receivedString);
    }

    public String sendPostRequest()
    {

        HttpURLConnection connection;
        OutputStreamWriter request = null;

        URL url = null;
        String response = null;

        hostURL = hostURL +appendTag+"/";
        Log.e("URL formed ", "URL formed  " + hostURL);
        Log.e("URL JSON", "JSON " + params);

        try
        {
            url = new URL(hostURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("a", params);
            connection.setRequestMethod("POST");

            request = new OutputStreamWriter(connection.getOutputStream());
            request.write(params);
            request.flush();
            request.close();
            String line = "";
//            System.out.println("this is connection "+connection);
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
            isr.close();
            reader.close();
            connection.disconnect();

        }
        catch(IOException e)
        {
            e.printStackTrace();
            // Error
        }
        finally {

        }

        return response;

    }
    public interface AsyncTaskCompleteListenerForPostMethod
    {
        public void onRequestComplete(String loadedString);
    }

}