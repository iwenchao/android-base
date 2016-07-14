package com.wenchaos.mxnews.api;

import android.accounts.NetworkErrorException;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.orhanobut.logger.Logger;
import com.wenchaos.mxnews.BuildConfig;
import com.wenchaos.mxnews.R;
import com.wenchaos.mxnews.base.App;
import com.wenchaos.mxnews.base.Injection;
import com.wenchaos.mxnews.base.util.CacheConfig;
import com.wenchaos.mxnews.base.util.CacheManager;
import com.wenchaos.mxnews.base.util.Constant;
import com.wenchaos.mxnews.base.util.FetchResult;
import com.wenchaos.mxnews.data.BusProvider;
import com.wenchaos.mxnews.event.TokenInvalidEvent;
import com.wenchaos.mxnews.util.DeviceHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;


public class ApiConnector {

    public static final String PAGE_SIZE = "10";
    private static ApiConnector sApiConnector = null;

    private App app;

    private ApiConnector() {
        this.app = App.getGlobalApp();
    }

    public static ApiConnector getInstance() {
        if (sApiConnector == null) {
            sApiConnector = new ApiConnector();
        }
        return sApiConnector;
    }

    /**
     * 如果有缓存
     *
     * @param path
     * @param paramsMap
     * @param cacheExpire
     * @param callback
     * @return
     */
    private boolean handlerCach(String path, Map<String, String> paramsMap, long cacheExpire, FetchResultCallback callback) {
        boolean flag = false;
        String cacheName = getCacheNameKey(path, paramsMap);
        String cachedJson = CacheManager.getFileCache(app, cacheName);
        if (!TextUtils.isEmpty(cachedJson)) {

            FetchResult fetchResult = new FetchResult();
            fetchResult.setSuccess(true);
            fetchResult.setModelStr(cachedJson);

            if (cacheExpire == CacheConfig.AFTER_REFRESH) {
                callback.onFetchResultCallback(fetchResult);
            } else if (cacheExpire == CacheConfig.FOREVER) {
                callback.onFetchResultCallback(fetchResult);
                return true;
            }
        }
        return flag;
    }

    /**
     * 判断是否有网络
     * @return 有返回true，无则false
     */
    private boolean handlerNetwork(FetchResultCallback callback) {
        if (!DeviceHelper.isNetworkConnected(app)) {

            if (null != callback) {
                FetchResult fetchResult = new FetchResult();
                fetchResult.setSuccess(false);
                fetchResult.setErrorCode(Constant.ERROR_CODE_NETWORK);
                fetchResult.setMessage(app.getString(R.string.network_error_info));
                callback.onFetchResultCallback(fetchResult);
            }
            return false;
        }

        return true;
    }

    public void getResultFromApi(String path, Map<String, String> paramsMap, FetchResultCallback callback) {
        getResultFromApi(path, paramsMap, CacheConfig.NO_CACHE, callback);
    }

    /**
     * Get请求
     *
     * @param path
     * @param paramsMap
     * @param cacheExpire
     * @param callback
     */
    public void getResultFromApi(String path, Map<String, String> paramsMap, long cacheExpire, FetchResultCallback callback) {
        if (handlerCach(path, paramsMap, cacheExpire, callback) || !handlerNetwork(callback)) {
            return;
        }

        addFinalParams(paramsMap);
        String targetUrl = Constant.getApiUrl() + path + parseMapToUrl(paramsMap);
        Ion.with(app)
                .load(targetUrl)
                .setHeader("equipmentId", "" + DeviceHelper.getIMEI(app))
                .setHeader("system", "android")
                .setHeader("appVersion", "" + BuildConfig.VERSION_CODE)
                .setHeader("screenSize", "" + DeviceHelper.getDisplayResolution(app))
                .setHeader("sysVersion", "" + Build.VERSION.SDK_INT)
                .setHeader("network", "" + DeviceHelper.getNetwork(app))
                .setHeader("deviceBrand", "" + DeviceHelper.getBrand())
                .setHeader("sessionId", Injection.provideUserDataRepository().getSessionId())
                .asJsonObject()
                .setCallback(cacheExpire == CacheConfig.NO_CACHE ? getFutureCallback(callback) : getFutureCallback(callback, cacheExpire, getCacheNameKey(path, paramsMap)));
    }

    public void fetchResultFromApi(String path, Map<String, String> paramsMap, FetchResultCallback callback) {
        fetchResultFromApi(path, paramsMap, CacheConfig.NO_CACHE, callback);
    }

