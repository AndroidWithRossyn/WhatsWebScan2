package com.whatsweb.whatswebscanner.gbwhats.gb_adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.AdapterCallBackInterface;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.FileUtilsa;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_DetailActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class StatusDataAdapter extends BaseAdapter {
    private static final String TAG = "StatusDataAdapter";
    private static File file;
    private static String filen;
    public static ArrayList<Uri> removableList;
    public static File str3;
    AdapterCallBackInterface adapterCallBackInterface;
    public boolean checkimage = true;
    public boolean checkimages = true;
    public boolean checkvideo = true;
    public boolean checkvideos = true;
    public Activity context;
    private final int fromsavepage;
    public boolean isImage;
    public boolean isNativeADsShow = true;
    private LayoutInflater layoutInflater;
    private onClickListene onClickListene;
    String path = new String();
    int size;
    public ArrayList<String> stringArrayList;

    public interface onClickListene {
        void onClick(String str);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public class ViewHolder {

        CardView cardView;
        FrameLayout frameLayout;
        ImageView imgDownDelete;
        RelativeLayout layDownDelete;
        RelativeLayout layoutShare;
        ImageView thumbImage;

        private ViewHolder() {
        }
    }

    public void setOnClickListene(onClickListene onclicklistene) {
        this.onClickListene = onclicklistene;
    }

    public StatusDataAdapter(AdapterCallBackInterface adapterCallBackInterface2, FragmentActivity fragmentActivity, ArrayList<String> arrayList, int i, boolean z) {
        Log.d("TAG", "StatusDataAdapter: " + arrayList.size());
        this.adapterCallBackInterface = adapterCallBackInterface2;
        this.context = fragmentActivity;
        this.stringArrayList = arrayList;
        this.fromsavepage = i;
        this.isImage = z;
        this.layoutInflater = (LayoutInflater) fragmentActivity.getSystemService("layout_inflater");
    }

    public StatusDataAdapter(Context context2, ArrayList<String> arrayList, int i, boolean z) {
        Log.d("TAG", "StatusDataAdapter: " + arrayList.size());
        this.context = (Activity) context2;
        this.stringArrayList = arrayList;
        this.fromsavepage = i;
        this.isImage = z;
        this.layoutInflater = (LayoutInflater) context2.getSystemService("layout_inflater");
    }

    public static String copyFileOnAboveQ(String str, Uri uri, String str2, File file2, Activity activity) {
        String str4;
        String str5 = null;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        FileNotFoundException e;
        IOException e2;
        Bitmap bitmap = null;
        FileOutputStream fileOutputStream2 = null;
        String str6 = "mp4";
        if (str.endsWith(str6)) {
            str3 = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + activity.getResources().getString(R.string.app_name) + "/" + activity.getResources().getString(R.string.gb_status_video));
        } else {
            str3 = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + activity.getResources().getString(R.string.app_name) + "/" + activity.getResources().getString(R.string.gb_status_image));
        }
        if (Build.VERSION.SDK_INT < 30) {
            String substring = str.substring(str.lastIndexOf("/"), str.lastIndexOf("."));
            str4 = substring.substring(1) + str.substring(str.lastIndexOf("."));
        } else {
            String substring2 = str.substring(str.lastIndexOf("%2F"), str.lastIndexOf("."));
            str4 = substring2.substring(3) + str.substring(str.lastIndexOf("."));
        }
        File file3 = new File(str3 + "/" + str4);
        file = file3;
        FileOutputStream fileOutputStream3 = null;
        if (file3.exists()) {
            Toast.makeText(activity, "Already Downloaded", Toast.LENGTH_SHORT).show();
        } else {
            ContentResolver contentResolver = activity.getContentResolver();
            String name = file2.getName();
            new File(str).getName();
            if (!str.endsWith(".jpg")) {
                if (str.endsWith(".png")) {
                    str6 = "png";
                } else if (!str.endsWith(".jpeg")) {
                    if (!str.endsWith(".mp4")) {
                        str6 = null;
                    }
                }
                if (!str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".png")) {
                    if (!file2.getPath().contains(Environment.DIRECTORY_PICTURES)) {
                        str5 = Environment.DIRECTORY_PICTURES + File.separator + "Whats web scan" + File.separator + name;
                    } else if (file2.getPath().contains(Environment.DIRECTORY_DCIM)) {
                        str5 = Environment.DIRECTORY_DCIM + File.separator + "Whats web scan" + File.separator + name;
                    } else {
                        str5 = Environment.DIRECTORY_PICTURES + File.separator + "Whats web scan" + File.separator + name;
                    }
                } else if (file2.getPath().contains(Environment.DIRECTORY_PICTURES)) {
                    str5 = Environment.DIRECTORY_PICTURES + File.separator + "Whats web scan" + File.separator + "Status Videos";
                } else if (file2.getPath().contains(Environment.DIRECTORY_DCIM)) {
                    str5 = Environment.DIRECTORY_DCIM + File.separator + "Whats web scan" + File.separator + "Status Videos";
                } else {
                    str5 = Environment.DIRECTORY_PICTURES + File.separator + "Whats web scan" + File.separator + "Status Videos";
                }
                if (!str.endsWith(".jpg") || str.endsWith(".png")) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_display_name", str2);
                    contentValues.put("mime_type", "image/" + str6);
                    contentValues.put("relative_path", str5);
                    try {
                        fileOutputStream2 = (FileOutputStream) contentResolver.openOutputStream(contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues));
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2)) {
                        try {
                            fileOutputStream2.flush();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    Toast.makeText(activity, "Image Downloaded Successfully", Toast.LENGTH_SHORT).show();
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                } else {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("_display_name", str2);
                    contentValues2.put("mime_type", "video/" + str6);
                    contentValues2.put("relative_path", str5);
                    Uri insert = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues2);
                    try {
                        fileOutputStream = (FileOutputStream) contentResolver.openOutputStream(insert);
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    try {
                        FileInputStream fileInputStream = new FileInputStream(activity.getContentResolver().openFileDescriptor(uri, "r").getFileDescriptor());
                        FileChannel channel = fileInputStream.getChannel();
                        channel.transferTo(0, channel.size(), fileOutputStream.getChannel());
                        fileInputStream.close();
                        fileOutputStream.close();
                        Toast.makeText(activity, "Video Downloaded Successfully", Toast.LENGTH_SHORT).show();
                        String realPath = FileUtilsa.getRealPath(activity, insert);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        return realPath;
                    } catch (FileNotFoundException e5) {
                        e = e5;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                        return null;
                    } catch (IOException e6) {
                        e2 = e6;
                        try {
                            e2.printStackTrace();
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream3 = fileOutputStream;
                            if (fileOutputStream3 != null) {
                                try {
                                    fileOutputStream3.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                }
                            }
                            try {
                                throw th;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    }
                }
            }
            str6 = "jpeg";
            if (!str.endsWith(".jpg")) {
            }
            if (!file2.getPath().contains(Environment.DIRECTORY_PICTURES)) {
            }
            try {
                if (!str.endsWith(".jpg")) {
                }
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                ContentValues contentValues3 = new ContentValues();
                contentValues3.put("_display_name", str2);
                contentValues3.put("mime_type", "image/" + str6);
                contentValues3.put("relative_path", str5);
                fileOutputStream2 = (FileOutputStream) contentResolver.openOutputStream(contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues3));
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2)) {
                }
                Toast.makeText(activity, "Image Downloaded Successfully", Toast.LENGTH_SHORT).show();
                if (fileOutputStream2 != null) {
                }
            } catch (FileNotFoundException e8) {
                e = e8;
                fileOutputStream = null;
                e.printStackTrace();
                if (fileOutputStream != null) {
                }
                return null;
            } catch (IOException e9) {
                e2 = e9;
                fileOutputStream = null;
                e2.printStackTrace();
                if (fileOutputStream != null) {
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                if (fileOutputStream3 != null) {
                }
                try {
                    throw th;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void saveFile(String str, Context context2) {
        String str2 = Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + context2.getResources().getString(R.string.app_name) + "/" + context2.getResources().getString(R.string.gb_status_image);
        File file2 = new File(str2);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        String str4 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            String[] split = str.split("/");
            str4 = split[split.length - 1];
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + str4);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                fileInputStream.close();
            } catch (IOException unused) {
            }
        } catch (IOException e) {
            e.printStackTrace();
            MediaScannerConnection.scanFile(context2, new String[]{new File(str2 + "/" + str4).getPath()}, new String[]{"image/jpeg"}, null);
            Toast.makeText(context2, "Image Downloaded Successfully", Toast.LENGTH_SHORT).show();
        }
        MediaScannerConnection.scanFile(context2, new String[]{new File(str2 + "/" + str4).getPath()}, new String[]{"image/jpeg"}, null);
        Toast.makeText(context2, "Image Downloaded Successfully", Toast.LENGTH_SHORT).show();
    }

    public static void saveVideo(String str, Context context2) {
        String str2 = Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + context2.getResources().getString(R.string.app_name) + "/" + context2.getResources().getString(R.string.gb_status_video);
        File file2 = new File(str2);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        String str4 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            String[] split = str.split("/");
            str4 = split[split.length - 1];
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + str4);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                fileInputStream.close();
            } catch (IOException unused) {
            }
        } catch (IOException e) {
            e.printStackTrace();
            MediaScannerConnection.scanFile(context2, new String[]{new File(str2 + "/" + str4).getPath()}, new String[]{"image/jpeg"}, null);
            Toast.makeText(context2, "Video Downloaded Successfully", Toast.LENGTH_SHORT).show();
        }
        MediaScannerConnection.scanFile(context2, new String[]{new File(str2 + "/" + str4).getPath()}, new String[]{"image/jpeg"}, null);
        Toast.makeText(context2, "Video Downloaded Successfully", Toast.LENGTH_SHORT).show();
    }

    private void loadNativeAd(final boolean z, final ViewHolder viewHolder) {
//        this.admobAdManager.LoadNativeAd(this.context, "ca-app-pub-3940256099942544/2247696110", new AdEventListener() {
//            /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass1 */
//
//
//            public void onAdLoaded(Object obj) {
//                if (obj != null) {
//                    viewHolder.frameLayout.setVisibility(View.VISIBLE);
//                    viewHolder.cardView.setVisibility(View.VISIBLE);
//                    StatusDataAdapter.this.isNativeADsShow = true;
//                    StatusDataAdapter.this.admobAdManager.populateUnifiedNativeAdView(StatusDataAdapter.this.context, viewHolder.frameLayout, (NativeAd) obj, z, false);
//                    return;
//                }
//                StatusDataAdapter.this.isNativeADsShow = false;
//                viewHolder.cardView.setVisibility(View.GONE);
//                viewHolder.frameLayout.setVisibility(View.GONE);
//            }
//
//
//            public void onAdClosed() {
//                viewHolder.cardView.setVisibility(View.GONE);
//                viewHolder.frameLayout.setVisibility(View.GONE);
//            }
//
//
//            public void onLoadError(String str) {
//                StatusDataAdapter.this.isNativeADsShow = false;
//                viewHolder.cardView.setVisibility(View.GONE);
//                viewHolder.frameLayout.setVisibility(View.GONE);
//            }
//        });
    }

    public int getCount() {
        return this.stringArrayList.size();
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        File file2;
        Log.i(TAG, "getView: view start position :- " + i);
        try {
            final ViewHolder viewHolder = new ViewHolder();
            view = this.layoutInflater.inflate(R.layout.gb_statusitem, viewGroup, false);
            viewHolder.thumbImage = (ImageView) view.findViewById(R.id.thumbImage);
            viewHolder.imgDownDelete = (ImageView) view.findViewById(R.id.imgDownDelete);
            viewHolder.layoutShare = (RelativeLayout) view.findViewById(R.id.layoutShare);
            viewHolder.layDownDelete = (RelativeLayout) view.findViewById(R.id.layDownDelete);
            viewHolder.frameLayout = (FrameLayout) view.findViewById(R.id.fl_adplaceholder);
            viewHolder.cardView = (CardView) view.findViewById(R.id.card_view);
            view.setTag(viewHolder);
            if (i != 1) {
                Log.i(TAG, "getView: normal view ");
                if (this.stringArrayList.get(i).startsWith("content")) {
                    Glide.with(this.context).load(this.stringArrayList.get(i)).into(viewHolder.thumbImage);
                } else {
                    RequestManager with = Glide.with(this.context);
                    with.load("file://" + this.stringArrayList.get(i)).into(viewHolder.thumbImage);
                }
                if (this.fromsavepage == 1) {
                    viewHolder.imgDownDelete.setImageResource(R.drawable.gb_ic_status_delete);
                    viewHolder.layDownDelete.setOnClickListener(new View.OnClickListener() {
                        /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass2 */

                        public void onClick(View view) {
                            final File file = new File(StatusDataAdapter.this.stringArrayList.get(i));
                            if (Build.VERSION.SDK_INT >= 30) {
                                StatusDataAdapter.this.path = file.getPath();
                                file.getAbsolutePath();
                                StatusDataAdapter.this.onClickListene.onClick(StatusDataAdapter.this.path);
                                StatusDataAdapter.this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
                                StatusDataAdapter.this.notifyDataSetChanged();
                                return;
                            }
                            AlertDialog.Builder builder = new AlertDialog.Builder(StatusDataAdapter.this.context);
                            if (StatusDataAdapter.this.isImage) {
                                builder.setTitle("Are you sure you want to delete this Image?");
                            } else {
                                builder.setTitle("Are you sure you want to delete this Video?");
                            }
                            builder.setNegativeButton("No", (DialogInterface.OnClickListener) null);
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass2.AnonymousClass1 */

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d("TAG", "deletonClick: " + StatusDataAdapter.this.stringArrayList.size() + "   " + i);
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("onClick: ");
                                    sb.append(file);
                                    Log.i(StatusDataAdapter.TAG, sb.toString());
                                    if (file.delete()) {
                                        StatusDataAdapter.this.stringArrayList.remove(i);
                                        StatusDataAdapter.this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
                                        StatusDataAdapter.this.notifyDataSetChanged();
                                    }
                                }
                            });
                            builder.create().show();
                        }
                    });
                    viewHolder.thumbImage.setOnClickListener(new View.OnClickListener() {
                        /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass3 */

                        public void onClick(View view) {
                            Uri uri;
                            if (StatusDataAdapter.this.isImage) {
                                new gb_DetailActivity().setdata(StatusDataAdapter.this.stringArrayList);
                                Intent intent = new Intent(StatusDataAdapter.this.context, gb_DetailActivity.class);
                                intent.putExtra("position", i);
                                intent.putExtra("stringvalue", StatusDataAdapter.this.stringArrayList);
                                intent.putExtra("Is_From", "true");
                                intent.putExtra("adapter", true);
                                StatusDataAdapter.this.context.startActivity(intent);
                                return;
                            }
                            File file = new File(StatusDataAdapter.this.stringArrayList.get(i));
                            if (Build.VERSION.SDK_INT >= 24) {
                                try {
                                    Activity activity = StatusDataAdapter.this.context;
                                    uri = FileProvider.getUriForFile(activity, StatusDataAdapter.this.context.getPackageName() + ".provider", file);
                                } catch (Exception unused) {
                                    uri = Uri.fromFile(file);
                                }
                            } else {
                                uri = Uri.fromFile(file);
                            }
                            Intent intent2 = new Intent("android.intent.action.VIEW");
                            intent2.setDataAndType(uri, "video/*");
                            intent2.addFlags(1);
                            StatusDataAdapter.this.context.startActivity(intent2);
                        }
                    });
                    viewHolder.layoutShare.setOnClickListener(new View.OnClickListener() {
                        /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass4 */

                        public void onClick(View view) {
                            Uri uri;
                            try {
                                File file = new File(StatusDataAdapter.this.stringArrayList.get(i));
                                if (Build.VERSION.SDK_INT >= 29) {
                                    uri = Uri.parse(StatusDataAdapter.this.stringArrayList.get(i));
                                } else if (Build.VERSION.SDK_INT >= 24) {
                                    try {
                                        Activity activity = StatusDataAdapter.this.context;
                                        uri = FileProvider.getUriForFile(activity, StatusDataAdapter.this.context.getPackageName() + ".fileprovider", file);
                                    } catch (Exception unused) {
                                        uri = Uri.fromFile(file);
                                    }
                                } else {
                                    uri = Uri.fromFile(file);
                                }
                                Intent intent = new Intent("android.intent.action.SEND");
                                if (StatusDataAdapter.this.isImage) {
                                    intent.setType("image/*");
                                } else {
                                    intent.setType("video/*");
                                }
                                intent.putExtra("android.intent.extra.STREAM", uri);
                                intent.putExtra("android.intent.extra.SUBJECT", StatusDataAdapter.this.context.getResources().getString(R.string.app_name));
                                intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + StatusDataAdapter.this.context.getPackageName());
                                intent.addFlags(1);
                                intent.addFlags(2);
                                intent.setFlags(268435456);
                                StatusDataAdapter.this.context.startActivity(Intent.createChooser(intent, "Choose one..."));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Log.e("position---->", String.valueOf(i));
                    Log.e("data ---->", this.stringArrayList.get(i));
                    final String[] strArr = {this.stringArrayList.get(i)};
                    String[] strArr2 = new String[1];
                    if (Build.VERSION.SDK_INT < 30) {
                        strArr2[0] = strArr[0].substring(strArr[0].lastIndexOf("/"), strArr[0].lastIndexOf("."));
                        String substring = strArr[0].substring(strArr[0].lastIndexOf("."));
                        filen = strArr2[0].substring(1) + substring;
                    } else {
                        strArr2[0] = strArr[0].substring(strArr[0].lastIndexOf("%2F"), strArr[0].lastIndexOf("."));
                        String substring2 = strArr[0].substring(strArr[0].lastIndexOf("."));
                        filen = strArr2[0].substring(3) + substring2;
                    }
                    if (strArr[0].endsWith(".mp4")) {
                        file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + this.context.getString(R.string.app_name) + "/" + this.context.getString(R.string.gb_status_video));
                    } else {
                        file2 = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + this.context.getString(R.string.app_name) + "/" + this.context.getString(R.string.gb_status_image));
                    }
                    final File file3 = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" + this.context.getString(R.string.app_name) + "/" + this.context.getString(R.string.gb_status_image));
                    if (new File(file2 + "/" + filen).exists()) {
                        viewHolder.imgDownDelete.setImageResource(R.drawable.gb_truecheck);
                    } else {
                        viewHolder.imgDownDelete.setImageResource(R.drawable.gb_ic_head_down);
                    }
                    viewHolder.layDownDelete.setOnClickListener(new View.OnClickListener() {
                        /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass5 */

                        public void onClick(View view) {
                            strArr[0] = StatusDataAdapter.this.stringArrayList.get(i);
                            if (Build.VERSION.SDK_INT < 30) {
//                                String[] strArr = strArr;
                                String substring = strArr[0].substring(strArr[0].lastIndexOf("/"), strArr[0].lastIndexOf("."));
                                String[] strArr2 = strArr;
                                String unused = StatusDataAdapter.filen = substring.substring(1) + strArr2[0].substring(strArr2[0].lastIndexOf("."));
                            } else {
                                String[] strArr3 = strArr;
                                String substring2 = strArr3[0].substring(strArr3[0].lastIndexOf("%2F"), strArr[0].lastIndexOf("."));
                                String[] strArr4 = strArr;
                                String unused2 = StatusDataAdapter.filen = substring2.substring(3) + strArr4[0].substring(strArr4[0].lastIndexOf("."));
                            }
                            if (new File(file3 + "/" + StatusDataAdapter.filen).exists()) {
                                Toast.makeText(StatusDataAdapter.this.context, "Already Downloaded", Toast.LENGTH_SHORT).show();
                            } else if (StatusDataAdapter.this.isImage) {
                                viewHolder.imgDownDelete.setImageResource(R.drawable.gb_truecheck);
                                if (Build.VERSION.SDK_INT < 30) {
                                    StatusDataAdapter.saveFile(StatusDataAdapter.this.stringArrayList.get(i), StatusDataAdapter.this.context);
                                } else if (StatusDataAdapter.this.stringArrayList.get(i).startsWith("content")) {
                                    StatusDataAdapter.copyFileOnAboveQ(StatusDataAdapter.this.stringArrayList.get(i), Uri.parse(StatusDataAdapter.this.stringArrayList.get(i)), StatusDataAdapter.filen, file3, StatusDataAdapter.this.context);
                                } else {
                                    StatusDataAdapter.copyFileOnAboveQ("file://" + StatusDataAdapter.this.stringArrayList.get(i), Uri.parse("file://" + StatusDataAdapter.this.stringArrayList.get(i)), StatusDataAdapter.filen, file3, StatusDataAdapter.this.context);
                                }
                            } else {
                                viewHolder.imgDownDelete.setImageResource(R.drawable.gb_truecheck);
                                if (Build.VERSION.SDK_INT < 30) {
                                    StatusDataAdapter.saveVideo(StatusDataAdapter.this.stringArrayList.get(i), StatusDataAdapter.this.context);
                                } else if (StatusDataAdapter.this.stringArrayList.get(i).startsWith("content")) {
                                    StatusDataAdapter.copyFileOnAboveQ(StatusDataAdapter.this.stringArrayList.get(i), Uri.parse(StatusDataAdapter.this.stringArrayList.get(i)), StatusDataAdapter.filen, file3, StatusDataAdapter.this.context);
                                } else {
                                    StatusDataAdapter.copyFileOnAboveQ("file://" + StatusDataAdapter.this.stringArrayList.get(i), Uri.parse("file://" + StatusDataAdapter.this.stringArrayList.get(i)), StatusDataAdapter.filen, file3, StatusDataAdapter.this.context);
                                }
                            }
                        }
                    });
                    viewHolder.layoutShare.setOnClickListener(new View.OnClickListener() {
                        /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass6 */

                        public void onClick(View view) {
                            Uri uri;
                            try {
                                File file = new File(StatusDataAdapter.this.stringArrayList.get(i));
                                if (Build.VERSION.SDK_INT >= 29) {
                                    uri = Uri.parse(StatusDataAdapter.this.stringArrayList.get(i));
                                } else if (Build.VERSION.SDK_INT >= 24) {
                                    try {
                                        Activity activity = StatusDataAdapter.this.context;
                                        uri = FileProvider.getUriForFile(activity, StatusDataAdapter.this.context.getPackageName() + ".fileprovider", file);
                                    } catch (Exception unused) {
                                        uri = Uri.fromFile(file);
                                    }
                                } else {
                                    uri = Uri.fromFile(file);
                                }
                                Intent intent = new Intent("android.intent.action.SEND");
                                if (StatusDataAdapter.this.isImage) {
                                    intent.setType("image/*");
                                } else {
                                    intent.setType("video/*");
                                }
                                intent.putExtra("android.intent.extra.STREAM", uri);
                                intent.putExtra("android.intent.extra.SUBJECT", StatusDataAdapter.this.context.getResources().getString(R.string.app_name));
                                intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + StatusDataAdapter.this.context.getPackageName());
                                intent.addFlags(1);
                                intent.addFlags(2);
                                intent.setFlags(268435456);
                                StatusDataAdapter.this.context.startActivity(Intent.createChooser(intent, "Choose one..."));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    viewHolder.thumbImage.setOnClickListener(new View.OnClickListener() {
                        /* class com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.AnonymousClass7 */

                        public void onClick(View view) {
                            Uri uri;
                            if (StatusDataAdapter.this.isImage) {
                                new gb_DetailActivity().setdata(StatusDataAdapter.this.stringArrayList);
                                Intent intent = new Intent(StatusDataAdapter.this.context, gb_DetailActivity.class);
                                intent.putExtra("position", i);
                                intent.putExtra("Is_From", "recent");
                                StatusDataAdapter.this.context.startActivity(intent);
                                return;
                            }
                            File file = new File(StatusDataAdapter.this.stringArrayList.get(i));
                            if (Build.VERSION.SDK_INT >= 29) {
                                uri = Uri.parse(StatusDataAdapter.this.stringArrayList.get(i));
                            } else if (Build.VERSION.SDK_INT >= 24) {
                                try {
                                    Activity activity = StatusDataAdapter.this.context;
                                    uri = FileProvider.getUriForFile(activity, StatusDataAdapter.this.context.getPackageName() + ".provider", file);
                                } catch (Exception unused) {
                                    uri = Uri.fromFile(file);
                                }
                            } else {
                                uri = Uri.fromFile(file);
                            }
                            Intent intent2 = new Intent("android.intent.action.VIEW");
                            intent2.setDataAndType(uri, "video/*");
                            intent2.addFlags(1);
                            StatusDataAdapter.this.context.startActivity(intent2);
                        }
                    });
                }
            } else {
                this.checkvideo = false;
                Log.i(TAG, "getView: load native ads pos :- " + i);
                viewHolder.layoutShare.setVisibility(View.GONE);
                viewHolder.layDownDelete.setVisibility(View.GONE);
                loadNativeAd(true, viewHolder);
            }
        } catch (Exception e) {
            Log.i(TAG, "getView: error :- " + e.getMessage());
        }
        return view;
    }
}
