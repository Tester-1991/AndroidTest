package com.shiyan.app.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.shiyan.app.R;

import java.text.RuleBasedCollator;

public class ScrollerImageViewActivity extends BaseActivity {

    private HorizontalScrollView scrollView;

    private ImageView imageView;

    private Handler handler = new Handler();

    private int x =0;

    private int lastX = -1;
    int deltaX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_image_view);

        scrollView = findViewById(R.id.scrollView);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        imageView = findViewById(R.id.imageView);

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = imageView.getMeasuredWidth();
                deltaX = width - ScreenUtils.getScreenWidth();
                scrollView.scrollTo(x, 0);
                handler.post(runnable);
            }
        });



    }

    public Runnable runnable = new Runnable() {
        @SuppressLint("NewApi")
        @Override
        public void run() {
            if(x <= deltaX && x >= 0) {
                if(x > lastX) {
                    lastX = x;
                    x += 1;
                }else{
                    lastX = x;
                    x -= 1;
                }
            }else{
                if(x > deltaX){
                    lastX = x;
                    x = deltaX;
                }else{
                    lastX = x;
                    x = 0;
                }
            }
            scrollView.smoothScrollTo(x, 0);
            handler.postDelayed(runnable,50);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
