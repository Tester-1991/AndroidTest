package com.shiyan.app.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.shiyan.app.anno.Network;
import com.shiyan.app.manager.NetWorkManager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by shiyan on 2019/2/26
 *
 * desc: 网络状态监听器
 */
public class NetReceiver extends BroadcastReceiver {

    private static NetHandler netHandler;

    public static final int NET_CONNECTED = 1;

    public static final int NET_DISCONNECTED = 2;

    public static final int NET_DELAY_TIME = 0;

    private Map<Object, List<Method>> networkList;

    private boolean currentNet;

    private static class NetHandler extends Handler {

        private final WeakReference<NetReceiver> weakReference;

        public NetHandler(NetReceiver receiver) {
            weakReference = new WeakReference<>(receiver);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg == null) return;

            if (weakReference == null) return;

            NetReceiver netReceiver = weakReference.get();

            if (netReceiver != null) {

                switch (msg.what) {
                    case NET_CONNECTED:

                        netHandler.removeMessages(NET_CONNECTED);

                        //网络已经连接
                        netReceiver.dispatchReceiver();

                        netReceiver.setCurrentNet(true);

                        break;
                    case NET_DISCONNECTED:

                        netHandler.removeMessages(NET_DISCONNECTED);

                        //网络断开连接
                        netReceiver.dispatchReceiver();

                        netReceiver.setCurrentNet(false);

                        break;
                }
            }
        }
    }

    public NetReceiver() {
        networkList = new HashMap<>();

        currentNet = NetworkUtils.isConnected();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        if (netHandler == null) {
            netHandler = new NetHandler(this);
        }
        //网络发生变化
        if (intent.getAction().equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (NetworkUtils.isConnected()) {

                //如果当前是有网络状态 返回
                if (currentNet) return;

                netHandler.removeMessages(NET_CONNECTED);

                netHandler.sendEmptyMessageDelayed(NET_CONNECTED, NET_DELAY_TIME);

            } else {

                //如果当前是无网络状态 返回
                if (!currentNet) return;

                netHandler.removeMessages(NET_DISCONNECTED);

                netHandler.sendEmptyMessageDelayed(NET_DISCONNECTED, NET_DELAY_TIME);

            }
        }
    }

    /**
     * 设置当前网络状态
     *
     * @param currentNet
     */
    public void setCurrentNet(boolean currentNet) {
        this.currentNet = currentNet;
    }

    /**
     * 分发通知
     */
    private void dispatchReceiver() {

        Set<Object> set = networkList.keySet();

        for (Object activity : set) {

            if (!(activity instanceof Activity)) return;

            if (ActivityUtils.getTopActivity() != activity) return;

            List<Method> methodList = networkList.get(activity);

            if (methodList == null) return;

            for (Method method : methodList) {
                try {
                    method.invoke(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void registerOberver(Object register) {
        List<Method> methodList = networkList.get(register);

        if (methodList == null) {

            methodList = findAnnotationMethod(register);

            networkList.put(register, methodList);
        }
    }

    private List<Method> findAnnotationMethod(Object register) {
        List<Method> methodList = new ArrayList<>();
        Class<?> clazz = register.getClass();
        //获取所有方法
        Method[] methods = clazz.getMethods();
        //循环
        for (Method method : methods) {

            Network network = method.getAnnotation(Network.class);

            if (network == null) continue;

            methodList.add(method);
        }

        return methodList;
    }

    public void unregisterObserver(Object register) {
        if (!networkList.isEmpty() && networkList.containsKey(register)) {
            networkList.remove(register);
        }
    }


    public void unregisterAllObserver() {
        if (!networkList.isEmpty()) {
            networkList.clear();
        }

        NetWorkManager.getInstance().getApplication().unregisterReceiver(this);

        networkList = null;
    }
}
