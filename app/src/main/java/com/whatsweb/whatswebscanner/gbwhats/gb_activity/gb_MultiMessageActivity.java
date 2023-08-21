package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import static com.pesonal.adsdk.AppManage.ADMOB_N;
import static com.pesonal.adsdk.AppManage.FACEBOOK_N;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class gb_MultiMessageActivity extends AppCompatActivity {

    private CardView btn_send_message;
    private CountryCodePicker country_picker;
    private EditText edt_message;
    private EditText edt_number;
    private EditText edt_select_time;
    private ImageView img_contact_picker;
    FrameLayout native_container;
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_MultiMessageActivity.this).showInterstitialAd(gb_MultiMessageActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_MultiMessageActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);

    }
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_multi_message);
        init();
        click();
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_MultiMessageActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_MultiMessageActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
    }


    private void init() {
        this.country_picker = (CountryCodePicker) findViewById(R.id.country_picker);
        this.edt_number = (EditText) findViewById(R.id.edt_number);
        this.img_contact_picker = (ImageView) findViewById(R.id.img_contact_picker);
        this.edt_message = (EditText) findViewById(R.id.edt_message);
        this.edt_select_time = (EditText) findViewById(R.id.edt_select_time);
        this.btn_send_message = (CardView) findViewById(R.id.btn_send_message);
        this.country_picker.setEditText_registeredCarrierNumber(this.edt_number);
        this.country_picker.showArrow(true);
    }

    private void click() {
        this.img_contact_picker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setPackage("com.whatsapp");
                try {
                    gb_MultiMessageActivity.this.startActivityForResult(intent, 1);
                } catch (Exception unused) {
                    Toast.makeText(gb_MultiMessageActivity.this, "Whatsapp not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.btn_send_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_MultiMessageActivity.this).showInterstitialAd(gb_MultiMessageActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        if (!gb_MultiMessageActivity.this.country_picker.isValidFullNumber()) {
                            gb_MultiMessageActivity.this.edt_number.setError("Invalide Phone no");
                        } else if (gb_MultiMessageActivity.this.edt_message.getText().toString().length() != 0 && gb_MultiMessageActivity.this.edt_select_time.getText().toString().length() != 0) {
                            PackageManager packageManager = gb_MultiMessageActivity.this.getPackageManager();
                            Intent intent = new Intent("android.intent.action.VIEW");
                            try {
                                String obj = gb_MultiMessageActivity.this.edt_message.getText().toString();
                                String str = "";
                                for (int i = 0; i < Integer.valueOf(gb_MultiMessageActivity.this.edt_select_time.getText().toString()).intValue(); i++) {
                                    str = i == 0 ? obj : str + "\n" + obj;
                                }
                                Log.d("TAG", "onClick+++: " + str);
                                String fullNumber = gb_MultiMessageActivity.this.country_picker.getFullNumber();
                                if (!fullNumber.contains("+")) {
                                    fullNumber = "+" + fullNumber;
                                }
                                gb_MultiMessageActivity.this.edt_message.setText("");
                                gb_MultiMessageActivity.this.edt_number.setText("");
                                gb_MultiMessageActivity.this.edt_select_time.setText("");
                                Log.d("TAG", "onClickpho: " + fullNumber);
                                intent.setPackage("com.whatsapp");
                                intent.setType("text/plain");
                                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + fullNumber + "&text=" + URLEncoder.encode(str, Key.STRING_CHARSET_NAME)));
                                if (intent.resolveActivity(packageManager) != null) {
                                    gb_MultiMessageActivity.this.startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (gb_MultiMessageActivity.this.edt_message.getText().toString().length() == 0) {
                            gb_MultiMessageActivity.this.edt_message.setError("Enter Message");
                        } else if (gb_MultiMessageActivity.this.edt_select_time.getText().toString().length() == 0) {
                            gb_MultiMessageActivity.this.edt_select_time.setError("Enter Repeating Time");
                        }
                    }
                },"",AppManage.app_innerClickCntSwAd);
              /*  if (!gb_MultiMessageActivity.this.country_picker.isValidFullNumber()) {
                    gb_MultiMessageActivity.this.edt_number.setError("Invalide Phone no");
                } else if (gb_MultiMessageActivity.this.edt_message.getText().toString().length() != 0 && gb_MultiMessageActivity.this.edt_select_time.getText().toString().length() != 0) {
                    PackageManager packageManager = gb_MultiMessageActivity.this.getPackageManager();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    try {
                        String obj = gb_MultiMessageActivity.this.edt_message.getText().toString();
                        String str = "";
                        for (int i = 0; i < Integer.valueOf(gb_MultiMessageActivity.this.edt_select_time.getText().toString()).intValue(); i++) {
                            str = i == 0 ? obj : str + "\n" + obj;
                        }
                        Log.d("TAG", "onClick+++: " + str);
                        String fullNumber = gb_MultiMessageActivity.this.country_picker.getFullNumber();
                        if (!fullNumber.contains("+")) {
                            fullNumber = "+" + fullNumber;
                        }
                        gb_MultiMessageActivity.this.edt_message.setText("");
                        gb_MultiMessageActivity.this.edt_number.setText("");
                        gb_MultiMessageActivity.this.edt_select_time.setText("");
                        Log.d("TAG", "onClickpho: " + fullNumber);
                        intent.setPackage("com.whatsapp");
                        intent.setType("text/plain");
                        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + fullNumber + "&text=" + URLEncoder.encode(str, Key.STRING_CHARSET_NAME)));
                        if (intent.resolveActivity(packageManager) != null) {
                            gb_MultiMessageActivity.this.startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (gb_MultiMessageActivity.this.edt_message.getText().toString().length() == 0) {
                    gb_MultiMessageActivity.this.edt_message.setError("Enter Message");
                } else if (gb_MultiMessageActivity.this.edt_select_time.getText().toString().length() == 0) {
                    gb_MultiMessageActivity.this.edt_select_time.setError("Enter Repeating Time");
                }*/
            }
        });
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 == -1 && intent.hasExtra("contact")) {
            String stringExtra = intent.getStringExtra("contact");
            stringExtra.substring(2, 12);
            this.edt_number.setText(stringExtra.substring(2, 12));
        }
    }
}
