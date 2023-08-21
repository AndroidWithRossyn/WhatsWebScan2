package com.whatsweb.whatswebscanner.gbwhats.gb_local;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;
import com.whatsweb.whatswebscanner.gbwhats.R;

import java.util.ArrayList;
import java.util.List;

public class gb_Typeads {

    public static boolean FBCreateLoadedFlag = false;
    public static boolean FBCreateRequestFlag = false;
    static Dialog pd;
    private static Context mContext;
    private static String FinishTag = "";
    private static onInterstitialAdsClose adsListener1;
    private static InterstitialAd AMInterstitial;
    static gb_AdsPrefs adsPreferance;
    static AdRequest adRequest;


    public static void LoadInterstitialAd(Context context, String finishTag) {
        mContext = context;
        adsPreferance = new gb_AdsPrefs(mContext);
        MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        List<String> testDevices = new ArrayList<>();
        testDevices.add(gb_AllKeyHub.TestDeviceID);

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(testDevices)
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
        adRequest = new AdRequest.Builder().build();
        if (FBCreateLoadedFlag) {
            Log.d("FBFBFBFBBFBBF", "====iiiiiiii=======>+<AIN CALL NOEW");

        } else {
            Log.d("FBFBFBFBBFBBF", "=====ooooooooo======>+<AIN CALL NOEW");

//            try {
//                if (ANInterstitialAd != null && ANInterstitialAd.isAdLoaded()) {
//                    ANInterstitialAd.destroy();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            FinishTag = finishTag;
            FBCreateLoadedFlag = true;
            FBCreateRequestFlag = true;


            displayAdMobInAdSEconD();


        }
    }


    private static void displayAdMobInAdSEconD() {

        // TODO Auto-generated method stub
        try {
            adsPreferance = new gb_AdsPrefs(mContext);
            InterstitialAd.load(mContext, adsPreferance.getAM_Inter(), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            AMInterstitial = interstitialAd;
                            Log.i(TAG, "onAdLoaded");
                            FBCreateRequestFlag = false;
                            AMInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                    super.onAdFailedToShowFullScreenContent(adError);
                                    if (adsListener1 != null)
                                        adsListener1.onAdsClose();
                                }

                                @Override
                                public void onAdImpression() {
                                    super.onAdImpression();
                                }

                                @Override
                                public void onAdClicked() {
                                    super.onAdClicked();
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when fullscreen content is dismissed.
                                    Log.d("TAG", "The ad was dismissed.");
                                    AMInterstitial = null;


                                    if (FinishTag.equals("Fail")) {

                                    } else {
                                        LoadInterstitialAd(mContext, FinishTag);
                                    }
                                    if (adsListener1 != null)
                                        adsListener1.onAdsClose();

                                }


                                @Override
                                public void onAdShowedFullScreenContent() {
                                    AMInterstitial = null;
                                    Log.d("TAG", "The ad was shown.");
                                }
                            });
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.i(TAG, loadAdError.getMessage());
                            FBCreateLoadedFlag = false;
                            Log.e("AD_LOGS", " AM Inter Failed to load, Error Msg -> " + loadAdError.getMessage());
                            Log.e("AD_LOGS", " AM Inter Failed to load, Error Code -> " + loadAdError.getCode());

                        }
                    });

        } catch (NullPointerException e) {
            e.printStackTrace();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    public static void ShowInterstitialAd(Context context, final String finishTag, onInterstitialAdsClose interstitialAdsListener1) {
        mContext = context;


        if (FBCreateLoadedFlag) {
            try {
                adsListener1 = interstitialAdsListener1;
                if (AMInterstitial != null) {
                    try {
                        FinishTag = finishTag;
                        AMInterstitial.show((Activity) mContext);
                        FBCreateLoadedFlag = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (adsListener1 != null)
                adsListener1.onAdsClose();
        }

    }




    public void ShowAdsOnCreate(final Context SourceActivity) {

        if (!FBCreateRequestFlag) {
            ShowInterstitialAd(SourceActivity, "false", new onInterstitialAdsClose() {
                @Override
                public void onAdsClose() {

                }
            });
        }
    }

    public void ShowAdsOnBackPress(final Context SourceActivity, onInterstitialAdsClose interstitialAdsListener1) {
        if (!FBCreateRequestFlag) {
            ShowInterstitialAd(SourceActivity, "false", new onInterstitialAdsClose() {
                @Override
                public void onAdsClose() {
                    if (interstitialAdsListener1 != null) {
                        interstitialAdsListener1.onAdsClose();
                    }
//                    Toast.makeText(SourceActivity, "onback", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            interstitialAdsListener1.onAdsClose();
        }
    }




}
