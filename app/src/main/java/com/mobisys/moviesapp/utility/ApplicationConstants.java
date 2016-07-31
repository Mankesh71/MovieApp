package com.mobisys.moviesapp.utility;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mankesh Mishra on 7/29/2016.
 */
public class ApplicationConstants {

    public static final String NETWORK_ERROR="Network is not available.Please establish network connection.";
    public static final String ERROR="Oops! Something went wrong. Please try again.";
    public static final String UPCOMINGMOVIES="UpComingMovies";
    public static final String UPCOMING="upcoming";
    public static final String MOVIEDETAIL="MovieDetail";
    public static final String MOVIEPOSTER="MoviePoster";
    public static final String BASEURL="https://api.themoviedb.org/3/movie/";
    public static final String APIKEY="?api_key=b7cd3340a794e5a2f35e3abb820b497f";
    public static final String IMAGEAPI="http://image.tmdb.org/t/p/w500";
    public static final String IMAGEARRAYLISTAPI="https://api.themoviedb.org/3/movie/";
    public static final String IMAGEAFTERMOVIEID="/images?api_key=b7cd3340a794e5a2f35e3abb820b497f";

    /*Constants Used in to parse data*/
    public static final String RESULTS="results";
    public static final String MOVIEID="id";
    public static final String POSTERS="posters";
    public static final String MOVIEPOSTERPATH="poster_path";
    public static final String MOVIETITLE="title";
    public static final String MOVIERATING="vote_average";
    public static final String MOVIEDESCRIPTION="overview";
    public static final String MOVIERELEASEDATE="release_date";
    public static final String ADULT="adult";

    /*Parsing data for Movie detail*/
    public static final String MOVIEORIGINALTITLE="original_title";
    public static final String MOVIELANGUAGE="original_language";
    public static final String MOVIEBACKDROP="backdrop_path";
    public static final String MOVIEFILEPATH="file_path";

    public static enum Status {
        SUCCESS,
        INVALID_LOGIN,
        UNKNOWN_ERROR,
        NETWORK_ERROR
    }

    /**********
     * Method to get string value from a JSONObject( with null check) by passing JsonObject and key.
     **********/
    public static String getValueOfJsonObject(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.has(key)) {
                if ("null".equals(jsonObject.getString(key))) {
                    return "";
                } else if (null == jsonObject.getString(key)) {
                    return "";
                } else {
                    return jsonObject.getString(key);
                }
            } else {
                return "";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static double getValueOfJsonObject( String key,JSONObject jsonObject) {
        try {
            if (jsonObject.has(key)) {
                if ("null".equals(jsonObject.getString(key))) {
                    return 0.0;
                } else if (null == jsonObject.getString(key)) {
                    return 0.0;
                } else {
                    return jsonObject.getDouble(key);
                }
            } else {
                return 0.0;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

}
