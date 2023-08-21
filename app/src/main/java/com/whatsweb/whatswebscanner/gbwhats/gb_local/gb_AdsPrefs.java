package com.whatsweb.whatswebscanner.gbwhats.gb_local;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class gb_AdsPrefs {
    public static final String ADS_PREFS = "ADS PREFS";
    public static SharedPreferences appSharedPref;
    public SharedPreferences.Editor prefEditor;
    private String InterstitialAdsType = "InterstitialAdsType";
    private String App_Name = "App_Name";
    private String AM_native = "AM_native";
    private String AM_inter = "AM_inter";
    private String add_status = "add_status";
    private String AM_app_open = "AM_app_open";
    private String extra_id = "extra_id";
    private String extra_2 = "extra_2";
    private String URL = "url";
    private String Screen_status = "screen_status";
    private String Live_button = "live_button";
    private String Globalstatus = "globalstatus";
    private String Nativeone = "nativeone";
    private String Vpn_url = "vpn_url";
    private String Vpn_keyid = "vpn_keyid";
    private String Back_status = "back_status";
    private String Back_count = "back_count";



    public String getVpn_url() {
        return appSharedPref.getString(Vpn_url, "");

    }

    public void setVpn_url(String vpn_url) {
        this.prefEditor.putString(Vpn_url, vpn_url).commit();

    }


    public String getVpn_keyid() {
        return appSharedPref.getString(Vpn_keyid, "");

    }

    public void setVpn_keyid(String vpn_keyid) {
        this.prefEditor.putString(Vpn_keyid, vpn_keyid).commit();

    }

    public String getBack_status() {
        return appSharedPref.getString(Back_status, "");

    }

    public void setBack_status(String back_status) {
        this.prefEditor.putString(Back_status, back_status).commit();

    }

    public String getBack_count() {
        return appSharedPref.getString(Back_count, "");

    }

    public void setBack_count(String back_count) {
        this.prefEditor.putString(Back_count, back_count).commit();

    }


    public String getGlobalstatus() {
        return appSharedPref.getString(Globalstatus, "");
    }

    public void setGlobalstatus(String globalstatus) {
        this.prefEditor.putString(Globalstatus, globalstatus).commit();

    }


    public String getNativeone() {
        return appSharedPref.getString(Nativeone, "");
    }

    public void setNativeone(String nativeone) {
        this.prefEditor.putString(Nativeone, nativeone).commit();
    }


    public String getScreen_status() {
        return appSharedPref.getString(Screen_status, "");
    }

    public void setScreen_status(String screen_status) {
        this.prefEditor.putString(Screen_status, screen_status).commit();
    }

    public String getLive_button() {
        return appSharedPref.getString(Live_button, "");
    }

    public void setLive_button(String live_button) {
        this.prefEditor.putString(Live_button, live_button).commit();
    }


    public String getUrl() {
        return appSharedPref.getString(URL, "");
    }

    public void setUrl(String url) {
        this.prefEditor.putString(URL, url).commit();
    }


    public String getInterstitialAdsType() {
        return appSharedPref.getString(InterstitialAdsType, "Your choice");
    }

    public void setInterstitialAdsType(String categoryName) {
        this.prefEditor.putString(InterstitialAdsType, categoryName).commit();
    }

    public gb_AdsPrefs(Context context) {
        this.appSharedPref = context.getSharedPreferences(ADS_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedPref.edit();
    }

    public String getApp_Name() {
        return appSharedPref.getString(App_Name, "");
    }

    public void setApp_Name(String appName) {
        this.prefEditor.putString(App_Name, appName).commit();
    }


    public String getAM_Native() {
        return appSharedPref.getString(AM_native, "");

    }

    public void setAM_Native(String aMNative) {
        this.prefEditor.putString(AM_native, aMNative).commit();
    }

    public String getAM_Inter() {
        return appSharedPref.getString(AM_inter, "");
    }

    public void setAM_Inter(String aMInter) {
        this.prefEditor.putString(AM_inter, aMInter).commit();

    }

    public String getAdd_Status() {
        return appSharedPref.getString(add_status, "");

    }

    public void setAdd_Status(String addStatus) {
        this.prefEditor.putString(add_status, addStatus).commit();
    }

    public String getAM_App_Open() {
        return appSharedPref.getString(AM_app_open, "");

    }

    public void setAM_App_Open(String aMAppOpen) {
        this.prefEditor.putString(AM_app_open, aMAppOpen).commit();

    }

    public String getExtra_Id() {
        return appSharedPref.getString(extra_id, "");
    }

    public void setExtra_Id(String extraId) {
        this.prefEditor.putString(extra_id, extraId).commit();
    }

    public String getExtra_2() {
        return appSharedPref.getString(extra_2, "");
    }

    public void setExtra_2(String extraId) {
        this.prefEditor.putString(extra_2, extraId).commit();
    }


}
