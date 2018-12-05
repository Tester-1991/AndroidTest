package com.shiyan.app.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;


/**
 * 作者: created by shiyan on 2018/11/30
 * 2秒内均速向右水平移动400像素
 **/

public class ScrollerView extends RelativeLayout {

    private Scroller scroller;

    public ScrollerView(Context context) {
        this(context, null);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scroller = new Scroller(context,new LinearInterpolator());

        //开启移动
        scroller.startScroll(0, 0, -1000, 0, 2000);

        invalidate();

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //如果当前正在移动过程中
        if (scroller.computeScrollOffset()) {

            scrollTo(scroller.getCurrX(), scroller.getCurrY());

            invalidate();

        }else{
            if(scroller.getCurrX() < 0) {

                scroller.startScroll(-1000, 0, 1000, 0, 2000);

            }else{

                scroller.startScroll(0, 0, -1000, 0, 2000);

            }

            invalidate();
        }
    }
}
