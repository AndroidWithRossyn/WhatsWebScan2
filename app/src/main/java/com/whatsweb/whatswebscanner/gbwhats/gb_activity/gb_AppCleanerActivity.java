package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Stack;

public class gb_AppCleanerActivity extends AppCompatActivity {
    public static TextView audio_count;
    public static TextView audio_size;
    public static int audiocount;
    public static String audiosize;
    public static String directory_path;
    public static int doccount;
    public static String docsize;
    public static TextView document_count;
    public static TextView document_size;
    public static long imagecount;
    public static TextView images_size;
    public static String imagesize;
    public static TextView imges_count;
    public static LinearLayout layout_audio;
    public static LinearLayout layout_document;
    public static LinearLayout layout_images;
    public static LinearLayout layout_video;
    public static TextView video_count;
    public static TextView video_size;
    public static int videocount;
    public static String videosize;
    public static String whats_audio;
    public static String whats_document;
    public static String whats_images;
    public static String whats_video;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_AppCleanerActivity.this).showInterstitialAd(gb_AppCleanerActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_AppCleanerActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_whatsapp_cleaner);

        AppManage.getInstance(gb_AppCleanerActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_AppCleanerActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);

        native_container = findViewById(R.id.native_container);

        layout_images = (LinearLayout) findViewById(R.id.layout_images);
        layout_video = (LinearLayout) findViewById(R.id.layout_video);
        layout_audio = (LinearLayout) findViewById(R.id.layout_audio);
        layout_document = (LinearLayout) findViewById(R.id.layout_document);
        imges_count = (TextView) findViewById(R.id.imges_count);
        images_size = (TextView) findViewById(R.id.images_size);
        video_count = (TextView) findViewById(R.id.video_count);
        video_size = (TextView) findViewById(R.id.video_size);
        audio_count = (TextView) findViewById(R.id.audio_count);
        audio_size = (TextView) findViewById(R.id.audio_size);
        document_count = (TextView) findViewById(R.id.document_count);
        document_size = (TextView) findViewById(R.id.document_size);
        init();
        click();
    }


    private void click() {
        layout_images.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_AppCleanerActivity.this).showInterstitialAd(gb_AppCleanerActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(2);
                        Intent intent = new Intent(gb_AppCleanerActivity.this, gb_GalleryActivity.class);
                        intent.putExtra("type", "images");
                        gb_AppCleanerActivity.this.startActivity(intent);
                    }
                },AppManage.FACEBOOK,AppManage.app_mainClickCntSwAd);

            }
        });
        layout_video.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_AppCleanerActivity.this).showInterstitialAd(gb_AppCleanerActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(2);
                        Intent intent = new Intent(gb_AppCleanerActivity.this, gb_GalleryActivity.class);
                        intent.putExtra("type", "video");
                        gb_AppCleanerActivity.this.startActivity(intent);
                    }
                },AppManage.FACEBOOK,AppManage.app_mainClickCntSwAd);

            }
        });
        layout_audio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_AppCleanerActivity.this).showInterstitialAd(gb_AppCleanerActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(2);
                        Intent intent = new Intent(gb_AppCleanerActivity.this, gb_GalleryActivity.class);
                        intent.putExtra("type", "audio");
                        gb_AppCleanerActivity.this.startActivity(intent);
                    }
                },AppManage.FACEBOOK,AppManage.app_mainClickCntSwAd);

            }
        });
        layout_document.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_AppCleanerActivity.this).showInterstitialAd(gb_AppCleanerActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(2);
                        Intent intent = new Intent(gb_AppCleanerActivity.this, gb_GalleryActivity.class);
                        intent.putExtra("type", "documents");
                        gb_AppCleanerActivity.this.startActivity(intent);
                    }
                },AppManage.FACEBOOK,AppManage.app_mainClickCntSwAd);

            }
        });
    }

    public void mo12141a(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                mo12141a(file2);
            }
        }
        file.delete();
    }

    public void init() {
        if (Build.VERSION.SDK_INT > 29) {
            directory_path = Environment.getExternalStorageDirectory().getAbsolutePath();
            whats_images = directory_path + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images";
            whats_video = directory_path + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video";
            whats_audio = directory_path + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio";
            whats_document = directory_path + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents";
        } else {
            directory_path = Environment.getExternalStorageDirectory().getAbsolutePath();
            whats_images = directory_path + "/WhatsApp/Media/WhatsApp Images";
            whats_video = directory_path + "/WhatsApp/Media/WhatsApp Video";
            whats_audio = directory_path + "/WhatsApp/Media/WhatsApp Audio";
            whats_document = directory_path + "/WhatsApp/Media/WhatsApp Documents";
        }
        getAllImages(whats_images);
        getAllVideo(whats_video);
        getAllAudio(whats_audio);
        getDocumentFiles(whats_document);
        TextView textView = imges_count;
        textView.setText("Files: " + imagecount);
        TextView textView2 = video_count;
        textView2.setText("Files: " + videocount);
        TextView textView3 = audio_count;
        textView3.setText("Files: " + audiocount);
        TextView textView4 = document_count;
        textView4.setText("Files: " + doccount);
        TextView textView5 = images_size;
        textView5.setText("Size: " + imagesize);
        TextView textView6 = video_size;
        textView6.setText("Size: " + videosize);
        TextView textView7 = document_size;
        textView7.setText("Size: " + docsize);
        TextView textView8 = audio_size;
        textView8.setText("Size: " + audiosize);
    }

    public void getDocumentFiles(String str) {
        File[] fileArr = new File[0];
        File file = new File(str);
        long j = 0;
        if (file.isDirectory()) {
            fileArr = file.listFiles();
            for (File file2 : fileArr) {
                j += file2.length();
            }
        }
        doccount = fileArr.length - 2;
        docsize = Formatter.formatShortFileSize(this, j);
    }

    public void getAllAudio(String str) {
        File[] fileArr = new File[0];
        File file = new File(str);
        long j = 0;
        if (file.isDirectory()) {
            fileArr = file.listFiles();
            for (File file2 : fileArr) {
                j += file2.length();
            }
        }
        audiocount = fileArr.length - 2;
        audiosize = Formatter.formatShortFileSize(this, j);
    }

    public long dirSize(File file) {
        Stack stack = new Stack();
        stack.clear();
        stack.push(file);
        long j = 0;
        while (!stack.isEmpty()) {
            File[] listFiles = ((File) stack.pop()).listFiles();
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    stack.push(file2);
                } else {
                    j += file2.length();
                }
            }
        }
        return j;
    }

    public void getAllImages(String str) {
        File[] fileArr = new File[0];
        File file = new File(str);
        long j = 0;
        if (file.isDirectory()) {
            fileArr = file.listFiles();
            for (File file2 : fileArr) {
                j += file2.length();
            }
        }
        imagecount = (long) (fileArr.length - 2);
        imagesize = Formatter.formatShortFileSize(this, j);
    }

    public static long getFolderSize(Context context, File file) {
        String path = file.getParentFile().getPath();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_data", "_size"}, "_data ALIKE ?", new String[]{path}, null);
            long j = 0;
            if (cursor == null || !cursor.moveToFirst()) {
                return j;
            }
            do {
                j += cursor.getLong(1);
            } while (cursor.moveToNext());
            return j;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void getAllVideo(String str) {
        File[] fileArr = new File[0];
        File file = new File(str);
        long j = 0;
        if (file.isDirectory()) {
            fileArr = file.listFiles();
            for (File file2 : fileArr) {
                j += file2.length();
            }
        }
        videocount = fileArr.length - 2;
        videosize = Formatter.formatShortFileSize(this, j);
    }

    public static String getStringSizeLengthFile(long j) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        float f = (float) j;
        if (f < 1048576.0f) {
            return decimalFormat.format((double) (f / 1024.0f)) + " Kb";
        } else if (f < 1.07374182E9f) {
            return decimalFormat.format((double) (f / 1048576.0f)) + " Mb";
        } else if (f >= 1.09951163E12f) {
            return "";
        } else {
            return decimalFormat.format((double) (f / 1.07374182E9f)) + " Gb";
        }
    }
}
