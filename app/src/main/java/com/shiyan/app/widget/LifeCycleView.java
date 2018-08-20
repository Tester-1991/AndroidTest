package com.shiyan.app.widget;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by shiyan on 2018/8/11.
 */

public class LifeCycleView extends RelativeLayout implements DefaultLifecycleObserver {


    public LifeCycleView(Context context) {
        super(context);

        initView();
    }

    private void initView() {
        LayoutParams rootParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        setLayoutParams(rootParams);
    }

    public void setLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        ToastUtils.showShort("onCreate");
    }
}
