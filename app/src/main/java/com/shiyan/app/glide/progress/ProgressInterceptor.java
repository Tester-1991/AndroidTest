package com.shiyan.app.glide.progress;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者: created by shiyan on 2018/8/3
 **/

public class ProgressInterceptor implements Interceptor{

    static HashMap<String,ProgressListener> LISTENER_MAP = new HashMap<>();

    public static void addListener(String url,ProgressListener listener){

        LISTENER_MAP.put(url,listener);

    }

    public static void removeListener(String url){

        LISTENER_MAP.remove(url);

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Response response = chain.proceed(request);

        String url = request.url().toString();

        ResponseBody body = response.body();

        Response newResponse = response.newBuilder().body(new ProgressResponseBody(url,body)).build();

        return newResponse;
    }
}
