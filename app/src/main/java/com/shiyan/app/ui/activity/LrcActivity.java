package com.shiyan.app.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shiyan.app.R;
import com.shiyan.app.entity.LrcEntity;
import com.shiyan.app.util.LrcUtil;

import java.util.List;

public class LrcActivity extends BaseActivity {

    private  final String TAG = getClass().getSimpleName();

    private int i = -1;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            i++;

            LrcEntity entity = datas.get(i);
            textView.setText(entity.getTime() + entity.getText());

            handler.sendEmptyMessageDelayed(1,1000);
        }
    };
    private List<LrcEntity> datas;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lrc);

        String lrcMessage = LrcUtil.getLrcText(this, "test2.lrc");

        textView = findViewById(R.id.tv_message);

        textView.setText(lrcMessage);

        datas = LrcEntity.parseLrc(lrcMessage);

        handler.sendEmptyMessageDelayed(1,1000);
    }
}
