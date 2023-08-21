package com.whatsweb.whatswebscanner.gbwhats.gb_receiver;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginPreferenceManager {
    public static final String LOGIN_PREFERENCE = "WhatsScan";
    public Context context;

    public LoginPreferenceManager(Context context2) {
        this.context = context2;
    }

    public static void saveTimerData(Context context2, String str, String str2) {
        SharedPreferences.Editor edit = context2.getSharedPreferences(LOGIN_PREFERENCE, 0).edit();
        edit.putString("" + str, str2);
        edit.commit();
    }

    public static String getTimerData(Context context2, String str) {
        SharedPreferences sharedPreferences = context2.getSharedPreferences(LOGIN_PREFERENCE, 0);
        return sharedPreferences.getString("" + str, "");
    }

    public static String getFavDatatest(Context context2, String str) {
        return context2.getSharedPreferences(LOGIN_PREFERENCE, 0).getString(str, null);
    }

    public static void SavebooleanData(Context context2, String str, boolean z) {
        SharedPreferences.Editor edit = context2.getSharedPreferences(LOGIN_PREFERENCE, 0).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public static void SaveStringData(Context context2, String str, String str2) {
        SharedPreferences.Editor edit = context2.getSharedPreferences(LOGIN_PREFERENCE, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String GetStringData(Context context2, String str) {
        return context2.getSharedPreferences(LOGIN_PREFERENCE, 0).getString(str, null);
    }

    public static boolean GetbooleanData(Context context2, String str) {
        return context2.getSharedPreferences(LOGIN_PREFERENCE, 0).getBoolean(str, false);
    }

    public static boolean GetbooleanAppNotify(Context context2, String str) {
        return context2.getSharedPreferences(LOGIN_PREFERENCE, 0).getBoolean(str, true);
    }

    public static void saveLocation(Context context2, String str, String str2) {
        SharedPreferences.Editor edit = context2.getSharedPreferences(LOGIN_PREFERENCE, 0).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getLocation(Context context2, String str) {
        return context2.getSharedPreferences(LOGIN_PREFERENCE, 0).getString(str, "");
    }

    public static void setVideoData(Context context2, String str) {
        PreferenceManager.getDefaultSharedPreferences(context2).edit().putString("AllData", str).commit();
    }

    public static String getVideoData(Context context2) {
        return PreferenceManager.getDefaultSharedPreferences(context2).getString("AllData", "");
    }
}
