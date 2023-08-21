package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.TabAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_fragment.AllinOneGalleryFragment;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

public class gb_SaveStatusActivity extends AppCompatActivity {

    Activity activity;
    private TabLayout tabs;
    private ViewPager viewpager;
    private Toolbar recent_story_toolbar;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_SaveStatusActivity.this).showInterstitialAd(gb_SaveStatusActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_SaveStatusActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb_activity_save_status);

        this.activity = this;
        native_container=findViewById(R.id.native_container);
        AppManage.getInstance(gb_SaveStatusActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_SaveStatusActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);

        tabs = (TabLayout) findViewById(R.id.tabs);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        init();
        initViews();

        this.recent_story_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gb_SaveStatusActivity.this.onBackPressed();
            }
        });
    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.recent_story_toolbar);
        this.recent_story_toolbar = toolbar;
        setSupportActionBar(toolbar);
        int i = 0;
        while (true) {
            if (i >= this.recent_story_toolbar.getChildCount()) {
                break;
            }
            View childAt = this.recent_story_toolbar.getChildAt(i);
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
        getSupportActionBar().setTitle("Saved Status");
        this.recent_story_toolbar.setNavigationIcon(R.drawable.gb_back);
    }

    private void initViews() {
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(), 1);
        tabAdapter.addFragment(new AllinOneGalleryFragment("Images"), this.activity.getResources().getString(R.string.gb_images));
        tabAdapter.addFragment(new AllinOneGalleryFragment("Videos"), this.activity.getResources().getString(R.string.gb_videos));
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(5);
    }

   
}