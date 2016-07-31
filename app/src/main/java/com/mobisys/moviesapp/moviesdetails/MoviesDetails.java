package com.mobisys.moviesapp.moviesdetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mobisys.moviesapp.R;
import com.mobisys.moviesapp.activities.BaseActivity;
import com.mobisys.moviesapp.activities.DeveloperInfo;
import com.mobisys.moviesapp.upcomingmovies.UpComingMovieManger;
import com.mobisys.moviesapp.utility.ApplicationConstants;
import com.mobisys.moviesapp.utility.CallbackInterface;
import com.mobisys.moviesapp.utility.ProgressDialog;

import java.util.ArrayList;

/**
 * Created by Mankesh Mishra on 7/28/2016.
 */
public class MoviesDetails extends BaseActivity implements View.OnClickListener, CallbackInterface {
    private static final String TAG = MoviesDetails.class.getName();
    private ViewFlipper mViewFlipper;
    private TextView mTextViewHeaderName, mTvMovieOverView, mTvMovieTitle, mTvMovieLanguage;
    private LinearLayout llForDotsImages;
    private ImageView mImageViewInfo;
    private RatingBar mRatingBar;
    private Context mContext = this;
    private ArrayList<ImageView> imageViewArrayList;
    private ArrayList<MovieDetailsModel> mMoviePosterList;
    private double movieRating=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLog(TAG,"onCreate invoked!");
        setContentView(R.layout.layout_movie_details);

        findIds();

    }

    /*findIds for all used view*/
    private void findIds() {

        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        mImageViewInfo = (ImageView) findViewById(R.id.imageViewInfo);
        mTextViewHeaderName = (TextView) findViewById(R.id.tvHeaderTitle);

        mTvMovieOverView = (TextView) findViewById(R.id.tvMovieOverView);
        mTvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        mTvMovieLanguage = (TextView) findViewById(R.id.tvMovieLanguage);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        llForDotsImages = (LinearLayout) findViewById(R.id.llForDotsImages);

        mImageViewInfo.setOnClickListener(this);

        Bundle mBundle = getIntent().getExtras();
        ;
        int movieId = mBundle.getInt(ApplicationConstants.MOVIEID);
        String movieName = mBundle.getString(ApplicationConstants.MOVIETITLE);
        movieRating = mBundle.getDouble(ApplicationConstants.MOVIERATING);
        mTextViewHeaderName.setText(movieName);

        imageViewArrayList = new ArrayList<>();
        mMoviePosterList = new ArrayList<>();

        initializeManager(movieId);
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

    /*TO initialize manager for movie detail*/
    private void initializeManager(int movieId) {
        printLog(TAG,"onCreate invoked!");
        if (isNetworkAvailable(mContext)) {
            ProgressDialog.showDialog(mContext);
            MoviesPosterManager.getInstance().initializeManagerPoster(this, mContext);
            MoviesPosterManager.getInstance().getmMovieList().clear();
            MoviesPosterManager.getInstance().getMoviesPosterList(movieId, ApplicationConstants.MOVIEPOSTER);
            UpComingMovieManger.getInstance().initializeManager(this, mContext);
            UpComingMovieManger.getInstance().getMoviesList(String.valueOf(movieId), ApplicationConstants.MOVIEDETAIL);
        } else {
            showToast(mContext, ApplicationConstants.NETWORK_ERROR);
        }
    }


    /*Load data to flipper*/
    private void flipperInitialize(int listSize) {

        if (movieRating>0.0){
            mRatingBar.setRating((float) movieRating);
            mRatingBar.setClickable(false);
        }

        for (int i = 0; i <listSize; i++) {
            ImageView imageView = new ImageView(MoviesDetails.this);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.circle_unselected));
            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLayoutParams.setMargins(0, 0, 2, 0);
            imageView.setLayoutParams(mLayoutParams);
            imageViewArrayList.add(imageView);
            llForDotsImages.addView(imageView);
        }

        for (int i = 0; i < listSize; i++) {

            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

                Glide.with(mContext)
                        .load(ApplicationConstants.IMAGEAPI + mMoviePosterList.get(i).getStrPosterPath())
                        .asBitmap()
                        .override(250, 250)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(false)
                        .listener(new RequestListener<String, Bitmap>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(iv);

            mViewFlipper.addView(iv);
            mViewFlipper.animate();
        }

        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(2000);
        mViewFlipper.startFlipping();
        mViewFlipper.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                for (int i = 0; i < mMoviePosterList.size(); i++) {

                    imageViewArrayList.get(i).setImageDrawable(getResources().getDrawable(R.drawable.circle_unselected));

                }
                imageViewArrayList.get(mViewFlipper.getDisplayedChild()).setImageDrawable(getResources().getDrawable(R.drawable.circle_selected));
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == mImageViewInfo) {
            startActivity(new Intent(MoviesDetails.this, DeveloperInfo.class));
        }
    }

    @Override
    public void successCallBack(ApplicationConstants.Status ObjectStatus, String tag) {
        printLog(TAG,"successCallBack: "+tag);
        if (ApplicationConstants.MOVIEPOSTER.equals(tag)) {
            mMoviePosterList.addAll(MoviesPosterManager.getInstance().getmMovieList());
            if (mMoviePosterList.size()>10) {
                flipperInitialize(10);
            }else{
                flipperInitialize(mMoviePosterList.size());
            }
        } else if (ApplicationConstants.MOVIEDETAIL.equals(tag)) {
            if (ObjectStatus == ApplicationConstants.Status.SUCCESS) {
                if (UpComingMovieManger.getInstance().getMovieDetailsModel() != null) {
                    mTvMovieTitle.setText(UpComingMovieManger.getInstance().getMovieDetailsModel().getStrMovieTitle());
                    mTvMovieOverView.setText(UpComingMovieManger.getInstance().getMovieDetailsModel().getStrMovieOverView());
                    mTvMovieLanguage.setText("(" + UpComingMovieManger.getInstance().getMovieDetailsModel().getStrMovieLanguage() + ")");
                }
            }
        }
        ProgressDialog.dismissDialog(mContext);
    }

    @Override
    public void errorCallBack(String msg, String tag) {
        printLog(TAG,"errorCallBack: "+tag);
        ProgressDialog.dismissDialog(mContext);
    }
}
