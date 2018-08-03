package com.shiyan.app.glide;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.shiyan.app.R;
import com.shiyan.app.ui.activity.PermissionActivity;
import com.shiyan.app.util.AppUtil;

import java.io.File;
import java.io.InputStream;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * 作者: created by shiyan on 2018/8/1
 * <p>
 * ExternalPreferredCacheDiskCacheFactory/Android/包名/cache/image_manager_disk_cache
 **/

@GlideModule
public class MyGlideModule extends AppGlideModule {

    private static final int ratio = 8;

    private static final String CACHE_FILE_NAME = "imgCache";

    private static final int MAX_CACHE_SIZE = 50 * 1024 * 1024;


    //修改Glide配置
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

        long availableCount = AppUtil.getAvailableMemory(context);

        long maxMemory = Runtime.getRuntime().maxMemory();

        if (maxMemory > availableCount) {

            builder.setMemoryCache(new LruResourceCache((int) (availableCount / ratio)));

            builder.setBitmapPool(new LruBitmapPool((int) (availableCount / ratio)));

        } else {

            builder.setMemoryCache(new LruResourceCache((int) (maxMemory / ratio)));

            builder.setBitmapPool(new LruBitmapPool((int) (maxMemory / ratio)));

        }
//        if (EasyPermissions.hasPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                String downloadDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                        CACHE_FILE_NAME;
                //路径---->sdcard/imgCache
                builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, MAX_CACHE_SIZE));
            } else {
                //路径---->/sdcard/Android/data/<application package>/cache/imgCache
                builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE));
            }
//        } else {
//
//            LogUtils.e("没有权限");
//
//        }

        RequestOptions requestOptions = new RequestOptions();
        //配置解码方式
        requestOptions.format(DecodeFormat.PREFER_RGB_565)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.icon_normal_default_bg)
                .error(R.drawable.icon_normal_default_bg)
                .priority(Priority.HIGH);

        builder.setDefaultRequestOptions(requestOptions);

        builder.setLogLevel(Log.VERBOSE);
    }

    //注册组件
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

        registry.append(CustomImageSizeModel.class, InputStream.class, new CustomImageSizeUrlLoader.Factory());

    }

    /**
     * 禁止解析Manifest文件
     *
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        //主要针对V3升级到v4的用户，可以提升初始化速度，避免一些潜在错误。
        return false;
    }
}
