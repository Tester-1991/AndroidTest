package com.shiyan.app.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * view滑动(offsetLeftAndRight offsetTopAndBottom)
 * 作者: created by shiyan on 2018/11/22
 **/

public class CustomView2 extends View{

    private int lastX;

    private int lastY;

    public CustomView2(Context context) {
        super(context);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();

        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                lastX = x;

                lastY = y;

                break;
            case MotionEvent.ACTION_MOVE:

                int offsetX = x - lastX;

                int offsetY = y - lastY;

                offsetLeftAndRight(offsetX);

                offsetTopAndBottom(offsetY);

                break;
        }

        return true;
    }
}
