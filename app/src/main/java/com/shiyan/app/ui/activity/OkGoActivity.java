package com.shiyan.app.ui.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.shiyan.app.R;
import com.shiyan.app.net.JsonCallBack;
import com.shiyan.app.net.entity.HttpResponse;

import java.io.File;

public class OkGoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_go);

        testGet();
    }

    public void testGet(){
        OkGo.<String>get("https://api.owlcar.com/api/sms/sendRegisterCode")
                .tag("sendRegisterCode")
                .params("mobile","15201438477")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LogUtils.eTag("response",response.body().toString());
                    }
                });
    }

    public void testGet2(){
        OkGo.<HttpResponse>get("https://api.owlcar.com/api/sms/sendRegisterCode")
                .tag("sendRegisterCode")
                .params("mobile","15201438477")
                .execute(new JsonCallBack<HttpResponse>() {
                    @Override
                    public void onSuccess(Response<HttpResponse> response) {
                        LogUtils.eTag("response",response.body().getTime());
                    }

                    @Override
                    public void onError(Response<HttpResponse> response) {
                        super.onError(response);

                        LogUtils.eTag("onError",response.code());
                    }
                });
    }

    public void testDownload(){
        OkGo.<File>get("https://img.owlcar.com/apk/owlcar_owlcar_code_18_v1.1.2_release.apk")
                .tag("testDownload")
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        LogUtils.eTag("onSuccess","下载结束");
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);

                        LogUtils.eTag("progress",progress.fraction * 100 + "%");
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        LogUtils.eTag("onStart","下载开始");
                    }
                });
    }

    public void testOkGoDownLoad(){
        GetRequest<File> request = OkGo.<File>get("https://img.owlcar.com/apk/owlcar_owlcar_code_18_v1.1.2_release.apk");

        DownloadTask downLoadTask = OkDownload.request("testDownload",request)
                .save()
                .register(new DownloadListener("DownloadListener") {
                    @Override
                    public void onStart(Progress progress) {
                        LogUtils.eTag("onStart","下载开始");
                    }

                    @Override
                    public void onProgress(Progress progress) {
                        LogUtils.eTag("progress",progress.fraction * 100 + "%");
                    }

                    @Override
                    public void onError(Progress progress) {

                    }

                    @Override
                    public void onFinish(File file, Progress progress) {
                        LogUtils.eTag("onSuccess","下载结束");
                    }

                    @Override
                    public void onRemove(Progress progress) {

                    }
                });
        downLoadTask.start();
    }
}
