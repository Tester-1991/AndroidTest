package com.shiyan.app.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.shiyan.app.R;
import com.shiyan.app.util.ResolutionUtil;

import org.joor.Reflect;

import java.util.ArrayList;

public class CommonTabLayoutActivity extends BaseActivity {

    private CommonTabLayout commonTabLayout;

    private ArrayList<CustomTabEntity> list = new ArrayList<>();

    private ResolutionUtil resolution;

    private HorizontalScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tab_layout);

        resolution = new ResolutionUtil(this);

        nestedScrollView = findViewById(R.id.scrollView);

        nestedScrollView.setHorizontalScrollBarEnabled(false);

        RelativeLayout.LayoutParams nestedScrollViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,resolution.px2dp2pxHeight(60));

        nestedScrollView.setLayoutParams(nestedScrollViewParams);

        commonTabLayout = findViewById(R.id.tabLayout);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);

        commonTabLayout.setLayoutParams(params);

        for(int i = 0; i < 20; i++){
            CustomTabEntity entity = new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return "热门";
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            };

            list.add(entity);
        }
        commonTabLayout.setTextsize(resolution.px2sp2px(28));
//        commonTabLayout.setIndicatorWidthFre(resolution.px2dp2pxWidth(20));
        Reflect.on(commonTabLayout).set("mIndicatorWidth",resolution.px2dp2pxWidth(20));
        commonTabLayout.setIndicatorColor(Color.rgb(255,196,0));
//        commonTabLayout.setIndicatorHeightFre(resolution.px2dp2pxHeight(5));
        Reflect.on(commonTabLayout).set("mIndicatorHeight",resolution.px2dp2pxHeight(5));
        commonTabLayout.setTextSelectColor(Color.rgb(50,50,50));
        commonTabLayout.setTextUnselectColor(Color.rgb(161,161,161));
        commonTabLayout.setTextBold(Reflect.on(commonTabLayout).get("TEXT_BOLD_BOTH"));
        LogUtils.eTag("bold:" + Reflect.on(commonTabLayout).get("TEXT_BOLD_BOTH"));
//        commonTabLayout.setTabPaddingFre(resolution.px2dp2pxWidth(25));
        Reflect.on(commonTabLayout).set("mTabPadding",resolution.px2dp2pxWidth(25));
        int childCount = commonTabLayout.getChildCount();
        for(int i = 0; i < childCount; i++){
            View child = commonTabLayout.getChildAt(i);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) child.getLayoutParams();
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            if(i != 0) {
                layoutParams.leftMargin = resolution.px2dp2pxWidth(80);
            }
            child.setLayoutParams(layoutParams);
        }
        commonTabLayout.setTabData(list);

        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                ToastUtils.showShort(position + "");
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
}
