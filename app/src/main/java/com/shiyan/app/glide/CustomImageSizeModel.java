package com.shiyan.app.glide;

/**
 * Created by shiyan on 2018/8/4.
 */

public class CustomImageSizeModel {

    private String baseImgUrl;

    public CustomImageSizeModel(String baseImgUrl) {
        this.baseImgUrl = baseImgUrl;
    }

    public String requestCustomSizeUrl(int width, int height){
        // TODO: 2018/8/4 对图片url进行处理 
        return this.baseImgUrl;
    }

}
