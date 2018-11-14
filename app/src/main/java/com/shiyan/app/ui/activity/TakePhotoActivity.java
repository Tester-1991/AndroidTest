package com.shiyan.app.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shiyan.app.R;
import com.shiyan.app.util.PhotoUtil;

import java.io.File;
import java.net.URI;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class TakePhotoActivity extends BaseActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks {

    private Button btn_camera;

    private Button btn_alumn;

    private static final int CODE_GALLERY_REQUEST = 0xa0;

    private static final int CODE_CAMERA_REQUEST = 0xa1;

    private static final int CODE_RESULT_REQUEST = 0xa2;

    private String[] photoManifests = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "photo.jpg");

    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "crop_photo.jpg");

    private Uri imageUri;

    private Uri cropImageUri;

    private static final int OUTPUT_X = 480;

    private static final int OUTPUT_Y = 480;

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

                albumnAction();

                break;
        }
    }

    /**
     * 相册动作
     */
    private void albumnAction() {
        if(EasyPermissions.hasPermissions(TakePhotoActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            takeAlbumn();
        }else{
            EasyPermissions.requestPermissions(TakePhotoActivity.this,"需要申请sd卡权限?",1,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    /**
     * 相机动作
     */
    private void cameraAction() {
        //检查系统相机权限
        if(EasyPermissions.hasPermissions(TakePhotoActivity.this,photoManifests)){
             takeCamera();
        }else{
            EasyPermissions.requestPermissions(TakePhotoActivity.this,"需要申请权限?",100,photoManifests);
        }
    }

    /**
     * 调用系统相机
     */
    private void takeCamera() {
        //如果当前存在sd卡
        if(SDCardUtils.isSDCardEnable()){
            imageUri = Uri.fromFile(fileUri);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){

                imageUri = FileProvider.getUriForFile(TakePhotoActivity.this,"com.shiyan.app",fileUri);

            }

            PhotoUtil.takePicture(TakePhotoActivity.this,imageUri,CODE_CAMERA_REQUEST);
        }
    }

    /**
     * 调用系统相册
     */
    private void takeAlbumn() {
        PhotoUtil.openPic(TakePhotoActivity.this,CODE_GALLERY_REQUEST);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(requestCode == 100 && perms.size() == photoManifests.length) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            switch (requestCode){
                //相机拍照
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);

                    PhotoUtil.cropImageUri(TakePhotoActivity.this,imageUri,cropImageUri,1,1,OUTPUT_X,OUTPUT_Y,CODE_RESULT_REQUEST);
                    break;

                case CODE_GALLERY_REQUEST:

                    if (SDCardUtils.isSDCardEnable()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtil.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this, "com.shiyan.app", new File(newUri.getPath()));
                        }
                        PhotoUtil.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    }
                    break;
            }
        }
    }
}
