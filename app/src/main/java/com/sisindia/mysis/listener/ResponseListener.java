package com.sisindia.mysis.listener;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ResponseListener {

    void onGetResponse(JSONArray jsonArray, int requestCode);
    void onGetResponse(JSONObject jsonObject, int requestCode);
    void onGetResponse(String response, int requestCode);
    void onFail();
    void onFail(String message);
}
