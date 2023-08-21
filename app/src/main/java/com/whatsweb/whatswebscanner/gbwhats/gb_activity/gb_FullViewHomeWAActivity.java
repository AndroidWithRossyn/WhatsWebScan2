package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.ShowImagesWAAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;

public class gb_FullViewHomeWAActivity extends AppCompatActivity {
    private gb_FullViewHomeWAActivity activity;
    private int basePosition = 0;
    private ArrayList<File> fileArrayList;
    String fileName = "";
    public String saveFilePath = (Utils.RootDirectoryWhatsappShow + File.separator);
    ShowImagesWAAdapter showImagesAdapter;
    private ArrayList<Uri> uriArrayList;
    private ImageView imDelete;
    private TextView tvDeleteText;
    private ViewPager vpView;
    private LinearLayout LLDelete;
    private LinearLayout LLShare;
    private LinearLayout LLWhatsappShare;
    private ImageView imBack;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_FullViewHomeWAActivity.this).showInterstitialAd(gb_FullViewHomeWAActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_FullViewHomeWAActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_full_view);
        this.activity = this;
        AppManage.getInstance(gb_FullViewHomeWAActivity.this).loadInterstitialAd(this);
        imDelete = (ImageView) findViewById(R.id.imDelete);
        imBack = (ImageView) findViewById(R.id.imBack);
        tvDeleteText = (TextView) findViewById(R.id.tvDeleteText);
        vpView = (ViewPager) findViewById(R.id.vpView);
        LLDelete = (LinearLayout) findViewById(R.id.LLDelete);
        LLShare = (LinearLayout) findViewById(R.id.LLShare);
        LLWhatsappShare = (LinearLayout) findViewById(R.id.LLWhatsappShare);

        if (getIntent().getExtras() != null) {
            if (Build.VERSION.SDK_INT > 29) {
                this.uriArrayList = (ArrayList) getIntent().getSerializableExtra("ImageDataFile");
            } else {
                this.fileArrayList = (ArrayList) getIntent().getSerializableExtra("ImageDataFile");
            }
            this.basePosition = getIntent().getIntExtra("Position", 0);
            PrintStream printStream = System.out;
            printStream.println("YOGIIIIII " + this.basePosition);
        }
        initViews();
        setUpAdView();
    }

    private void setUpAdView() {
        if (gb_NetworkUtils.isNetworkConnected(this)) {
            gb_AllKeyHub.ShowInterstitialAdsOnCreate(this);
        } else {
            gb_Internet_Alert.alertDialogShow(this, "" + getString(R.string.gb_network_error));
        }
    }

    public void initViews() {
        imDelete.setImageDrawable(getResources().getDrawable(R.drawable.gb_ic_download));
        tvDeleteText.setText("Download");
        if (Build.VERSION.SDK_INT > 29) {
            this.showImagesAdapter = new ShowImagesWAAdapter(this, null, this.uriArrayList);
        } else {
            this.showImagesAdapter = new ShowImagesWAAdapter(this, this.fileArrayList, null);
        }
        vpView.setAdapter(this.showImagesAdapter);
        vpView.setCurrentItem(this.basePosition);
        vpView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int i, float f, int i2) {
                Log.e("TAG", "onPageScrolled");
            }


            public void onPageSelected(int i) {
                gb_FullViewHomeWAActivity.this.basePosition = i;
            }

            public void onPageScrollStateChanged(int i) {
                Log.e("TAG", "onPageScrollStateChanged");
            }
        });
        LLDelete.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AppManage.getInstance(gb_FullViewHomeWAActivity.this).showInterstitialAd(gb_FullViewHomeWAActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        fileName = "status_" + System.currentTimeMillis() + ".png";
                        if (Build.VERSION.SDK_INT > 29) {
                            new DownloadFileTask().execute(uriArrayList.get(basePosition).toString());
                            return;
                        }
                        deleteFileAA(basePosition);
                    }
                },"",AppManage.app_innerClickCntSwAd);

            }
        });
        LLShare.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AppManage.getInstance(gb_FullViewHomeWAActivity.this).showInterstitialAd(gb_FullViewHomeWAActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        if (Build.VERSION.SDK_INT > 29) {
                            Utils.shareFile(activity, false, uriArrayList.get(basePosition).toString());
                        } else if (new File(fileArrayList.get(basePosition).toString()).getName().contains(".mp4")) {
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
                AppManage.getInstance(gb_FullViewHomeWAActivity.this).showInterstitialAd(gb_FullViewHomeWAActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        if (Build.VERSION.SDK_INT > 29) {
                            Utils.repostWhatsApp(activity, false, uriArrayList.get(basePosition).toString());
                        } else if (new File(fileArrayList.get(basePosition).toString()).getName().contains(".mp4")) {
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
        Utils.createFileFolder();
        String path = new File(this.fileArrayList.get(i).toString()).getPath();
        String substring = path.substring(path.lastIndexOf("/") + 1);
        try {
            FileUtils.copyFileToDirectory(new File(path), new File(this.saveFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String substring2 = substring.substring(12);
        MediaScannerConnection.scanFile(this.activity, new String[]{new File(this.saveFilePath + substring2).getAbsolutePath()}, new String[]{new File(this.fileArrayList.get(i).toString()).getPath().endsWith(".mp4") ? "video/*" : "image/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {

            public void onMediaScannerConnected() {
            }

            public void onScanCompleted(String str, Uri uri) {
            }
        });
        new File(this.saveFilePath, substring).renameTo(new File(this.saveFilePath, substring2));
        gb_FullViewHomeWAActivity whatsappFullViewHomeWAActivity = this.activity;
        Toast.makeText(whatsappFullViewHomeWAActivity, this.activity.getResources().getString(R.string.gb_saved_to) + this.saveFilePath + substring2, Toast.LENGTH_LONG).show();
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

    class DownloadFileTask extends AsyncTask<String, String, String> {

        void onPostExecute0(String str, Uri uri) {
        }


        public void onProgressUpdate(String... strArr) {
        }

        DownloadFileTask() {
        }


        public String doInBackground(String... strArr) {
            try {
                InputStream openInputStream = gb_FullViewHomeWAActivity.this.activity.getContentResolver().openInputStream(Uri.parse(strArr[0]));
                File file = new File(Utils.RootDirectoryWhatsappShow + File.separator + gb_FullViewHomeWAActivity.this.fileName);
                file.setWritable(true, false);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.close();
                        openInputStream.close();
                        return null;
                    }
                }
            } catch (Exception e) {
                System.out.println("error in creating a file");
                e.printStackTrace();
                return null;
            }
        }


        public void onPostExecute(String str) {
            Utils.setToast(gb_FullViewHomeWAActivity.this.activity, gb_FullViewHomeWAActivity.this.activity.getResources().getString(R.string.gb_download_complete));
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    gb_FullViewHomeWAActivity whatsappFullViewHomeWAActivity = gb_FullViewHomeWAActivity.this.activity;
                    MediaScannerConnection.scanFile(whatsappFullViewHomeWAActivity, new String[]{new File(Utils.RootDirectoryWhatsappShow + File.separator + gb_FullViewHomeWAActivity.this.fileName).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                            onPostExecute0(str, uri);
                        }
                    });
                } else {
                    gb_FullViewHomeWAActivity whatsappFullViewHomeWAActivity2 = gb_FullViewHomeWAActivity.this.activity;
                    whatsappFullViewHomeWAActivity2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Utils.RootDirectoryWhatsappShow + File.separator + gb_FullViewHomeWAActivity.this.fileName))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void onCancelled() {
            super.onCancelled();
        }
    }


}