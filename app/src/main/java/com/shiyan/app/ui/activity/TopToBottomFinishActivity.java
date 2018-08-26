package com.shiyan.app.ui.activity;

import android.os.Bundle;

import com.shiyan.app.R;
import com.shiyan.app.widget.TopToBottomFinishLayout;

public class TopToBottomFinishActivity extends BaseActivity {

    private TopToBottomFinishLayout finishLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_to_bottom_finish);

        finishLayout = findViewById(R.id.finishLayout);

    }
}
