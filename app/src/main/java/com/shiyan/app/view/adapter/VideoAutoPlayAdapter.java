package com.shiyan.app.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.shiyan.app.entity.VideoAutoPlayEntity;
import com.shiyan.app.view.item.VideoAutoPlayItemView;

import java.util.List;

import cc.solart.turbo.BaseTurboAdapter;
import cc.solart.turbo.BaseViewHolder;

/**
 * 作者: created by shiyan on 2018/8/20
 **/

public class VideoAutoPlayAdapter extends BaseTurboAdapter<VideoAutoPlayEntity,VideoAutoPlayAdapter.VideoAutoPlayHolder>{

    public VideoAutoPlayAdapter(Context context, List<VideoAutoPlayEntity> data) {
        super(context, data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return new VideoAutoPlayHolder(new VideoAutoPlayItemView(mContext));
    }

    @Override
    protected void convert(VideoAutoPlayHolder holder, VideoAutoPlayEntity item) {
        VideoAutoPlayItemView videoAutoPlayItemView = (VideoAutoPlayItemView) holder.itemView;
        videoAutoPlayItemView.setVideoAutoPlayEntity(item);
        videoAutoPlayItemView.setTag(holder.getLayoutPosition());
        videoAutoPlayItemView.loadImage(item.getImgUrl());
    }

    public class VideoAutoPlayHolder extends BaseViewHolder{

        public VideoAutoPlayHolder(View view) {
            super(view);
        }
    }
}
