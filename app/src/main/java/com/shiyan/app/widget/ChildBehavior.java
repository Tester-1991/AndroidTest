package com.shiyan.app.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.shiyan.app.R;

/**
 * 作者: created by shiyan on 2018/11/19
 **/

public class ChildBehavior extends CoordinatorLayout.Behavior {

    public ChildBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 查找依赖的父类
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == R.id.dependency;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        //子view属性修改
        child.setY(dependency.getY() + dependency.getHeight());
        return true;
    }

}
