package com.shiyan.app.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 作者: created by shiyan on 2018/11/19
 **/

public class DependencyView extends android.support.v7.widget.AppCompatButton{

    private int lastX;

    private int lastY;

    public DependencyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:

                CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) getLayoutParams();

                int left = layoutParams.leftMargin + x - lastX;

                int top = layoutParams.topMargin + y - lastY;

                layoutParams.leftMargin = left;

                layoutParams.topMargin = top;

                setLayoutParams(layoutParams);

                requestLayout();

                break;
        }

        lastX = x;
        lastY = y;
        return true;
    }
}
