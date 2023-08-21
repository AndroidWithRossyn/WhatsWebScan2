package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.load.Key;
import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_countrypicker.CountryCodePicker;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

import java.net.URLEncoder;

public class gb_DirectChatActivity extends AppCompatActivity {

    private CountryCodePicker country_picker;
    private EditText edt_message;
    private EditText edt_number;
    FrameLayout native_container;
    private CardView sendWhatsapp;

    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_DirectChatActivity.this).showInterstitialAd(gb_DirectChatActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_DirectChatActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_direct_chat);
        init();
        click();
        native_container = (FrameLayout) findViewById(R.id.native_container);
        AppManage.getInstance(gb_DirectChatActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
        AppManage.getInstance(gb_DirectChatActivity.this).loadInterstitialAd(this);
    }


    private void click() {
        this.sendWhatsapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_DirectChatActivity.this).showInterstitialAd(gb_DirectChatActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {
                        if (gb_DirectChatActivity.this.country_picker.isValidFullNumber()) {
                            PackageManager packageManager = gb_DirectChatActivity.this.getPackageManager();
                            Intent intent = new Intent("android.intent.action.VIEW");
                            try {
                                intent.setPackage("com.whatsapp");
                                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + gb_DirectChatActivity.this.country_picker.getFullNumber() + "&text=" + URLEncoder.encode("Hiiii", Key.STRING_CHARSET_NAME)));
                                if (intent.resolveActivity(packageManager) != null) {
                                    gb_DirectChatActivity.this.startActivity(intent);
                                }
                                gb_DirectChatActivity.this.edt_number.setText("");
                                gb_DirectChatActivity.this.edt_message.setText("");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {

                            gb_DirectChatActivity.this.edt_number.setError("Invalide Phone no");

                        }
                    }
                },"",AppManage.app_innerClickCntSwAd);
               /* if (gb_DirectChatActivity.this.country_picker.isValidFullNumber()) {
                    PackageManager packageManager = gb_DirectChatActivity.this.getPackageManager();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    try {
                        intent.setPackage("com.whatsapp");
                        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + gb_DirectChatActivity.this.country_picker.getFullNumber() + "&text=" + URLEncoder.encode("Hiiii", Key.STRING_CHARSET_NAME)));
                        if (intent.resolveActivity(packageManager) != null) {
                            gb_DirectChatActivity.this.startActivity(intent);
                        }
                        gb_DirectChatActivity.this.edt_number.setText("");
                        gb_DirectChatActivity.this.edt_message.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    gb_DirectChatActivity.this.edt_number.setError("Invalide Phone no");

                }*/

            }

        });

    }

    private void init() {
        this.country_picker = (CountryCodePicker) findViewById(R.id.country_picker);
        EditText editText = (EditText) findViewById(R.id.edt_number);
        this.edt_number = editText;
        this.country_picker.setEditText_registeredCarrierNumber(editText);
        this.country_picker.showArrow(true);
        this.edt_message = (EditText) findViewById(R.id.edt_message);
        this.sendWhatsapp = (CardView) findViewById(R.id.sendWhatsapp);
    }

}
