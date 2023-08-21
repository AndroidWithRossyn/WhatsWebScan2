package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.ImageFullviewAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.StatusDataAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

import java.io.File;
import java.util.ArrayList;

public class gb_DetailActivity extends AppCompatActivity {
    private static ArrayList<String> strings;
    private String Is_From;
    boolean adapt;
    boolean adapte;
    private Toolbar detail_toolbar;
    Menu downloadbtn;
    private ImageFullviewAdapter imageFullviewAdapter;
    private Menu menu;
    private int position = 0;
    Menu truebtn;
    FrameLayout native_container;
    private ViewPager viewpager;
    Boolean visibleicon = false;

    public void setdata(ArrayList<String> arrayList) {
        strings = arrayList;
    }

    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_DetailActivity.this).showInterstitialAd(gb_DetailActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_DetailActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_detail_activity);
        init();
        click();
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_DetailActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        AppManage.getInstance(gb_DetailActivity.this).loadInterstitialAd(this);
    }


    private void click() {
        this.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                if (gb_DetailActivity.this.Is_From.equals("recent")) {
                    gb_DetailActivity.this.isFileExist();
                }
            }
        });
    }

    private void init() {
        int i = 0;
        this.position = getIntent().getIntExtra("position", 0);
        this.Is_From = getIntent().getStringExtra("Is_From");
        this.adapt = getIntent().getBooleanExtra("adapter", false);
        this.adapte = getIntent().getBooleanExtra("adapter", false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        this.detail_toolbar = toolbar;
        setSupportActionBar(toolbar);
        while (true) {
            if (i >= this.detail_toolbar.getChildCount()) {
                break;
            }
            View childAt = this.detail_toolbar.getChildAt(i);
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
        if (this.Is_From.equals("recent")) {
            getSupportActionBar().setTitle("Recent Status");
        } else {
            getSupportActionBar().setTitle("Saved Status");
        }
        this.detail_toolbar.setNavigationIcon(R.drawable.gb_back);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        ImageFullviewAdapter imageFullviewAdapter2 = new ImageFullviewAdapter(getApplicationContext(), strings, this.Is_From);
        this.imageFullviewAdapter = imageFullviewAdapter2;
        this.viewpager.setAdapter(imageFullviewAdapter2);
        this.viewpager.setCurrentItem(this.position);
    }

    private Boolean checkdownloaded(String str) {
        String str2;
        File file;
        if (Build.VERSION.SDK_INT < 30) {
            String substring = str.substring(str.lastIndexOf("/"), str.lastIndexOf("."));
            str2 = substring.substring(1) + str.substring(str.lastIndexOf("."));
        } else {
            String substring2 = str.substring(str.lastIndexOf("%2F"), str.lastIndexOf("."));
            str2 = substring2.substring(3) + str.substring(str.lastIndexOf("."));
        }
        if (str.endsWith(".mp4")) {
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + getResources().getString(R.string.app_name) + "/" + getResources().getString(R.string.gb_status_video));
        } else {
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + getResources().getString(R.string.app_name) + "/" + getResources().getString(R.string.gb_status_image));
        }
        new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" + getResources().getString(R.string.app_name) + "/" + getResources().getString(R.string.gb_status_image));
        if (new File(file + "/" + str2).exists()) {
            this.visibleicon = true;
        } else {
            this.visibleicon = false;
        }
        return this.visibleicon;
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.menu = menu2;
        getMenuInflater().inflate(R.menu.gb_detail_menu, menu2);
        return super.onCreateOptionsMenu(menu2);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
            case R.id.item_download /*{ENCODED_INT: 2131231080}*/:
                mo12308a(this.imageFullviewAdapter.stringArrayList.get(this.viewpager.getCurrentItem()));
                break;
            case R.id.item_share /*{ENCODED_INT: 2131231081}*/:
                mo12308b(this.imageFullviewAdapter.stringArrayList.get(this.viewpager.getCurrentItem()));
                break;
            case R.id.truebtn /*{ENCODED_INT: 2131231364}*/:
                Toast.makeText(this, "Already Downloaded", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void mo12308b(String str) {
        Uri uri;
        try {
            File file = new File(str);
            if (Build.VERSION.SDK_INT >= 29) {
                uri = Uri.parse(str);
            } else if (Build.VERSION.SDK_INT >= 24) {
                try {
                    uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
                } catch (Exception unused) {
                    uri = Uri.fromFile(file);
                }
            } else {
                uri = Uri.fromFile(file);
            }
            Intent intent = new Intent("android.intent.action.SEND");
            if (str.endsWith("mp4")) {
                intent.setType("video/*");
            } else {
                intent.setType("image/*");
            }
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
            intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + getPackageName());
            intent.addFlags(1);
            intent.addFlags(2);
            intent.setFlags(268435456);
            startActivity(Intent.createChooser(intent, "Choose one..."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu2) {
        if (this.Is_From.equals("recent")) {
            Boolean checkdownloaded = checkdownloaded(strings.get(this.position));
            this.visibleicon = checkdownloaded;
            if (checkdownloaded.booleanValue()) {
                menu2.findItem(R.id.truebtn).setVisible(true);
                menu2.findItem(R.id.item_download).setVisible(false);
            } else {
                menu2.findItem(R.id.item_download).setVisible(true);
                menu2.findItem(R.id.truebtn).setVisible(false);
            }
        } else {
            menu2.findItem(R.id.item_download).setVisible(false);
            menu2.findItem(R.id.truebtn).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu2);
    }

    public void mo12308a(String str) {
        String str2;
        File file;
        if (Build.VERSION.SDK_INT < 30) {
            String substring = str.substring(str.lastIndexOf("/"), str.lastIndexOf("."));
            str2 = substring.substring(1) + str.substring(str.lastIndexOf("."));
        } else {
            String substring2 = str.substring(str.lastIndexOf("%2F"), str.lastIndexOf("."));
            str2 = substring2.substring(3) + str.substring(str.lastIndexOf("."));
        }
        if (str.endsWith(".mp4")) {
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + getResources().getString(R.string.app_name) + "/" + getResources().getString(R.string.gb_status_video));
        } else {
            file = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + getResources().getString(R.string.app_name) + "/" + getResources().getString(R.string.gb_status_image));
        }
        File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" + getResources().getString(R.string.app_name) + "/" + getResources().getString(R.string.gb_status_image));
        if (new File(file + "/" + str2).exists()) {
            Toast.makeText(this, "Already Downloaded", Toast.LENGTH_SHORT).show();
        } else if (Build.VERSION.SDK_INT >= 30) {
            StatusDataAdapter.copyFileOnAboveQ(str, Uri.parse(str), str2, file2, this);
        } else if (str.endsWith("mp4")) {
            StatusDataAdapter.saveVideo(str, this);
        } else {
            StatusDataAdapter.saveFile(str, this);
        }
    }

    private void isFileExist() {
        File file = new File(this.imageFullviewAdapter.stringArrayList.get((this.imageFullviewAdapter.stringArrayList.size() - this.viewpager.getCurrentItem()) - 1));
        Log.d("TAG", "onPageSelected: " + file.getName());
        File file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/" + getResources().getString(R.string.app_name) + "/" + getResources().getString(R.string.gb_status_image) + "/" + file.getName());
        StringBuilder sb = new StringBuilder();
        sb.append("onPageSelected: ");
        sb.append(file.getName());
        sb.append("  ");
        sb.append(file2.getName());
        sb.append("  ");
        sb.append(file2.exists());
        Log.d("TAG", sb.toString());
        if (file2.exists()) {
            Menu menu2 = this.menu;
            if (menu2 != null) {
                menu2.findItem(R.id.item_download).setVisible(false);
                return;
            }
            return;
        }
        Menu menu3 = this.menu;
        if (menu3 != null) {
            menu3.findItem(R.id.item_download).setVisible(true);
        }
    }
}
