package com.wenchaos.base.base.entity;


import android.text.TextUtils;

import com.wenchaos.base.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * 用户对象
 */
public class UserBean {
    private String mobile;
    private String name;
    private String spell;
    private String sex;
    private String birthday;
    private String birthdayStr;
    private String saleAddressId;
    private String isVerified;
    private String server;
    private String path;
    private String createTime;
    private String id;
    private String lastVer;
    private String isValid;
    private String opTime;
    private String url;
    private String unionId;
    private String weChatCountry;
    private String weChatNickName;
    private String weChatCity;
    private String weChatProvince;
    private String weChatLanguage;
    private String weChatSex;
    private String openId;
    private String weChatHeadUrl;

    private String bizCode;
    private String showMsg;
    private String sessionId;
    public UserBean() {
    }

    public UserBean(String weChatCountry, String weChatNickName, String weChatCity, String weChatProvince, String weChatLanguage, String weChatSex, String openId, String weChatHeadUrl) {
        this.weChatCountry = weChatCountry;
        this.weChatNickName = weChatNickName;
        this.weChatCity = weChatCity;
        this.weChatProvince = weChatProvince;
        this.weChatLanguage = weChatLanguage;
        this.weChatSex = weChatSex;
        this.openId = openId;
        this.weChatHeadUrl = weChatHeadUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() throws UnsupportedEncodingException {
        if(token==null){
            return  "";
        }
        return new String(Base64.decode(token), "utf-8");
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatarUrl() {
        return TextUtils.isEmpty(url) ? "http://" + server + "/upload_files/" + path : url;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getSaleAddressId() {
        return saleAddressId;
    }

    public void setSaleAddressId(String saleAddressId) {
        this.saleAddressId = saleAddressId;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastVer() {
        return lastVer;
    }

    public void setLastVer(String lastVer) {
        this.lastVer = lastVer;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public String getUrl() {
        return url;
    }

    public String getWeChatCountry() {
        return weChatCountry;
    }

    public void setWeChatCountry(String weChatCountry) {
        this.weChatCountry = weChatCountry;
    }

    public String getWeChatNickName() {
        return weChatNickName;
    }

    public void setWeChatNickName(String weChatNickName) {
        this.weChatNickName = weChatNickName;
    }

    public String getWeChatCity() {
        return weChatCity;
    }

    public void setWeChatCity(String weChatCity) {
        this.weChatCity = weChatCity;
    }

    public String getWeChatProvince() {
        return weChatProvince;
    }

    public void setWeChatProvince(String weChatProvince) {
        this.weChatProvince = weChatProvince;
    }

    public String getWeChatLanguage() {
        return weChatLanguage;
    }

    public void setWeChatLanguage(String weChatLanguage) {
        this.weChatLanguage = weChatLanguage;
    }

    public String getWeChatSex() {
        return weChatSex;
    }

    public void setWeChatSex(String weChatSex) {
        this.weChatSex = weChatSex;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWeChatHeadUrl() {
        return weChatHeadUrl;
    }

    public void setWeChatHeadUrl(String weChatHeadUrl) {
        this.weChatHeadUrl = weChatHeadUrl;
    }
}
