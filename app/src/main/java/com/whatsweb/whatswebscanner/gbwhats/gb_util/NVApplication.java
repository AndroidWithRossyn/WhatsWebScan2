package com.whatsweb.whatswebscanner.gbwhats.gb_util;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.anchorfree.sdk.HydraTransportConfig;
import com.anchorfree.sdk.NotificationConfig;
import com.anchorfree.sdk.TransportConfig;
import com.anchorfree.sdk.UnifiedSDK;
import com.anchorfree.vpnsdk.callbacks.CompletableCallback;
import com.northghost.caketube.OpenVpnTransportConfig;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_aom;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_SplashSCreenActivity;


import java.util.ArrayList;
import java.util.List;


public class NVApplication extends MultiDexApplication implements LifecycleObserver, Application.ActivityLifecycleCallbacks{

    private static NVApplication instance;
    public static gb_aom appOpenAdManager;
    public Activity currentActivity;
    private static boolean isAnyActivityVisible;

    private static final String CHANNEL_ID = "AIOSS";
    private void initVPNSdk() {

        createNotificationChannel();

        List<TransportConfig> transportConfigList = new ArrayList<>();
        transportConfigList.add(HydraTransportConfig.create());
        transportConfigList.add(OpenVpnTransportConfig.tcp());
        transportConfigList.add(OpenVpnTransportConfig.udp());
        UnifiedSDK.update(transportConfigList, CompletableCallback.EMPTY);

        NotificationConfig notificationConfig = NotificationConfig.newBuilder()
                .title(getResources().getString(R.string.app_name))
                .channelId(CHANNEL_ID)
                .build();

        UnifiedSDK.update(notificationConfig);

        UnifiedSDK.setLoggingLevel(Log.VERBOSE);

    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = "VPN notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(this);
        appOpenAdManager = new gb_aom(NVApplication.this);
        MultiDex.install(this);
        initVPNSdk();
    }

    public static Context getContext() {
        return instance;
    }

    public static NVApplication getInstance() {
        return instance;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!this.appOpenAdManager.isShowingAd) {
            this.currentActivity = activity;
        }
    }

    public void onActivityResumed(Activity activity) {
        this.currentActivity = activity;
        isAnyActivityVisible = true;
    }

    public void onActivityPaused(Activity activity) {
        isAnyActivityVisible = false;
    }


    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void appInResumeState() {
        appOpenAdManager.showAdIfAvailable(this.currentActivity);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void appInPauseState() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onMoveToForeground() {
        this.appOpenAdManager.showAdIfAvailable(this.currentActivity);
    }

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void appIndestoryState() {

    }
}
