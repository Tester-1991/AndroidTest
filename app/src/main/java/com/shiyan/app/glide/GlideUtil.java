package com.shiyan.app.glide;

import android.content.Context;
import android.os.AsyncTask;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * 作者: created by shiyan on 2018/8/3
 **/

public class GlideUtil {

    /**
     * 清理内存缓存
     * @param context
     */
    public static void clearMemory(Context context){

        // This method must be called on the main thread.
        Glide.get(context).clearMemory();

    }

    /**
     * 清理磁盘缓存
     * @param context
     */
    public static void clearDiskCache(Context context){

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                Glide.get(context.getApplicationContext()).clearDiskCache();
                return null;
            }
        };

    }

    public static void downloadImage(Context context,String url){
        new Thread(new Runnable() {
        FutureTarget<File> target = Glide.with(context).asFile().load(url).submit();
            @Override
            public void run() {
                try {
                    File file = target.get();
                    LogUtils.e("file",file.getAbsolutePath() + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
