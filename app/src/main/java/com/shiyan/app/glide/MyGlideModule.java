package com.shiyan.app.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.shiyan.app.R;
import com.shiyan.app.util.AppUtil;

import java.io.InputStream;

/**
 * 作者: created by shiyan on 2018/8/1
 * <p>
 * ExternalCacheDiskCacheFactory的默认缓存路径是在sdcard/Android/包名/cache/image_manager_disk_cache
 **/

@GlideModule
public class MyGlideModule extends AppGlideModule {

    private static final int ratio = 8;

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

        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, (int) (availableCount / ratio)));

        //配置硬盘存储存储到sd卡
        //builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context));

        RequestOptions requestOptions = new RequestOptions();
        //配置解码方式
        requestOptions.format(DecodeFormat.PREFER_RGB_565)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.icon_normal_default_bg)
                .error(R.drawable.icon_normal_default_bg)
                .priority(Priority.HIGH);

        builder.setDefaultRequestOptions(requestOptions);
    }

    //注册组件
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

        registry.append(CustomImageSizeModel.class, InputStream.class, new CustomImageSizeUrlLoader.Factory());

    }
}
