package com.shiyan.app.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者: created by shiyan on 2018/11/21
 **/

public class FooterBehaviorAppBar extends CoordinatorLayout.Behavior<View>{

    public FooterBehaviorAppBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY(Math.abs(dependency.getY()));
        return true;
    }
}
