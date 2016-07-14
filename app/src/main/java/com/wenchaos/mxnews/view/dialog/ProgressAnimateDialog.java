package com.wenchaos.mxnews.view.dialog;


import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;

import com.wenchaos.mxnews.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;


@EFragment(R.layout.dialog_progress_animate)
public class ProgressAnimateDialog extends DialogFragment {

    public static final int ANIMATE_TYPE_PAY = 0;
    public static final int ANIMATE_TYPE_CART = 1;
    public static final int ANIMATE_TYPE_COMMON = -1;
    private static final int REFRESH_TIME_TXT = 2;

    @FragmentArg
    int type;
    @FragmentArg
    String message;
    @FragmentArg
    boolean cancelable = true;
    private int mDuration;
    private CountDownHandler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.LoadingProgressDialogFragment);
        mHandler = new CountDownHandler();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().setCancelable(true);
    }

    @AfterViews
    void afterView() {
    }

    private class CountDownHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == REFRESH_TIME_TXT) {
                mDuration -= 1;
                if(mDuration < 0){
                    ProgressAnimateDialog.this.dismissAllowingStateLoss();
                }else{
                    mHandler.sendEmptyMessageDelayed(REFRESH_TIME_TXT, 1000);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
