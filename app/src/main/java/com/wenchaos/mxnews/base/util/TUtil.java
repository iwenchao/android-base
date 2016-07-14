package com.wenchaos.mxnews.base.util;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator
 * on 2016/6/27 0027.
 */
public class TUtil {
    public static <T> T getT(Object o,int i){
        try {
            return ((Class<T>)((ParameterizedType)(o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
