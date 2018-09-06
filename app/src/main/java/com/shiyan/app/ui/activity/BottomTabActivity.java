package com.shiyan.app.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.shiyan.app.R;
import com.shiyan.app.ui.fragment.HomePageFragment;
import com.shiyan.app.ui.fragment.LiveFragment;
import com.shiyan.app.ui.fragment.MineFragment;

public class BottomTabActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_firstpage;

    private RelativeLayout rl_live;

    private RelativeLayout rl_mine;

    private String[] TAG_FRAGMENT = {"HomePageFragment","LiveFragment","MineFragment"};

    private HomePageFragment homePageFragment;

    private LiveFragment liveFragment;

    private MineFragment mineFragment;

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);

        rl_firstpage = findViewById(R.id.rl_homepage);

        rl_live = findViewById(R.id.rl_live);

        rl_mine = findViewById(R.id.rl_mine);

        rl_firstpage.setOnClickListener(this);

        rl_live.setOnClickListener(this);

        rl_mine.setOnClickListener(this);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt("fragment_index");
            homePageFragment = (HomePageFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT[0]);
            liveFragment =  (LiveFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT[1]);
            mineFragment =  (MineFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT[2]);
        }
        setSelect(0);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.rl_homepage:

                setSelect(0);

                break;
            case R.id.rl_live:


                setSelect(1);

                break;
            case R.id.rl_mine:

                setSelect(2);

                break;
        }
    }

    private void setSelect(int i) {
        currentIndex = i;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (i){
            case 0:
                if(homePageFragment == null){
                    homePageFragment = HomePageFragment.newInstance();
                }

                if(!homePageFragment.isAdded()){
                    transaction.add(R.id.container,homePageFragment,TAG_FRAGMENT[0]);
                }else{
                    transaction.show(homePageFragment);
                }
                break;
            case 1:
                if(liveFragment == null){
                    liveFragment = LiveFragment.newInstance();
                }

                if(!liveFragment.isAdded()){
                    transaction.add(R.id.container,liveFragment,TAG_FRAGMENT[1]);
                }else{
                    transaction.show(liveFragment);
                }
                break;
            case 2:
                if(mineFragment == null){
                    mineFragment = MineFragment.newInstance();
                }

                if(!mineFragment.isAdded()){
                    transaction.add(R.id.container,mineFragment,TAG_FRAGMENT[2]);
                }else{
                    transaction.show(mineFragment);
                }
                break;
        }

        transaction.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("fragment_index",currentIndex);
        super.onSaveInstanceState(outState);
    }
}
