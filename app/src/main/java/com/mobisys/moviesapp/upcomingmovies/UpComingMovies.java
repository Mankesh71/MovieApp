package com.mobisys.moviesapp.upcomingmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisys.moviesapp.R;
import com.mobisys.moviesapp.activities.BaseActivity;
import com.mobisys.moviesapp.activities.DeveloperInfo;
import com.mobisys.moviesapp.moviesdetails.MoviesDetails;
import com.mobisys.moviesapp.utility.ApplicationConstants;
import com.mobisys.moviesapp.utility.CallbackInterface;
import com.mobisys.moviesapp.utility.OnClickFromAdapter;
import com.mobisys.moviesapp.utility.ProgressDialog;

import java.util.ArrayList;

/**
 * Created by Mankesh Mishra on 7/28/2016.
 */
public class UpComingMovies extends BaseActivity implements View.OnClickListener,CallbackInterface,OnClickFromAdapter{
    private RecyclerView mUpComingMoviesRecyclerView;
    private TextView mTextViewHeaderName,textViewNoDataFound;
    private Context mContext = this;
    private AdapterForUpComingMovies mAdapterForUpComingMovies;
    private ImageView mImageViewInfo;
    private ArrayList<UpComingMoviesModel> mMoviesArraysList;
    private static final String TAG = UpComingMovies.class.getName();
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_upcoming_movies);
        printLog(TAG,"onCreate invoked!");

        findIds();

    }

    /*Find ids for all views used*/
    private void findIds() {
        mUpComingMoviesRecyclerView = (RecyclerView) findViewById(R.id.mUpComingMoviesRecyclerView);
        textViewNoDataFound = (TextView) findViewById(R.id.textViewNoDataFound);
        mTextViewHeaderName = (TextView) findViewById(R.id.tvHeaderTitle);
        mImageViewInfo = (ImageView) findViewById(R.id.imageViewInfo);
        mTextViewHeaderName.setText(mContext.getResources().getString(R.string.upcoming_movies_name));

        // set click listener to info icon
        mImageViewInfo.setOnClickListener(this);
        textViewNoDataFound.setOnClickListener(this);

        mMoviesArraysList = new ArrayList<>();
        mAdapterForUpComingMovies = new AdapterForUpComingMovies(mContext,mMoviesArraysList,this);
        mUpComingMoviesRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mUpComingMoviesRecyclerView.setLayoutManager(mLayoutManager);
        mUpComingMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mUpComingMoviesRecyclerView.setAdapter(mAdapterForUpComingMovies);

        initializeManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        printLog(TAG,"onResume invoked!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printLog(TAG,"onPause invoked!");
    }

    /*TO initialize manager for upcoming movies*/
    private void initializeManager(){
        printLog(TAG,"initializeManager");
        ProgressDialog.showDialog(mContext);
        if (isNetworkAvailable(mContext)) {
            UpComingMovieManger.getInstance().initializeManager(this, mContext);
            UpComingMovieManger.getInstance().getmMovieList().clear();
            UpComingMovieManger.getInstance().getMoviesList(ApplicationConstants.UPCOMING, ApplicationConstants.UPCOMINGMOVIES);
        }else{
            showToast(mContext,ApplicationConstants.NETWORK_ERROR);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mImageViewInfo){
            startActivity(new Intent(UpComingMovies.this, DeveloperInfo.class));
        }else if (view == textViewNoDataFound){
            initializeManager();
        }
    }

    @Override
    public void successCallBack(ApplicationConstants.Status ObjectStatus, String tag) {
        printLog(TAG,"successCallBack "+tag);
        if (ObjectStatus == ApplicationConstants.Status.SUCCESS){
            ProgressDialog.dismissDialog(mContext);
            if (null!=UpComingMovieManger.getInstance().getmMovieList() && UpComingMovieManger.getInstance().getmMovieList().size()>0) {
                textViewNoDataFound.setVisibility(View.GONE);
                mMoviesArraysList.addAll(UpComingMovieManger.getInstance().getmMovieList());
                if (mAdapterForUpComingMovies!=null){
                    mAdapterForUpComingMovies.notifyAdapter(mMoviesArraysList);
                }
            }else{
                textViewNoDataFound.setVisibility(View.VISIBLE);
                mUpComingMoviesRecyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        printLog(TAG,"errorCallBack "+tag);
        ProgressDialog.dismissDialog(mContext);
        textViewNoDataFound.setVisibility(View.VISIBLE);
        mUpComingMoviesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        printLog(TAG, "onBackPressed invoked !");
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        showToast(mContext,getResources().getString(R.string.press_again));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
        printLog(TAG, "onBackPressed exit !");
    }

    /*Callback from recyclerview item click*/
    @Override
    public void onClick(int pos) {
        Intent mIntent = new Intent(mContext, MoviesDetails.class);
        Bundle mBundle = new Bundle();
        mBundle.putInt(ApplicationConstants.MOVIEID, mMoviesArraysList.get(pos).getMovieId());
        mBundle.putString(ApplicationConstants.MOVIETITLE, mMoviesArraysList.get(pos).getStrTitle());
        mBundle.putDouble(ApplicationConstants.MOVIERATING, mMoviesArraysList.get(pos).getVoteAverage());
        mIntent.putExtras(mBundle);
        mContext.startActivity(mIntent);
    }
}
