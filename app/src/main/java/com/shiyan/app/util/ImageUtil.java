package com.shiyan.app.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 作者: created by shiyan on 2018/7/26
 **/

public class ImageUtil {

    /**
     * 单例模式 构造方法私有化
     */
    private ImageUtil() {
    }

    public static ImageUtil getInstance(){
        return ImageUtilHolder.instance;
    }

    private static class ImageUtilHolder{

        private static final ImageUtil instance = new ImageUtil();

    }

    /**
     * drawable转bitmap
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable){

        if(drawable instanceof BitmapDrawable){

            BitmapDrawable bd = (BitmapDrawable) drawable;

            return bd.getBitmap();
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0,0,w,h);

        drawable.draw(canvas);

        return bitmap;
    }
}
