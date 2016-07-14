package com.wenchaos.mxnews.base.util;


import com.wenchaos.mxnews.BuildConfig;

public class Constant {


    /**
     * #正式环境
     * #baseUrl=http://zm.2dfire.com
     * #apiUrl=http://api.2dfire.com
     * #couponShareUrl=http://pay.2dfire.com/hongbao/receive.do?couponId=
     * #预发环境
     * #baseUrl=http://zm.2dfire-pre.com
     * #apiUrl=http://api.2dfire-pre.com
     * #couponShareUrl=http://pay.2dfire-pre.com/hongbao/receive.do?couponId=
     * #内网环境
     * baseUrl=http://10.1.6.154:8080
     * apiUrl=http://10.1.6.154:8080
     * couponShareUrl=http://pay.2dfire-daily.com/hongbao/receive.do?couponId=
     */

    private static final String RELEASE_API_URL = "https://consumer-api.2dfire.com";
    private static final String RELEASE_COUPON_SHARE_URL = "http://weidian.2dfire.com/hongbao/receive.do?couponId=";
    private static final String RELEASE_IMAGE_BASE_URL = "http://ifile.2dfire.com/";

    private static final String RC_API_URL = "http://consumer-api.2dfire-pre.com";
    private static final String RC_COUPON_SHARE_URL = "http://weidian.2dfire-pre.com/hongbao/receive.do?couponId=";


    public static  String INTERNAL_API_URL = "http://10.1.5.141:8080/consumer-api";
//    private static final String INTERNAL_API_URL = "http://10.1.7.22:8080/consumer-api";
    private static final String INTERNAL_COUPON_SHARE_URL = "http://weidian.2dfire-daily.com/hongbao/receive.do?couponId=";
    private static final String INTERNAL_IMAGE_BASE_URL = "http://ifiletest.2dfire.com/";


    public static String apiUrl;
    public static String couponShareUrl;
    public static String imageBaseUrl;

    public static final String wxAppId = "wxc6405a4615d25a7c";
    public static final String wxAppSecret = "3157569cdb8b64ee536893374a508bbb";


    public static final int ERROR_CODE_NETWORK = -99;

    public static final int SUPPORT = 1;

    public static String getApiUrl() {
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("Rc")) {
            return apiUrl = RC_API_URL;
        } else if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("Debug")) {
            return apiUrl = INTERNAL_API_URL;
        } else {
            return apiUrl = RELEASE_API_URL;
        }
    }


    public static String getCouponShareUrl() {
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("Rc")) {
            return couponShareUrl = RC_COUPON_SHARE_URL;
        } else if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("Debug")) {
            return couponShareUrl = INTERNAL_COUPON_SHARE_URL;
        } else {
            return couponShareUrl = RELEASE_COUPON_SHARE_URL;
        }
    }

    public static String getBaseImagePathUrl() {
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("Rc")) {
            return imageBaseUrl = RELEASE_IMAGE_BASE_URL;
        } else if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("Debug")) {
            return imageBaseUrl = INTERNAL_IMAGE_BASE_URL;
        } else {
            return imageBaseUrl = RELEASE_IMAGE_BASE_URL;
        }
    }


}
