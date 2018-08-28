package com.shiyan.app.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.shiyan.app.R;

/**
 * Created by shiyan on 2018/8/27.
 */

public class ScrollerPictureView extends LinearLayout {

    private int deltaX = 0;

    private Scroller scroller;

    private int duration = 0;

    public ScrollerPictureView(Context context) {
        this(context,null);
    }

    public ScrollerPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        scroller = new Scroller(getContext(),new LinearInterpolator());

        initView();
    }

    private void initView() {
        LayoutParams rootParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(rootParams);

        ScrollerImageView scrollerImageView = new ScrollerImageView(getContext());
        LinearLayout.LayoutParams scrollerPictureViewParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500);
        scrollerImageView.setLayoutParams(scrollerPictureViewParams);
        addView(scrollerImageView);

        scrollerImageView.setBackgroundResource(R.drawable.longimage);

        scrollerImageView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int picWidth = scrollerImageView.getMeasuredWidth();

            LogUtils.eTag("滑动测试","picWidth" + picWidth);

            deltaX = picWidth - ScreenUtils.getScreenWidth();

            LogUtils.eTag("滑动测试","deltaX" + deltaX);

            duration = (deltaX / 20) * 1000;

            LogUtils.eTag("滑动测试","duration" + duration);

            scroller.startScroll(0,0,deltaX,0,duration);

            invalidate();

        });

    }


    @Override
    public void computeScroll() {
        super.computeScroll();

        if(scroller.computeScrollOffset()){

            scrollTo(scroller.getCurrX(),scroller.getCurrY());

            invalidate();
        }
        else{
            LogUtils.eTag("滑动测试","computeScroll" + scroller.getCurrX());
            if(scroller.getCurrX() > 0){
                scroller.startScroll(deltaX,0,-deltaX,0,duration);
            }else{
                scroller.startScroll(0,0,deltaX,0,duration);
            }
            invalidate();
        }
    }
}
