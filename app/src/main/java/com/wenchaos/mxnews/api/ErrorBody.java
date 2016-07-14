package com.wenchaos.mxnews.api;


import com.wenchaos.mxnews.base.util.FetchResult;


public class ErrorBody {

    private int errorCode;
    private String message;

    public ErrorBody(FetchResult fetchResult){
        this(fetchResult.getErrorCode(), fetchResult.getMessage());
    }

    public ErrorBody(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
