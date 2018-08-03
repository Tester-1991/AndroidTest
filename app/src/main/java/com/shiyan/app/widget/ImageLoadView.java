package com.shiyan.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.shiyan.app.R;
import com.shiyan.app.glide.CircleTransformation;
import com.shiyan.app.glide.CustomImageSizeModel;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.shiyan.app.glide.GlideOptions.bitmapTransform;

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
    }


    public void setIconImage(String url){

        CustomImageSizeModel customImageSizeModel = new CustomImageSizeModel(url);

        Glide.with(getContext().getApplicationContext())
                .load(customImageSizeModel)
                .into(this);
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

}
