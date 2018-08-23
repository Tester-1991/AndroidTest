package com.shiyan.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: created by shiyan on 2018/8/20
 **/

public class VideoAutoPlayEntity implements Parcelable {

    private String imgUrl;

    private String videoUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgUrl);
        dest.writeString(this.videoUrl);
    }

    public VideoAutoPlayEntity() {
    }

    protected VideoAutoPlayEntity(Parcel in) {
        this.imgUrl = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Parcelable.Creator<VideoAutoPlayEntity> CREATOR = new Parcelable.Creator<VideoAutoPlayEntity>() {
        @Override
        public VideoAutoPlayEntity createFromParcel(Parcel source) {
            return new VideoAutoPlayEntity(source);
        }

        @Override
        public VideoAutoPlayEntity[] newArray(int size) {
            return new VideoAutoPlayEntity[size];
        }
    };

    @Override
    public String toString() {
        return "VideoAutoPlayEntity{" +
                "imgUrl='" + imgUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
