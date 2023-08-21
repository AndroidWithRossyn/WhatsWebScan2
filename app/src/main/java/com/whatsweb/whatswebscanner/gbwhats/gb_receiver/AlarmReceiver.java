package com.whatsweb.whatswebscanner.gbwhats.gb_receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.bumptech.glide.load.Key;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;

public class AlarmReceiver extends BroadcastReceiver {
    private HashMap<String, DailyClass> allFileDatas = new HashMap<>();
    private Gson gson = new Gson();
    private Type type = new TypeToken<HashMap<String, DailyClass>>() {
    }.getType();

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Receiver", 1).show();
        Log.d("TAG", "onReceive++: ");
        if (!LoginPreferenceManager.getVideoData(context).equals("")) {
            this.allFileDatas = new HashMap<>();
            this.allFileDatas = (HashMap) this.gson.fromJson(LoginPreferenceManager.getVideoData(context), this.type);
        }
        for (String str : this.allFileDatas.keySet()) {
            DailyClass dailyClass = this.allFileDatas.get(str);
            if (Calendar.getInstance().get(11) == dailyClass.getHour()) {
                if (dailyClass.getTyper().equalsIgnoreCase("Hourly")) {
                    dailyClass.setHour(dailyClass.getHour() + 1);
                }
                PackageManager packageManager = context.getPackageManager();
                Intent intent2 = new Intent("android.intent.action.VIEW");
                try {
                    intent2.setPackage("com.whatsapp");
                    intent2.setFlags(268435456);
                    intent2.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + str + "&text=" + URLEncoder.encode(dailyClass.getMessage(), Key.STRING_CHARSET_NAME)));
                    if (intent2.resolveActivity(packageManager) != null) {
                        context.startActivity(intent2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        LoginPreferenceManager.setVideoData(context, this.gson.toJson(this.allFileDatas));
    }

    public void setRepeatAlarm(Context context, Calendar calendar, int i, long j) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i, new Intent(context, AlarmReceiver.class), 268435456);
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        long timeInMillis2 = calendar.getTimeInMillis() - timeInMillis;
        alarmManager.setRepeating(3, SystemClock.elapsedRealtime() + timeInMillis2, j, broadcast);
        Log.d("", "setRepeatAlarm: " + j + "??/" + (SystemClock.elapsedRealtime() + timeInMillis2) + ">>>" + timeInMillis + ">>>" + (calendar.getTimeInMillis() + timeInMillis2));
    }
}
