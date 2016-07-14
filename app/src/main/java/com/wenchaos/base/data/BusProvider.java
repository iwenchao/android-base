package com.wenchaos.base.data;

import com.squareup.otto.Bus;

import org.androidannotations.annotations.EBean;

/**
 * Created by Administrator
 * on 2016/6/28 0028.
 */
@EBean(scope = EBean.Scope.Singleton)
public class BusProvider {
    private static final Bus BUS = new Bus();
    public static Bus getBus(){
        return BUS;
    }
}
