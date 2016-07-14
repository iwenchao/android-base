package com.wenchaos.mxnews.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenchaos.mxnews.R;
import com.wenchaos.mxnews.base.mvp.BaseModel;
import com.wenchaos.mxnews.base.mvp.BasePresent;
import com.wenchaos.mxnews.base.util.TUtil;
import com.wenchaos.mxnews.data.BusProvider;
import com.wenchaos.mxnews.view.dialog.ProgressAnimateDialog;
import com.wenchaos.mxnews.view.dialog.ProgressAnimateDialog_;
import com.wenchaos.mxnews.view.layout.SwipeBackLayout;

import butterknife.ButterKnife;

/**
 * Created by Administrator
 * on 2016/6/27 0027.
 */
public abstract class BaseActivity<T extends BasePresent,E extends BaseModel> extends FragmentActivity {
    public T mPresent;
    public E mModel;
    public Context mContext;

    protected final static String ANIMATED_LOADING_DIALOG = "loadingDialog";

    private SwipeBackLayout swipeBackLayout;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        this.setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        mPresent = TUtil.getT(this,0);
        mModel = TUtil.getT(this,1);
        BusProvider.getBus().register(this);
        this.initView();
        this.initPresent();

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if(layoutResID == R.layout.activity_main ){
            super.setContentView(layoutResID);
        }else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.window_background));
            swipeBackLayout.addView(view);

        }
    }
    private View getContainer(){
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        container.addView(swipeBackLayout);
        return  container;
    }
    public void setActionBar(String title){
        TextView tv = (TextView) findViewById(R.id.actionbar_title);
        if(tv != null){
            tv.setText(title);
        }
    }
    public void setActionBarLeftCallBack(@NonNull String leftTxt, @Nullable Drawable drawable, @NonNull View.OnClickListener listener){
        TextView txt = (TextView) findViewById(R.id.action_bar_left_view);
        View leftLy = findViewById(R.id.action_bar_left_ly);

        if(null != txt){
            if (TextUtils.isEmpty(leftTxt) && null == drawable){
                txt.setVisibility(View.GONE);
            }else {
                txt.setVisibility(View.VISIBLE);
            }
            txt.setText(leftTxt);
            if (leftLy != null) {
                leftLy.setOnClickListener(listener);
            } else {
                txt.setOnClickListener(listener);
            }
            txt.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }

    }
    //设置actionbar右侧回调
    public void setActionBarRightCallBack(@Nullable String rightTxt, @Nullable Drawable drawable, @Nullable View.OnClickListener listener) {
        TextView txt = (TextView) findViewById(R.id.action_bar_right_view);
        if (null != txt) {
            if (TextUtils.isEmpty(rightTxt) && null == drawable) {
                txt.setVisibility(View.GONE);
            } else {
                txt.setVisibility(View.VISIBLE);
            }
            txt.setText(rightTxt);
            txt.setOnClickListener(listener);
            txt.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setActionBarRightDrawable(Drawable drawable) {
        TextView txt = (TextView) findViewById(R.id.action_bar_right_view);
        txt.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setActionBarRightTitle(String rightTxt) {
        TextView txt = (TextView) findViewById(R.id.action_bar_right_view);
        if (null != txt) {
            txt.setText(rightTxt);
        }
    }

    public void setActionBarLeftTitle(String leftTxt) {
        TextView txt = (TextView) findViewById(R.id.action_bar_left_view);
        if (null != txt) {
            txt.setText(leftTxt);
        }
    }

    public void showAbTipView(){
        ImageView tipView = (ImageView) findViewById(R.id.ab_round_tip_view);
        if (null != tipView) {
            tipView.setVisibility(View.VISIBLE);
        }
    }

    public void hideAbTipView(){
        ImageView tipView = (ImageView) findViewById(R.id.ab_round_tip_view);
        if (null != tipView) {
            tipView.setVisibility(View.GONE);
        }
    }

    protected void removePrevDialog() {
        removePrevDialog(null);
    }

    protected void removePrevDialog(String tag) {
        if (isFinishing()) {
            return;
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(TextUtils.isEmpty(tag) ? ANIMATED_LOADING_DIALOG : tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }


    protected void showBaseLoadingProgressDialog() {
        showLoadingProgressDialog(null);
    }

    protected void showLoadingProgressDialog(@Nullable String message) {
        showLoadingProgressDialog(message, ProgressAnimateDialog.ANIMATE_TYPE_COMMON, true);
    }

    protected void showLoadingProgressDialog(@Nullable String message, int type, boolean cancelable) {
        if (isFinishing()) {
            return;
        }
        removePrevDialog(ANIMATED_LOADING_DIALOG);
        ProgressAnimateDialog progressAnimateDialog = ProgressAnimateDialog_.builder()
                .type(type)
                .message(message)
                .cancelable(cancelable)
                .build();
        progressAnimateDialog.show(getFragmentManager(), ANIMATED_LOADING_DIALOG);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresent != null)
            mPresent.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initPresent();
}
