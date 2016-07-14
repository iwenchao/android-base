package com.wenchaos.mxnews.ui.home;

import com.wenchaos.mxnews.base.mvp.BaseModel;
import com.wenchaos.mxnews.base.mvp.BasePresent;
import com.wenchaos.mxnews.base.mvp.BaseView;

/**
 * Created by Administrator
 * on 2016/6/28 0028.
 */
public interface MainContract {
    interface  Model extends BaseModel{

    }

    interface View extends BaseView{

    }

    abstract class Present extends BasePresent<Model,View> {

    }
}
