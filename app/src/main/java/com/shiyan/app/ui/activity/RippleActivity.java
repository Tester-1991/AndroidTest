package com.shiyan.app.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.meituan.android.walle.WalleChannelReader;
import com.shiyan.app.R;

public class RippleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);

        TextView tv_one = findViewById(R.id.tv_one);

        String value = WalleChannelReader.get(getApplicationContext(), "channel");

        tv_one.setText(value + "----");

    }
}
