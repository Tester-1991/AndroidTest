package com.shiyan.app.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;

/**
 * Created by shiyan on 2018/8/27.
 */

public class ScrollerImageView extends AppCompatImageView {
    public ScrollerImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getBackground().getIntrinsicWidth();

        setMeasuredDimension(width,heightMeasureSpec);
    }
}
