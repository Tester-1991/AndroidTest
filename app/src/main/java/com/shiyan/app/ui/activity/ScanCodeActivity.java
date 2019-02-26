package com.shiyan.app.ui.activity;

import android.Manifest;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.shiyan.app.R;

import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 二维码扫描测试
 */
public class ScanCodeActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, QRCodeView.Delegate {

    private int requestCode = 1;

    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    private ZXingView zXingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        zXingView = findViewById(R.id.zxingview);

        zXingView.setDelegate(this);

        if (EasyPermissions.hasPermissions(this, permissions)) {
            //开始扫码
            startScan();
        } else {
            //请求权限
            EasyPermissions.requestPermissions(this, "扫描二维码需要相机和读取存储权限?", requestCode, permissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //将请求结果传递到EasyPermissions库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == this.requestCode && perms.size() == 2) {
            startScan();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    private void startScan() {

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        ToastUtils.showShort("扫描成功:" + result);

        zXingView.startSpot();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.showShort("打开相机失败");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开后置摄像头开始预览，但是并未开始识别
        zXingView.startCamera();
        // 显示扫描框，并开始识别
        zXingView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        zXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }
}
