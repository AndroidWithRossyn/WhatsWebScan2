package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

public class gb_HowItWorksActivity extends AppCompatActivity {
    private Toolbar toolBarWebView;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_HowItWorksActivity.this).showInterstitialAd(gb_HowItWorksActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_HowItWorksActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_help_activity);
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_HowItWorksActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_HowItWorksActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarWebView);
        this.toolBarWebView = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.toolBarWebView.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gb_HowItWorksActivity.this.onBackPressed();
            }
        });
    }

}
