package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

public class gb_extraActivity extends AppCompatActivity {

    RelativeLayout iv_getstart;
    FrameLayout native_container;
    TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb_activity_secound);
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_extraActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_extraActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        iv_getstart = findViewById(R.id.iv_getstart);
        iv_getstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(gb_extraActivity.this).showInterstitialAd(gb_extraActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(gb_extraActivity.this, gb_MainActivity.class);
                        startActivity(intent);
                    }
                },AppManage.FACEBOOK,AppManage.app_innerClickCntSwAd);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_extraActivity.this).showInterstitialAd(gb_extraActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_extraActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
}
