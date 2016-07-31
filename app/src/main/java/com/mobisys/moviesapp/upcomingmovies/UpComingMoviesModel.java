package com.mobisys.moviesapp.upcomingmovies;

/**
 * Created by Mankesh Mishra on 7/29/2016.
 */
public class UpComingMoviesModel {

    int movieId = 0;
    String strTitle="",strDescription="",strReleaseDate="",strPosterPath="";
    boolean isAdult = false;
    double voteAverage=0.0;

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrReleaseDate() {
        return strReleaseDate;
    }

    public void setStrReleaseDate(String strReleaseDate) {
        this.strReleaseDate = strReleaseDate;
    }

    public String getStrPosterPath() {
        return strPosterPath;
    }

    public void setStrPosterPath(String strPosterPath) {
        this.strPosterPath = strPosterPath;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}
