package com.shiyan.app.glide;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;


/**
 * 作者: created by shiyan on 2018/8/1
 **/

public class CustomImageSizeUrlLoader extends BaseGlideUrlLoader<CustomImageSizeModel> {


    protected CustomImageSizeUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    @Override
    protected String getUrl(CustomImageSizeModel customImageSizeModel, int width, int height, Options options) {
        //对url进行处理
        return customImageSizeModel.requestCustomSizeUrl(width,height);
    }

    @Nullable
    @Override
    protected Headers getHeaders(CustomImageSizeModel customImageSizeModel, int width, int height, Options options) {
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        //添加统一图片请求头
        return  builder.build();
    }

    @Override
    public boolean handles(@NonNull CustomImageSizeModel customImageSizeModel) {
        return true;
    }

    public static class Factory  implements ModelLoaderFactory<CustomImageSizeModel,InputStream>{


        @NonNull
        @Override
        public ModelLoader<CustomImageSizeModel, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new CustomImageSizeUrlLoader(multiFactory.build(GlideUrl.class,InputStream.class));
        }

        @Override
        public void teardown() {

        }
    }


}
