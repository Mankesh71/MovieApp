package com.mobisys.moviesapp.moviesdetails;

/**
 * Created by Mankesh Mishra on 7/31/2016.
 */
public class MovieDetailsModel {
    String strMovieTitle = "",strMovieOverView="",strMovieLanguage="",strPosterPath="",strBackDropPath="";

    public String getStrMovieTitle() {
        return strMovieTitle;
    }

    public void setStrMovieTitle(String strMovieTitle) {
        this.strMovieTitle = strMovieTitle;
    }

    public String getStrMovieOverView() {
        return strMovieOverView;
    }

    public void setStrMovieOverView(String strMovieOverView) {
        this.strMovieOverView = strMovieOverView;
    }

    public String getStrMovieLanguage() {
        return strMovieLanguage;
    }

    public void setStrMovieLanguage(String strMovieLanguage) {
        this.strMovieLanguage = strMovieLanguage;
    }

    public String getStrPosterPath() {
        return strPosterPath;
    }

    public void setStrPosterPath(String strPosterPath) {
        this.strPosterPath = strPosterPath;
    }

    public String getStrBackDropPath() {
        return strBackDropPath;
    }

    public void setStrBackDropPath(String strBackDropPath) {
        this.strBackDropPath = strBackDropPath;
    }
}
