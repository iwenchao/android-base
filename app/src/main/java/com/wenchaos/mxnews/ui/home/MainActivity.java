package com.wenchaos.mxnews.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.wenchaos.mxnews.R;
import com.wenchaos.mxnews.base.BaseActivity;

import org.androidannotations.annotations.EActivity;

import butterknife.Bind;
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity<MainPresent,MainModel> {

    @Bind(R.id.app_main_toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_main_bar)
    TabLayout tabs;
    @Bind(R.id.app_main_viewpager)
    ViewPager viewPager;



    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresent() {

    }
}