    /**
     * Post 请求
     *
     * @param path
     * @param paramsMap
     * @param cacheExpire
     * @param callback
     */
    public void fetchResultFromApi(String path, Map<String, String> paramsMap, long cacheExpire, FetchResultCallback callback) {
        Logger.i("path: " + path);
        if (handlerCach(path, paramsMap, cacheExpire, callback) || !handlerNetwork(callback)) {
            return;
        }

        String targetUrl = Constant.getApiUrl() + path;
        Ion.with(app)
                .load(targetUrl)
                .setHeader("equipmentId", "" + DeviceHelper.getIMEI(app))
                .setHeader("system", "android")
                .setHeader("appVersion", "" + BuildConfig.VERSION_CODE)
                .setHeader("screenSize", "" + DeviceHelper.getDisplayResolution(app))
                .setHeader("sysVersion", "" + Build.VERSION.SDK_INT)
                .setHeader("network", "" + DeviceHelper.getNetwork(app))
                .setHeader("deviceBrand", "" + DeviceHelper.getBrand())
                .setHeader("sessionId", Injection.provideUserDataRepository().getSessionId())
                .setBodyParameters(getFinalParams(paramsMap))
                .asJsonObject()
                .setCallback(cacheExpire == CacheConfig.NO_CACHE ? getFutureCallback(callback) : getFutureCallback(callback, cacheExpire, getCacheNameKey(path, paramsMap)));
    }

    /**
     * 包装给Post请求用
     *
     * @param paramsMap
     * @return
     */
    protected Map<String, List<String>> getFinalParams(Map<String, String> paramsMap) {
        addFinalParams(paramsMap);
        Map<String, List<String>> finalParams = new TreeMap<>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            finalParams.put(entry.getKey(), Arrays.asList(entry.getValue()));
        }
        return finalParams;
    }

    /**
     * 添加公用值
     *
     * @param paramsMap
     */
    private void addFinalParams(Map<String, String> paramsMap) {
//        paramsMap.put("timestamp", String.valueOf(Injection.provideConfigRepository().getCorrectionalTimestamp()));
        paramsMap.put("appKey", "100010");
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            if (TextUtils.isEmpty(entry.getValue())) {
                entry.setValue("");
            }
        }
        TreeMap<String, String> map = new TreeMap<>(paramsMap);
        map.put("token", Injection.provideUserDataRepository().getUserToken());
//        paramsMap.put("sign", SignUtil.getSign(map));
    }

    private FutureCallback<JsonObject> getFutureCallback(final FetchResultCallback callback) {
        return getFutureCallback(callback, CacheConfig.NO_CACHE, null);
    }

    private FutureCallback<JsonObject> getFutureCallback(final FetchResultCallback callback, final long expireTime, @Nullable final String cacheName) {
        return new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                FetchResult fetchResult = new FetchResult();
                if (e == null && result != null && result.get("code") != null &&
                        !result.get("code").isJsonNull() && result.get("code").getAsInt() == 1) {
                    fetchResult.setSuccess(true);
                    fetchResult.setMessage("success");
                    fetchResult.setErrorCode(!stringIsNumber(result.get("errorCode"))
                            ? 0 : result.get("errorCode").getAsInt());
                    try {
                        fetchResult.setModelObj(result.get("data") == null || result.get("data").isJsonNull() ? "" :
                                result.get("data").isJsonArray() ? result.get("data").getAsJsonArray() :
                                        result.get("data").isJsonObject()
                                                ? result.get("data").getAsJsonObject() : result.get("data").getAsString());
                        fetchResult.setResultData(result);
                        if (expireTime != CacheConfig.NO_CACHE && !TextUtils.isEmpty(cacheName)) {
                            CacheManager.writeFileCache(app, cacheName, fetchResult.getModelStr(), expireTime);
                        }
                    } catch (UnsupportedOperationException e1) {
                        fetchResult.setSuccess(false);
                    }
                } else {
                    if (e != null) {
                        if (e instanceof TimeoutException) {
                            fetchResult.setMessage(app.getResources().getString(R.string.error_genernal));
                        } else if (e instanceof NetworkErrorException) {
                            fetchResult.setMessage(app.getResources().getString(R.string.error_genernal));
                        }
                    }
                    if (result != null) {
                        try {
                            fetchResult.setErrorCode(!stringIsNumber(result.get("errorCode"))
                                    ? 0 : result.get("errorCode").getAsInt());
                            fetchResult.setMessage(result.get("message") == null || result.get("errorCode").isJsonNull()
                                    ? "" : result.get("message").getAsString());
                            if (fetchResult.getErrorCode() == 403 || fetchResult.getErrorCode() == 4004
                                    || fetchResult.getErrorCode() == 4001) {
                                fetchResult.setMessage("登录信息过期, 请重新登录");
                                BusProvider.getBus().post(new TokenInvalidEvent());
                            }
                        } catch (UnsupportedOperationException e1) {
                            fetchResult.setMessage("");
                        }
                    }
                }
                callback.onFetchResultCallback(fetchResult);
            }
        };
    }

    private boolean stringIsNumber(JsonElement s) {
        if (s == null || s.isJsonNull()) {
            return false;
        }
        try {
            Integer.parseInt(s.getAsString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getCacheNameKey(String path, Map<String, String> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append(entry.getValue());
        }
        return String.valueOf(stringBuilder.toString().hashCode());
    }

    /**
     * 将Map转化成url参数
     *
     * @param paramsMap
     * @return
     */
    private String parseMapToUrl(Map<String, String> paramsMap) {
        if (null == paramsMap || paramsMap.size() < 1) {
            return "";
        }

        int index = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            if (TextUtils.isEmpty(entry.getKey())) {
                continue;
            }
            if (0 == index) {
                stringBuilder.append("?");
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(entry.getValue());

            if (++index < paramsMap.size()) {
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }
}
