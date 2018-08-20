package com.shiyan.app.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.shiyan.app.R;
import com.shiyan.app.entity.VideoAutoPlayEntity;
import com.shiyan.app.util.PlayerManager;
import com.shiyan.app.view.adapter.VideoAutoPlayAdapter;
import com.shiyan.app.view.item.VideoAutoPlayItemView;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class VideoAutoPlayActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private VideoAutoPlayAdapter videoAutoPlayAdapter;

    private List<VideoAutoPlayEntity> list = new ArrayList<>();

    private int lastVisibleItem;

    private int totalItemCount;

    private int firstVisiblePosition;

    private int lastVisiblePosition;

    private int visibleCount;

    private LinearLayoutManager linearManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_auto_play);

        recyclerView = findViewById(R.id.recyclerView);

        linearManager = new LinearLayoutManager(this);

        linearManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearManager);

        recyclerView.addOnScrollListener(onScrollListener);

        initData();

        videoAutoPlayAdapter = new VideoAutoPlayAdapter(this,list);

        recyclerView.setAdapter(videoAutoPlayAdapter);

        recyclerView.post(() -> {
            //自动播放第一个
            if (recyclerView.getChildCount() > 0) {
                if (recyclerView.getChildAt(0) instanceof VideoAutoPlayItemView) {
                    VideoAutoPlayItemView videoAutoPlayItemView = (VideoAutoPlayItemView) recyclerView.getChildAt(0);
                    videoAutoPlayItemView.autoPlayVideo();
                }
            }
        });



    }

    /**
     * 初始化数据源
     */
    private void initData() {
        for(int i = 0; i < 10; i++) {
            VideoAutoPlayEntity videoAutoPlayEntity = new VideoAutoPlayEntity();
            videoAutoPlayEntity.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534765256400&di=d6063951743bd9aef2022f05102a3fac&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2017-11-08%2F5a02ae7dd9db4.jpg");
            videoAutoPlayEntity.setVideoUrl("http://ips.ifeng.com/video19.ifeng.com/video09/2017/05/24/4664192-102-008-1012.mp4");
            list.add(videoAutoPlayEntity);
        }
    }

    /**
     * 自动播放
     * @param view
     */
    public void autoPlayVideo(RecyclerView view) {
        //循环遍历可视区域videoview,如果完全可见就开始播放
        for (int i = 0; i < visibleCount; i++) {
            if (view == null || view.getChildAt(i) == null) continue;
            RelativeLayout videoLayout = view.getChildAt(i).findViewById(R.id.video_autoplay);
            if (videoLayout != null) {
                Rect rect = new Rect();
                videoLayout.getLocalVisibleRect(rect);
                int videoHeight = videoLayout.getHeight();
                if (rect.top == 0 && rect.bottom == videoHeight) {
                    if(view.getChildAt(i) instanceof VideoAutoPlayItemView){
                        VideoAutoPlayItemView videoAutoPlayItemView = (VideoAutoPlayItemView) view.getChildAt(i);
                        videoAutoPlayItemView.autoPlayVideo();
                    }
                    return;
                }
            }
        }

        //处理滑动到最后一个位置
        if(lastVisibleItem == totalItemCount - 1) {
            if (recyclerView.getChildCount() > 0) {
                if (recyclerView.getChildAt(recyclerView.getChildCount() - 1) instanceof VideoAutoPlayItemView) {
                    VideoAutoPlayItemView videoAutoPlayItemView = (VideoAutoPlayItemView) recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    videoAutoPlayItemView.autoPlayVideo();
                }
            }
        }
    }

    //---------------------------------listener---------------------------------
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener(){
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            switch (newState) {
                case SCROLL_STATE_IDLE: //滚动停止

                    lastVisibleItem = linearManager.findLastCompletelyVisibleItemPosition();

                    totalItemCount = linearManager.getItemCount();

                    autoPlayVideo(recyclerView);
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            firstVisiblePosition = linearManager.findFirstVisibleItemPosition();

            lastVisiblePosition = linearManager.findLastVisibleItemPosition();

            visibleCount = lastVisiblePosition - firstVisiblePosition;

            lastVisibleItem = linearManager.findLastCompletelyVisibleItemPosition();

            int currentPosition = PlayerManager.getInstance().getPosition();

            if (currentPosition == firstVisiblePosition - 1 || currentPosition == lastVisiblePosition + 1) {

                PlayerManager.getInstance().stop();
            }

        }

    };
    //---------------------------------listener---------------------------------
}
