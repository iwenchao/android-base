/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wenchaos.mxnews.api.repository.source;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wenchaos.mxnews.base.App;
import com.wenchaos.mxnews.base.entity.UserBean;
import com.wenchaos.mxnews.data.GsonProvider;

import java.io.UnsupportedEncodingException;


/**
 * Storing user cache data.
 */
public class UserDataPrefsSource {

    private static final String USER_PREF = "USER_PREF";
    private static final String KEY_CACHED_USER = "KEY_CACHED_USER";
    private static final String KEY_CACHED_NUM_CONFIG = "KEY_CACHED_NUM_CONFIG";
    private static final String KEY_CACHED_QR_JSON = "KEY_CACHED_QR_JSON";
    private static final String KEY_CACHED_FEEDSETTING = "KEY_CACHED_FEEDSETTING";
    private static final String KEY_AB_TAG = "KEY_AB_TAG";
    private static volatile UserDataPrefsSource singleton;
    private final SharedPreferences prefs;
    private UserBean mUserBean;

    public static UserDataPrefsSource getInstance() {
        if (singleton == null) {
            synchronized (UserDataPrefsSource.class) {
                singleton = new UserDataPrefsSource(App.getGlobalApp());
            }
        }
        return singleton;
    }

    private UserDataPrefsSource(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(USER_PREF, Context
                .MODE_PRIVATE);
    }

    public void setCachedUser(UserBean userBean) {
        SharedPreferences.Editor editor = prefs.edit();
        String cacheStr = GsonProvider.getGson().toJson(userBean);
        editor.putString(KEY_CACHED_USER, cacheStr);
        editor.apply();
    }

    public UserBean getCachedUser() {
        String cachedStr = prefs.getString(KEY_CACHED_USER, "");
        if (!TextUtils.isEmpty(cachedStr)) {
            mUserBean = GsonProvider.getGson().fromJson(cachedStr, UserBean.class);
        }
        return mUserBean;
    }

    public void clearCachedUser() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CACHED_USER, "");
        editor.apply();
    }

    public void clearCachedlastNumConfig() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CACHED_NUM_CONFIG, "");
        editor.apply();
    }

    public void clearCachedQrJson() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CACHED_QR_JSON, "");
        editor.apply();
    }

    public void clearFeedSetting() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CACHED_FEEDSETTING, "");
        editor.apply();
    }


    /**
     * 获取当前用户手机
     *
     * @return User对象
     */
    public String getUserMobile() {
        return mUserBean == null ? "" : mUserBean.getMobile();
    }

    /**
     * 获取当前用户token
     *
     * @return User对象
     */
    public String getUserToken() {
        try {
            return mUserBean == null ? "" : mUserBean.getToken();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 删除当前用户
     */
    public void deleteCurrentUser() {
        clearCachedUser();

    }

    public String getSessionId() {
        return mUserBean == null ? "" : mUserBean.getSessionId();
    }

    public boolean checkVerified() {
        if (mUserBean == null) return false;
        String verifi = mUserBean.getIsVerified();
        return "1".equals(verifi) ? true : false;
    }

    public String getBizCode() {
        if (mUserBean == null)
            return "";
        return mUserBean.getBizCode();
    }
}
