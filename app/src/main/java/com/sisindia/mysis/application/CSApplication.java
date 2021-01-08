package com.sisindia.mysis.application;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;
import androidx.work.Configuration;
import androidx.work.WorkManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.sisindia.mysis.BuildConfig;
import com.sisindia.mysis.R;
import com.sisindia.mysis.ThemeHelper;
import com.sisindia.mysis.dataBase.AppDatabase;
import com.sisindia.mysis.utils.CSShearedPrefence;
import com.sisindia.mysis.utils.CSStringUtil;
import com.sisindia.mysis.utils.Constants;
import com.sisindia.mysis.utils.DatabaseAssistant;
import com.sisindia.mysis.utils.DateUtils;
import com.sisindia.mysis.view.CSProgressBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


//@ReportsCrashes(mailTo = "sushant.rijal@cubesquaretech.com",mode = ReportingInteractionMode.SILENT)
public class CSApplication extends Application {

    AppCompatActivity _activity;
    private String packageName;
    private RequestQueue mRequestQueue;
    private DrawerLayout drawerLayout;
    int themeId = R.style.DigitalAppTheme;
    private AppDatabase appDatabase;
    private int targetFrame = -1;
    private CSProgressBar csProgressBar;
    private ArrayList<String> memoryIds = new ArrayList<>();
    private HashSet<String> hashSet;
    private Map<String, String> postDataParam;
    private static Context context;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);

    }

    public static Context getAppContext() {
        return CSApplication.context;
    }

    public HashSet<String> getHashSet() {
        return hashSet;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CSApplicationHelper.setCSApplication(this);

        if (CSShearedPrefence.isNightModeEnabled()) {
            ThemeHelper.applyTheme(ThemeHelper.DARK_MODE);

        } else {
            ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE);
        }
        hashSet = new HashSet<>();
        Stetho.initializeWithDefaults(this);
//        LeakCanary.install(this);
        Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build();
//        Stetho.initializeWithDefaults(this);
        CSApplication.context = getApplicationContext();
        Configuration myConfig = new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();

//initialize WorkManager
        WorkManager.initialize(this, myConfig);
        // Fabric.with(this, new Crashlytics());

//        Thread.setDefaultUncaughtExceptionHandler(new CSTException(this));
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        setTheme(CSShearedPrefence.getThemeId());
    }

    public void setDatabaseInstance() {
        if (appDatabase == null)
            this.appDatabase = AppDatabase.getDatabase(this);
    }

    public AppDatabase getDatabaseInstance() {
        if (appDatabase == null)
            this.appDatabase = AppDatabase.getDatabase(this);
        return appDatabase;
    }

    public AppCompatActivity getActivity() {

        return _activity;
    }

    public String addQueryParam(String URL, String param) {

        if (param == null || TextUtils.isEmpty(param)) param = "";

        String url = null; //if null something issue in url

        if (URL.toLowerCase().contains("lastModified".toLowerCase())) {

            if (URL.toLowerCase().contains("?lastModifiedDatetime".toLowerCase())) {

                URL = URL.replace("?lastModifiedDatetime=", "");
                url = Uri.parse(URL).buildUpon().appendQueryParameter("lastModifiedDatetime", param).build().toString();
                Log.e("", "the s = " + URL + "url = " + url);
            }
        } else {
            url = Uri.parse(URL).buildUpon().build().toString();
        }

        Log.d("addQueryParam", url);
        return url;

        /*String url;
        url = Uri.parse(URL).buildUpon().appendQueryParameter("LastUpdateDatetime=", param).build().toString();

        LogUtil.d(TAG + "addQueryParam", url);
        return url;*/

    }


    public void setActivity(AppCompatActivity activity) {
        this._activity = activity;
        CSShearedPrefence.getInstance();
        csProgressBar = new CSProgressBar(activity);
        setDatabaseInstance();
    }

    public int getResourceId(String resourceName, String defType) {
        return getResourceId(resourceName, defType, this.packageName());
    }

    public int getResourceId(String resourceName, String defType, String packageName) {
        return CSStringUtil.isNonEmptyStr(resourceName) ? this.getResources().getIdentifier(resourceName, defType, packageName) : 0;
    }

    public String packageName() {
        if (this.packageName == null) {
            PackageInfo info = this.packageInfo();
            this.packageName = info != null ? info.packageName : "org.srilaprabhupadalila";
        }
        return this.packageName;
    }

    public ProgressDialog getInstance_ProgresssBar() {
        if (csProgressBar == null)
            csProgressBar = new CSProgressBar(CSApplicationHelper.application().getActivity());
        csProgressBar.setMessage(CSApplicationHelper.application().getActivity().getResources().getString(R.string.please_wait));

        return csProgressBar;
    }

    public PackageInfo packageInfo() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        return info;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }


    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);

