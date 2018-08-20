package com.shiyan.app.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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

        iv_photo.setImageBitmap( createRoundRectBitmap(bitmap,50));
    }

    /**
     * 图片裁剪成圆角(利用RoundRect)
     * @param bitmap
     * @return
     */
    private Bitmap createRoundRectBitmap(Bitmap bitmap,int roundPx){

        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
//            paint.setColor(Color.BLACK);
            final RectF rectF = new RectF(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
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
     * 图片裁剪成圆形
     * @param bitmap
     * @return
     */
    private Bitmap createCircleImage(Bitmap bitmap){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        int radius;

        if(bitmap.getWidth() > bitmap.getHeight()){

            radius = bitmap.getHeight() / 2;

        }else{

            radius = bitmap.getWidth() / 2;

        }

        //绘制圆形
        canvas.drawCircle(output.getWidth() / 2,output.getHeight() / 2,radius,paint);

        //使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //绘制图片
        canvas.drawBitmap(bitmap,0,0,paint);

        return output;
    }
}
