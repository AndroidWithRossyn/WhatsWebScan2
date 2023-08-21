package com.whatsweb.whatswebscanner.gbwhats.gb_util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.whatsweb.whatswebscanner.gbwhats.R;

import java.io.File;
import java.io.PrintStream;

public class Utils {
    public static String BASE_URL_PUBLIC = "http://snapbuddyapp.com/";
    public static String FilePath = "";
    public static String MY_ANDROID_10_IDENTIFIER_OF_FILE = "/GB_Downloader/TikTok/";
    public static final String PRIVACYPOLICYURL = "http://snapbuddyapp.com/privacy/privacypolicy.html";
    public static final String ROOTDIRECTORYCHINGARI = "/GB_Downloader/Chingari/";
    public static final String ROOTDIRECTORYFACEBOOK = "/GB_Downloader/Facebook/";
    public static final String ROOTDIRECTORYINSTA = "/GB_Downloader/Insta/";
    public static final String ROOTDIRECTORYJOSH = "/GB_Downloader/Josh/";
    public static final String ROOTDIRECTORYLIKEE = "/GB_Downloader/Likee/";
    public static final String ROOTDIRECTORYMITRON = "/GB_Downloader/Mitron/";
    public static final String ROOTDIRECTORYMOJ = "/GB_Downloader/Moj/";
    public static final String ROOTDIRECTORYMX = "/GB_Downloader/MXTakaTak/";
    public static final String ROOTDIRECTORYROPOSO = "/GB_Downloader/Roposo/";
    public static final String ROOTDIRECTORYSHARECHAT = "/GB_Downloader/ShareChat/";
    public static final String ROOTDIRECTORYSNACKVIDEO = "/GB_Downloader/SnackVideo/";
    public static final String ROOTDIRECTORYTIKI = "/GB_Downloader/Tiki/";
    public static final String ROOTDIRECTORYTIKTOK = "/GB_Downloader/TikTok/";
    public static final String ROOTDIRECTORYTWITTER = "/GB_Downloader/Twitter/";
    public static final String ROOTDIRECTORYVIMEO = "/GB_Downloader/Vimeo/";
    public static final File RootDirectoryChingariShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Chingari");
    public static final File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Facebook");
    public static final File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Insta");
    public static final File RootDirectoryJoshShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Josh");
    public static final File RootDirectoryLikeeShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Likee");
    public static File RootDirectoryMXShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/MXTakaTak");
    public static File RootDirectoryMain = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader");
    public static final File RootDirectoryMitronShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Mitron");
    public static File RootDirectoryMojShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Moj");
    public static final File RootDirectoryRoposoShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Roposo");
    public static final File RootDirectoryShareChatShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/ShareChat");
    public static final File RootDirectorySnackVideoShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/SnackVideo");
    public static final File RootDirectoryTikTokShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/TikTok");
    public static File RootDirectoryTikiShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Tiki");
    public static final File RootDirectoryTwitterShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Twitter");
    public static File RootDirectoryVimeoShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Vimeo");
    public static final File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/GB_Downloader/Whatsapp");
    public static String StaticShareDownloadRepost = "";
    public static final String TIKTOKURL = "http://snapbuddyapp.com/other_api/api.php";
    public static String USER_FEED_PUBLIC = (BASE_URL_PUBLIC + "/insta_api/?action=getUserPost");
    public static String USER_IGTV_PUBLIC = (BASE_URL_PUBLIC + "/insta_api/?action=GetUserIGTV");
    public static String USER_SEARCH_PUBLIC = (BASE_URL_PUBLIC + "/insta_api/?action=serachUser");
    public static String USER_STORY_PUBLIC = (BASE_URL_PUBLIC + "/insta_api/?action=GetStories");
    private static Context context;
    public static Dialog customDialog;
    public static BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            if (Utils.StaticShareDownloadRepost.equals("Share")) {
                if (Utils.FilePath.contains(".mp4")) {
                    Utils.shareVideo(Utils.context, Utils.FilePath);
                } else {
                    Utils.shareImage(Utils.context, Utils.FilePath);
                }
            } else if (!Utils.StaticShareDownloadRepost.equals("Repost")) {
            } else {
                if (Utils.FilePath.contains(".mp4")) {
                    Utils.shareImageVideoOnInstagram(Utils.context, Utils.FilePath, true);
                } else {
                    Utils.shareImageVideoOnInstagram(Utils.context, Utils.FilePath, false);
                }
            }
        }
    };

    public Utils(Context context2) {
        context = context2;
    }

    public static void setToast(Context context2, String str) {
        Toast makeText = Toast.makeText(context2, str,  Toast.LENGTH_SHORT);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    public static void createFileFolder() {
        File file = RootDirectoryFacebookShow;
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = RootDirectoryInstaShow;
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = RootDirectoryTikTokShow;
        if (!file3.exists()) {
            file3.mkdirs();
        }
        File file4 = RootDirectoryTwitterShow;
        if (!file4.exists()) {
            file4.mkdirs();
        }
        File file5 = RootDirectoryWhatsappShow;
        if (!file5.exists()) {
            file5.mkdirs();
        }
        File file6 = RootDirectoryLikeeShow;
        if (!file6.exists()) {
            file6.mkdirs();
        }
        File file7 = RootDirectoryShareChatShow;
        if (!file7.exists()) {
            file7.mkdirs();
        }
        File file8 = RootDirectoryRoposoShow;
        if (!file8.exists()) {
            file8.mkdirs();
        }
        File file9 = RootDirectorySnackVideoShow;
        if (!file9.exists()) {
            file9.mkdirs();
        }
        File file10 = RootDirectoryJoshShow;
        if (!file10.exists()) {
            file10.mkdirs();
        }
        File file11 = RootDirectoryChingariShow;
        if (!file11.exists()) {
            file11.mkdirs();
        }
        File file12 = RootDirectoryMitronShow;
        if (!file12.exists()) {
            file12.mkdirs();
        }
        if (!RootDirectoryMXShow.exists()) {
            RootDirectoryMXShow.mkdirs();
        }
        if (!RootDirectoryMojShow.exists()) {
            RootDirectoryMojShow.mkdirs();
        }
        if (!RootDirectoryTikiShow.exists()) {
            RootDirectoryTikiShow.mkdirs();
        }
    }

//    public static void showProgressDialog(Activity activity) {
//        Dialog dialog = customDialog;
//        if (dialog != null) {
//            dialog.dismiss();
//            customDialog = null;
//        }
//        customDialog = new Dialog(activity);
//        View inflate = LayoutInflater.from(activity).inflate(R.layout.gb_progress_dialog, (ViewGroup) null);
//        customDialog.setCancelable(false);
//        customDialog.setContentView(inflate);
//        if (!customDialog.isShowing() && !activity.isFinishing()) {
//            customDialog.show();
//        }
//    }

    public static void hideProgressDialog() {
        try {
            Dialog dialog = customDialog;
            if (dialog != null && dialog.isShowing()) {
                customDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public boolean isNetworkAvailable() {
//        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    public static void openApp(Context context2, String str) {
        Intent launchIntentForPackage = context2.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context2.startActivity(launchIntentForPackage);
        } else {
            setToast(context2, context2.getResources().getString(R.string.gb_app_not_available));
        }
    }

//    public static void setLocale(String str, Context context2) {
//        if (str.equals("")) {
//            str = AppLangSessionManager.KEY_APP_LANGUAGE;
//        }
//        Locale locale = new Locale(str);
//        Configuration configuration = new Configuration(context2.getResources().getConfiguration());
//        Locale.setDefault(locale);
//        configuration.setLocale(locale);
//        context2.getResources().updateConfiguration(configuration, context2.getResources().getDisplayMetrics());
//    }

//    public static void startDownload(String str, String str2, Context context2, String str3, String str4) {
//        try {
//            StaticShareDownloadRepost = str4;
//            FilePath = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3).getAbsolutePath();
//            if (str4.equals("Share")) {
//                if (!new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3).exists()) {
//                    setToast(context2, context2.getResources().getString(R.string.gb_download_started));
//                    DownloadFile(str, str2, context2, str3);
//                    context2.registerReceiver(onComplete, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
//                } else if (str3.contains(".mp4")) {
//                    shareVideo(context2, FilePath);
//                } else {
//                    shareImage(context2, FilePath);
//                }
//            } else if (str4.equals("Repost")) {
//                if (!new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3).exists()) {
//                    setToast(context2, context2.getResources().getString(R.string.gb_download_started));
//                    DownloadFile(str, str2, context2, str3);
//                    context2.registerReceiver(onComplete, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
//                } else if (str3.contains(".mp4")) {
//                    shareImageVideoOnInstagram(context2, FilePath, true);
//                } else {
//                    shareImageVideoOnInstagram(context2, FilePath, false);
//                }
//            } else {
//                if (!new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3).exists()) {
//                    setToast(context2, context2.getResources().getString(R.string.gb_download_started));
//                    DownloadFile(str, str2, context2, str3);
//                    return;
//                }
//                setToast(context2, context2.getResources().getString(R.string.gb_already_downloader));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void DownloadFile(String str, String str2, Context context2, String str3) {
//        try {
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
//            request.setAllowedNetworkTypes(3);
//            request.setNotificationVisibility(1);
//            request.setTitle(str3 + "");
//            request.setVisibleInDownloadsUi(true);
//            String str4 = Environment.DIRECTORY_DOWNLOADS;
//            request.setDestinationInExternalPublicDir(str4, str2 + str3);
//            ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
//            if (Build.VERSION.SDK_INT >= 19) {
//                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
//
//                    public void onScanCompleted(String str, Uri uri) {
//                    }
//                });
//                return;
//            }
//            context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + "/" + str2 + str3))));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void startDownload(String str, String str2, Context context2, String str3) {
//        try {
//            setToast(context2, context2.getResources().getString(R.string.gb_download_started));
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
//            request.setAllowedNetworkTypes(3);
//            request.setNotificationVisibility(1);
//            request.setTitle(str3 + "");
//            request.setVisibleInDownloadsUi(true);
//            String str4 = Environment.DIRECTORY_DOWNLOADS;
//            request.setDestinationInExternalPublicDir(str4, str2 + str3);
//            ((DownloadManager) context2.getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(request);
//            if (Build.VERSION.SDK_INT >= 19) {
//                MediaScannerConnection.scanFile(context2, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + File.pathSeparator + str2).getAbsolutePath()}, null, null);
//                return;
//            }
//            context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + File.pathSeparator + str2))));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void repostWhatsApp(Context context2, boolean z, String str) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        if (z) {
            intent.setType("Video/*");
        } else {
            intent.setType("image/*");
        }
        if (str.startsWith("content")) {
            uri = Uri.parse(str);
        } else {
            uri = FileProvider.getUriForFile(context2, context2.getApplicationContext().getPackageName() + ".provider", new File(str));
        }
        intent.putExtra("android.intent.extra.STREAM", uri);
        intent.setPackage("com.whatsapp");
        context2.startActivity(intent);
    }

    public static void shareFile(Context context2, boolean z, String str) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        if (z) {
            intent.setType("Video/*");
        } else {
            intent.setType("image/*");
        }
        if (str.startsWith("content")) {
            uri = Uri.parse(str);
        } else {
            uri = FileProvider.getUriForFile(context2, context2.getApplicationContext().getPackageName() + ".provider", new File(str));
        }
        PrintStream printStream = System.out;
        printStream.println("PRINNNNNN " + uri);
        intent.putExtra("android.intent.extra.STREAM", uri);
        context2.startActivity(intent);
    }

    public static void shareImage(Context context2, String str) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.TEXT", context2.getResources().getString(R.string.gb_share_txt));
            intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(context2.getContentResolver(), str, "", (String) null)));
            intent.setType("image/*");
            context2.startActivity(Intent.createChooser(intent, context2.getResources().getString(R.string.gb_share_image_via)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareImageVideoOnWhatsapp(Context context2, String str, boolean z) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.TEXT", "");
        intent.putExtra("android.intent.extra.STREAM", parse);
        if (z) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.addFlags(1);
        try {
            context2.startActivity(intent);
        } catch (Exception unused) {
            setToast(context2, context2.getResources().getString(R.string.gb_whatsapp_not_installed));
        }
    }

    public static void shareVideo(Context context2, String str) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("video/mp4");
        intent.putExtra("android.intent.extra.STREAM", parse);
        intent.addFlags(1);
        try {
            context2.startActivity(Intent.createChooser(intent, "Share Video using"));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context2, context2.getResources().getString(R.string.gb_no_app_installed), Toast.LENGTH_LONG).show();
        }
    }

