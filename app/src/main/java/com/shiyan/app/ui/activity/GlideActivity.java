package com.shiyan.app.ui.activity;

import android.os.Bundle;

import com.shiyan.app.R;
import com.shiyan.app.glide.GlideUtil;
import com.shiyan.app.widget.ImageLoadView;

public class GlideActivity extends BaseActivity {

    private ImageLoadView iv_one;

    private String bookUrl = "http://www.guolin.tech/book.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        iv_one = findViewById(R.id.iv_one);

//        iv_one.setIconProgressImage(bookUrl);

        GlideUtil.downloadImage(this,bookUrl);

        iv_one.setIconImage(bookUrl);

    }

}
