package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.FileUtilsa;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gb_MainActivity extends AppCompatActivity {

    public static String adsimagestring;
    public static String adsvideostring;
    private ImageView img_direct_chat;
    private ImageView img_multi_message;
    private ImageView img_status_saver;
    private ImageView img_timer_message;
    private ImageView img_whats_cleaner;
    private ImageView img_whatsapp_web;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_MainActivity.this).showInterstitialAd(gb_MainActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_MainActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);

    }
    ActivityResultLauncher<Intent> permiisionActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

        public void onActivityResult(ActivityResult activityResult) {
            if (activityResult.getData() != null) {
                Uri data = activityResult.getData().getData();
                if (Build.VERSION.SDK_INT >= 19) {
                    gb_MainActivity.this.getContentResolver().takePersistableUriPermission(data, 2);
                    Log.e("onActivityResult", "treeUri--->> " + data.toString());
                    Prefrancemanager.putUriWp(gb_MainActivity.this, data.toString());
                    gb_MainActivity.this.setallData();
                }
            }
        }
    });

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_main);
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_MainActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_MainActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);

        isNetworkAvailable();
        if (Build.VERSION.SDK_INT > 29 && ((Prefrancemanager.getUriWp(this) == null || Prefrancemanager.getUriWp(this).equals("") || !Prefrancemanager.getUriWp(this).endsWith(".Statuses")) && isAppInstalled())) {
            askPermission();
        }
        if (isStoragePermissionGranted()) {
            init();
            click();
        }

    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void init() {
        this.img_whatsapp_web = (ImageView) findViewById(R.id.img_whatsapp_web);
        this.img_status_saver = (ImageView) findViewById(R.id.img_status_saver);
        this.img_direct_chat = (ImageView) findViewById(R.id.img_direct_chat);
        this.img_whats_cleaner = (ImageView) findViewById(R.id.img_whats_cleaner);
        this.img_timer_message = (ImageView) findViewById(R.id.img_timer_message);
        this.img_multi_message = (ImageView) findViewById(R.id.img_multi_message);
        setallData();

    }

    private void click() {
        this.img_whatsapp_web.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_MainActivity.this).showInterstitialAd(gb_MainActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(3);
                        gb_MainActivity.this.startActivity(new Intent(gb_MainActivity.this, gb_AppWebactivity.class));
                    }
                },"",AppManage.app_innerClickCntSwAd);
            }
        });
        this.img_status_saver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_MainActivity.this).showInterstitialAd(gb_MainActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(3);
                        gb_MainActivity.this.startActivity(new Intent(gb_MainActivity.this, gb_StatusSaverActivity.class));
                    }
                },"",AppManage.app_innerClickCntSwAd);

            }
        });
        this.img_direct_chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_MainActivity.this).showInterstitialAd(gb_MainActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(3);
                        gb_MainActivity.this.startActivity(new Intent(gb_MainActivity.this, gb_DirectChatActivity.class));
                    }
                },"",AppManage.app_innerClickCntSwAd);
            }
        });
        this.img_whats_cleaner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_MainActivity.this).showInterstitialAd(gb_MainActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(3);
                        gb_MainActivity.this.startActivity(new Intent(gb_MainActivity.this, gb_AppCleanerActivity.class));
                    }
                },"",AppManage.app_innerClickCntSwAd);
            }
        });
        this.img_timer_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_MainActivity.this).showInterstitialAd(gb_MainActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(3);
                        gb_MainActivity.this.startActivity(new Intent(gb_MainActivity.this, gb_TimerMessageActivity.class));
                    }
                },"",AppManage.app_innerClickCntSwAd);
            }
        });
        this.img_multi_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_MainActivity.this).showInterstitialAd(gb_MainActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        new Random().nextInt(3);
                        gb_MainActivity.this.startActivity(new Intent(gb_MainActivity.this, gb_MultiMessageActivity.class));
                    }
                },"",AppManage.app_innerClickCntSwAd);
            }
        });
    }

    private void setallData() {
        String str;
        int i;
        File file;
        File file2;
        DocumentFile[] documentFileArr;
        String str2;
        int i2;
        String str3 = "/storage/sdcard0/";
        String str4 = "/storage/usbcard1/";
        String str5 = "/storage/sdcard1/";
        String str6 = "/storage/extSdCard/";
        try {
            if (Build.VERSION.SDK_INT > 29) {
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
                if (Prefrancemanager.getUriWp(this) != null && Prefrancemanager.getUriWp(this).endsWith(".Statuses")) {
                    Uri parse = Uri.parse(Prefrancemanager.getUriWp(this));
                    Log.e("DocumentFile", "treeUri -->>" + parse.toString());
                    if (parse != null) {
                        getContentResolver().takePersistableUriPermission(parse, 2);
                        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getApplicationContext(), parse);
                        if (fromTreeUri.isDirectory()) {
                            DocumentFile[] listFiles = fromTreeUri.listFiles();
                            int length = listFiles.length;
                            int i3 = 0;
                            i = 0;
                            while (i3 < length) {
                                DocumentFile documentFile = listFiles[i3];
                                if (documentFile.getUri().toString().endsWith(".jpg") || documentFile.getUri().toString().endsWith(".png")) {
                                    documentFileArr = listFiles;
                                    i = 0;
                                    while (i <= Constant.recentImageArraylist.size()) {
                                        if (!Constant.recentImageArraylist.contains(String.valueOf(documentFile.getUri()))) {
                                            if (Constant.recentImageArraylist.size() == 1) {
                                                Constant.recentImageArraylist.add("");
                                            }
                                            Constant.recentImageArraylist.add(String.valueOf(documentFile.getUri()));
                                            Constant.f16277a.add(String.valueOf(documentFile.getUri()));
                                            String realPath = FileUtilsa.getRealPath(getApplicationContext(), documentFile.getUri());
                                            i2 = length;
                                            StringBuilder sb = new StringBuilder();
                                            str2 = str3;
                                            sb.append("path-->> ");
                                            sb.append(realPath);
                                            sb.append(" file-->> ");
                                            sb.append(documentFile.getUri());
                                            Log.e("DocumentFile: ", sb.toString());
                                            if (realPath != null) {
                                                realPath.equals("");
                                            }
                                        } else {
                                            str2 = str3;
                                            i2 = length;
                                        }
                                        i++;
                                        length = i2;
                                        str3 = str2;
                                    }
                                } else if (!documentFile.getUri().toString().endsWith(".nomedia")) {
                                    documentFileArr = listFiles;
                                    if (!Constant.recentVideoArraylist.contains(String.valueOf(documentFile.getUri()))) {
                                        if (Constant.recentVideoArraylist.size() == 1) {
                                            Constant.recentVideoArraylist.add("");
                                        }
                                        Constant.recentVideoArraylist.add(String.valueOf(documentFile.getUri()));
                                        Constant.f16277a.add(String.valueOf(documentFile.getUri()));
                                    }
                                } else {
                                    documentFileArr = listFiles;
                                }
                                i3++;
                                length = length;
                                listFiles = documentFileArr;
                                str3 = str3;
                            }
                            str = str3;
                            if (Constant.recentImageArraylist.size() == 1) {
                                Constant.recentImageArraylist.add("");
                            }
                            if (Constant.recentVideoArraylist.size() == 1) {
                                Constant.recentVideoArraylist.add("");
                            }
                            if (!new File(str6).exists()) {
                                str6 = "";
                            }
                            if (!new File(str5).exists()) {
                                str5 = str6;
                            }
                            if (!new File(str4).exists()) {
                                str4 = str5;
                            }
                            String str7 = !new File(str).exists() ? str : str4;
                            file = new File(str7 + "/WhatsApp/Media/.Statuses");
                            if (!file.exists()) {
                                String[] list = file.list();
                                Constant.recentImageArraylist = new ArrayList<>();
                                Constant.recentVideoArraylist = new ArrayList<>();
                                Constant.f16277a = new ArrayList<>();
                                int length2 = list.length;
                                while (i < length2) {
                                    String str8 = list[i];
                                    if (str8.toLowerCase().endsWith(".png") || str8.toLowerCase().endsWith(".jpg") || str8.toLowerCase().endsWith(".jpeg")) {
                                        Constant.recentImageArraylist.add(str7 + "/WhatsApp/Media/.Statuses/" + str8);
                                        Constant.f16277a.add(str7 + "/WhatsApp/Media/.Statuses/" + str8);
                                    } else {
                                        Constant.recentVideoArraylist.add(str7 + "/WhatsApp/Media/.Statuses/" + str8);
                                        Constant.f16277a.add(str7 + "/WhatsApp/Media/.Statuses/" + str8);
                                    }
                                    i++;
                                }
                                return;
                            }
                            return;
                        }
                    }
                }
                str = str3;
            } else {
                str = str3;
                if (Build.VERSION.SDK_INT < 30) {
                    file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses");
                } else {
                    file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
                }
                if (file2.exists()) {
                    String[] list2 = file2.list();

                    Constant.recentImageArraylist = new ArrayList<>();
                    Constant.recentVideoArraylist = new ArrayList<>();
                    Constant.f16277a = new ArrayList<>();
                    for (String str9 : list2) {
                        if (str9.toLowerCase().endsWith(".png") || str9.toLowerCase().endsWith(".jpg") || str9.toLowerCase().endsWith(".jpeg")) {
                            if (Constant.recentImageArraylist.size() == 1) {
                                Constant.recentImageArraylist.add("");
                            }
                            Constant.recentImageArraylist.add(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/.Statuses/" + str9);
                            Constant.f16277a.add(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/.Statuses/" + str9);
                        } else if (!str9.toLowerCase().endsWith(".nomedia")) {
                            if (Constant.recentVideoArraylist.size() == 1) {
                                Constant.recentVideoArraylist.add("");
                            }
                            Constant.recentVideoArraylist.add(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/.Statuses/" + str9);
                            Constant.f16277a.add(Environment.getExternalStorageDirectory().getPath() + "/WhatsApp/Media/.Statuses/" + str9);
                        }
                    }
                    return;
                }
            }
            i = 0;
            if (Constant.recentImageArraylist.size() == 1) {
            }
            if (Constant.recentVideoArraylist.size() == 1) {
            }
            if (!new File(str6).exists()) {
            }
            if (!new File(str5).exists()) {
            }
            if (!new File(str4).exists()) {
            }
            if (!new File(str).exists()) {
            }
            file = new File(!new File(str).exists() ? str : str4 + "/WhatsApp/Media/.Statuses");
            if (!file.exists()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ActivityCompat.checkSelfPermission(getApplicationContext(), "android.permission.READ_CONTACTS") == 0) {
            return true;
        }
        Get_CameraAndStorage_Permission();
        return false;
    }

    public boolean isAppInstalled() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
        return new File(sb.toString()).isDirectory();
    }

    private boolean askPermission() {
        Intent createOpenDocumentTreeIntent = ((StorageManager) getSystemService("storage")).getPrimaryStorageVolume().createOpenDocumentTreeIntent();
        String replace = ((Uri) createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString().replace("/root/", "/document/");
        createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(replace + "%3A" + "Android%2FMedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses"));
        this.permiisionActivityResultLauncher.launch(createOpenDocumentTreeIntent);
        return true;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        setallData();
    }

//    public void onBackPressed() {
//        RateMeNowDialog.showRateDialog(this, 0);
//    }

    private void Get_CameraAndStorage_Permission() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!addPermission(arrayList2, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            arrayList.add("Write Storage");
        }
        if (!addPermission(arrayList2, "android.permission.READ_CONTACTS")) {
            arrayList.add("Read Contact");
        }
        if (arrayList2.size() <= 0) {
            return;
        }
        if (arrayList.size() > 0) {
            for (int i = 0; i < 1; i++) {
                requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 1);
            }
            return;
        }
        requestPermissions((String[]) arrayList2.toArray(new String[arrayList2.size()]), 1);
    }

    private boolean addPermission(List<String> list, String str) {
        if (getApplicationContext().checkSelfPermission(str) == 0) {
            return true;
        }
        list.add(str);
        return shouldShowRequestPermissionRationale(str);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr[0] == 0) {
            init();
            click();
        }
    }

}
