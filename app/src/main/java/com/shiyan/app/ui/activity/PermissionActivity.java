package com.shiyan.app.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.shiyan.app.R;

/**
 * 检查读写SD卡权限
 */
public class PermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        //如果当前没有获取权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //当用户第一次申请拒绝时，再次申请该权限调用
            if(ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ToastUtils.showShort("申请权限");
            }
            //申请权限
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0x01);
        } else{//已经获得了权限

            ToastUtils.showShort("已经获取权限");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0x01){
            //授权成功
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                ToastUtils.showShort("获取权限成功");

            }else{//授权失败

                ToastUtils.showShort("获取权限失败");

            }
        }
    }
}
