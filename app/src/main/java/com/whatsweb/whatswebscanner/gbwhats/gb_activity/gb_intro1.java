package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.MoreApp_Data;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_all_native_ads;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

import java.util.List;

public class gb_intro1 extends AppCompatActivity {

    CardView btn_next;
    FrameLayout native_container;

    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_intro1.this).showInterstitialAd(gb_intro1.this, new AppManage.MyCallback() {
            public void callbackCall() {
                BottomSheetDialog dialog = new BottomSheetDialog(gb_intro1.this);
                dialog.setContentView(R.layout.gb_ads_exit_dialog);
                //GET SPLASH MORE APP DATA
                List<MoreApp_Data> splash_more_data = AppManage.getInstance(gb_intro1.this).get_SPLASHMoreAppData();
                FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                dialog.show();
                Button yes = dialog.findViewById(R.id.exit_ok);
                Button cancel = dialog.findViewById(R.id.exit_cnl);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        },"",AppManage.app_mainClickCntSwAd);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb_activity_intro1);

        native_container=findViewById(R.id.native_container);
        AppManage.getInstance(gb_intro1.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        AppManage.getInstance(gb_intro1.this).loadInterstitialAd(this);
        //GET SPLASH MORE APP DATA
        List<MoreApp_Data> splash_more_data = AppManage.getInstance(gb_intro1.this).get_SPLASHMoreAppData();
        for(int i=0;i<splash_more_data.size();i++){
            Log.e("splash_more_data", "onCreate: "+splash_more_data.get(i).getApp_id()+"    "+splash_more_data.get(i).getApp_logo()+"    "+splash_more_data.get(i).getApp_name()+"    "+splash_more_data.get(i).getApp_packageName());
        }

        //GET EXIT MORE APP DATA
        List<MoreApp_Data> exit_more_data = AppManage.getInstance(gb_intro1.this).get_EXITMoreAppData();
        for(int i=0;i<exit_more_data.size();i++){
            Log.e("exit_more_data", "onCreate: "+exit_more_data.get(i).getApp_id()+"    "+exit_more_data.get(i).getApp_logo()+"    "+exit_more_data.get(i).getApp_name()+"    "+exit_more_data.get(i).getApp_packageName());
        }



        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(gb_intro1.this).showInterstitialAd(gb_intro1.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        Intent intent = new Intent(gb_intro1.this, gb_intro2.class);
                        startActivity(intent);
                    }
                },AppManage.FACEBOOK,AppManage.app_innerClickCntSwAd);

            }
        });

    }


}