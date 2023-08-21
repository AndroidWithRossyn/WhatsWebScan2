package com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class gb_ID_Response implements Serializable {

    @SerializedName("App_Name")
    @Expose
    private String App_Name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("am_app_id")
    @Expose
    private String am_app_id;
    @SerializedName("am_native_add")
    @Expose
    private String am_native_add;
    @SerializedName("am_interstial_add")
    @Expose
    private String am_interstial_add;
    @SerializedName("screen_status")
    @Expose
    private String screen_status;
    @SerializedName("live_button")
    @Expose
    private String live_button;
    @SerializedName("av_status")
    @Expose
    private String av_status;
    @SerializedName("extra_id")
    @Expose
    private String extra_id;
    @SerializedName("extra_2")
    @Expose
    private String extra_2;
    @SerializedName("GLobalstatus")
    @Expose
    private String gLobalstatus;
    @SerializedName("Nativeone")
    @Expose
    private String nativeone;
    @SerializedName("vpn_url")
    @Expose
    private String vpn_url;
    @SerializedName("vpn_keyid")
    @Expose
    private String vpn_keyid;

    @SerializedName("back_status")
    @Expose
    private String back_status;

    @SerializedName("back_count")
    @Expose
    private String back_count;


    public String getgLobalstatus() {
        return gLobalstatus;
    }

    public void setgLobalstatus(String gLobalstatus) {
        this.gLobalstatus = gLobalstatus;
    }

    public String getNativeone() {return nativeone;}

    public void setNativeone(String nativeone) {
        this.nativeone = nativeone;
    }

    public String getAppName() {
        return App_Name;
    }

    public void setAppName(String appName) {
        this.App_Name = appName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAmAppId() {
        return am_app_id;
    }

    public void setAmAppId(String amAppId) {
        this.am_app_id = amAppId;
    }

    public String getAmNativeAdd() {
        return am_native_add;
    }

    public void setAmNativeAdd(String amNativeAdd) {
        this.am_native_add = amNativeAdd;
    }

    public String getAmInterstialAdd() {
        return am_interstial_add;
    }

    public void setAmInterstialAdd(String amInterstialAdd) {this.am_interstial_add = amInterstialAdd;}

    public String getScreenStatus() {
        return screen_status;
    }

    public void setScreenStatus(String screenStatus) {
        this.screen_status = screenStatus;
    }

    public String getLiveButton() {
        return live_button;
    }

    public void setLiveButton(String liveButton) {
        this.live_button = liveButton;
    }

    public String getAvStatus() {
        return av_status;
    }

    public void setAvStatus(String avStatus) {
        this.av_status = avStatus;
    }

    public String getExtraId() {
        return extra_id;
    }

    public void setExtraId(String extraId) {
        this.extra_id = extraId;
    }

    public String getExtra2() {
        return extra_2;
    }

    public void setExtra2(String extraId) {
        this.extra_2 = extraId;
    }

    public String getVpn_url() {
        return vpn_url;
    }

    public void setVpn_url(String vpn_url) {
        this.vpn_url = vpn_url;
    }

    public String getVpn_keyid() {
        return vpn_keyid;
    }

    public void setVpn_keyid(String vpn_keyid) {
        this.vpn_keyid = vpn_keyid;
    }

    public String getBack_status() {
        return back_status;
    }

    public void setBack_status(String back_status) {
        this.back_status = back_status;
    }

    public String getBack_count() {
        return back_count;
    }

    public void setBack_count(String back_count) {
        this.back_count = back_count;
    }
}
