package com.whatsweb.whatswebscanner.gbwhats.gb_receiver;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.work.PeriodicWorkRequest;

import java.util.Calendar;

public class NotificationHelper {
    public static int ALARM_TYPE_ELAPSED = 101;
    public static int ALARM_TYPE_RTC = 100;
    private static PendingIntent alarmIntentElapsed;
    private static PendingIntent alarmIntentRTC;
    private static AlarmManager alarmManagerElapsed;
    private static AlarmManager alarmManagerRTC;

    public static void scheduleRepeatingRTCNotification(Context context, String str, String str2) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.set(11, Integer.getInteger(str, 8).intValue(), Integer.getInteger(str2, 0).intValue());
        alarmIntentRTC = PendingIntent.getBroadcast(context, ALARM_TYPE_RTC, new Intent(context, AlarmReceiver.class), 134217728);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        alarmManagerRTC = alarmManager;
        alarmManager.setInexactRepeating(3, instance.getTimeInMillis(), 86400000, alarmIntentRTC);
    }

    public static void scheduleRepeatingElapsedNotification(Context context) {
        alarmIntentElapsed = PendingIntent.getBroadcast(context, ALARM_TYPE_ELAPSED, new Intent(context, AlarmReceiver.class), 134217728);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        alarmManagerElapsed = alarmManager;
        alarmManager.setInexactRepeating(3, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS + SystemClock.elapsedRealtime(), PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, alarmIntentElapsed);
    }

    public static void cancelAlarmRTC() {
        AlarmManager alarmManager = alarmManagerRTC;
        if (alarmManager != null) {
            alarmManager.cancel(alarmIntentRTC);
        }
    }

    public static void cancelAlarmElapsed() {
        AlarmManager alarmManager = alarmManagerElapsed;
        if (alarmManager != null) {
            alarmManager.cancel(alarmIntentElapsed);
        }
    }

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService("notification");
    }

    public static void enableBootReceiver(Context context) {
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, AlarmBootReceiver.class), 1, 1);
    }

    public static void disableBootReceiver(Context context) {
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, AlarmBootReceiver.class), 2, 1);
    }
}
