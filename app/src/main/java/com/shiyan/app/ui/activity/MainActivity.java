package com.shiyan.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shiyan.app.R;
import com.shiyan.app.anno.Network;
import com.shiyan.app.manager.NetWorkManager;

public class MainActivity extends BaseActivity {

    private long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetWorkManager.getInstance().registerOberver(this);

        Intent intent = new Intent(this, CanvasActivity.class);

        startActivity(intent);

    }

    /**
     * 监听网络状态回调处理方法
     */
    @Network
    public void netChage() {
        Log.e("netChage", NetworkUtils.isConnected() + "");
    }

    /**
     * 双击回退(2秒钟)
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        NetWorkManager.getInstance().unregisterObserver(this);

        NetWorkManager.getInstance().unregisterAllObserver();
    }
}
