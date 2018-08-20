package com.shiyan.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.shiyan.app.glide.CircleTransformation;
import com.shiyan.app.glide.CustomImageSizeModel;
import com.shiyan.app.glide.progress.ProgressInterceptor;
import com.shiyan.app.glide.progress.ProgressListener;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * 作者: created by shiyan on 2018/8/1
 **/

@SuppressLint("AppCompatCustomView")
public class ImageLoadView extends ImageView {


    public ImageLoadView(Context context) {
        this(context,null);
    }

    public ImageLoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageLoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setScaleType(ScaleType.FIT_XY);
    }


    public void setIconImage(String url){

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        Glide.with(getContext().getApplicationContext())
                .load(customImageSizeModel)
                .into(this);
    }

    public void setIconProgressImage(String url){

        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.e("onProgress",progress + "");
            }
        });

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        Glide.with(getContext().getApplicationContext())
                .load(customImageSizeModel)
                .into(new DrawableImageViewTarget(this){

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        super.onLoadStarted(placeholder);

                        Log.e("onLoadStarted","onLoadStarted");
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        Log.e("onResourceReady","onResourceReady");
                    }
                });
    }

    public void setCircleImage(String url){

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        Glide.with(getContext().getApplicationContext())
                .load(customImageSizeModel)
                .apply(bitmapTransform(new CircleCrop()))
                .into(this);
    }

    public void setCircleCropImage(String url,CircleTransformation.CropType cropType){

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        Glide.with(getContext().getApplicationContext())
                .load(customImageSizeModel)
                .apply(bitmapTransform(new CircleTransformation(getContext().getApplicationContext(),cropType)))
                .into(this);
    }

    public void setRoundImage(String url){

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        Glide.with(getContext().getApplicationContext())
                .load(customImageSizeModel)
                .apply(bitmapTransform(new RoundedCornersTransformation(50,0)))
                .into(this);
    }

    public void setBlurImage(String url){

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        Glide.with(getContext().getApplicationContext())
                .load(customImageSizeModel)
                .apply(bitmapTransform(new BlurTransformation(10,3)))
                .into(this);
    }

    /**
     * 只从缓存里面加载图片
     * @param url
     */
    public void setRetrieveFromCacheImageIcon(String url){

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.onlyRetrieveFromCache(true);

        Glide.with(getContext().getApplicationContext())
                .load(url)
                .apply(requestOptions)
                .into(this);
    }

}
