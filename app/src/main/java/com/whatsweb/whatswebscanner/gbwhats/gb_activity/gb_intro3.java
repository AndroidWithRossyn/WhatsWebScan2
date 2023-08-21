package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

public class gb_intro3 extends AppCompatActivity {
    CardView btn_next;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_intro3.this).showInterstitialAd(gb_intro3.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_intro3.super.onBackPressed();
            }
        },"",AppManage.app_mainClickCntSwAd);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb_activity_intro3);
        native_container=findViewById(R.id.native_container);
        AppManage.getInstance(gb_intro3.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_intro3.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);



        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(gb_intro3.this).showInterstitialAd(gb_intro3.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(gb_intro3.this, gb_intro4.class);
                        startActivity(intent);
                    }
                },AppManage.FACEBOOK,AppManage.app_mainClickCntSwAd);
            }
        });
    }
}