package com.whatsweb.whatswebscanner.gbwhats.gb_util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.ContextThemeWrapper;

import androidx.appcompat.app.AlertDialog;

import com.whatsweb.whatswebscanner.gbwhats.R;

public class RateMeNowDialog {
    public static void showRateDialog(final Context context, int i) {
        AlertDialog.Builder builder;
        if (context != null) {
            final SharedPreferences sharedPreferences = context.getSharedPreferences("RateDialog", 0);
            if (!sharedPreferences.getBoolean("dont_show_again", false)) {
                int i2 = sharedPreferences.getInt("launch_count", i);
                if (i2 > 0) {
                    sharedPreferences.edit().putInt("launch_count", i2 - 1).commit();
                    return;
                }
                sharedPreferences.edit().putInt("launch_count", i).commit();
                if (Build.VERSION.SDK_INT >= 23) {
                    builder = new AlertDialog.Builder(new ContextThemeWrapper(context, (int) R.style.Alert_Dialog));
                } else {
                    builder = new AlertDialog.Builder(new ContextThemeWrapper(context, (int) R.style.Alert_Dialog));
                }
                builder.setTitle(R.string.gb_rate_me_title);
                builder.setMessage(R.string.gb_rate_me_message);
                builder.setPositiveButton(R.string.gb_rate_me_positive_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
                        Log.e("playstoreurl", "" + str);
                        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                        sharedPreferences.edit().putBoolean("dont_show_again", true).commit();
                    }
                });
                builder.setNeutralButton(R.string.gb_rate_me_neutral_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Activity) context).finish();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog create = builder.create();
                create.show();
                create.getButton(-1).setTextColor(context.getResources().getColor(R.color.gb_black));
                create.getButton(-3).setTextColor(context.getResources().getColor(R.color.gb_black));
                return;
            }
            ((Activity) context).finish();
        }
    }
}
