package com.shiyan.app.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.shiyan.app.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class TakePhotoActivity extends BaseActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks {

    private Button btn_camera;

    private Button btn_alumn;

    private String[] photoManifests = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        btn_camera = findViewById(R.id.btn_camera);

        btn_alumn = findViewById(R.id.btn_alumn);

        btn_camera.setOnClickListener(this);

        btn_alumn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera:

                cameraAction();

                break;
            case R.id.btn_alumn:


                break;
        }
    }

    private void cameraAction() {
        //检查系统相机权限
        if(EasyPermissions.hasPermissions(TakePhotoActivity.this,photoManifests)){
             takeCamera();
        }else{
            EasyPermissions.requestPermissions(TakePhotoActivity.this,"需要申请权限?",0,photoManifests);
        }
    }

    /**
     * 调用系统相机
     */
    private void takeCamera() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(perms.size() == photoManifests.length) {
            cameraAction();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}
