package com.shiyan.app.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

/**
 * 上滑隐藏 下滑显示
 * 作者: created by shiyan on 2018/11/21
 **/

public class FooterBehavior extends CoordinatorLayout.Behavior<View>{

    private int directionChange;

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 表明这次滑动我们要不要关心，这里我们只关心Y轴方向
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    /**
     * 处理滑动
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy dy为正 向上滑动 dy为负 向下滑动
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        if(dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0){

            child.animate().cancel();

            directionChange = 0;
        }

        directionChange += dy;

        if(directionChange > child.getHeight() && child.getVisibility() == View.VISIBLE){

            hide(child);

        }else if(directionChange < 0 && child.getVisibility() == View.INVISIBLE){

            show(child);

        }
    }

    private void show(View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(200);

        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });

        animator.start();
    }

    private void hide(View view) {
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight())
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(200);

        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });

        animator.start();
    }
}
