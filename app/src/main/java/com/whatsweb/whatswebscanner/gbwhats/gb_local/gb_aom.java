package com.whatsweb.whatswebscanner.gbwhats.gb_local;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.NVApplication;

import java.util.Date;

public class gb_aom implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "gb_aom";
    private AppOpenAd appOpenAd = null;
    public static boolean isShowingAd = false;
    private long loadTime = 0;


    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private Activity currentActivity;

    private final NVApplication myApplication;

    /**
     * Constructor
     */
    public gb_aom(NVApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            return;
        }
        gb_AdsPrefs appPrefs = new gb_AdsPrefs(myApplication);
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                super.onAdLoaded(appOpenAd);
                gb_aom.this.appOpenAd = ad;
                gb_aom.this.loadTime = (new Date()).getTime();
            }
        };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                myApplication,appPrefs.getAM_App_Open() , request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }


    /**
     * ActivityLifecycleCallback methods
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }

    /**
     * Shows the ad if one isn't already showing.
     * @param currentActivity
     */


    public void showAdIfAvailable(Activity currentActivity) {
        if (this.isShowingAd) {
//            Log.e(TAG, "Can't show the ad: Already showing the ad");
            return;
        }

        if (!isAdAvailable()) {
//            Log.e(TAG, "Can't show the ad: Ad not available");
            fetchAd();
            return;
        }

        FullScreenContentCallback callback = new FullScreenContentCallback() {
            @Override
            public void onAdFailedToShowFullScreenContent(AdError error) {
//                if (listener != null) {
//                    listener.onAdFailedToShowFullScreenContent(error);
//                }
            }

            @Override
            public void onAdShowedFullScreenContent() {
//                if (listener != null) {
//                    listener.onAdShowedFullScreenContent();
//                }
                isShowingAd = true;
            }

            @Override
            public void onAdDismissedFullScreenContent() {
//                if (listener != null) {
//                    listener.onAdDismissedFullScreenContent();
//                }
                isShowingAd = false;
                appOpenAd = null;
                fetchAd();
            }
        };

        appOpenAd.setFullScreenContentCallback(callback);
        appOpenAd.show(currentActivity);

    }


    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        showAdIfAvailable(this.currentActivity);
        Log.d(LOG_TAG, "onStart");
    }
}