package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.ShowImagesAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class gb_FullViewActivity extends AppCompatActivity {
    private gb_FullViewActivity activity;
    private int basePosition = 0;
    RelativeLayout rl1,rl2,rl3;
    private ArrayList<File> fileArrayList;
    ShowImagesAdapter showImagesAdapter;
    private ViewPager vpView;
    private LinearLayout LLDelete;
    private LinearLayout LLShare;
    private LinearLayout LLWhatsappShare;
    private ImageView imBack;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_full_view);
        this.activity = this;
        AppManage.getInstance(gb_FullViewActivity.this).loadInterstitialAd(this);


        vpView = (ViewPager) findViewById(R.id.vpView);
        LLDelete = (LinearLayout) findViewById(R.id.LLDelete);
        LLShare = (LinearLayout) findViewById(R.id.LLShare);
        LLWhatsappShare = (LinearLayout) findViewById(R.id.LLWhatsappShare);
        imBack = (ImageView) findViewById(R.id.imBack);

        if (getIntent().getExtras() != null) {
            this.fileArrayList = (ArrayList) getIntent().getSerializableExtra("ImageDataFile");
            this.basePosition = getIntent().getIntExtra("Position", 0);
        }
        initViews();
    }
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_FullViewActivity.this).showInterstitialAd(gb_FullViewActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_FullViewActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    public void initViews() {
        this.showImagesAdapter = new ShowImagesAdapter(this, this.fileArrayList, this);
        vpView.setAdapter(this.showImagesAdapter);
        vpView.setCurrentItem(this.basePosition);
        vpView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            public void onPageScrolled(int i, float f, int i2) {
                Log.e("TAG", "onPageScrolled");
            }


            public void onPageSelected(int i) {
                gb_FullViewActivity.this.basePosition = i;
            }


            public void onPageScrollStateChanged(int i) {
                Log.e("TAG", "onPageScrollStateChanged");
            }
        });
        LLDelete.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AppManage.getInstance(gb_FullViewActivity.this).showInterstitialAd(gb_FullViewActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setPositiveButton(getResources().getString(R.string.gb_yes), new DialogInterface.OnClickListener() {
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                if (fileArrayList.get(basePosition).delete()) {
                                    deleteFileAA(basePosition);
                                }
                            }
                        });
                        builder.setNegativeButton(getResources().getString(R.string.gb_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog create = builder.create();
                        create.setTitle(getResources().getString(R.string.gb_do_u_want_to_dlt));
                        create.show();
                    }
                },"",AppManage.app_innerClickCntSwAd);
               /* AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setPositiveButton(getResources().getString(R.string.gb_yes), new DialogInterface.OnClickListener() {
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        if (fileArrayList.get(basePosition).delete()) {
                            deleteFileAA(basePosition);
                        }
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.gb_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog create = builder.create();
                create.setTitle(getResources().getString(R.string.gb_do_u_want_to_dlt));
                create.show();*/
            }
        });
        LLShare.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AppManage.getInstance(gb_FullViewActivity.this).showInterstitialAd(gb_FullViewActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        if (fileArrayList.get(basePosition).getName().contains(".mp4")) {
                            Utils.shareVideo(activity, fileArrayList.get(basePosition).getPath());
                        } else {
                            Utils.shareImage(activity, fileArrayList.get(basePosition).getPath());
                        }
                    }
                },"",AppManage.app_innerClickCntSwAd);


            }
        });
        LLWhatsappShare.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AppManage.getInstance(gb_FullViewActivity.this).showInterstitialAd(gb_FullViewActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        if (fileArrayList.get(basePosition).getName().contains(".mp4")) {
                            Utils.shareImageVideoOnWhatsapp(activity, fileArrayList.get(basePosition).getPath(), true);
                        } else {
                            Utils.shareImageVideoOnWhatsapp(activity, fileArrayList.get(basePosition).getPath(), false);
                        }
                    }
                },"",AppManage.app_innerClickCntSwAd);

            }
        });
        imBack.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void onResume() {
        super.onResume();
        this.activity = this;
    }

    public void deleteFileAA(int i) {
        this.fileArrayList.remove(i);
        this.showImagesAdapter.notifyDataSetChanged();
        Utils.setToast(this.activity, getResources().getString(R.string.gb_file_deleted));
        if (this.fileArrayList.isEmpty()) {
            onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void setLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }


}