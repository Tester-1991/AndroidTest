package com.shiyan.app.net.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 */
public class HttpResponse implements Parcelable {

    /**
     * 描述信息
     */
    @SerializedName("msg")
    private String msg;

    /**
     * 状态码
     */
    @SerializedName("code")
    private String code;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("result")
    private Object result;

    /**
     * 接口返回时间
     */
    @SerializedName("time")
    private String time;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeString(this.code);
        dest.writeParcelable((Parcelable) this.result, flags);
        dest.writeString(this.time);
    }

    public HttpResponse() {
    }

    protected HttpResponse(Parcel in) {
        this.msg = in.readString();
        this.code = in.readString();
        this.result = in.readParcelable(Object.class.getClassLoader());
        this.time = in.readString();
    }

    public static final Creator<HttpResponse> CREATOR = new Creator<HttpResponse>() {
        @Override
        public HttpResponse createFromParcel(Parcel source) {
            return new HttpResponse(source);
        }

        @Override
        public HttpResponse[] newArray(int size) {
            return new HttpResponse[size];
        }
    };
}