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

package com.wenchaos.base.base.util;

import android.content.Context;
import android.content.SharedPreferences;



public class CacheMetaPrefs {

    private static final String CACHE_META_PREF = "CACHE_META_PREF";

    private static volatile CacheMetaPrefs singleton;
    private final SharedPreferences prefs;

    public static CacheMetaPrefs get(Context context) {
        if (singleton == null) {
            synchronized (CacheMetaPrefs.class) {
                singleton = new CacheMetaPrefs(context);
            }
        }
        return singleton;
    }

    private CacheMetaPrefs(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(CACHE_META_PREF, Context
                .MODE_PRIVATE);
    }

    public void setCacheExpireTime(String cacheName, long expireTime) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(cacheName, expireTime);
        editor.apply();
    }


    public long getExpireTime(String cacheName){
       return prefs.getLong(cacheName, CacheConfig.NO_CACHE);
    }


    public boolean isExpired(String cacheName){
        long at = getExpireTime(cacheName);
        if(at == CacheConfig.FOREVER){
            return false;
        }else if(at == CacheConfig.AFTER_REFRESH){
            return false;
        }
        return System.currentTimeMillis() >= getExpireTime(cacheName);
    }
}
