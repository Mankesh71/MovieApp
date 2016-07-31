package com.mobisys.moviesapp.upcomingmovies;

import android.content.Context;

import com.android.volley.Request;
import com.mobisys.moviesapp.moviesdetails.MovieDetailsModel;
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
 * Created by Mankesh Mishra on 7/29/2016.
 */
public class UpComingMovieManger implements VolleyResponse {

    public static VolleyRequest mVolleyRequest;
    private CallbackInterface mCallbackInterface;
    private Context mContext;
    private ArrayList<UpComingMoviesModel> mMovieList = new ArrayList<>();
    private MovieDetailsModel movieDetailsModel;

    public MovieDetailsModel getMovieDetailsModel() {
        return movieDetailsModel;
    }

    public void setMovieDetailsModel(MovieDetailsModel movieDetailsModel, String tag) {
        this.movieDetailsModel = movieDetailsModel;
        mCallbackInterface.successCallBack(ApplicationConstants.Status.SUCCESS, tag);
    }

    public ArrayList<UpComingMoviesModel> getmMovieList() {
        return mMovieList;
    }

    public void setmMovieList(ArrayList<UpComingMoviesModel> mMovieList, String tag) {
        this.mMovieList = mMovieList;
        mCallbackInterface.successCallBack(ApplicationConstants.Status.SUCCESS, tag);
    }

    private static UpComingMovieManger ourInstance = new UpComingMovieManger();

    public static UpComingMovieManger getInstance() {
        return ourInstance;
    }

    public void initializeManager(CallbackInterface mCallbackInterface, Context mContext) {
        this.mContext = mContext;
        this.mCallbackInterface = mCallbackInterface;
        mVolleyRequest.setContext(mContext);
    }

    public void getMoviesList(String page,String tag) {
        mVolleyRequest.volleyJSONObjectRequest(Request.Method.GET, ApplicationConstants.BASEURL + page + ApplicationConstants.APIKEY, null, getHeader(), "", tag);
    }


    private UpComingMovieManger() {
        mVolleyRequest = new VolleyRequest(this);
    }

    @Override
    public void getResponse(String string) {

    }

    @Override
    public void getResponse(JSONObject jsonObject) {

    }

    @Override
    public void getResponse(JSONObject jsonObject, String tag) throws JSONException {
        switch (tag) {
            case ApplicationConstants.UPCOMINGMOVIES:
                parseData(jsonObject, tag);
                break;
            case ApplicationConstants.MOVIEDETAIL:
                parseData(jsonObject,tag);
                break;
        }

    }

    @Override
    public void error(String msg, String tag) {

        switch (tag) {
            case ApplicationConstants.UPCOMINGMOVIES:
                mCallbackInterface.errorCallBack(msg, tag);
                break;
            case ApplicationConstants.MOVIEDETAIL:
                mCallbackInterface.errorCallBack(msg, tag);
                break;
        }

    }

    void parseData(JSONObject jsonObject, String tag) throws JSONException {

        if (ApplicationConstants.UPCOMINGMOVIES.equals(tag)) {
            if (jsonObject != null) {
                if (jsonObject.has(ApplicationConstants.RESULTS)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(ApplicationConstants.RESULTS);
                    mMovieList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        UpComingMoviesModel moviesModel = new UpComingMoviesModel();
                        JSONObject jsonObjectForMoviesItem = jsonArray.getJSONObject(i);
                        if (jsonObjectForMoviesItem.has(ApplicationConstants.MOVIEPOSTERPATH)) {
                            moviesModel.setStrPosterPath(ApplicationConstants.getValueOfJsonObject(jsonObjectForMoviesItem, ApplicationConstants.MOVIEPOSTERPATH));
                            moviesModel.setAdult(Boolean.parseBoolean(ApplicationConstants.getValueOfJsonObject(jsonObjectForMoviesItem, ApplicationConstants.ADULT)));
                            moviesModel.setMovieId(Integer.parseInt(ApplicationConstants.getValueOfJsonObject(jsonObjectForMoviesItem, ApplicationConstants.MOVIEID)));
                            moviesModel.setStrDescription(ApplicationConstants.getValueOfJsonObject(jsonObjectForMoviesItem, ApplicationConstants.MOVIEDESCRIPTION));
                            moviesModel.setStrReleaseDate(ApplicationConstants.getValueOfJsonObject(jsonObjectForMoviesItem, ApplicationConstants.MOVIERELEASEDATE));
                            moviesModel.setStrTitle(ApplicationConstants.getValueOfJsonObject(jsonObjectForMoviesItem, ApplicationConstants.MOVIETITLE));
                            moviesModel.setVoteAverage(ApplicationConstants.getValueOfJsonObject(ApplicationConstants.MOVIERATING,jsonObjectForMoviesItem));
                        }
                        mMovieList.add(moviesModel);
                    }
                }
            }

            setmMovieList(mMovieList, tag);
        }else if (ApplicationConstants.MOVIEDETAIL.equals(tag)){
            if (jsonObject != null) {
                MovieDetailsModel mMovieDetailsModel = new MovieDetailsModel();
                mMovieDetailsModel.setStrMovieTitle(ApplicationConstants.getValueOfJsonObject(jsonObject,ApplicationConstants.MOVIEORIGINALTITLE));
                mMovieDetailsModel.setStrMovieLanguage(ApplicationConstants.getValueOfJsonObject(jsonObject,ApplicationConstants.MOVIELANGUAGE));
                mMovieDetailsModel.setStrPosterPath(ApplicationConstants.getValueOfJsonObject(jsonObject,ApplicationConstants.MOVIEPOSTERPATH));
                mMovieDetailsModel.setStrBackDropPath(ApplicationConstants.getValueOfJsonObject(jsonObject,ApplicationConstants.MOVIEBACKDROP));
                mMovieDetailsModel.setStrMovieOverView(ApplicationConstants.getValueOfJsonObject(jsonObject,ApplicationConstants.MOVIEDESCRIPTION));
                setMovieDetailsModel(mMovieDetailsModel,tag);
            }
        }
    }

    public static HashMap<String, String> getHeader() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Content-Type", "application/json; charset=utf-8");

        return hashMap;
    }
}
