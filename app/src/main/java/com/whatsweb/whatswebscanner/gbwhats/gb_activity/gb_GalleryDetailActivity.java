package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.GalleryAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.ImageFullviewAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

import java.io.File;
import java.util.ArrayList;

public class gb_GalleryDetailActivity extends AppCompatActivity {
    public static int DELETE_REQUEST_CODE = 1003;
    private static ArrayList<String> strings;
    private String Is_From;
    private Toolbar detail_toolbar;
    GalleryAdapter galleryAdapter;
    private ImageFullviewAdapter imageFullviewAdapter;
    private int position = 0;
    ArrayList<Uri> removableList = new ArrayList<>();
    public String removableLists = new String();
    ActivityResultLauncher<IntentSenderRequest> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new GalleryDetailActivity$$ExternalSyntheticLambda0(this));
    private ViewPager viewpager;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_GalleryDetailActivity.this).showInterstitialAd(gb_GalleryDetailActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_GalleryDetailActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    public void lambda$new$0$GalleryDetailActivity(ActivityResult activityResult) {
        Log.i("TAG", "onActivityResult: ");
        if (activityResult == null || activityResult.getResultCode() != -1) {
            Log.i("TAG", "onActivityResult: can not delete");
            return;
        }
        for (int i = 0; i < gb_GalleryActivity.stringArrayListname.size(); i++) {
            if (gb_GalleryActivity.stringArrayListname.get(i).equalsIgnoreCase(this.removableLists)) {
                gb_GalleryActivity.stringArrayListname.remove(i);
                this.galleryAdapter.notifyDataSetChanged();
            }
        }
        Intent intent = new Intent(this, gb_GalleryActivity.class);
        intent.putExtra("type", this.Is_From);
        startActivity(intent);
        finish();
        Log.i("TAG", "onActivityResult: deleted");
    }

    public void setdata(ArrayList<String> arrayList) {
        strings = arrayList;
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_detail_activity);
        init();
        click();
        AppManage.getInstance(gb_GalleryDetailActivity.this).loadInterstitialAd(this);
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
                Toolbar toolbar = gb_GalleryDetailActivity.this.detail_toolbar;
                toolbar.setTitle("" + (i + 1) + "/" + gb_GalleryDetailActivity.strings.size());
            }
        });
    }

    private void init() {
        int i = 0;
        this.position = getIntent().getIntExtra("position", 0);
        this.Is_From = getIntent().getStringExtra("Is_From");
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
        this.detail_toolbar.setNavigationIcon(R.drawable.gb_back);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        ImageFullviewAdapter imageFullviewAdapter2 = new ImageFullviewAdapter(getApplicationContext(), strings, this.Is_From);
        this.imageFullviewAdapter = imageFullviewAdapter2;
        this.viewpager.setAdapter(imageFullviewAdapter2);
        this.viewpager.setCurrentItem(this.position);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("" + (this.position + 1) + "/" + strings.size());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gb_single_image, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            finish();
        } else if (itemId != R.id.action_delete) {
            if (itemId == R.id.action_share) {
                mo12308b(this.imageFullviewAdapter.stringArrayList.get(this.viewpager.getCurrentItem()));
            }
        } else if (Build.VERSION.SDK_INT >= 30) {
            delete(this.imageFullviewAdapter.stringArrayList.get(this.viewpager.getCurrentItem()));
        } else {
            deletin10(this.imageFullviewAdapter.stringArrayList.get(this.viewpager.getCurrentItem()));
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void deletin10(String str) {
        final File file = new File(str);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete?");
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("TAG", "onClick: " + file);
                if (file.delete()) {
                    for (int i2 = 0; i2 < gb_GalleryActivity.stringArrayListname.size(); i2++) {
                        if (gb_GalleryActivity.stringArrayListname.get(i2).equalsIgnoreCase(gb_GalleryDetailActivity.this.removableLists)) {
                            gb_GalleryActivity.stringArrayListname.remove(i2);
                            gb_GalleryDetailActivity.this.galleryAdapter.notifyDataSetChanged();
                        }
                    }
                    Intent intent = new Intent(gb_GalleryDetailActivity.this, gb_GalleryActivity.class);
                    intent.putExtra("type", gb_GalleryDetailActivity.this.Is_From);
                    gb_GalleryDetailActivity.this.startActivity(intent);
                    gb_GalleryDetailActivity.this.finish();
                }
            }
        });
        builder.create().show();
    }

    private void delete(String str) {
        this.removableList.add(Uri.parse(str));
        this.removableLists = str;
        final ArrayList arrayList = new ArrayList();
        MediaScannerConnection.scanFile(this, new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String str, Uri uri) {
                PendingIntent pendingIntent;
                arrayList.add(uri);
                if (Build.VERSION.SDK_INT >= 30) {
                    pendingIntent = MediaStore.createDeleteRequest(gb_GalleryDetailActivity.this.getContentResolver(), arrayList);
                    gb_GalleryDetailActivity.this.resultLauncher.launch(new IntentSenderRequest.Builder(pendingIntent).build());
                } else {
                    pendingIntent = null;
                }
                try {
                    gb_GalleryDetailActivity.this.startIntentSenderForResult(pendingIntent.getIntentSender(), gb_GalleryDetailActivity.DELETE_REQUEST_CODE, null, 0, 0, 0, null);
                } catch (Exception unused) {
                }
            }
        });
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
}
