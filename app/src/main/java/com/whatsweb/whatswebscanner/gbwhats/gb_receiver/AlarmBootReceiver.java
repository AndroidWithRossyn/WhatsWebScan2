package com.whatsweb.whatswebscanner.gbwhats.gb_receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmBootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            ((AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM)).setInexactRepeating(0, System.currentTimeMillis(), (long) 8000, PendingIntent.getBroadcast(context, 0, new Intent(context, AlarmReceiver.class), 0));
        }
    }
}
