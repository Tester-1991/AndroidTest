package com.shiyan.app.util;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.Surface;

import com.blankj.utilcode.util.LogUtils;
import com.shiyan.app.view.item.VideoAutoPlayItemView;

/**
 * 作者: created by shiyan on 2018/8/20
 **/

public class PlayerManager {

    private int position = -1;

    private MediaPlayer mediaPlayer;

    private VideoAutoPlayItemView videoAutoPlayItemView;

    private Surface surface;

    /**
     * 构造方法私有化
     */
    private PlayerManager() {
    }

    /**
     * 单例模式(静态内部类创建)
     *
     * @return
     */
    public static PlayerManager getInstance() {
        return PlayerManagerHolder.INSTANCE;
    }

    public static class PlayerManagerHolder {
        private static PlayerManager INSTANCE = new PlayerManager();
    }

    /**
     * 设置Surface
     * @param surfaceTexture
     */
    public void setSurface(SurfaceTexture surfaceTexture) {
        this.surface = new Surface(surfaceTexture);
    }

    /**
     * 播放视频
     *
     * @param videoAutoPlayItemView
     * @param videoUrl
     */
    public void start(VideoAutoPlayItemView videoAutoPlayItemView, String videoUrl) throws Exception {
        LogUtils.eTag("视频自动播放测试","start getTag" + (int) videoAutoPlayItemView.getTag());
        LogUtils.eTag("视频自动播放测试","start position" + position);
        if (videoAutoPlayItemView == null || TextUtils.isEmpty(videoUrl)) return;

        if ((int)videoAutoPlayItemView.getTag() == this.position) {
            return;
        }

        this.videoAutoPlayItemView = videoAutoPlayItemView;

        this.position = (int) videoAutoPlayItemView.getTag();

        mediaPlayer = new MediaPlayer();

        mediaPlayer.reset();

        mediaPlayer.setDataSource(videoUrl);

        mediaPlayer.setLooping(true);

        mediaPlayer.setScreenOnWhilePlaying(true);

        mediaPlayer.setOnPreparedListener(onPreparedListener);

        mediaPlayer.setOnErrorListener(onErrorListener);

        mediaPlayer.prepareAsync();

    }

    /**
     * 停止播放视频
     */
    public void stop() {
        LogUtils.eTag("视频自动播放测试","stop" + position);
        if (position < 0) {
            return;
        }

        this.position = -1;

        mediaPlayer.stop();

        mediaPlayer.release();

        mediaPlayer = null;

        if (videoAutoPlayItemView != null) {
            videoAutoPlayItemView.removeTextureView();
        }
    }

    /**
     * 获取当前播放位置
     * @return
     */
    public int getPosition() {
        return position;
    }

    //------------------------------listener-------------------------------
    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            if(surface != null){
                mediaPlayer.setSurface(surface);
            }
            mediaPlayer.start();

            LogUtils.eTag("视频自动播放测试","start");
        }
    };

    MediaPlayer.OnErrorListener onErrorListener = (mp, what, extra) -> {
        LogUtils.eTag("视频自动播放测试","onErrorListener:what" + what + ",extra:" +extra);
        return true;
    };

    //------------------------------listener-------------------------------
}
