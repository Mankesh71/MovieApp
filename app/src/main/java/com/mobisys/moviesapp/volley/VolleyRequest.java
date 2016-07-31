package com.mobisys.moviesapp.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mankesh on 7/28/2016.
 */
public class VolleyRequest {

    private VolleyResponse mVolleyResponse;
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    private String TAG = "VolleyRequest";
    public static String TAG_REQUEST = "RequestCancel";

    public VolleyRequest(Context context, VolleyResponse volleyResponse) {
        this.mContext = context;
        this.mVolleyResponse = volleyResponse;
    }
    public VolleyRequest(VolleyResponse volleyResponse) {
        this.mVolleyResponse = volleyResponse;
    }

    public void volleyJSONObjectRequest(int method, String url, JSONObject param, final HashMap<String, String> header, String msg, final String tag) {
        JSONObject jsonObject = null;
        Log.d(TAG, "volleyJSONObjectRequest " + "Called");
        if (param != null) {
            jsonObject = param;
        }

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    sendCallBack(jsonObject, tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "volleyJSONObjectRequest " + "Success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                sendError(volleyError, tag);
                Log.d(TAG, "volleyJSONObjectRequest " + "Fail");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjectRequest.setTag(TAG_REQUEST);
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void sendError(VolleyError volleyError, String tag)
    {
        String json = null;
        NetworkResponse response = volleyError.networkResponse;

        if (response != null && response.data != null) {
            json = new String(response.data);
            json = trimMessage(json, "message");
        }
        mVolleyResponse.error(json,tag);


    }
    private void sendCallBack(String string) {
        mVolleyResponse.getResponse(string);
    }

    private void sendCallBack(JSONObject jsonObject) {
        mVolleyResponse.getResponse(jsonObject);
    }

    private void sendCallBack(JSONObject jsonObject, String tag) throws JSONException {
        mVolleyResponse.getResponse(jsonObject, tag);
    }


    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


}