//        CSShearedPrefence.setThemeId(resid);
//        this.themeId = resid;
    }

    public ArrayList<String> getMemoryIds() {
        return memoryIds;
    }

    public void setMemoryIds(ArrayList<String> memoryIds) {
        this.memoryIds = memoryIds;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

/*    public ArrayList<CSObject> getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(ArrayList<CSObject> targetObject) {
        this.targetObject = targetObject;
    }*/

    public int getTargetFrame() {
        return targetFrame;
    }

    public void setTargetFrame(int targetFrame) {
        this.targetFrame = targetFrame;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        Log.e("TAG URL", "addToRequestQueue: " + tag);
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        req.setTag(TextUtils.isEmpty(tag) ? "TAG" : tag);
        getRequestQueue().add(req);
    }

    /*
        ///Volley SSL
        @SuppressLint("TrulyRandom")
        public static void handleSSLHandshake() {
            try {
                TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }};

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            } catch (Exception ignored) {
            }


        }*/
    public Map<String, String> getPostDataParam() {
        postDataParam = new HashMap<>();
//        postDataParam.put("Content-Type", "application/json");
        postDataParam.put("UserName", CSShearedPrefence.getUser());
        postDataParam.put("Password", CSShearedPrefence.getPassword());
        postDataParam.put("MPIN", CSShearedPrefence.getMpin());
        postDataParam.put("DeviceID", CSShearedPrefence.getDeviceToken());
        postDataParam.put("AppType", Constants.APP_TYPE);
        postDataParam.put("VERSION", BuildConfig.VERSION_NAME);
        return postDataParam;
    }

    public void exportXMLDb() {
        String currentDBPath = "/data/data/" + getPackageName() + "/databases/GUARD.db";
        new CopyDatabase(currentDBPath, _activity).execute();
    }

    private class CopyDatabase extends AsyncTask<String, Integer, String> {

        String databasePath, backupPath;
        Context context;
        String fileName = "GUARD_" + DateUtils.getInstance().getSaveDateTimeString() + ".db";

        public CopyDatabase(String databasePath, Context context) {
            this.databasePath = databasePath;
            this.context = context;
            this.backupPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DatabaseBackup" + "/" + fileName;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                File sd = Environment.getExternalStorageDirectory();

                if (sd.canWrite()) {
                    Log.d("", "currentDBPath " + databasePath);
                    Calendar c = Calendar.getInstance();
                    String backupDBPath = fileName;

                    String pathDirectory = sd.getAbsolutePath() + "/DatabaseBackup";
                    File path = new File(pathDirectory);
                    if (!path.exists())
                        path.mkdirs();

                    File currentDB = new File(databasePath);
                    File backupDB = new File(path, backupDBPath);

                    if (currentDB.exists()) {
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }
                }

            } catch (Exception e) {
                Log.e("", "error db copy " + e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(context, "Database Copied", Toast.LENGTH_SHORT).show();
            new WriteXML(backupPath, context).execute();
        }
    }

    private class WriteXML extends AsyncTask<String, Integer, String> {
        String backupPath;
        Context context;

        public WriteXML(String backupPath, Context context) {
            this.backupPath = backupPath;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                File sd = Environment.getExternalStorageDirectory();

                if (sd.canWrite()) {
                    Log.d("", "currentDBPath " + backupPath);

                    File currentDB = new File(backupPath);

                    if (currentDB.exists()) {

                        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(backupPath, MODE_PRIVATE, null);
                        DatabaseAssistant databaseAssistant = new DatabaseAssistant(getApplicationContext(), sqLiteDatabase);
                        databaseAssistant.exportData();
                    }
                }
            } catch (Exception e) {
                Log.e("", "error db copy " + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(_activity, "XML Generated", Toast.LENGTH_SHORT).show();
        }
    }


//First Sprint Successfully Done
}
