package com.whatsweb.whatswebscanner.gbwhats.gb_activity;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.anchorfree.partner.api.ClientInfo;
import com.anchorfree.partner.api.auth.AuthMethod;
import com.anchorfree.partner.api.data.Country;
import com.anchorfree.partner.api.response.AvailableCountries;
import com.anchorfree.partner.api.response.User;
import com.anchorfree.reporting.TrackingConstants;
import com.anchorfree.sdk.SessionConfig;
import com.anchorfree.sdk.UnifiedSDK;
import com.anchorfree.sdk.rules.TrafficRule;
import com.anchorfree.vpnsdk.callbacks.CompletableCallback;
import com.anchorfree.vpnsdk.exceptions.VpnException;
import com.anchorfree.vpnsdk.transporthydra.HydraTransport;
import com.anchorfree.vpnsdk.vpnservice.VPNState;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.northghost.caketube.CaketubeTransport;
import com.pesonal.adsdk.ADS_SplashActivity;
import com.pesonal.adsdk.AppManage;
import com.pesonal.adsdk.getDataListner;
import com.scottyab.rootbeer.RootBeer;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_in.gb_CountryModal;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AdsPrefs;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_getAdds;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class gb_SplashSCreenActivity extends ADS_SplashActivity {
    public static Handler handler;
    gb_AdsPrefs loan_pref;
    SharedPreferences home_Activity_SP;
    private boolean isAdDataFatchSuccess = false, isVpnDataFatchSuccess = false;
    public static List<Country> list;
    private ClientInfo clientInfo;
    public static ArrayList<gb_CountryModal> listCountry;
    Dialog pd;
    private static String selected = "";
    private static final String TAG = "whatsapp_SplashActivity";

    private void initHandler() {
        handler = new Handler(message -> {
            if (message.what == 444) {
                if (message.obj.toString().equalsIgnoreCase("true")) {
                    Log.e("Ads_Issue", " Splash_1 -> MAKE LOAD CALL ");
                    gb_AllKeyHub.LoadInterstitialAds(this);
                    isAdDataFatchSuccess = true;
                    if (loan_pref.getGlobalstatus().equals("1") /*&& !loan_pref.getIsFirstTime()*/)
                    {

                        startVpnServiceCall();
                    }

                    else{
                        MoveToNext();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to fetch Data..!!", Toast.LENGTH_SHORT).show();
                }
            } else if (message.what == 222) {
                Log.e("VPN_ISSUE", " Splash_1 -> MAKE LOAD CALL ");
                gb_AllKeyHub.LoadInterstitialAds(this);
                MoveToNext();
            }
            return false;
        });
    }
    private void MoveToNext() {
        if (loan_pref.getScreen_status().equals("0")) {
            handler.postDelayed(() -> startActivity(new Intent(gb_SplashSCreenActivity.this, gb_intro1.class)), 4000);
        } else {
            handler.postDelayed(() -> startActivity(new Intent(gb_SplashSCreenActivity.this, gb_onActivity.class)), 4000);

        }
    }



    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.gb_activity_splash_screen);
        RootBeer rootBeer = new RootBeer(this);
        if (rootBeer.isRooted()) {
            Toast.makeText(this, "ROOT DEVICE FOUND", Toast.LENGTH_SHORT).show();
        } else {
            loan_pref = new gb_AdsPrefs(this);
//            this.wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            pd = new Dialog(gb_SplashSCreenActivity.this, R.style.Transparent);
            pd.setContentView(R.layout.gb_ads_waitng);
            pd.setCanceledOnTouchOutside(true);
            pd.setCancelable(false);
            initHandler();
            MobileAds.initialize(getApplicationContext());
            List<String> testDeviceIds = Arrays.asList(gb_AllKeyHub.TestDeviceID);
            RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
            MobileAds.setRequestConfiguration(configuration);

            new gb_getAdds(this);
        }


        ADSinit(gb_SplashSCreenActivity.this, getCurrentVersionCode(), new getDataListner() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(gb_SplashSCreenActivity.this, gb_intro1.class));
            }

            @Override
            public void onUpdate(String url) {
                Log.e("my_log", "onUpdate: " + url);
                showUpdateDialog(url);
            }

            @Override
            public void onRedirect(String url) {
                Log.e("my_log", "onRedirect: " + url);
                showRedirectDialog(url);
            }

            @Override
            public void onReload() {
                startActivity(new Intent(gb_SplashSCreenActivity.this, gb_SplashSCreenActivity.class));
                finish();
            }

            @Override
            public void onGetExtradata(JSONObject extraData) {
                Log.e("my_log", "ongetExtradata: " + extraData.toString());
            }
        });

    }

    public void showRedirectDialog(final String url) {

        final Dialog dialog = new Dialog(gb_SplashSCreenActivity.this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.installnewappdialog, null);
        dialog.setContentView(view);
        TextView update = view.findViewById(R.id.update);
        TextView txt_title = view.findViewById(R.id.txt_title);
        TextView txt_decription = view.findViewById(R.id.txt_decription);

        update.setText("Install Now");
        txt_title.setText("Install our new app now and enjoy");
        txt_decription.setText("We have transferred our server, so install our new app by clicking the button below to enjoy the new features of app.");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManage.getInstance(gb_SplashSCreenActivity.this).showInterstitialAd(gb_SplashSCreenActivity.this, new AppManage.MyCallback() {
                    public void callbackCall() {

                        try {
                            Uri marketUri = Uri.parse(url);
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                            startActivity(marketIntent);
                        } catch (ActivityNotFoundException ignored1) {
                        }
                    }
                },AppManage.ADMOB,AppManage.app_mainClickCntSwAd);

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }


    public void showUpdateDialog(final String url) {

        final Dialog dialog = new Dialog(gb_SplashSCreenActivity.this);
        dialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.installnewappdialog, null);
        dialog.setContentView(view);
        TextView update = view.findViewById(R.id.update);
        TextView txt_title = view.findViewById(R.id.txt_title);
        TextView txt_decription = view.findViewById(R.id.txt_decription);

        update.setText("Update Now");
        txt_title.setText("Update our new app now and enjoy");
        txt_decription.setText("");
        txt_decription.setVisibility(View.GONE);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri marketUri = Uri.parse(url);
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);
                } catch (ActivityNotFoundException ignored1) {
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public int getCurrentVersionCode() {
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getPackageName(), 0);
            return info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }


    public void createLogin(ClientInfo clientInfo, Context context) {
        UnifiedSDK unifiedSDK = UnifiedSDK.getInstance(clientInfo);
        AuthMethod authMethod = AuthMethod.anonymous();
        unifiedSDK.getBackend().login(authMethod, new com.anchorfree.vpnsdk.callbacks.Callback<User>() {
            @Override
            public void success(@NonNull User user) {
                Log.e("V_hayd", "success: " + user.getAccessToken() + " " + user.getSubscriber());
                pd.show();
                getCountry();
            }

            @Override
            public void failure(@NonNull VpnException e) {
//                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show();
                Log.e("V_hayd", "failure: ", e);
            }
        });

    }

    public void getCountry() {
        UnifiedSDK.getInstance().getBackend().countries(new com.anchorfree.vpnsdk.callbacks.Callback<AvailableCountries>() {
            @Override
            public void success(@NonNull AvailableCountries availableCountries) {
                try {
                    list = availableCountries.getCountries();
                    Log.d("V_hayd_COUNTRYLIS", "============>" + availableCountries.getCountries());
                    if (!list.isEmpty()) {
                        selected = getRandomString(list);
                    } else {
                        selected = "";
                    }
                    connectToVpn(selected);
                } catch (Exception e) {
                    connectToVpn(selected);
                }
            }

            @Override
            public void failure(@NonNull VpnException e) {
                connectToVpn("");
            }
        });
    }

    private void connectToVpn(String country) {
        isLoggedIn(new com.anchorfree.vpnsdk.callbacks.Callback<Boolean>() {
            @Override
            public void success(@NonNull Boolean aBoolean) {
                if (aBoolean) {
                    Log.e(TAG, "success: logged in");
                    pd.dismiss();
                    List<String> fallbackOrder = new ArrayList<>();
                    fallbackOrder.add(HydraTransport.TRANSPORT_ID);
                    fallbackOrder.add(CaketubeTransport.TRANSPORT_ID_TCP);
                    fallbackOrder.add(CaketubeTransport.TRANSPORT_ID_UDP);

                    List<String> bypassDomains = new LinkedList<>();
                    UnifiedSDK.getInstance().getVPN().start(new SessionConfig.Builder()
                            .withReason(TrackingConstants.GprReasons.M_UI)
                            .withTransportFallback(fallbackOrder)
                            .withTransport(HydraTransport.TRANSPORT_ID)
                            .withVirtualLocation(country)
                            .addDnsRule(TrafficRule.Builder.bypass().fromDomains(bypassDomains))
                            .build(), new CompletableCallback() {
                        @Override
                        public void complete() {
                            Log.d("V_hayd", "connected");

                            MoveToNext();

                        }

                        @Override
                        public void error(@NonNull VpnException e) {
                            Log.d("V_hayd", "error");

                            MoveToNext();

                        }
                    });

                } else {
                    Log.e(TAG, "success: logged out");
                    Log.d("V_hayd", "success: logged out");
                    MoveToNext();

                }
            }

            @Override
            public void failure(@NonNull VpnException e) {
                Log.d("V_hayd", "failure");
                MoveToNext();

            }
        });

    }


    protected void isLoggedIn(com.anchorfree.vpnsdk.callbacks.Callback<Boolean> callback) {
        UnifiedSDK.getInstance().getBackend().isLoggedIn(callback);
    }

    private void disconnectFromVpn() {
        UnifiedSDK.getVpnState(new com.anchorfree.vpnsdk.callbacks.Callback<VPNState>() {
            @Override
            public void success(@NonNull VPNState vpnState) {
                if (vpnState == VPNState.CONNECTED) {
                    UnifiedSDK.getInstance().getVPN().stop(TrackingConstants.GprReasons.M_UI, new CompletableCallback() {
                        @Override
                        public void complete() {
                            Log.e(TAG, "msg_disconnected:");
//                            Toast.makeText(whatsapp_SplashActivity.this, R.string.gb_msg_disconnected, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void error(@NonNull VpnException e) {
                            Log.e(TAG, "error: ", e);
                        }
                    });
                }
            }

            @Override
            public void failure(@NonNull VpnException e) {

            }
        });


    }

    private String getRandomString(List<Country> list) {
        int min = 0;
        int max = list.size();
        return list.get(new Random().nextInt(((max - min) + 1) + min)).getCountry();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectFromVpn();
    }

    private void startVpnServiceCall() {
        if (isAdDataFatchSuccess) {
            isAdDataFatchSuccess = false;
            Log.e("key_url",loan_pref.getVpn_url()+" key"+loan_pref.getVpn_keyid());
            clientInfo = ClientInfo.newBuilder().addUrl("https://d2isj403unfbyl.cloudfront.net").carrierId("touchvpn").build();
            UnifiedSDK.clearInstances();
            createLogin(clientInfo, gb_SplashSCreenActivity.this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1) {
            if (iArr.length <= 0 || iArr[0] != 0 || iArr[1] != 0) {
                finish();
            }
        }
    }

}
