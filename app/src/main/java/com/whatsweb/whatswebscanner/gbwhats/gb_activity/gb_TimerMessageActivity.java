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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.load.Key;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_countrypicker.CountryCodePicker;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_Internet_Alert;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_NetworkUtils;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;
import com.whatsweb.whatswebscanner.gbwhats.gb_receiver.AlarmReceiver;
import com.whatsweb.whatswebscanner.gbwhats.gb_receiver.DailyClass;
import com.whatsweb.whatswebscanner.gbwhats.gb_receiver.LoginPreferenceManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;

public class gb_TimerMessageActivity extends AppCompatActivity {

    private HashMap<String, DailyClass> allFileDatas = new HashMap<>();
    private CardView btn_send_message;
    private ImageView contact_list;
    private CountryCodePicker country_code;
    private EditText edt_message;
    private EditText edt_number;
    private Gson gson = new Gson();
    private RadioButton radio_daily;
    private RadioButton radio_hourly;
    private long time;
    FrameLayout native_container;
    private Type type = new TypeToken<HashMap<String, DailyClass>>() {
    }.getType();

    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_TimerMessageActivity.this).showInterstitialAd(gb_TimerMessageActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_TimerMessageActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_timer_message);
        init();
        click();
        native_container = findViewById(R.id.native_container);
        AppManage.getInstance(gb_TimerMessageActivity.this).loadInterstitialAd(this);
        AppManage.getInstance(gb_TimerMessageActivity.this).showNative((ViewGroup) findViewById(R.id.native_container), ADMOB_N[0], FACEBOOK_N[0]);
    }


    private void click() {

        this.contact_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setPackage("com.whatsapp");
                try {
                    gb_TimerMessageActivity.this.startActivityForResult(intent, 1);
                } catch (Exception unused) {
                    Toast.makeText(gb_TimerMessageActivity.this, "Whatsapp not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.btn_send_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppManage.getInstance(gb_TimerMessageActivity.this).showInterstitialAd(gb_TimerMessageActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        if (!gb_TimerMessageActivity.this.country_code.isValidFullNumber()) {
                            gb_TimerMessageActivity.this.edt_number.setError("Invalide Phone no");
                        } else if (gb_TimerMessageActivity.this.edt_message.getText().toString().length() != 0) {
                            PackageManager packageManager = gb_TimerMessageActivity.this.getPackageManager();
                            Intent intent = new Intent("android.intent.action.VIEW");
                            String obj = gb_TimerMessageActivity.this.edt_message.getText().toString();
                            String fullNumber = gb_TimerMessageActivity.this.country_code.getFullNumber();
                            if (!fullNumber.contains("+")) {
                                fullNumber = "+" + fullNumber;
                            }
                            Calendar instance = Calendar.getInstance();
                            instance.add(12, 1);
                            if (!LoginPreferenceManager.getVideoData(gb_TimerMessageActivity.this.getApplicationContext()).equals("")) {
                                gb_TimerMessageActivity.this.allFileDatas = new HashMap();
                                gb_TimerMessageActivity whatsappTimerMessageActivity = gb_TimerMessageActivity.this;
                                whatsappTimerMessageActivity.allFileDatas = (HashMap) whatsappTimerMessageActivity.gson.fromJson(LoginPreferenceManager.getVideoData(gb_TimerMessageActivity.this.getApplicationContext()), gb_TimerMessageActivity.this.type);
                                DailyClass dailyClass = new DailyClass();
                                dailyClass.setMessage(obj);
                                int i = Calendar.getInstance().get(11);
                                if (gb_TimerMessageActivity.this.radio_daily.isChecked()) {
                                    dailyClass.setTyper("Daily");
                                } else {
                                    dailyClass.setTyper("Hourly");
                                }
                                dailyClass.setHour(i);
                                gb_TimerMessageActivity.this.allFileDatas.put(fullNumber, dailyClass);
                            } else {
                                gb_TimerMessageActivity.this.allFileDatas = new HashMap();
                                DailyClass dailyClass2 = new DailyClass();
                                dailyClass2.setMessage(obj);
                                int i2 = Calendar.getInstance().get(11);
                                if (gb_TimerMessageActivity.this.radio_daily.isChecked()) {
                                    dailyClass2.setTyper("Daily");
                                } else {
                                    dailyClass2.setTyper("Hourly");
                                }
                                dailyClass2.setHour(i2);
                                gb_TimerMessageActivity.this.allFileDatas.put(fullNumber, dailyClass2);
                            }
                            LoginPreferenceManager.setVideoData(gb_TimerMessageActivity.this.getApplicationContext(), gb_TimerMessageActivity.this.gson.toJson(gb_TimerMessageActivity.this.allFileDatas));
                            if (LoginPreferenceManager.GetbooleanData(gb_TimerMessageActivity.this.getApplicationContext(), "SetAlarm")) {
                                new AlarmReceiver().setRepeatAlarm(gb_TimerMessageActivity.this.getApplicationContext(), instance, 0, 60000);
                                LoginPreferenceManager.SavebooleanData(gb_TimerMessageActivity.this.getApplicationContext(), "SetAlarm", true);
                            }
                            gb_TimerMessageActivity.this.edt_number.setText("");
                            gb_TimerMessageActivity.this.edt_message.setText("");
                            gb_TimerMessageActivity.this.radio_hourly.setChecked(true);
                            gb_TimerMessageActivity.this.radio_daily.setChecked(false);
                            try {
                                intent.setPackage("com.whatsapp");
                                intent.setType("text/plain");
                                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + fullNumber + "&text=" + URLEncoder.encode(obj, Key.STRING_CHARSET_NAME)));
                                if (intent.resolveActivity(packageManager) != null) {
                                    gb_TimerMessageActivity.this.startActivity(intent);
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else if (gb_TimerMessageActivity.this.edt_message.getText().toString().length() == 0) {
                            gb_TimerMessageActivity.this.edt_message.setError("Enter Message");
                        }
                    }
                }, "", AppManage.app_innerClickCntSwAd);
              /*  if (!gb_TimerMessageActivity.this.country_code.isValidFullNumber()) {
                    gb_TimerMessageActivity.this.edt_number.setError("Invalide Phone no");
                } else if (gb_TimerMessageActivity.this.edt_message.getText().toString().length() != 0) {
                    PackageManager packageManager = gb_TimerMessageActivity.this.getPackageManager();
                    Intent intent = new Intent("android.intent.action.VIEW");
                    String obj = gb_TimerMessageActivity.this.edt_message.getText().toString();
                    String fullNumber = gb_TimerMessageActivity.this.country_code.getFullNumber();
                    if (!fullNumber.contains("+")) {
                        fullNumber = "+" + fullNumber;
                    }
                    Calendar instance = Calendar.getInstance();
                    instance.add(12, 1);
                    if (!LoginPreferenceManager.getVideoData(gb_TimerMessageActivity.this.getApplicationContext()).equals("")) {
                        gb_TimerMessageActivity.this.allFileDatas = new HashMap();
                        gb_TimerMessageActivity whatsappTimerMessageActivity = gb_TimerMessageActivity.this;
                        whatsappTimerMessageActivity.allFileDatas = (HashMap) whatsappTimerMessageActivity.gson.fromJson(LoginPreferenceManager.getVideoData(gb_TimerMessageActivity.this.getApplicationContext()), gb_TimerMessageActivity.this.type);
                        DailyClass dailyClass = new DailyClass();
                        dailyClass.setMessage(obj);
                        int i = Calendar.getInstance().get(11);
                        if (gb_TimerMessageActivity.this.radio_daily.isChecked()) {
                            dailyClass.setTyper("Daily");
                        } else {
                            dailyClass.setTyper("Hourly");
                        }
                        dailyClass.setHour(i);
                        gb_TimerMessageActivity.this.allFileDatas.put(fullNumber, dailyClass);
                    } else {
                        gb_TimerMessageActivity.this.allFileDatas = new HashMap();
                        DailyClass dailyClass2 = new DailyClass();
                        dailyClass2.setMessage(obj);
                        int i2 = Calendar.getInstance().get(11);
                        if (gb_TimerMessageActivity.this.radio_daily.isChecked()) {
                            dailyClass2.setTyper("Daily");
                        } else {
                            dailyClass2.setTyper("Hourly");
                        }
                        dailyClass2.setHour(i2);
                        gb_TimerMessageActivity.this.allFileDatas.put(fullNumber, dailyClass2);
                    }
                    LoginPreferenceManager.setVideoData(gb_TimerMessageActivity.this.getApplicationContext(), gb_TimerMessageActivity.this.gson.toJson(gb_TimerMessageActivity.this.allFileDatas));
                    if (LoginPreferenceManager.GetbooleanData(gb_TimerMessageActivity.this.getApplicationContext(), "SetAlarm")) {
                        new AlarmReceiver().setRepeatAlarm(gb_TimerMessageActivity.this.getApplicationContext(), instance, 0, 60000);
                        LoginPreferenceManager.SavebooleanData(gb_TimerMessageActivity.this.getApplicationContext(), "SetAlarm", true);
                    }
                    gb_TimerMessageActivity.this.edt_number.setText("");
                    gb_TimerMessageActivity.this.edt_message.setText("");
                    gb_TimerMessageActivity.this.radio_hourly.setChecked(true);
                    gb_TimerMessageActivity.this.radio_daily.setChecked(false);
                    try {
                        intent.setPackage("com.whatsapp");
                        intent.setType("text/plain");
                        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + fullNumber + "&text=" + URLEncoder.encode(obj, Key.STRING_CHARSET_NAME)));
                        if (intent.resolveActivity(packageManager) != null) {
                            gb_TimerMessageActivity.this.startActivity(intent);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                } else if (gb_TimerMessageActivity.this.edt_message.getText().toString().length() == 0) {
                    gb_TimerMessageActivity.this.edt_message.setError("Enter Message");
                }*/
            }
        });
    }

    private void init() {
        this.country_code = (CountryCodePicker) findViewById(R.id.country_code);
        this.edt_number = (EditText) findViewById(R.id.edt_number);
        this.contact_list = (ImageView) findViewById(R.id.contact_list);
        this.edt_message = (EditText) findViewById(R.id.edt_message);
        this.radio_hourly = (RadioButton) findViewById(R.id.radio_hourly);
        radio_hourly.setChecked(true);
        this.radio_daily = (RadioButton) findViewById(R.id.radio_daily);
        this.btn_send_message = (CardView) findViewById(R.id.btn_send_message);
        this.country_code.setEditText_registeredCarrierNumber(this.edt_number);
        this.country_code.showArrow(true);
    }

    public void onRadioButtonClicked(View view) {
        view.getId();
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
