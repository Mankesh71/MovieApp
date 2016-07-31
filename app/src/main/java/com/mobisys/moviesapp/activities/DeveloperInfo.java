package com.mobisys.moviesapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisys.moviesapp.R;

/**
 * Created by Mankesh Mishra on 7/29/2016.
 */
public class DeveloperInfo extends BaseActivity {
    private TextView mTextViewHeaderName;
    private Context mContext = this;
    private ImageView mImageViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_developer_info);

        findIds();

    }

    /*Find ids for all views used*/
    private void findIds() {
        mTextViewHeaderName = (TextView) findViewById(R.id.tvHeaderTitle);
        mImageViewInfo = (ImageView) findViewById(R.id.imageViewInfo);
        mImageViewInfo.setVisibility(View.GONE);
        mTextViewHeaderName.setText(mContext.getResources().getString(R.string.developer_info));
    }
}
