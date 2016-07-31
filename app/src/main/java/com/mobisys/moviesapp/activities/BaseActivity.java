package com.mobisys.moviesapp.activities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Mankesh Mishra on 7/28/2016.
 */
public class BaseActivity extends AppCompatActivity {

	private Context context=this;
	private String TAG = "BaseActivity";
	private static final boolean DEBUG_ENABLE = true;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		printLog(TAG,"onCreate invoked!");
	}

	@Override
	protected void onResume() {
		super.onResume();
		printLog(TAG,"onResume invoked!");
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void printLog(String tag, String message)
	{
		if(DEBUG_ENABLE)
		{
			Log.d(tag, message);
		}
	}

	public void printError(String tag, String message, Exception e)
	{
		if(DEBUG_ENABLE)
		{
			Log.e(tag, message, e);
		}
	}

	/**********
	 * Method to hide keyboard.
	 **********/
	public static void hideKeyboard(Context context, View view) {
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**********
	 * Method to check data connection availability (if network is available then it will return true otherwise false).
	 **********/
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
			return true;
		}
		return false;
	}

	/**********
	 * Method to show toast message by passing message on it.
	 *********/
	public static void showToast(Context context, String message) {
		Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
	}

}

