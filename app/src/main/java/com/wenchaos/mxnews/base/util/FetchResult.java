package com.wenchaos.mxnews.base.util;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.wenchaos.mxnews.util.UiUtils;


public class FetchResult {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    //    private int resultCode;
    private int errorCode;
    private String message;
    private int totalRecord;
    private String model;
    private Object modelObj;
    private boolean success;
    private JsonObject resultData;
    private String modelStr;

//    public int getErrorCode() {
//        return resultCode;
//    }
//
//    public void setResultCode(int resultCode) {
//        this.resultCode = resultCode;
//    }

    public String getMessage() {
        return TextUtils.isEmpty(message) ? "连接服务器出现异常"
                : (message.contains("html") || message.length() > 50) ? "连接服务器出现异常" : message;
    }

    public boolean isValid(Context context) {
        if (success) {
            return true;
        } else {
            UiUtils.showErrorToaster(getMessage(), context);
            return false;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }


//    public void setModel(String model) {
//        this.model = model;
//    }

//    public String getModelData() {
//        return model;
//
//    }

    public Object getModelObj() {
        return modelObj;
    }

    public String getModelStr() {
        return TextUtils.isEmpty(modelStr) ? (modelObj == null ? null : modelObj.toString()) : modelStr;
    }

    public void setModelObj(Object modelObj) {
        this.modelObj = modelObj;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JsonObject getResultData() {
        return resultData;
    }

    public void setResultData(JsonObject resultData) {
        this.resultData = resultData;
    }

    public void setModelStr(String modelStr) {
        this.modelStr = modelStr;
    }
}
