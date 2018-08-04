package com.shiyan.app.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;


/**
 * 作者: created by shiyan on 2018/8/2
 *
 * 圆形头像支持CropType:top center bottom
 */
public class CircleTransformation implements Transformation<Bitmap> {

    private static final int VERSION = 1;
    private static final String ID =
            "com.shiyan.app.glide.CircleTransformation." + VERSION;

    private BitmapPool mBitmapPool;
    private CropType mCropType = CropType.CENTER;

    public enum CropType {
        TOP,
        CENTER,
        BOTTOM

    }

    public CircleTransformation(Context context) {
        this(Glide.get(context).getBitmapPool(), CropType.CENTER);
    }

    public CircleTransformation(Context context, CropType cropType) {
        this(Glide.get(context).getBitmapPool(), cropType);
    }

    public CircleTransformation(BitmapPool pool, CropType cropType) {
        this.mBitmapPool = pool;
        this.mCropType = cropType;
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int w = source.getWidth();
        int h = source.getHeight();
        int size = Math.min(w, h);

        int width = (w - size) / 2;
        int height = (h - size) / 2;

        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader =
                new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            // 图像不是正方形,移动窗口
            Matrix matrix = new Matrix();
            switch (mCropType) {
                case TOP:
                    matrix.setTranslate(-width, 0);
                    break;
                case CENTER:
                    matrix.setTranslate(-width, -height);
                    break;
                case BOTTOM:
                    matrix.setTranslate(-width, -(h-size));
                    break;
            }
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID).getBytes(CHARSET));
    }

}