//    public static void rateApp(Context context2) {
//        String packageName = context2.getPackageName();
//        try {
//            context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
//        } catch (ActivityNotFoundException unused) {
//            context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
//        }
//    }

//    public static void moreApp(Context context2) {
//        try {
//            context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Nice+creations")));
//        } catch (ActivityNotFoundException unused) {
//            context2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Nice+creations")));
//        }
//    }

//    public static void shareApp(Context context2) {
//        Intent intent = new Intent("android.intent.action.SEND");
//        intent.putExtra("android.intent.extra.SUBJECT", context2.getString(R.string.app_name));
//        intent.putExtra("android.intent.extra.TEXT", context2.getString(R.string.gb_share_app_message) + ("\nhttps://play.google.com/store/apps/details?id=" + context2.getPackageName()));
//        intent.setType("text/plain");
//        context2.startActivity(Intent.createChooser(intent, "Share"));
//    }

//    public static boolean isNullOrEmpty(String str) {
//        return str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.equalsIgnoreCase("0");
//    }

//    public static void dialogHowTo(Context context2) {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context2, R.style.SheetDialog);
//        bottomSheetDialog.requestWindowFeature(1);
//        bottomSheetDialog.setContentView(R.layout.gb_layout_how_to);
//
//        ImageView im_howto3 = bottomSheetDialog.findViewById(R.id.im_howto3);
//        ImageView im_howto4 = bottomSheetDialog.findViewById(R.id.im_howto4);
//
//        Glide.with(context2).load(Integer.valueOf((int) R.drawable.gb_how_to_use_2)).into(im_howto3);
//        Glide.with(context2).load(Integer.valueOf((int) R.drawable.gb_how_to_use_1)).into(im_howto4);
//        bottomSheetDialog.show();
//    }

    public static void shareImageVideoOnInstagram(Context context2, String str, boolean z) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setPackage("com.instagram.android");
            intent.putExtra("android.intent.extra.TEXT", "");
            if (z) {
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(str));
                intent.setType("video/*");
            } else {
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(context2.getContentResolver(), str, "", (String) null)));
                intent.setType("image/*");
            }
            intent.addFlags(1);
            try {
                context2.startActivity(intent);
            } catch (Exception unused) {
                setToast(context2, context2.getResources().getString(R.string.gb_app_not_available));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static List<String> extractUrls(String str) {
//        ArrayList arrayList = new ArrayList();
//        Matcher matcher = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", 2).matcher(str);
//        while (matcher.find()) {
//            arrayList.add(str.substring(matcher.start(0), matcher.end(0)));
//        }
//        return arrayList;
//    }

//    public static void sendToSettings(final Activity activity) {
//        new AlertDialog.Builder(activity).setTitle(activity.getResources().getString(R.string.gb_need_storage_permission)).setMessage(activity.getResources().getString(R.string.gb_storage_permission_message)).setPositiveButton(activity.getResources().getString(R.string.gb_allow), new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialogInterface, int i) {
//                activity.startActivityForResult(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", activity.getPackageName(), null)), 1001);
//            }
//        }).setNegativeButton(activity.getResources().getString(R.string.gb_cancel), new DialogInterface.OnClickListener() {
//
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        }).create().show();
//    }

//    public static boolean appInstalledOrNot(Context context2, String str) {
//        try {
//            context2.getPackageManager().getPackageInfo(str, 1);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public static String getFileSize(long j) {
//        if (j <= 0) {
//            return "0";
//        }
//        double d = (double) j;
//        int log10 = (int) (Math.log10(d) / Math.log10(1024.0d));
//        return new DecimalFormat("#,##0.#").format(d / Math.pow(1024.0d, (double) log10)) + " " + new String[]{"B", "KB", "MB", "GB", "TB"}[log10];
//    }

    public static void infoDialog(Context context2, String str, String str2) {
        new AlertDialog.Builder(context2).setTitle(str).setMessage(str2).setPositiveButton(context2.getResources().getString(R.string.gb_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
}
