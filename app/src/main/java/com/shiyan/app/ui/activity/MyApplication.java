package com.shiyan.app.ui.activity;

import android.app.Application;

import com.baidu.mobstat.StatService;
import com.blankj.utilcode.util.LogUtils;
import com.facebook.stetho.Stetho;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.shiyan.app.dbflow.AppDatabase;


public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化filedownloader
        initFileDownLoader();

        //初始化DBFlow
        FlowManager.init(FlowConfig.builder(this)
                .addDatabaseConfig(DatabaseConfig.builder(AppDatabase.class)
                        .databaseName(AppDatabase.NAME)
                        .build())
                .build());

        //初始化Stetho
        Stetho.initializeWithDefaults(this);

        StatService.autoTrace(this);

        String testDeviceId = StatService.getTestDeviceId(this);

        LogUtils.eTag("testDeviceId",testDeviceId);

    }

    private void initFileDownLoader() {
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection.Creator(new FileDownloadUrlConnection.Configuration()
                .connectTimeout(15 * 1000)
                .readTimeout(15 * 1000)))
                .commit();
    }
}
