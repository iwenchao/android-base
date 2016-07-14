package com.wenchaos.mxnews.base;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.wenchaos.mxnews.api.ErrorBody;


/**
 * Created by wangtao on 2016-06-24.
 */
public interface BaseView {
    void showBaseLoadingProgressDialog();
    void showLoadingProgressDialog(@Nullable String message);
    void showLoadingProgressDialog(@Nullable String message, int type, boolean cancelable);
    void removePrevDialog();
    void removePrevDialog(String tag);
    void showErrorToast(ErrorBody body);
    void showToast(String message);
    Resources getRes();
    boolean isActive();
}
