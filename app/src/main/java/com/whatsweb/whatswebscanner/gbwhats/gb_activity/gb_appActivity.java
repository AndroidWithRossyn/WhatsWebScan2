package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.TabAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_fragment.WhatsappImageFragment;
import com.whatsweb.whatswebscanner.gbwhats.gb_fragment.WhatsappQImageFragment;
import com.whatsweb.whatswebscanner.gbwhats.gb_fragment.WhatsappQVideoFragment;
import com.whatsweb.whatswebscanner.gbwhats.gb_fragment.WhatsappVideoFragment;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Utils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class gb_appActivity extends AppCompatActivity {
    private gb_appActivity activity;
    private ArrayList<Uri> fileArrayList;
    ProgressDialog progressDialog;
    private TabLayout tabs;
    private ViewPager viewpager;
    private TextView tvAllowAccess;
    private Toolbar recent_story_toolbar;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_appActivity.this).showInterstitialAd(gb_appActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_appActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_whatsapp);

        this.activity = this;
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_appActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        tvAllowAccess = (TextView) findViewById(R.id.tvAllowAccess);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        init();
        Utils.createFileFolder();
        initViews();
        this.recent_story_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gb_appActivity.this.onBackPressed();
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
        getSupportActionBar().setTitle("Recent Status");
        this.recent_story_toolbar.setNavigationIcon(R.drawable.gb_back);
    }

   

    public void onResume() {
        super.onResume();
        this.activity = this;
    }

    private void initViews() {
        this.fileArrayList = new ArrayList<>();
        initProgress();
        if (Build.VERSION.SDK_INT <= 29) {
            setupViewPager(viewpager);
            tabs.setupWithViewPager(viewpager);
        } else if (getContentResolver().getPersistedUriPermissions().size() > 0) {
            this.progressDialog.show();
            new LoadAllFiles().execute();
            tvAllowAccess.setVisibility(View.GONE);
        } else {
            tvAllowAccess.setVisibility(View.VISIBLE);
        }
        tvAllowAccess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Dialog dialog = new Dialog(gb_appActivity.this.activity, R.style.SheetDialog);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.gb_dialog_whatsapp_permission);
                TextView tvAllow = (TextView) dialog.findViewById(R.id.tvAllow);

                tvAllow.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        try {
                            if (Build.VERSION.SDK_INT > 29) {
                                Intent createOpenDocumentTreeIntent = ((StorageManager) gb_appActivity.this.activity.getSystemService(STORAGE_SERVICE)).getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                                String replace = ((Uri) createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString().replace("/root/", "/document/");
                                createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(replace + "%3A" + "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"));
                                gb_appActivity.this.startActivityForResult(createOpenDocumentTreeIntent, 2001);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter tabAdapter = new TabAdapter(this.activity.getSupportFragmentManager(), 1);
        tabAdapter.addFragment(new WhatsappImageFragment(), getResources().getString(R.string.gb_images));
        tabAdapter.addFragment(new WhatsappVideoFragment(), getResources().getString(R.string.gb_videos));
        viewPager.setAdapter(tabAdapter);
        viewPager.setOffscreenPageLimit(1);
    }

    public void setLocale(String str) {
        Locale locale = new Locale(str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public void initProgress() {
        ProgressDialog progressDialog2 = new ProgressDialog(this.activity, R.style.AppCompatAlertDialogStyle);
        this.progressDialog = progressDialog2;
        progressDialog2.setProgressStyle(0);
        this.progressDialog.setTitle("Loading");
        this.progressDialog.setMessage("Loading Status. Please wait...");
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setCanceledOnTouchOutside(false);
    }


    public void onActivityResult(int i, int i2, Intent intent) {
        try {
            super.onActivityResult(i, i2, intent);
            if (i == 2001 && i2 == -1) {
                Uri data = intent.getData();
                if (data.toString().contains(".Statuses")) {
                    getContentResolver().takePersistableUriPermission(data, 3);
                    this.progressDialog.show();
                    if (Build.VERSION.SDK_INT > 29) {
                        new LoadAllFiles().execute();
                        return;
                    }
                    return;
                }
                gb_appActivity gbappActivity = this.activity;
                Utils.infoDialog(gbappActivity, gbappActivity.getResources().getString(R.string.gb_wrong_folder), this.activity.getResources().getString(R.string.gb_selected_wrong_folder));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class LoadAllFiles extends AsyncTask<String, String, String> {

        public void onProgressUpdate(String... strArr) {
        }

        LoadAllFiles() {
        }


        public String doInBackground(String... strArr) {
            DocumentFile[] listFiles = Objects.requireNonNull(DocumentFile.fromTreeUri(gb_appActivity.this.activity, gb_appActivity.this.activity.getContentResolver().getPersistedUriPermissions().get(0).getUri())).listFiles();
            for (DocumentFile documentFile : listFiles) {
                if (!documentFile.isDirectory() && !documentFile.getName().equals(".nomedia")) {
                    gb_appActivity.this.fileArrayList.add(documentFile.getUri());
                }
            }
            return null;
        }


        public void onPostExecute(String str) {
            gb_appActivity.this.progressDialog.dismiss();
            TabAdapter tabAdapter = new TabAdapter(gb_appActivity.this.activity.getSupportFragmentManager(), 1);
            tabAdapter.addFragment(new WhatsappQImageFragment(gb_appActivity.this.fileArrayList), gb_appActivity.this.getResources().getString(R.string.gb_images));
            tabAdapter.addFragment(new WhatsappQVideoFragment(gb_appActivity.this.fileArrayList), gb_appActivity.this.getResources().getString(R.string.gb_videos));
            viewpager.setAdapter(tabAdapter);
            viewpager.setOffscreenPageLimit(1);
            tabs.setupWithViewPager(viewpager);
            tvAllowAccess.setVisibility(View.GONE);
        }


        public void onCancelled() {
            super.onCancelled();
            gb_appActivity.this.progressDialog.dismiss();
        }
    }
}