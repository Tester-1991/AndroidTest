package com.shiyan.app.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.ImageView;

import com.shiyan.app.R;

public class RoundImageViewActivity extends BaseActivity {

    private ImageView iv_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_image_view);

        iv_photo = findViewById(R.id.iv_photo);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.time);

        iv_photo.setImageBitmap( createBitmapShaderRoundRectBitmap(bitmap,50,600,600));
    }

    /**
     * 图片裁剪成圆角(利用RoundRect,Xfermode)
     * @param bitmap
     * @return
     */
    private Bitmap createXfermodeRoundRectBitmap(Bitmap bitmap,int angle){

        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final RectF rectF = new RectF(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            paint.setAntiAlias(true);
            canvas.drawRoundRect(rectF, angle, angle, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    /**
     * 图片裁剪成圆形(利用RoundRect,Xfermode)
     * @param bitmap
     * @return
     */
    private Bitmap createXfermodeCircleBitmap(Bitmap bitmap){

        int radius = Math.min(bitmap.getWidth() / 2,bitmap.getHeight() /2 );

        bitmap = Bitmap.createScaledBitmap(bitmap,2 * radius,2 * radius,true);

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        //绘制圆形
        canvas.drawCircle(radius,radius,radius,paint);

        //使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //绘制图片
        canvas.drawBitmap(bitmap,0,0,paint);

        return output;
    }

    /**
     * 图片裁剪成圆角(BitmapShader)
     * @param bitmap
     * @param angle
     * @return
     */
    private Bitmap createBitmapShaderRoundRectBitmap(Bitmap bitmap,int angle,int width,int height){

        Bitmap out = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        float scale = Math.max(width * 1.0f / out.getWidth(),height * 1.0f / out.getHeight());

        Matrix matrix = new Matrix();
        matrix.setScale(scale,scale);
        //设置变换矩阵
        bitmapShader.setLocalMatrix(matrix);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        Canvas canvas = new Canvas(out);
        RectF rectF = new RectF(0,0,width,height);
        canvas.drawRoundRect(rectF,angle,angle,paint);

        return  out;

    }


    /**
     * 图片裁剪成圆(BitmapShader)
     * @param bitmap
     * @return
     */
    private Bitmap createBitmapShaderCircleBitmap(Bitmap bitmap,int radius){

        int width = radius * 2;
        int height = radius * 2;

        Bitmap out = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        float scale = width * 1.0f / Math.min(out.getWidth(),out.getHeight());

        Matrix matrix = new Matrix();
        matrix.setScale(scale,scale);
        //设置变换矩阵
        bitmapShader.setLocalMatrix(matrix);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);

        Canvas canvas = new Canvas(out);
        canvas.drawCircle(width * 1.0f / 2 ,width * 1.0f / 2, width * 1.0f / 2,paint);

        return  out;

    }
}
