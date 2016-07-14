package com.wenchaos.mxnews.api.repository;

import android.text.TextUtils;

import com.wenchaos.mxnews.api.FetchResultCallback;
import com.wenchaos.mxnews.api.repository.source.UserDataCloudSource;
import com.wenchaos.mxnews.api.repository.source.UserDataPrefsSource;
import com.wenchaos.mxnews.api.repository.source.UserDataSource;
import com.wenchaos.mxnews.base.entity.UserBean;



public class UserDataRepository implements UserDataSource {

    private UserDataPrefsSource mUserDataPrefsSource;
    private UserDataCloudSource mUserDataCloudSource;

    private static UserDataRepository INSTANCE = null;

    private UserDataRepository(UserDataPrefsSource userDataPrefsSource, UserDataCloudSource userDataCloudSource) {
        mUserDataPrefsSource = userDataPrefsSource;
        mUserDataCloudSource = userDataCloudSource;
    }

    public static UserDataRepository getInstance(UserDataPrefsSource userDataPrefsSource, UserDataCloudSource userDataCloudSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository(userDataPrefsSource, userDataCloudSource);
        }
        return INSTANCE;
    }

    public void setCachedUser(UserBean userBean) {
        mUserDataPrefsSource.setCachedUser(userBean);
    }

    public UserBean getCachedUser() {
        return mUserDataPrefsSource.getCachedUser();
    }

    public boolean checkCachedUid() {
        return null == getCachedUser() || TextUtils.isEmpty(getCachedUser().getId());
    }
    public boolean nonCachedUid(){
        return null == getCachedUser() ||TextUtils.isEmpty(getCachedUser().getId());
    }
    public boolean isVerified(){
        return mUserDataPrefsSource.checkVerified();
    }
    public String getBizCode(){
        return mUserDataPrefsSource.getBizCode();
    }

    private void clearCachedUser() {
        mUserDataPrefsSource.clearCachedUser();
    }

    public String getUserMobile() {
        return mUserDataPrefsSource.getUserMobile();
    }

    public String getUserToken() {
        return mUserDataPrefsSource.getUserToken();
    }

    public void deleteCurrentUser() {
        mUserDataPrefsSource.deleteCurrentUser();
    }
    public String getSessionId(){
        return mUserDataPrefsSource.getSessionId();
    }



    @Override
    public void login(String mobile, String password, LoginCallback callback) {
        mUserDataCloudSource.login(mobile, password, callback);
    }

    @Override
    public void loginOut(LoginOutCallback callback) {
        mUserDataCloudSource.loginOut(callback);
    }

    @Override
    public void changePwd(String originPsw, String newPsw, ChangePwdCallback callback) {
        mUserDataCloudSource.changePwd(originPsw, newPsw, callback);
    }

    @Override
    public void getAddressList(GetAddressListCallback callback) {
        mUserDataCloudSource.getAddressList(callback);
    }



    @Override
    public void removeAddress(String addressId, RemoveAddressCallback callback) {
        mUserDataCloudSource.removeAddress(addressId, callback);
    }

    @Override
    public void feedback(String version, String name, String email, String sex, String birthday, String backMemo, String mobile, FeedBackCallback callback) {
        mUserDataCloudSource.feedback(version, name, email, sex, birthday, backMemo, mobile, callback);
    }

    @Override
    public void bindMobile(String weChatId, String code, String sex, String imgUrl, String weChatName, String mobile,String newMobile, int type,final BindMobileCallback callback) {
        mUserDataCloudSource.bindMobile(weChatId, code, sex, imgUrl, weChatName, mobile,newMobile,type, callback);
    }

    @Override
    public void checkValidMobile(String newMobile, CheckValidMobileCallback callback) {
        mUserDataCloudSource.checkValidMobile(newMobile,callback);
    }


    @Override
    public void verifyBinding(String weChatId, String weixinName, String imgUrl, String sex, VerifyBindingCallback callback) {
        mUserDataCloudSource.verifyBinding(weChatId, weixinName, imgUrl, sex, callback);
    }

    @Override
    public void sendVerCode(String mobile, SendCodeCallback callback) {
        mUserDataCloudSource.sendVerCode(mobile, callback);
    }

    @Override
    public void deskTosavePeople(String entityId, String seatCode, String customerCount, String memo, FetchResultCallback callback) {
        mUserDataCloudSource.deskTosavePeople(entityId,seatCode,customerCount,memo,callback);
    }

    @Override
    public void shopTosavePeople(String entityId, String customerId, String customerCount, String memo, FetchResultCallback callback) {
        mUserDataCloudSource.shopTosavePeople(entityId,customerId,customerCount,memo,callback);
    }


    @Override
    public void isSetPeople(String entityId, String orderId, String seatCode, IsSetPeopleCallback callback) {
        mUserDataCloudSource.isSetPeople(entityId,orderId,seatCode,callback);
    }
}
