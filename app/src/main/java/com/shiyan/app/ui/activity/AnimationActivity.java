package com.shiyan.app.ui.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.github.florent37.viewanimator.ViewAnimator;
import com.shiyan.app.R;

/**
 * 动画效果演示
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    private Button btnTranslation;

    private RelativeLayout rlTranslationLayout;

    private Button btnTranslationReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        btnTranslation = findViewById(R.id.btn_transaltion);

        rlTranslationLayout = findViewById(R.id.rl_translationlayout);

        btnTranslationReset = findViewById(R.id.btn_transaltion_reset);

        btnTranslation.setOnClickListener(this);

        btnTranslationReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //平移动画
            case R.id.btn_transaltion:

//                CommonTranslationX(rlTranslationLayout, 0, 300);

                translationX(rlTranslationLayout,0,300);

                break;

            case R.id.btn_transaltion_reset:

//                CommonTranslationX(rlTranslationLayout, 300, 0);

                translationX(rlTranslationLayout,300,0);
                break;
        }
    }

    /**
     * 水平平移动画
     * @param view
     * @param i
     * @param j
     */
    public void CommonTranslationX(View view, int i, int j) {
        ObjectAnimator transaltionObjectAnimator = ObjectAnimator.ofFloat(view, "translationX", i, j);

        transaltionObjectAnimator.setDuration(1000);

        transaltionObjectAnimator.start();
    }

    /**
     * 水平平移动画
     * @param view
     */
    public void translationX(View view,int i,int j){
        ViewAnimator.animate(view)
                .translationX(i,j)
                .duration(1000)
                .start();
    }


}
