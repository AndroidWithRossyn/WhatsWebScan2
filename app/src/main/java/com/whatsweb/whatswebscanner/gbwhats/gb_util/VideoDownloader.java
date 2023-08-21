package com.whatsweb.whatswebscanner.gbwhats.gb_util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

import com.whatsweb.whatswebscanner.gbwhats.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoDownloader extends AsyncTask {
    private onVideoDownloadListner f16288a;
    private final ProgressDialog f16289b;
    private final String f16290c;

    public VideoDownloader(String str, ProgressDialog progressDialog, onVideoDownloadListner onvideodownloadlistner) {
        this.f16290c = str;
        this.f16289b = progressDialog;
        this.f16288a = onvideodownloadlistner;
    }

    @Override
    public Object doInBackground(Object[] objArr) {
        String str = Environment.getExternalStorageDirectory().getPath() + "/" + NVApplication.getContext().getResources().getString(R.string.app_name) + "/" + NVApplication.getContext().getResources().getString(R.string.gb_status_video);
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(this.f16290c);
            String[] split = this.f16290c.split("/");
            String str2 = split[split.length - 1];
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str + "/" + str2);
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
            } catch (FileNotFoundException unused) {
            } catch (IOException e) {
                e.printStackTrace();
                this.f16289b.setProgress(1);
                this.f16288a.onDownloadComplete(str + "/" + str2);
                return null;
            }
            this.f16289b.setProgress(1);
            this.f16288a.onDownloadComplete(str + "/" + str2);
            return null;
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            this.f16289b.setProgress(1);
            this.f16288a.onDownloadComplete(str + "/" + "");
            return null;
        }
    }
}
