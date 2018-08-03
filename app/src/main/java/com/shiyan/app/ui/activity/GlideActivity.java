package com.shiyan.app.ui.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.shiyan.app.R;
import com.shiyan.app.glide.CircleTransformation;
import com.shiyan.app.glide.CustomImageSizeModel;
import com.shiyan.app.glide.GlideUtil;
import com.shiyan.app.widget.ImageLoadView;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class GlideActivity extends BaseActivity {

    private ImageLoadView iv_one;

    private String bookUrl = "http://www.guolin.tech/book.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        iv_one = findViewById(R.id.iv_one);

//        iv_one.setIconProgressImage(bookUrl);

//        GlideUtil.downloadImage(this,bookUrl);

        iv_one.setIconImage(bookUrl);
    }

}
