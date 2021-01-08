package com.sisindia.mysis.asynctask;

/**
 * Created by Consultit on 25/05/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sisindia.mysis.BuildConfig;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.Constants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Consultit on 20/09/2016.
 */
public class WebServiceFileUploadViaPost extends AsyncTask<String, Void, String>
{
    private String webResponse = null;
    private HashMap<String, String> apiParams;
    private AsyncRequestListenerViaPost asyncTaskListener;
    private HttpURLConnection httpURLConnection = null;
    private InputStream is = null;
    private OutputStream os = null;
    private static String json = null,tag=null,imageName=null, urlAppend=null;
    private File imageFileURI;
    private ProgressDialog mProgressDialog;
    private Context activityContext;
    private String urlParam=null;
    private boolean check = false;
    private SharedPreferences preferences;
    int tagnumber,tag_update=0;

    String baseUrl = "http://sfcc.sisersys.com:92/Rest.svc/";


    public WebServiceFileUploadViaPost(AsyncRequestListenerViaPost listener, File imageFileURI,  String urlParam) {
//        this.activityContext = activityContext;
//        this.json = apiParams;
        this.imageFileURI = imageFileURI;
//        this.imageName = imageName;
//        this.tagnumber = tagnumber;
        this.urlParam = urlParam;
//        this.activityContext = activityContext;
        asyncTaskListener=listener;
//        mProgressDialog = new ProgressDialog(activityContext);
//        mProgressDialog.setMessage(message);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public WebServiceFileUploadViaPost(AsyncRequestListenerViaPost listener, Context activityContext
            , String apiParams, File imageFileURI, String imageName, String urlParam, String message, boolean check) {
//        this.activityContext = activityContext;
        this.json = apiParams;
        this.check = check;
        this.imageFileURI = imageFileURI;
        this.imageName = imageName;
        this.urlParam = urlParam;
        this.activityContext = activityContext;
        asyncTaskListener=listener;
//        mProgressDialog = new ProgressDialog(activityContext);
//        mProgressDialog.setMessage(message);
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
//        if(mProgressDialog!=null)
//            mProgressDialog.show();
    }
    @Override
    protected String doInBackground(String... url) {
        // TODO Auto-generated method stub
        try {

//            preferences= activityContext.getSharedPreferences("EapPrefFile", MODE_PRIVATE);
//            baseUrlBuildShop = "http://"+preferences.getString("APP_DOMAIN","cyberdukaan.in")+"/apex/cyberduaan/";

            URL baseURL = new URL(Constants.BASE_URL+urlParam);

            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundrey = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            DataOutputStream dos = null;
            int maxBufferSize = 1 * 1024 * 1024;

            Log.e("JUDO URL formed", "JUDO URL formed  " + baseURL);
            Log.e("JSON", "JSON " + json);

            if (!imageFileURI.isFile()) {
                Log.e("SOURECE FILE NOT EXIST", "Source file does not exist : " + imageFileURI);

            }
            else
            {
                FileInputStream fileInputStream = new FileInputStream(imageFileURI);
                File file = new File(imageFileURI.getAbsolutePath());
                int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
                Log.d("","file Size return... "+file_size);
                Log.d("fileInputStream>>>","fileInputStream.. "+fileInputStream);

                httpURLConnection = (HttpURLConnection) baseURL.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(7000);
                httpURLConnection.setReadTimeout(50000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data");
                httpURLConnection.setRequestProperty("UserName", CSShearedPrefence.getUser());
                httpURLConnection.setRequestProperty("Password", CSShearedPrefence.getPassword());
                httpURLConnection.setRequestProperty("DeviceID",CSShearedPrefence.getDeviceToken());
                httpURLConnection.setRequestProperty("AppType   ", Constants.APP_TYPE);
                httpURLConnection.setRequestProperty("VERSION",  BuildConfig.VERSION_NAME);
//                httpURLConnection.setRequestProperty("FILE_NAME", "MarkAttendance.jpg");
//                httpURLConnection.setRequestProperty("ID", "fd97d415-c218-4070-82c4-51b293ed63d9");
//                httpURLConnection.setRequestProperty("MODULE_NAME", "SHIFT_CLOSER");
//                httpURLConnection.setRequestProperty("PARENT_ID", "25c89caf-a0af-46aa-9649-1bc2263e4990");
//                httpURLConnection.setRequestProperty("USERNAME", "PAT087392");
//                httpURLConnection.setRequestProperty("Password", "1234");
                //httpURLConnection.setRequestProperty("a", encryptedData);

                os = httpURLConnection.getOutputStream();

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    os.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                os.close();
                fileInputStream.close();
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
//                is.close();
                }
                is.close();
            }

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
    protected void onPostExecute(String receivedString)
    {
//        if(tag_update==1){//by ANKIT
//            asyncTaskListener.onRequestComplete((receivedString));
//
//        }
////        if(mProgressDialog!=null && mProgressDialog.isShowing())
////            mProgressDialog.cancel();
//        Log.e("JSON", "JSON RESPONSE ENCRYPTED " + receivedString);
//
//        if(check)
//        {
//            Log.e("JSON", "JSON RESPONSE ENCRYPTED2222222 " +(receivedString));
//            asyncTaskListener.onRequestComplete((receivedString));
//        }
//        else
//        {
//            if(tagnumber==1){
//
//                asyncTaskListener.onRequestComplete(receivedString);
//
//            }if(tagnumber==2){
//                asyncTaskListener.onRequestComplete((receivedString));
//            }
//            else{
//
//                asyncTaskListener.onRequestComplete((receivedString));
//
//            }
//
//        }
        asyncTaskListener.onRequestComplete((receivedString));

    }

    public interface AsyncRequestListenerViaPost {

        public void onRequestComplete(String loadedString);
    }
}
