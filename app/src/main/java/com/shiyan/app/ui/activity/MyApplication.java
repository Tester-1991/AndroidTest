package com.shiyan.app.ui.activity;

import android.app.Application;

import com.blankj.utilcode.util.ActivityUtils;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;

import org.xutils.x;


public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);

        //初始化filedownloader
        initFileDownLoader();

    }

    private void initFileDownLoader() {
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection.Creator(new FileDownloadUrlConnection.Configuration()
                .connectTimeout(15 * 1000)
                .readTimeout(15 * 1000)))
                .commit();
    }
}
