package com.whatsweb.whatswebscanner.gbwhats.gb_util;

import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.view.View;
import android.webkit.MimeTypeMap;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

public class Util {
    public static void setLightStatusBarColor(View view, Activity activity) {
    }

    public static String formateSize(long j) {
        if (j <= 0) {
            return "0";
        }
        double d = (double) j;
        int log10 = (int) (Math.log10(d) / Math.log10(1024.0d));
        StringBuilder sb = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
        double pow = Math.pow(1024.0d, (double) log10);
        Double.isNaN(d);
        sb.append(decimalFormat.format(d / pow));
        sb.append(" ");
        sb.append(new String[]{"B", "KB", "MB", "GB", "TB"}[log10]);
        return sb.toString();
    }

    public static boolean externalMemoryAvailable(Context context) {
        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(context, null);
        return (externalFilesDirs.length <= 1 || externalFilesDirs[0] == null || externalFilesDirs[1] == null) ? false : true;
    }

    public static String getExternalStoragePath(Context context, boolean z) {
        StorageManager storageManager = (StorageManager) context.getSystemService("storage");
        try {
            Class<?> cls = Class.forName("android.os.storage.StorageVolume");
            Method method = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
            Method method2 = cls.getMethod("getPath", new Class[0]);
            Method method3 = cls.getMethod("isRemovable", new Class[0]);
            Object invoke = method.invoke(storageManager, new Object[0]);
            int length = Array.getLength(invoke);
            for (int i = 0; i < length; i++) {
                Object obj = Array.get(invoke, i);
                String str = (String) method2.invoke(obj, new Object[0]);
                if (z == ((Boolean) method3.invoke(obj, new Object[0])).booleanValue()) {
                    return str;
                }
            }
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static String getMimeTypeFromFilePath(String str) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFilenameExtension(str));
        if (mimeTypeFromExtension == null) {
            return null;
        }
        return mimeTypeFromExtension;
    }

    public static String getFilenameExtension(String str) {
        return str.substring(str.lastIndexOf(".") + 1);
    }

    public static File moveFile(File file, File file2) throws IOException {
        Throwable th;
        FileChannel fileChannel;
        File file3 = new File(file2, file.getName());
        FileChannel fileChannel2 = null;
        try {
            fileChannel = new FileOutputStream(file3).getChannel();
            try {
                fileChannel2 = new FileInputStream(file).getChannel();
                fileChannel2.transferTo(0, fileChannel2.size(), fileChannel);
                fileChannel2.close();
                file.delete();
                if (fileChannel2 != null) {
                    fileChannel2.close();
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                return file3;
            } catch (Throwable th2) {
                th = th2;
                if (fileChannel2 != null) {
                    fileChannel2.close();
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileChannel = null;
            if (fileChannel2 != null) {
            }
            if (fileChannel != null) {
            }
            try {
                throw th;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return file3;
    }

    public static void moveDirectory(File file, File file2, Context context) throws IOException {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdir();
            }
            String[] list = file.list();
            for (int i = 0; i < file.listFiles().length; i++) {
                moveDirectory(new File(file, list[i]), new File(file2, list[i]), context);
            }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileInputStream.close();
                fileOutputStream.close();
                MediaScannerConnection.scanFile(context, new String[]{file2.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                return;
            }
        }
    }

    public static boolean copyDirectoryOneLocationToAnotherLocation(File file, File file2, Context context) {
        Exception e;
        boolean z = false;
        try {
            if (file.isDirectory()) {
                if (!file2.exists()) {
                    file2.mkdir();
                }
                try {
                    String[] list = file.list();
                    for (int i = 0; i < file.listFiles().length; i++) {
                        copyDirectoryOneLocationToAnotherLocation(new File(file, list[i]), new File(file2, list[i]), context);
                    }
                    return false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return false;
                }
            } else {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read > 0) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            fileInputStream.close();
                            fileOutputStream.close();
                            try {
                                MediaScannerConnection.scanFile(context, new String[]{file2.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                                    /* class com.whatsweb.whatswebscanner.gbwhats.util.Util.AnonymousClass2 */

                                    public void onScanCompleted(String str, Uri uri) {
                                    }
                                });
                                return true;
                            } catch (Exception e3) {
                                e = e3;
                                z = true;
                                e.printStackTrace();
                                return z;
                            }
                        }
                    }
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    return z;
                }
            }
        } catch (Exception e5) {
            e5.printStackTrace();
            return false;
        }
    }
}
