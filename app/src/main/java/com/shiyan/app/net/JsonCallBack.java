package com.shiyan.app.net;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者: created by shiyan on 2018/10/16
 **/

public abstract class JsonCallBack<T> extends AbsCallback<T> {

    private Type type;

    private Class<T> clazz;

    public JsonCallBack() {
    }

    public JsonCallBack(Type type) {
        this.type = type;
    }

    public JsonCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {

        ResponseBody body = response.body();

        if(body == null) return null;

        T data = null;

        Gson gson = new Gson();

        JsonReader jsonReader = new JsonReader(body.charStream());

        if(type != null){

            data = gson.fromJson(jsonReader,type);

        }else if(clazz != null){

            data = gson.fromJson(jsonReader,clazz);

        }else{

            Type genType = getClass().getGenericSuperclass();

            Type type = ((ParameterizedType)genType).getActualTypeArguments()[0];

            data = gson.fromJson(jsonReader,type);

        }

        return data;
    }
}
