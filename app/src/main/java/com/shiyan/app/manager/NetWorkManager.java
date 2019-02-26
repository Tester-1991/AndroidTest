package com.shiyan.app.manager;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.shiyan.app.receiver.NetReceiver;

public class NetWorkManager {

    private static volatile NetWorkManager instance;

    private NetReceiver netReceiver;

    private Application application;

    private NetWorkManager() {
        netReceiver = new NetReceiver();
    }

    /**
     * 获取NetWorkManager实例
     *
     * @return
     */
    public static NetWorkManager getInstance() {
        if (instance == null) {
            synchronized (NetWorkManager.class) {
                if (instance == null) {
                    instance = new NetWorkManager();
                }
            }
        }
        return instance;
    }

    /**
     * 注册网络广播监听器
     *
     * @param application
     */
    public void init(Application application) {

        this.application = application;

        IntentFilter netFilter = new IntentFilter();

        netFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        application.registerReceiver(netReceiver, netFilter);
    }

    /**
     * 获取Application
     * @return
     */
    public Application getApplication() {
        return application;
    }

    /**
     * 注册观察者
     */
    public void registerOberver(Object activity) {
        netReceiver.registerOberver(activity);
    }

    /**
     * 反注册观察者
     */
    public void unregisterObserver(Object activity) {
        netReceiver.unregisterObserver(activity);
    }

    /**
     * 反注册所有观察者
     */
    public void unregisterAllObserver() {
        netReceiver.unregisterAllObserver();
    }

}
