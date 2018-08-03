package com.shiyan.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shiyan.app.R;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(this,GlideActivity.class));
    }


    private long currentTime;

    /**
     * 双击回退(2秒钟)
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {

                case KeyEvent.KEYCODE_BACK:

                    if (System.currentTimeMillis() - currentTime < 2000) {

                        finish();
                    }

                    currentTime = System.currentTimeMillis();

                    ToastUtils.showShort("再按一次退出");

                    return true;
            }
        }


        return super.dispatchKeyEvent(event);
    }
}
