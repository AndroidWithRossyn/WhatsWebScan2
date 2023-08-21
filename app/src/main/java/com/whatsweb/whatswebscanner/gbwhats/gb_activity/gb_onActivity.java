package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.RatingDialogBuilder;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_all_native_ads;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AdsPrefs;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;

public class gb_onActivity extends AppCompatActivity {

    ImageView iv_start, iv_share, iv_rate, iv_privacy, iv_more;
    gb_AdsPrefs whatspref;
    LinearLayout native_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb_activity_start);
        native_container = findViewById(R.id.native_container);
        whatspref = new gb_AdsPrefs(this);
        AppManage.getInstance(gb_onActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_onActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);




        Dialog dialog = this.mRatingDialog;
        if (dialog == null || !dialog.isShowing()) {
            Dialog build = new RatingDialogBuilder(this, new RatingDialogBuilder.Callback() {
                public final void onDialogDismiss() {
                    dialog.dismiss();
                }
            }).build();
            this.mRatingDialog = build;
//            build.show();

            if (whatspref.getLive_button().equalsIgnoreCase("1")){
                build.show();
            }
        }
        iv_share = findViewById(R.id.iv_share);
        iv_share.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(intent);


        });

        iv_rate = findViewById(R.id.iv_rate);
        iv_rate.setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
            intent.setFlags(268468224);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(gb_onActivity.this, "You don't have Google Play installed", Toast.LENGTH_SHORT).show();
            }

        });

        iv_privacy = findViewById(R.id.iv_privacy);
        iv_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppManage.app_privacyPolicyLink));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {

                    e.printStackTrace();
                }


            }
        });
        iv_more = findViewById(R.id.iv_more);
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Bhola+App+Zone")));


            }
        });



        iv_start = findViewById(R.id.iv_start);
        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManage.getInstance(gb_onActivity.this).showInterstitialAd(gb_onActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        gb_onActivity.this.startActivity(new Intent(gb_onActivity.this,
                                whatspref.getScreen_status().equals("0") ? gb_extraActivity.class : gb_MainActivity.class));
                    }
                }, "", AppManage.app_mainClickCntSwAd);

            }
        });
    }
    private Dialog mRatingDialog;

    @Override
    public void onBackPressed() {

        BottomSheetDialog dialog = new BottomSheetDialog(gb_onActivity.this);
        dialog.setContentView(R.layout.gb_ads_exit_dialog);
        FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.show();
        Button yes = dialog.findViewById(R.id.exit_ok);

       FrameLayout Ad_Native =  dialog.findViewById(R.id.Ad_Native);

        if (whatspref.getAdd_Status().equals("1")) {
            gb_all_native_ads.NativeAdd(gb_onActivity.this, Ad_Native);
        }
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
    private void gotoUrl(String s) {
        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri)); }

}
