package com.whatsweb.whatswebscanner.gbwhats.gb_util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

public class Constant {
    public static boolean SHOW_OPEN_ADS = true;
    public static ArrayList<String> f16277a = new ArrayList<>();
    public static String f16282f = "http://jalanmudu.website";
    public static String f16283g = "2776119817185_2776163150422";
    public static String f16284h = "2776158197185_2776178596981";
    public static String f16285i = "2776119817185_2776202816738";
    public static String f16286j = "2776158817185_2776206483376";
    public static String f16287k = "2776159817185_2776156483588";
    public static ArrayList<String> recentImageArraylist = new ArrayList<>();
    public static ArrayList<String> recentVideoArraylist = new ArrayList<>();
    public static ArrayList<String> savedImagesArraylist;
    public static ArrayList<String> savedVideoArraylist;

    public static void showOpenAd() {
        SHOW_OPEN_ADS = true;
    }

    public static void hideOpenAd() {
        SHOW_OPEN_ADS = false;
    }

    public static boolean m19787a(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
