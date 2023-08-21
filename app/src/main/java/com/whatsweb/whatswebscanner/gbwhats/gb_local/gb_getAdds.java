package com.whatsweb.whatswebscanner.gbwhats.gb_local;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_model.gb_Tegres;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.APIService;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_SplashSCreenActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class gb_getAdds {

    private Context context;
    public static boolean IS_RUN = false ;


    gb_AdsPrefs appPrefs;
    gb_JsonData whatsappJsonData = new gb_JsonData();
    String readLine22 = null;


    public gb_getAdds(Context context) {
        this.context = context;
        appPrefs = new gb_AdsPrefs(context);
        Log.e("HANDLER_MSG"," call handler class ");

        verify_otp();

    }
    

    public void verify_otp() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient okHttpClient_login = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        try {
            readLine22 = new String(whatsappJsonData.b(gb_NetworkUtils.TYPE_OTHER));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Retrofit retrofit_auth = new Retrofit.Builder().
                baseUrl(readLine22.trim())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient_login)
                .build();

        APIService userlogin = retrofit_auth.create(APIService.class);
        Call<gb_Tegres> responseMetalogin = userlogin.TEGRES_CALL(Integer.parseInt(gb_AllKeyHub.APPDATA));
        responseMetalogin.enqueue(new Callback<gb_Tegres>() {
            @Override
            public void onResponse(Call<gb_Tegres> call, retrofit2.Response<gb_Tegres> response) {

                if (response.body().getStatus() == 1) {
                    if (response.body().getResponse().size() == 0) {
                        sendHandlerData(false);
                        IS_RUN = true;
                        Intent intent = new Intent("data_fetch_receiver");
                        intent.putExtra("data_fetched", false);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        try {
                            appPrefs.setApp_Name(response.body().getResponse().get(0).getAppName());
                            appPrefs.setAdd_Status(response.body().getResponse().get(0).getAvStatus());
                            appPrefs.setExtra_Id(response.body().getResponse().get(0).getExtraId());
                            appPrefs.setExtra_2(response.body().getResponse().get(0).getExtra2());
                            appPrefs.setUrl(response.body().getResponse().get(0).getUrl());
                            appPrefs.setScreen_status(response.body().getResponse().get(0).getScreenStatus());
                            appPrefs.setLive_button(response.body().getResponse().get(0).getLiveButton());
                            appPrefs.setAM_App_Open(response.body().getResponse().get(0).getAmAppId());
                            appPrefs.setAM_Inter(response.body().getResponse().get(0).getAmInterstialAdd());
                            appPrefs.setAM_Native(response.body().getResponse().get(0).getAmNativeAdd());
                            appPrefs.setGlobalstatus(response.body().getResponse().get(0).getgLobalstatus());
                            appPrefs.setNativeone(response.body().getResponse().get(0).getNativeone());
                            appPrefs.setVpn_url(response.body().getResponse().get(0).getVpn_url());
                            appPrefs.setVpn_keyid(response.body().getResponse().get(0).getVpn_keyid());
                            appPrefs.setBack_status(response.body().getResponse().get(0).getBack_status());
                            appPrefs.setBack_count(response.body().getResponse().get(0).getBack_count());



                            IS_RUN = true;
                            sendHandlerData(true);
                            Intent intent = new Intent("data_fetch_receiver");
                            intent.putExtra("data_fetched", true);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            IS_RUN = true;
                            sendHandlerData(false);
                            Intent intent = new Intent("data_fetch_receiver");
                            intent.putExtra("data_fetched", false);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<gb_Tegres> call, Throwable t) {
                IS_RUN = true;
                sendHandlerData(false);
                Intent intent = new Intent("data_fetch_receiver");
                intent.putExtra("data_fetched", false);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    private void sendHandlerData(boolean isDataFatch) {
        if (gb_SplashSCreenActivity.handler != null) {
            Message m = new Message();
            m.what = 444;
            m.obj = isDataFatch;
            gb_SplashSCreenActivity.handler.sendMessage(m);
        }
    }
}
