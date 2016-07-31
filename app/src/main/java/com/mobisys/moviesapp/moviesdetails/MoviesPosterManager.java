package com.mobisys.moviesapp.moviesdetails;

import android.content.Context;

import com.android.volley.Request;
import com.mobisys.moviesapp.utility.ApplicationConstants;
import com.mobisys.moviesapp.utility.CallbackInterface;
import com.mobisys.moviesapp.volley.VolleyRequest;
import com.mobisys.moviesapp.volley.VolleyResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mankesh Mishra on 7/31/2016.
 */
public class MoviesPosterManager implements VolleyResponse {
    public static VolleyRequest mVolleyRequest;
    private CallbackInterface mCallbackInterface;
    private Context mContext;
    private ArrayList<MovieDetailsModel> mMovieList = new ArrayList<>();

    public ArrayList<MovieDetailsModel> getmMovieList() {
        return mMovieList;
    }

    public void setmMovieList(ArrayList<MovieDetailsModel> mMovieList, String tag) {
        this.mMovieList = mMovieList;
        mCallbackInterface.successCallBack(ApplicationConstants.Status.SUCCESS, tag);
    }

    private static MoviesPosterManager ourInstance = new MoviesPosterManager();

    public static MoviesPosterManager getInstance() {
        return ourInstance;
    }

    public void initializeManagerPoster(CallbackInterface mCallbackInterface, Context mContext) {
        this.mCallbackInterface = mCallbackInterface;
        this.mContext = mContext;
        mVolleyRequest.setContext(mContext);
    }

    private MoviesPosterManager() {
        mVolleyRequest = new VolleyRequest(this);
    }

    public void getMoviesPosterList(int movieId, String tag) {
        mVolleyRequest.volleyJSONObjectRequest(Request.Method.GET, ApplicationConstants.IMAGEARRAYLISTAPI + movieId + ApplicationConstants.IMAGEAFTERMOVIEID, null, getHeader(), "", tag);
    }

    @Override
    public void getResponse(String string) {

    }

    @Override
    public void getResponse(JSONObject jsonObject) {

    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {
        parseData(jsonObject, tag);
    }

    private void parseData(JSONObject jsonObject, String tag) {
        if (jsonObject != null) {
            if (jsonObject.has(ApplicationConstants.POSTERS)) {
                try {
                    JSONArray mJsonArray = jsonObject.getJSONArray(ApplicationConstants.POSTERS);
                    mMovieList.clear();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        JSONObject mJsonObjectForPosters = mJsonArray.getJSONObject(i);
                        if (mJsonObjectForPosters.has(ApplicationConstants.MOVIEFILEPATH)) {
                            MovieDetailsModel movieDetailsModel = new MovieDetailsModel();
                            movieDetailsModel.setStrMovieOverView("");
                            movieDetailsModel.setStrBackDropPath("");
                            movieDetailsModel.setStrMovieLanguage("");
                            movieDetailsModel.setStrMovieTitle("");
                            movieDetailsModel.setStrPosterPath(ApplicationConstants.getValueOfJsonObject(mJsonObjectForPosters, ApplicationConstants.MOVIEFILEPATH));
                            mMovieList.add(movieDetailsModel);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            setmMovieList(mMovieList, tag);
        }
    }

    @Override
    public void error(String msg, String tag) {
        mCallbackInterface.errorCallBack(msg, tag);
    }

    public static HashMap<String, String> getHeader() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Content-Type", "application/json; charset=utf-8");

        return hashMap;
    }
}
