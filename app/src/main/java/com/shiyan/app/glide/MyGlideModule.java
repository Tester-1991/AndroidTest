package com.shiyan.app.glide;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.shiyan.app.R;
import com.shiyan.app.util.AppUtil;

import java.io.File;
import java.io.InputStream;

/**
 * 作者: created by shiyan on 2018/8/1
 *
 * /storage/emulated/0/Android/data/com.shiyan.app/cache/imgCache/792c7504180865d06349c50b081a9537ed5e95afb5bb8cbbd6f9dcdad7fb5bbc.0
 * ExternalPreferredCacheDiskCacheFactory(context, "imgCache", MAX_CACHE_SIZE)
 *
 * /storage/emulated/0/Android/data/com.shiyan.app/cache/cache_dir_name2/792c7504180865d06349c50b081a9537ed5e95afb5bb8cbbd6f9dcdad7fb5bbc.0
 * File cacheLocation = new File(context.getExternalCacheDir(), "cache_dir_name2");
 *
 * /data/user/0/com.shiyan.app/cache/image_manager_disk_cache/792c7504180865d06349c50b081a9537ed5e95afb5bb8cbbd6f9dcdad7fb5bbc.0
 * InternalCacheDiskCacheFactory(context,MAX_CACHE_SIZE)
 *
 *
 *
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

//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                String downloadDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator +
                        CACHE_FILE_NAME;
                //路径---->sdcard/imgCache
                builder.setDiskCache(new DiskCache.Factory() {
                    @Override
                    public DiskCache build() {
                        // Careful: the external cache directory doesn't enforce permissions
                        File cacheLocation = new File(context.getExternalCacheDir(), "cache_dir_name2");
//                        File cacheLocation = new File(downloadDirectoryPath);
//                        cacheLocation.mkdirs();
                        return DiskLruCacheWrapper.get(cacheLocation, MAX_CACHE_SIZE);
                    }
                });

//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath,maxMemory));
//            } else {
//                //路径---->/sdcard/Android/data/<application package>/cache/imgCache
//                builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE));
//            }
//         builder.setDiskCache(new InternalCacheDiskCacheFactory(context,MAX_CACHE_SIZE));

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
