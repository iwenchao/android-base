package com.wenchaos.mxnews.api.repository.source;


import com.wenchaos.mxnews.api.BaseDataCallback;
import com.wenchaos.mxnews.api.FetchResultCallback;
import com.wenchaos.mxnews.base.util.FetchResult;


public interface UserDataSource {

    interface LoginCallback extends BaseDataCallback {
//        void onSuccess(UserBean userBean);
    }
    /**
     * 用户登录
     *
     * @param mobile   手机号码
     * @param password 密码
     * @param callback 回调接口
     */
    void login(String mobile, String password, LoginCallback callback);

    /**
     * 退出登录
     */
    void loginOut(LoginOutCallback callback);
    interface LoginOutCallback extends BaseDataCallback{
        void onSuccess(FetchResult result);
    }


    interface ChangePwdCallback extends BaseDataCallback{
        void onSuccess();
    }

    /**
     * 修改密码
     *
     * @param originPsw 就密码
     * @param newPsw    新密码
     * @param callback  回调
     */
    void changePwd(String originPsw, String newPsw, ChangePwdCallback callback);

    interface GetAddressListCallback {
        void onSuccess(FetchResult fetchResult);
    }

    /**
     * 获取用户地址列表
     *
     * @param callback 回调
     */
    void getAddressList(GetAddressListCallback callback);

    interface SaveAddressCallback extends BaseDataCallback {
        void onSuccess(FetchResult fetchResult);
    }

    /**
     * 保存地址
     *
//     * @param address   地址序列化对象
//     * @param isDefault 是否为默认地址
//     * @param callback  回调
     */
//    void saveAddress(AddressBean address, String isDefault, SaveAddressCallback callback);

    interface RemoveAddressCallback extends BaseDataCallback {
        void onSuccess();
    }

    /**
     * 删除地址
     *
     * @param addressId 地址id
     * @param callback  回调
     */
    void removeAddress(String addressId, RemoveAddressCallback callback);

    interface FeedBackCallback extends BaseDataCallback {
        void onSuccess();
    }

    /**
     * 意见反馈
     *
     * @param callback 回调
     */
    void feedback(String version, String name, String email, String sex, String birthday, String backMemo,
                  String mobile, FeedBackCallback callback);


    interface BindMobileCallback extends BaseDataCallback {
//        void onSuccess(UserBean userBean);
    }

    /**
     * 绑定手机号
     * @param weChatId 微信id
     * @param code     短信验证码
     * @param sex      性别
     * @param imgUrl   头像
     * @param weChatName 昵称
     * @param mobile    手机号
     * @param callback
     */
    void bindMobile(String weChatId, String code, String sex, String imgUrl,
                    String weChatName, String mobile, String newMobile, int type, final BindMobileCallback callback);

    interface VerifyBindingCallback extends BaseDataCallback {
//        void onSuccess(UserBean userBean);
    }

    /**
     * 验证是否为可用的手机号
     * @param newMobile
     * @param callback
     */
    void checkValidMobile(String newMobile, CheckValidMobileCallback callback);

    interface CheckValidMobileCallback extends  BaseDataCallback{
        void onSuccess(FetchResult userBean);
    }

    /**
     * 查询是否绑定成功
     * @param weChatId 微信id
     * @param weixinName 昵称
     * @param imgUrl    头像
     * @param sex       性别
     * @param callback
     */
    void verifyBinding(String weChatId, String weixinName, String imgUrl, String sex, VerifyBindingCallback callback);

    interface SendCodeCallback extends BaseDataCallback{
        void onSuccess();
    }

    /**
     * 获取绑定手机号验证码
     *
     * @param callback 回调
     */
    void sendVerCode(String mobile, SendCodeCallback callback);
    //保存人数和备注

    void deskTosavePeople(String entityId, String seatCode, String customerCount, String memo, FetchResultCallback callback);
    void shopTosavePeople(String entityId, String customerId, String customerCount, String memo, FetchResultCallback callback);

    //是否展示人数
    interface  IsSetPeopleCallback extends BaseDataCallback{
        void  onSuccess(FetchResult fetchResult);
    }
    void isSetPeople(String entityId, String orderId, String seatCode, IsSetPeopleCallback isSetPeopleCallback);
}
