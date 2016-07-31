package com.mobisys.moviesapp.utility;


/**
 * Created by Mankesh Mishra on 7/29/2016.
 */
public interface CallbackInterface {

    void successCallBack(ApplicationConstants.Status ObjectStatus, String tag);

    void errorCallBack(String msg, String tag);

}
