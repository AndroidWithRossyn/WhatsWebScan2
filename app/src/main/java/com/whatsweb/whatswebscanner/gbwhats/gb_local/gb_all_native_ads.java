package com.whatsweb.whatswebscanner.gbwhats.gb_local;


import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.whatsweb.whatswebscanner.gbwhats.R;

import java.util.ArrayList;
import java.util.List;

public class gb_all_native_ads {


    public static void NativeBannerAdd120(final Context context, final ViewGroup BannerContainer) {

        final gb_AdsPrefs sbiAdsPrefs = new gb_AdsPrefs(context);

        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            final View MainMenu = inflater.inflate(R.layout.gb_ads_native_ads_gg_temp, BannerContainer, false);
            final TemplateView nativeAdLayout = (TemplateView) MainMenu.findViewById(R.id.my_template_small);
            final TemplateView my_template_large = (TemplateView) MainMenu.findViewById(R.id.my_template_large);
            my_template_large.setVisibility(View.GONE);
            nativeAdLayout.setVisibility(View.GONE);


            AdLoader adLoader = new AdLoader.Builder(context, sbiAdsPrefs.getAM_Native())
                    .forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                            Log.e("NativeAds", "Loaded");
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(MainMenu);
                            nativeAdLayout.setVisibility(View.VISIBLE);
                            nativeAdLayout.setStyles(styles);
                            nativeAdLayout.setNativeAd(nativeAd);

                        }
                    }).withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            Log.e("NativebannerAds", "Error");
                            Log.e("admobNativebannerAds", "Error" + adError.getMessage());
                            nativeAdLayout.setVisibility(View.GONE);
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder()
                            // Methods in the NativeAdOptions.Builder class can be
                            // used here to specify individual options settings.
                            .build())
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void NativeAdd(final Context context, final ViewGroup BannerContainer) {

        List<String> testDevices = new ArrayList<>();
        testDevices.add(gb_AllKeyHub.TestDeviceID);
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder().setTestDeviceIds(testDevices).build();
        MobileAds.setRequestConfiguration(requestConfiguration);


        final gb_AdsPrefs appPrefs = new gb_AdsPrefs(context);
        try {
            LayoutInflater inflater = LayoutInflater.from(context);

            final View MainMenu = inflater.inflate(R.layout.gb_ads_native_ads_gg_temp, BannerContainer, false);
            final TemplateView nativeAdLayout = (TemplateView) MainMenu.findViewById(R.id.my_template_large);
            final TemplateView my_template_small = (TemplateView) MainMenu.findViewById(R.id.my_template_small);
            my_template_small.setVisibility(View.GONE);
            nativeAdLayout.setVisibility(View.GONE);


            AdLoader adLoader = new AdLoader.Builder(context, appPrefs.getAM_Native())
                    .forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                            Log.e("NativeAds", "Loaded");
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();

                            BannerContainer.removeAllViews();
                            BannerContainer.addView(MainMenu);


                            nativeAdLayout.setVisibility(View.VISIBLE);
                            nativeAdLayout.setStyles(styles);
                            nativeAdLayout.setNativeAd(nativeAd);

                        }
                    }).withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            Log.e("NativeAds", "Error");
                            Log.e("admobNativeAds", "Error" + adError.getMessage());

                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder().build())
                    .build();

            adLoader.loadAd(new AdRequest.Builder().build());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}