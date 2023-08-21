package com.whatsweb.whatswebscanner.gbwhats.gb_util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

public class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private File f16291a;
    private MediaScannerConnection f16292b;

    public MediaScanner(Context context, File file) {
        this.f16291a = file;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.f16292b = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    public void onMediaScannerConnected() {
        this.f16292b.scanFile(this.f16291a.getAbsolutePath(), null);
    }

    public void onScanCompleted(String str, Uri uri) {
        this.f16292b.disconnect();
    }
}
