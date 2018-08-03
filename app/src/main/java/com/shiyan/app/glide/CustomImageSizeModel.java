package com.shiyan.app.glide;

import android.util.Log;

/**
 * Created by yuqihui on 2016/12/9.
 */

public class CustomImageSizeModel {

    private String baseImgUrl;

    public CustomImageSizeModel(String baseImgUrl) {
        this.baseImgUrl = baseImgUrl;
    }

    public String requestCustomSizeUrl(int width, int height){
        return this.baseImgUrl;
    }

}
