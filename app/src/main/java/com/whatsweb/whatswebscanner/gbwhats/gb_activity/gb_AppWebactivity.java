package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

public class gb_AppWebactivity extends AppCompatActivity {
    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";
    private static final String MOBILE_USER_AGENT = "Mozilla/5.0 (Linux; U; Android 4.4; en-us; Nexus 4 Build/JOP24G) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    private WebView webview;
    private Toolbar whatsapp_web_toolbar;

    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_AppWebactivity.this).showInterstitialAd(gb_AppWebactivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_AppWebactivity.super.onBackPressed();
                WebStorage.getInstance().deleteAllData();
                CookieSyncManager.createInstance(getApplicationContext());
                CookieManager.getInstance().removeAllCookie();
                finish();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_whatsapp_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.whatsapp_web_toolbar);
        this.whatsapp_web_toolbar = toolbar;

        AppManage.getInstance(gb_AppWebactivity.this).loadInterstitialAd(this);
        setSupportActionBar(toolbar);
        int i = 0;
        while (true) {
            if (i >= this.whatsapp_web_toolbar.getChildCount()) {
                break;
            }
            View childAt = this.whatsapp_web_toolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                AssetManager assets = getAssets();
                Typeface createFromAsset = Typeface.createFromAsset(assets, "fonts/" + getString(R.string.gb_PoppinsMedium));
                if (textView.getText().equals(getTitle())) {
                    textView.setTypeface(createFromAsset);
                    textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gb_white));
                    textView.setTextSize(getResources().getDimension(R.dimen._5sdp));
                    break;
                }
            }
            i++;
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Whatsapp Web");
        WebView webView = (WebView) findViewById(R.id.webview);
        this.webview = webView;
        webView.setWebViewClient(new C2987a());
        this.webview.getSettings().setLoadWithOverviewMode(true);
        this.webview.loadUrl(getString(R.string.gb_whatsapp_web_url));
        this.webview.getSettings().setJavaScriptEnabled(true);
        this.webview.getSettings().setBuiltInZoomControls(true);
        this.webview.getSettings().setDomStorageEnabled(true);
        this.webview.getSettings().setDisplayZoomControls(false);
        this.webview.getSettings().setUserAgentString(getString(R.string.gb_user_agent_string));
        this.whatsapp_web_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gb_AppWebactivity.this.onBackPressed();
            }
        });
    }

    private void setUpAdView() {
        if (gb_NetworkUtils.isNetworkConnected(this)) {
            gb_AllKeyHub.ShowInterstitialAdsOnCreate(this);
        } else {
            gb_Internet_Alert.alertDialogShow(this, "" + getString(R.string.gb_network_error));
        }
    }


    private class C2987a extends WebViewClient {
        private C2987a() {
        }

        private boolean m19859a(Uri uri) {
            if (uri.getHost().contains(gb_AppWebactivity.this.getString(R.string.gb_whatsapp_host))) {
                return false;
            }
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            if (intent.resolveActivity(gb_AppWebactivity.this.getPackageManager()) == null) {
                return true;
            }
            gb_AppWebactivity.this.startActivity(intent);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            return m19859a(webResourceRequest.getUrl());
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            return m19859a(Uri.parse(str));
        }
    }
}
