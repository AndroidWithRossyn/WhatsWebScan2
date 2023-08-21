package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.GalleryAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class gb_GalleryActivity extends AppCompatActivity {
    public static ArrayList<String> stringArrayListname = new ArrayList<>();
    private GalleryAdapter galleryAdapter;
    private Toolbar gallery_toolbar;
    private RecyclerView image_recyclerview;
    private String path;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private String type;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_GalleryActivity.this).showInterstitialAd(gb_GalleryActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_GalleryActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_gallery);
        init();
        click();
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_GalleryActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        AppManage.getInstance(gb_GalleryActivity.this).loadInterstitialAd(this);
    }


    private void click() {
        this.gallery_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gb_GalleryActivity.this.onBackPressed();
            }
        });
    }

    private void init() {
        this.type = getIntent().getStringExtra("type");

        Toolbar toolbar = (Toolbar) findViewById(R.id.gallery_toolbar);
        this.gallery_toolbar = toolbar;
        setSupportActionBar(toolbar);
        int i = 0;
        while (true) {
            if (i >= this.gallery_toolbar.getChildCount()) {
                break;
            }
            View childAt = this.gallery_toolbar.getChildAt(i);
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
        this.gallery_toolbar.setNavigationIcon(R.drawable.gb_back);
        this.image_recyclerview = (RecyclerView) findViewById(R.id.image_recyclerview);
        this.image_recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        if (Build.VERSION.SDK_INT > 29) {
            if (this.type.equalsIgnoreCase("images")) {
                this.path = Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images";
                getSupportActionBar().setTitle("WhatsApp Images");
                getAllImages(this.path);
            } else if (this.type.equalsIgnoreCase("video")) {
                this.path = Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video";
                getSupportActionBar().setTitle("WhatsApp Video");
                getAllVideo(this.path);
            } else if (this.type.equalsIgnoreCase("audio")) {
                this.path = Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio";
                getSupportActionBar().setTitle("WhatsApp Audio");
                getAllAudio(this.path);
            } else if (this.type.equalsIgnoreCase("documents")) {
                this.path = Environment.getExternalStorageDirectory() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents";
                getSupportActionBar().setTitle("WhatsApp Documents");
                getAllDocument(this.path);
            }
        } else if (this.type.equalsIgnoreCase("images")) {
            this.path = Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Images";
            getSupportActionBar().setTitle("WhatsApp Images");
            getAllImages(this.path);
        } else if (this.type.equalsIgnoreCase("video")) {
            this.path = Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Video";
            getSupportActionBar().setTitle("WhatsApp Video");
            getAllVideo(this.path);
        } else if (this.type.equalsIgnoreCase("audio")) {
            this.path = Environment.getExternalStorageDirectory() + "/WhatsApp/Media/WhatsApp Audio";
            getSupportActionBar().setTitle("WhatsApp Audio");
            getAllAudio(this.path);
        }
        Collections.reverse(this.stringArrayList);
        GalleryAdapter galleryAdapter2 = new GalleryAdapter(this, this.stringArrayList, this.type);
        this.galleryAdapter = galleryAdapter2;
        this.image_recyclerview.setAdapter(galleryAdapter2);
    }

    private void getAllDocument(String str) {
        new ArrayList();
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].getAbsolutePath().contains("Private") && !listFiles[i].getAbsolutePath().contains("Sent")) {
                    this.stringArrayList.add(listFiles[i].getAbsolutePath());
                }
            }
        }
    }

    public void getAllAudio(String str) {
        new ArrayList();
        File file = new File(str);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].getAbsolutePath().contains("Private") && !listFiles[i].getAbsolutePath().contains("Sent")) {
                    this.stringArrayList.add(listFiles[i].getAbsolutePath());
                    String valueOf = String.valueOf(listFiles[i]);
                    String substring = valueOf.substring(valueOf.lastIndexOf("/"), valueOf.lastIndexOf("."));
                    String substring2 = valueOf.substring(valueOf.lastIndexOf("."));
                    stringArrayListname.add(substring.substring(1) + substring2);
                }
            }
        }
    }

    public void getAllImages(String str) {
        File file = new File(str);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].getAbsolutePath().contains("Private") && !listFiles[i].getAbsolutePath().contains("Sent")) {
                    this.stringArrayList.add(listFiles[i].getAbsolutePath());
                }
            }
        }
        if (Build.VERSION.SDK_INT > 29) {
            this.stringArrayList.remove(0);
            this.stringArrayList.remove(1);
        }
    }

    public void getAllVideo(String str) {
        new ArrayList();
        File file = new File(str);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].getAbsolutePath().contains("Private") && !listFiles[i].getAbsolutePath().contains("Sent")) {
                    this.stringArrayList.add(listFiles[i].getAbsolutePath());
                }
            }
        }
    }


    public void onResume() {
        super.onResume();
        GalleryAdapter galleryAdapter2 = this.galleryAdapter;
        if (galleryAdapter2 != null) {
            galleryAdapter2.notifyDataSetChanged();
        }
    }
}
