package com.shiyan.app.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.blankj.utilcode.util.ToastUtils;

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setFullScreen(){
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void test(){
        ThreadLocal<String> threadLocal = new ThreadLocal(){
            @Override
            protected Object initialValue() {
                return "1";
            }
        };

        ToastUtils.showShort(threadLocal.get());
    }


}
