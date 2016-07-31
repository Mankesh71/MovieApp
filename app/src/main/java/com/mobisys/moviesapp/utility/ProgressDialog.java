package com.mobisys.moviesapp.utility;

import android.content.Context;

import com.mobisys.moviesapp.R;


/**
 * Created by Mankesh Mishra on 7/29/2016.
 */
public class ProgressDialog {

    public static android.app.ProgressDialog mDialog;

    public static void showDialog(Context context) {


        try {
            mDialog= android.app.ProgressDialog.show(context,null,null);
            mDialog.setCancelable(false);
            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            mDialog.setContentView(R.layout.progressloader);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public static void dismissDialog(Context context) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
