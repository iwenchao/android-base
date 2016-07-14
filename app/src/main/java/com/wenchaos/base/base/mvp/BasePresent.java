package com.wenchaos.base.base.mvp;

import android.content.Context;

/**
 * Created by Administrator
 * on 2016/6/27 0027.
 */
public abstract class BasePresent<E,T> {
    public Context context;
    public E mModel;
    public T mView;

    public void setVM(E m,T v){
        this.mModel = m;
        this.mView = v;
        this.onStart();
    }
    public abstract void onStart();

    public void onDestroy(){

    }
}
