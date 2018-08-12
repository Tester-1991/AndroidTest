package com.shiyan.app.ui.activity;

import android.os.Bundle;

import com.shiyan.app.widget.LifeCycleView;

public class LifeCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LifeCycleView lifeCycleView = new LifeCycleView(this);
        lifeCycleView.setLifecycle(getLifecycle());
        setContentView(lifeCycleView);
    }
}
