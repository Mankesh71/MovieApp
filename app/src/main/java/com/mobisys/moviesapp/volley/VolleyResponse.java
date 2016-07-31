package com.mobisys.moviesapp.volley;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Mankesh on 7/28/2016.
 */
public interface VolleyResponse {

    void getResponse(String string);

    void getResponse(JSONObject jsonObject);

    void getResponse(JSONObject jsonObject, String tag) throws JSONException;

    void error(String msg, String tag);


}
