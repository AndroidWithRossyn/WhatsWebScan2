package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.gb_customview.CustomTextView;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.StatusMainRecylerviewAdapter;

import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Constant;

import java.io.File;
import java.util.ArrayList;

public class gb_StatusSaverActivity extends AppCompatActivity {

    public static String adsimagestrings;
    public static String adsvideostrings;
    private CardView layout_how_works;
    private CardView layout_recent_status;
    private CardView layout_saved_status;
    StatusMainRecylerviewAdapter statusMainRecylerviewAdapter;
    FrameLayout native_container;
    CustomTextView gb_one,gb_two,gb_three,gb_four;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_status_server);
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_StatusSaverActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_StatusSaverActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        init();
        layout_recent_status = (CardView) findViewById(R.id.layout_recent_status);
        layout_how_works = (CardView) findViewById(R.id.layout_how_works);
        layout_saved_status = (CardView) findViewById(R.id.layout_saved_status);
       /* gb_one = (CustomTextView) findViewById(R.id.gb_one);
        gb_two = (CustomTextView) findViewById(R.id.gb_two);
        gb_three = (CustomTextView) findViewById(R.id.gb_three);
        gb_four = (CustomTextView) findViewById(R.id.gb_four);
        gb_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(getApplicationContext(), gb_appActivity.class));
                    }
                }, "", AppManage.app_innerClickCntSwAd);


            }
        });
        gb_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(getApplicationContext(), gb_appActivity.class));
                    }
                }, "", AppManage.app_innerClickCntSwAd);


            }
        });
        gb_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(getApplicationContext(), gb_appActivity.class));
                    }
                }, "", AppManage.app_innerClickCntSwAd);


            }
        });
        gb_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(getApplicationContext(), gb_appActivity.class));
                    }
                }, "", AppManage.app_innerClickCntSwAd);


            }
        });*/
        layout_recent_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(getApplicationContext(), gb_appActivity.class));
                    }
                }, "", AppManage.app_innerClickCntSwAd);

            }
        });
        layout_saved_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        startActivity(new Intent(getApplicationContext(), gb_SaveStatusActivity.class));
                    }
                }, "", AppManage.app_innerClickCntSwAd);

            }
        });
        layout_how_works.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                    AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                        public void callbackCall() {
                            startActivity(new Intent(getApplicationContext(), gb_HowItWorksActivity.class));
                        }
                    }, "", AppManage.app_innerClickCntSwAd);

                }
            });
        }
        @Override
        public void onBackPressed () {
            AppManage.getInstance(gb_StatusSaverActivity.this).showInterstitialAd(gb_StatusSaverActivity.this, new AppManage.MyCallback() {
                public void callbackCall() {
                    gb_StatusSaverActivity.super.onBackPressed();
                }
            }, "", AppManage.app_mainClickCntSwAd);
        }

        private void init () {

            this.layout_recent_status = (CardView) findViewById(R.id.layout_recent_status);
            this.layout_saved_status = (CardView) findViewById(R.id.layout_saved_status);
            this.layout_how_works = (CardView) findViewById(R.id.layout_how_works);
            if (Constant.f16277a.isEmpty()) {
                Log.e("Akash", "Constant.f16277a.isEmpty():  " + Constant.f16277a.isEmpty());
                return;
            }
            StatusMainRecylerviewAdapter statusMainRecylerviewAdapter2 = new StatusMainRecylerviewAdapter(this, Constant.f16277a);
            this.statusMainRecylerviewAdapter = statusMainRecylerviewAdapter2;
        }


        public void onResume () {
            super.onResume();
            try {
                Constant.savedImagesArraylist = new ArrayList<>();
                File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/Whats Web Scan/Status Images");
                String[] list = file.list();
                int length = list.length;
                for (int i = 0; i < length; i++) {
                    if (i == 1) {
                        adsimagestrings = file.getPath() + "/" + list[i];
                        ArrayList<String> arrayList = Constant.savedImagesArraylist;
                        arrayList.add(file.getPath() + "/" + list[i]);
                    }
                    ArrayList<String> arrayList2 = Constant.savedImagesArraylist;
                    arrayList2.add(file.getPath() + "/" + list[i]);
                }
                if (Constant.savedImagesArraylist.size() == 1) {
                    Constant.savedImagesArraylist.add(1, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Constant.savedVideoArraylist = new ArrayList<>();
                File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/Whats Web Scan/Status Videos");
                String[] list2 = file2.list();
                int length2 = list2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    if (i2 == 1) {
                        adsvideostrings = file2.getPath() + "/" + list2[i2];
                        ArrayList<String> arrayList3 = Constant.savedVideoArraylist;
                        arrayList3.add(file2.getPath() + "/" + list2[i2]);
                    }
                    ArrayList<String> arrayList4 = Constant.savedVideoArraylist;
                    arrayList4.add(file2.getPath() + "/" + list2[i2]);
                }
                if (Constant.savedVideoArraylist.size() == 1) {
                    Constant.savedVideoArraylist.add(1, "");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
