package com.wenchaos.mxnews.base.util;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class CacheManager {

    public static void writeFileCache(Context context, String cacheName, String valueJson, long expireTime) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(cacheName, Activity.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(valueJson);
            writer.close();
            CacheMetaPrefs.get(context).setCacheExpireTime(cacheName, expireTime);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getFileCache(Context context, String cacheName) {
        BufferedReader input = null;
        if(CacheMetaPrefs.get(context).isExpired(cacheName)) {
            context.deleteFile(cacheName);
            return null;
        }
        try {
            input = new BufferedReader(new InputStreamReader(context.openFileInput(cacheName)));
            String line;
            final StringBuilder builder = new StringBuilder();
            while ((line = input.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
