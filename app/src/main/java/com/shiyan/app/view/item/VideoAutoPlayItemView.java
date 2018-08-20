package com.shiyan.app.view.item;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.shiyan.app.R;
import com.shiyan.app.entity.VideoAutoPlayEntity;
import com.shiyan.app.util.PlayerManager;
import com.shiyan.app.widget.ImageLoadView;

/**
 * 作者: created by shiyan on 2018/8/20
 **/

public class VideoAutoPlayItemView extends RelativeLayout{

    private ImageLoadView imageLoadView;

    private TextureView textureView;

    private VideoAutoPlayEntity videoAutoPlayEntity;

    public VideoAutoPlayItemView(Context context) {
        super(context);

        initView();
    }

    private void initView() {
        LayoutParams rootParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth());
        setLayoutParams(rootParams);

        RelativeLayout controlLayout = new RelativeLayout(getContext());
        controlLayout.setId(R.id.video_autoplay);
        LayoutParams controlLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        controlLayout.setLayoutParams(controlLayoutParams);
        addView(controlLayout);

        //设置背景图片
        imageLoadView = new ImageLoadView(getContext());
        LayoutParams imageLoadViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageLoadView.setLayoutParams(imageLoadViewParams);
        controlLayout.addView(imageLoadView);
    }

    /**
     * 加载背景图片
     * @param url
     */
    public void loadImage(String url){
        imageLoadView.setIconImage(url);
    }

    /**
     * 添加TextureView
     * @param textureView
     */
    public void addTextureView(TextureView textureView){
        if(getChildCount() >= 2) return;

        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        addView(textureView,1,params);
    }

    /**
     * 移除TextureView
     */
    public void removeTextureView(){
        if(getChildCount() >= 2){
            View firstChildView = getChildAt(1);

            if(firstChildView instanceof TextureView){
                removeView(firstChildView);
            }
        }
    }

    /**
     * 自动播放视频
     */
    public void autoPlayVideo() {

        LogUtils.eTag("视频自动播放测试","autoPlayVideo");

        LogUtils.eTag("视频自动播放测试","getTag" + (int)getTag());

        LogUtils.eTag("视频自动播放测试","getPosition" + PlayerManager.getInstance().getPosition());

        if ((int)getTag() == PlayerManager.getInstance().getPosition()){

            return;

        }else{

            PlayerManager.getInstance().stop();

        }

        textureView = new TextureView(getContext());

        textureView.setSurfaceTextureListener(surfaceTextureListener);

        addTextureView(textureView);

    }

    /**
     * 设置数据源
     * @param videoAutoPlayEntity
     */
    public void setVideoAutoPlayEntity(VideoAutoPlayEntity videoAutoPlayEntity) {
        this.videoAutoPlayEntity = videoAutoPlayEntity;
    }

    //------------------------------listener------------------------------
    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if(videoAutoPlayEntity != null) {
                try {
                    PlayerManager.getInstance().start(VideoAutoPlayItemView.this, videoAutoPlayEntity.getVideoUrl());
                    PlayerManager.getInstance().setSurface(surface);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
    //------------------------------listener------------------------------
}
