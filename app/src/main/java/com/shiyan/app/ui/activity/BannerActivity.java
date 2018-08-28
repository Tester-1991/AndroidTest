package com.shiyan.app.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiyan.app.R;
import com.shiyan.app.extra.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.view.BannerViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BannerActivity extends BaseActivity implements OnBannerListener {

    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        banner = findViewById(R.id.banner);

        updateWidth();

        String[] urls = getResources().getStringArray(R.array.url);

        List list = Arrays.asList(urls);

        List<String> imageList = new ArrayList<>(list);

        banner.setImages(imageList);

        banner.setImageLoader(new GlideImageLoader());

        banner.setOnBannerListener(this);

        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);

        BannerViewPager bannerViewPager = banner.findViewById(R.id.bannerViewPager);

        bannerViewPager.setClipChildren(false);

        bannerViewPager.setClipChildren(false);

        bannerViewPager.setPageMargin(60);

        RelativeLayout.LayoutParams bannerParams = (RelativeLayout.LayoutParams) bannerViewPager.getLayoutParams();

        bannerParams.leftMargin = 120;
        bannerParams.rightMargin = 120;

        bannerViewPager.setLayoutParams(bannerParams);

        RelativeLayout parent = (RelativeLayout) bannerViewPager.getParent();

        TextView title = new TextView(this);
        title.setText("123");
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleParams.topMargin = 120;
        titleParams.leftMargin = 120;
        title.setLayoutParams(titleParams);
        parent.addView(title);


        banner.start();

    }

    private void updateWidth() {
        try {
            Field privateIntField = Banner.class.getDeclaredField("mIndicatorWidth");
            privateIntField.setAccessible(true);
            privateIntField.set(banner, LinearLayout.LayoutParams.WRAP_CONTENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
