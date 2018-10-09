package com.shiyan.app.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.shiyan.app.R;

import java.io.File;

public class installApkActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_apk);

        btn_download = findViewById(R.id.btn_download);

        btn_download.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_download:

                //获取文件apk目录
                String filePath = getApkPath();

                LogUtils.eTag("filePath",filePath);

                FileDownloader.getImpl().create("https://img.owlcar.com/apk/owlcar_owlcar_code_18_v1.1.2_release.apk")
                        .setForceReDownload(true)
                        .setPath(filePath,false)
                        .setListener(new FileDownloadSampleListener(){
                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                super.progress(task, soFarBytes, totalBytes);
                                ToastUtils.showShort("下载过程:" + (int)((soFarBytes * 1.0f / totalBytes) * 100));
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                super.completed(task);

                                ToastUtils.showShort("下载完成");

                                //安装apk
                                installApk(task.getPath());
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                                super.error(task, e);
                            }
                        })
                        .start();

                break;
        }
    }

    /**
     * 安装apk
     */
    private void installApk(String filePath) {
        startActivity(IntentUtils.getInstallAppIntent(filePath,"com.shiyan.app"));
    }

    /**
     * 获取apk文件下载路径
     * @return
     */
    private String getApkPath() {
        StringBuffer filePath = new StringBuffer();
        filePath.append(getRootSaveRootPath());

        filePath.append(File.separator);

        filePath.append("apk");

        filePath.append(File.separator);

        filePath.append("test.apk");

        return filePath.toString();
    }

    private  String getRootSaveRootPath(){

        if (FileDownloadHelper.getAppContext().getExternalCacheDir() == null) {
            return Environment.getDownloadCacheDirectory().getAbsolutePath();
        } else {
            //noinspection ConstantConditions
            return FileDownloadHelper.getAppContext().getExternalCacheDir().getAbsolutePath();
        }
    }
}
