package com.wenchaos.base.api.repository.source;

import android.text.TextUtils;

import com.wenchaos.base.api.ApiConnector;
import com.wenchaos.base.api.ErrorBody;
import com.wenchaos.base.api.FetchResultCallback;
import com.wenchaos.base.base.App;
import com.wenchaos.base.base.entity.UserBean;
import com.wenchaos.base.base.util.FetchResult;
import com.wenchaos.base.data.GsonProvider;
import com.wenchaos.base.util.DeviceHelper;

import java.util.HashMap;
import java.util.Map;


public class UserDataCloudSource implements UserDataSource {

    private ApiConnector mApiConnector;

    private static volatile UserDataCloudSource sInstance = null;

    private UserDataCloudSource(ApiConnector apiConnector) {
        mApiConnector = apiConnector;
    }

    public static UserDataCloudSource getInstance(ApiConnector apiConnector) {
        if (sInstance == null) {
            synchronized (UserDataCloudSource.class) {
                if (sInstance == null) {
                    sInstance = new UserDataCloudSource(apiConnector);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void login(String mobile, String password, final LoginCallback callback) {
//        ZMD5 zmd5 = new ZMD5();
//        password = zmd5.encode(password);
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);
        params.put("equipment_id", DeviceHelper.getIMEI(App.getGlobalApp()));
//        params.put("uid", mobile);
        mApiConnector.fetchResultFromApi("/member/action/v1/login", params, new FetchResultCallback() {//old /consumer/member/login
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    UserBean user = GsonProvider.getGson().fromJson(fetchResult.getModelStr(), UserBean.class);
//                    callback.onSuccess(user);
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    @Override
    public void loginOut(final LoginOutCallback callback) {
        Map<String, String> params = new HashMap<>();
        mApiConnector.fetchResultFromApi("/member/action/v1/login_out", params, new FetchResultCallback() {
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    callback.onSuccess(fetchResult);
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }


    @Override
    public void changePwd(String originPsw, String newPsw, final ChangePwdCallback callback) {
        Map<String, String> params = new HashMap<>();
//        ZMD5 zmd5 = new ZMD5();
//        originPsw = zmd5.encodeSalt(zmd5.encode(originPsw));
//        newPsw = zmd5.encodeSalt(zmd5.encode(newPsw));
        params.put("oldPwd", originPsw);
        params.put("newPwd", newPsw);
//        params.put("customerRegisterId", mApiConnector.getUserId());
        mApiConnector.fetchResultFromApi("/member/changePwd", params, new FetchResultCallback() {
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    /**
     * 获取用户地址列表
     *
     * @param callback 回调
     */
    @Override
    public void getAddressList(final GetAddressListCallback callback) {
        Map<String, String> params = new HashMap<>();
//        params.put("user_id", mApiConnector.getUserId());
        mApiConnector.getResultFromApi("/member/address/v1/get_user_address_list", params, new FetchResultCallback() {//old /member/getUserAddressList
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                callback.onSuccess(fetchResult);
            }
        });
    }



    /**
     * 删除地址
     *
     * @param addressId 地址id
     * @param callback  回调
     */
    @Override
    public void removeAddress(String addressId, final RemoveAddressCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("sale_address", addressId);
//        params.put("user_id", mApiConnector.getUserId());
        mApiConnector.fetchResultFromApi("/member/address/v1/remove_sale_address", params, new FetchResultCallback() {//old /member/removeSaleAddress
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    /**
     * 火小二用户发送邮件
     */
    @Override
    public void feedback(String version, String name, String email, String sex, String birthday, String backMemo, String mobile, final FeedBackCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("version", version);
        params.put("name", name);
        params.put("email", email);
        params.put("sex", sex);
        params.put("birthday", TextUtils.isEmpty(birthday) ? "" : birthday);
        params.put("back_memo", backMemo);
//        params.put("mobile", mobile);
        mApiConnector.fetchResultFromApi("/member/action/v1/email_feed_back", params, new FetchResultCallback() {//old /feed/cardAappBack
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    @Override
    public void bindMobile(String weChatId, String code, String sex, String imgUrl, String weChatName, String mobile, String newMobile, int type, final BindMobileCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("new_mobile", TextUtils.isEmpty(newMobile) ? "" : newMobile);
//        params.put("customer_id", mApiConnector.getUserId());
        params.put("weixin_id", weChatId);
        params.put("weixin_name", weChatName);
        params.put("img_url", imgUrl);
        params.put("sex", (TextUtils.isEmpty(sex) || "null".equals(sex)) ? "" : sex);
        params.put("code", code);
        params.put("type", type + "");
        params.put("equipment_id", DeviceHelper.getIMEI(App.getGlobalApp()));
        mApiConnector.fetchResultFromApi("/member/action/v1/bind_mobile", params, new FetchResultCallback() {
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    UserBean userBean = GsonProvider.getGson().fromJson(fetchResult.getModelStr(), UserBean.class);
//                    callback.onSuccess(userBean);
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    @Override
    public void checkValidMobile(String newMobile, final CheckValidMobileCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("new_mobile", newMobile);
        mApiConnector.getResultFromApi("/member/action/v1/is_mobile_reg", params, new FetchResultCallback() {
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
//                    UserBean userBean = GsonProvider.getGson().fromJson(fetchResult.getModelStr(), UserBean.class);
                    callback.onSuccess(fetchResult);
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    /**
     * 检查是否绑定手机号
     */
    @Override
    public void verifyBinding(String weChatId, String weixinName, String imgUrl, String sex, final VerifyBindingCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("weixin_id", weChatId);
        params.put("weixin_name", weixinName);
        params.put("img_url", imgUrl);
        params.put("equipment_id", DeviceHelper.getIMEI(App.getGlobalApp()));
        params.put("sex", sex);
        mApiConnector.getResultFromApi("/member/action/v1/verify_binding", params, new FetchResultCallback() {//old bindingmobile/verifyBinding
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    UserBean userBean = GsonProvider.getGson().fromJson(fetchResult.getModelStr(), UserBean.class);
//                    callback.onSuccess(userBean);
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    /**
     * 获取绑定手机号验证码
     */
    @Override
    public void sendVerCode(String mobile, final SendCodeCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        mApiConnector.fetchResultFromApi("/member/action/v1/send_bind_ver_code", params, new FetchResultCallback() {//old bindingmobile/sendVerCode
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {
                if (fetchResult.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onFailed(new ErrorBody(fetchResult));
                }
            }
        });
    }

    @Override
    public void deskTosavePeople(String entityId, String seatCode,  String customerCount, String memo, FetchResultCallback callback) {

        String path  = "/cart/v1/modify_people_memo";;
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("entity_id", entityId);
        paramsMap.put("seat_code", TextUtils.isEmpty(seatCode) ? "" : seatCode);
        paramsMap.put("people", customerCount + "");
        paramsMap.put("memo", memo);
        mApiConnector.fetchResultFromApi(path, paramsMap,callback );
    }
    @Override
    public void shopTosavePeople(String entityId, String customerId, String customerCount, String memo, FetchResultCallback callback) {

        String path = "/precart/v1/modify_people_memo";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("entity_id", entityId);
        paramsMap.put("people", customerCount + "");
        paramsMap.put("memo", memo);
        mApiConnector.fetchResultFromApi(path, paramsMap, callback);
               
    }

    @Override
    public void isSetPeople(String entityId, String orderId, String seatCode, final IsSetPeopleCallback callback) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("entity_id", entityId);
        paramsMap.put("order_id", orderId);
        paramsMap.put("seat_code", seatCode);
        mApiConnector.getResultFromApi("/qrcode/v1/fill_set_people", paramsMap, new FetchResultCallback() {
            @Override
            public void onFetchResultCallback(FetchResult fetchResult) {


                if (fetchResult.isSuccess()) callback.onSuccess(fetchResult);
                else {
                    callback.onFailed(new ErrorBody(fetchResult));

                }
            }
        });
    }
}
