package com.sisindia.mysis.fragment.base;

import android.accounts.Account;
import android.content.ContentResolver;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sisindia.mysis.activity.base.BaseActivity;
import com.sisindia.mysis.asynctask.Volley_Asynclass_Get;
import com.sisindia.mysis.listener.GetLoctaionInterface;
import com.sisindia.mysis.listener.ResponseListener;
import com.sisindia.mysis.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;

import static com.sisindia.mysis.utils.SyncUtils.getAccount;

abstract public class CSFragment extends Fragment implements View.OnClickListener,
        ResponseListener, Volley_Asynclass_Get.VolleyResponse, GetLoctaionInterface {

    public View fragmentView;
    final public String TAG = "CSFRAGMET" + this.getClass();
    public BaseActivity csActivity;
//    public CSTileAdapter csTileAdapter;


//    abstract public void getArgs();

    abstract public int layoutResource();

//    abstract public boolean closeSearch();

    abstract protected void setUpMainHeaderView() throws Exception;

//    abstract public void setClickOnListener();

//    abstract public void setOnData();

    //    abstract public int getScreenName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
//            getArgs();
            csActivity = ((BaseActivity) getActivity());
            fragmentView = inflater.inflate(layoutResource(), container, false);

            init(savedInstanceState);
            ButterKnife.bind(this, fragmentView);
            if (csActivity != null)
                setUpMainHeaderView();

        } catch (Exception ex) {
            Log.e(TAG, "onCreateView: " + ex);
            ex.printStackTrace();
        }
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fragmentView != null) {
//            setClickOnListener();
        }
//        setOnData();
    }

    abstract public void init() throws Exception;

    public void init(Bundle saveInstance) throws Exception {
        init();
    }

    public void setClickListener(View... params) {
        for (View view : params) {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetResponse(JSONArray jsonArray, int requestCode) {

    }

    @Override
    public void onGetResponse(JSONObject jsonObject, int requestCode) {

    }

    @Override
    public void onGetResponse(String jsonObject, int requestCode) {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onFail(String message) {
//        CSDialogUtil.showInfoDialog(csActivity, message);
    }

    @Override
    public void getDataFromVolleyInterFace(String loadedString, String regNO) {

    }

    @Override
    public void getCurrent_Location(Location location) {

    }

    public boolean isProgressVisible() {
        return true;
    }

    protected void triggerSync() {
        Log.e("TriggerSync", "Coming To Trigger Sync");
        Bundle b = new Bundle();
        b.putString("sync", "1");
        b.putString("api_name", "All");
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        Account account = getAccount(Constants.SYNC_ACCOUNT_30_MINUTE);
        ContentResolver.requestSync(account, "com.sisindia.mysis", b);
    }
}
