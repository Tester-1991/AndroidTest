package com.shiyan.app.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * 作者: created by shiyan on 2018/9/6
 **/

public class CommonTabEntity implements CustomTabEntity {

    private String title;

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
