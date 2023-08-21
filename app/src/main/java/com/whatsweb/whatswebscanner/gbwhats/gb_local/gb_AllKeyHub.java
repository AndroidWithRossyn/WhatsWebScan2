package com.whatsweb.whatswebscanner.gbwhats.gb_local;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

public class gb_AllKeyHub {
    public static String TestDeviceID = "C084E0BC87C2247C7F8BE092739D965E";
    public static String APPDATA = "106";
    public static int clickcount = 0;
    public static int nativeclickcount = 0;
    public static int backclickcount = 0;


    public static void LoadInterstitialAds(Context context) {
        gb_AdsPrefs appPrefs = new gb_AdsPrefs(context);
        if (appPrefs.getInterstitialAdsType().equalsIgnoreCase("Your choice")) {
            gb_Typeads.LoadInterstitialAd(context, "Fail");
        } else {
            gb_Typeads.LoadInterstitialAd(context, "Fail");
        }
    }


    public static void ShowInterstitialAdsOnCreate(Context context) {
        gb_AdsPrefs appPrefs = new gb_AdsPrefs(context);
        if (!appPrefs.getAdd_Status().equals("1"))
            return;
        if (gb_AllKeyHub.clickcount == Integer.parseInt(appPrefs.getExtra_Id())) {
            gb_AllKeyHub.clickcount = 0;
            gb_Typeads.LoadInterstitialAd(context, "Fail");
            new gb_Typeads().ShowAdsOnCreate(context);
        } else {
            gb_AllKeyHub.clickcount++;

        }


    }


    public static void ShowAllNativeAds(Context context, FrameLayout bigNative, FrameLayout smallNative) {
        gb_AdsPrefs appPrefs = new gb_AdsPrefs(context);
        if (!appPrefs.getAdd_Status().equals("1"))
            return;
        if (gb_AllKeyHub.nativeclickcount == Integer.parseInt(appPrefs.getExtra_2())) {
            gb_AllKeyHub.nativeclickcount = 0;
            if (bigNative != null) {
                gb_all_native_ads.NativeAdd(context, bigNative);
                if (appPrefs.getNativeone().equals("1") && smallNative != null) {
                    smallNative.setVisibility(View.VISIBLE);
                    gb_all_native_ads.NativeBannerAdd120(context, smallNative);
                }
            } else if (smallNative != null) {
                smallNative.setVisibility(View.VISIBLE);
                gb_all_native_ads.NativeBannerAdd120(context, smallNative);
            }
        } else {
            gb_AllKeyHub.nativeclickcount++;
        }
    }

    public static void backShowInterstitialAds(Context context, onInterstitialAdsClose interstitialAdsListener1) {
        gb_AdsPrefs appPrefs = new gb_AdsPrefs(context);

        if (appPrefs.getBack_status().equals("0")) {
            interstitialAdsListener1.onAdsClose();
            return;
        }

        if (gb_AllKeyHub.backclickcount == Integer.parseInt(appPrefs.getBack_count())) {
            gb_AllKeyHub.backclickcount = 0;
            gb_Typeads.LoadInterstitialAd(context, "Fail");
            new gb_Typeads().ShowAdsOnBackPress(context, interstitialAdsListener1);
        } else {
            interstitialAdsListener1.onAdsClose();
            gb_AllKeyHub.backclickcount++;
        }
    }

}
