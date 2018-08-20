package com.shiyan.app.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.shiyan.app.R;
import com.shiyan.app.extra.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

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
        try {
            Field privateIntField = Banner.class.getDeclaredField("mIndicatorWidth");
            privateIntField.setAccessible(true);
            privateIntField.set(banner, LinearLayout.LayoutParams.WRAP_CONTENT);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        String[] urls = getResources().getStringArray(R.array.url);

        List list = Arrays.asList(urls);

        List<String> imageList = new ArrayList<>(list);

        banner.setImages(imageList);

        banner.setImageLoader(new GlideImageLoader());

        banner.setOnBannerListener(this);

        banner.start();

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
