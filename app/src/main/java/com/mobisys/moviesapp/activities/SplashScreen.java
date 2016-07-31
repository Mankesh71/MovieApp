package com.mobisys.moviesapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.mobisys.moviesapp.R;
import com.mobisys.moviesapp.upcomingmovies.UpComingMovies;

/**
 * Created by Mankesh Mishra on 7/28/2016.
 */
public class SplashScreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, UpComingMovies.class);
                startActivity(i);

                // finish this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
