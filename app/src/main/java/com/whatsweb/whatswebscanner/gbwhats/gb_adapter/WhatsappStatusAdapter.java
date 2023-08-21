package com.whatsweb.whatswebscanner.gbwhats.gb_adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_VideoPlayerActivity;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.FileListWhatsappClickInterface;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.WhatsappStatusModel;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class WhatsappStatusAdapter extends RecyclerView.Adapter<WhatsappStatusAdapter.ViewHolder> {
    private Context context;
    ProgressDialog dialogProgress;
    private ArrayList<WhatsappStatusModel> fileArrayList;
    private FileListWhatsappClickInterface fileListClickInterface;
    String fileName = "";
    private LayoutInflater layoutInflater;
    public String saveFilePath = (Utils.RootDirectoryWhatsappShow + File.separator);

    public WhatsappStatusAdapter(Context context2, ArrayList<WhatsappStatusModel> arrayList, FileListWhatsappClickInterface fileListWhatsappClickInterface) {
        this.context = context2;
        this.fileArrayList = arrayList;
        this.fileListClickInterface = fileListWhatsappClickInterface;
        initProgress();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gb_items_whatsapp_view, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final WhatsappStatusModel whatsappStatusModel = this.fileArrayList.get(i);
        if (whatsappStatusModel.getUri().toString().endsWith(".mp4")) {
            viewHolder.iv_play.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_play.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT > 29) {
            Glide.with(this.context).load(whatsappStatusModel.getUri()).into(viewHolder.pcw);
        } else {
            Glide.with(this.context).load(whatsappStatusModel.getPath()).into(viewHolder.pcw);
        }
        viewHolder.iv_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(WhatsappStatusAdapter.this.context, gb_VideoPlayerActivity.class);
                intent.putExtra("PathVideo", whatsappStatusModel.getUri().toString());
                WhatsappStatusAdapter.this.context.startActivity(intent);
            }
        });
        viewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WhatsappStatusAdapter.this.fileListClickInterface.getPosition(i);
            }
        });
        viewHolder.tv_download.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                WhatsappStatusAdapter.this.onWhatsappStatus_Adapter1(whatsappStatusModel, view);
            }
        });
    }

    public void onWhatsappStatus_Adapter1(WhatsappStatusModel whatsappStatusModel, View view) {
        Utils.createFileFolder();
        if (Build.VERSION.SDK_INT > 29) {
            try {
                if (whatsappStatusModel.getUri().toString().endsWith(".mp4")) {
                    this.fileName = "status_" + System.currentTimeMillis() + ".mp4";
                    new DownloadFileTask().execute(whatsappStatusModel.getUri().toString());
                    return;
                }
                this.fileName = "status_" + System.currentTimeMillis() + ".png";
                new DownloadFileTask().execute(whatsappStatusModel.getUri().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String path = whatsappStatusModel.getPath();
            String substring = path.substring(path.lastIndexOf("/") + 1);
            try {
                FileUtils.copyFileToDirectory(new File(path), new File(this.saveFilePath));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            String substring2 = substring.substring(12);
            MediaScannerConnection.scanFile(this.context, new String[]{new File(this.saveFilePath + substring2).getAbsolutePath()}, new String[]{whatsappStatusModel.getUri().toString().endsWith(".mp4") ? "video/*" : "image/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {

                public void onMediaScannerConnected() {
                }

                public void onScanCompleted(String str, Uri uri) {
                }
            });
            new File(this.saveFilePath, substring).renameTo(new File(this.saveFilePath, substring2));
            Context context2 = this.context;
            Toast.makeText(context2, this.context.getResources().getString(R.string.gb_saved_to) + this.saveFilePath + substring2, Toast.LENGTH_LONG).show();
        }
    }


    public int getItemCount() {
        return fileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_play;
        ImageView pcw;
        TextView tv_download;
        RelativeLayout rl_main;

        public ViewHolder(View view) {
            super(view);
            iv_play = (ImageView) view.findViewById(R.id.iv_play);
            pcw = (ImageView) view.findViewById(R.id.pcw);
            tv_download = (TextView) view.findViewById(R.id.tv_download);
            rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        }
    }

    private static File createFileFromInputStream(Uri uri, Context context2, String str) {
        File file;
        try {
            InputStream openInputStream = context2.getContentResolver().openInputStream(uri);
            if (str.equals("video")) {
                file = new File(Utils.RootDirectoryWhatsappShow + File.separator + "Status_ " + System.currentTimeMillis() + ".mp4");
            } else {
                file = new File(Utils.RootDirectoryWhatsappShow + File.separator + "Status_ " + System.currentTimeMillis() + ".png");
            }
            file.setWritable(true, false);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    openInputStream.close();
                    return file;
                }
            }
        } catch (Exception e) {
            System.out.println("error in creating a file");
            e.printStackTrace();
            return null;
        }
    }

    public void initProgress() {
        ProgressDialog progressDialog = new ProgressDialog(this.context);
        this.dialogProgress = progressDialog;
        progressDialog.setProgressStyle(0);
        this.dialogProgress.setTitle("Saving");
        this.dialogProgress.setMessage("Saving. Please wait...");
        this.dialogProgress.setIndeterminate(true);
        this.dialogProgress.setCanceledOnTouchOutside(false);
    }

    class DownloadFileTask extends AsyncTask<String, String, String> {
        void onPostExecute0(String str, Uri uri) {
        }

        public void onProgressUpdate(String... strArr) {
        }

        DownloadFileTask() {
        }

        public String doInBackground(String... strArr) {
            try {
                InputStream openInputStream = WhatsappStatusAdapter.this.context.getContentResolver().openInputStream(Uri.parse(strArr[0]));
                File file = new File(Utils.RootDirectoryWhatsappShow + File.separator + WhatsappStatusAdapter.this.fileName);
                file.setWritable(true, false);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.close();
                        openInputStream.close();
                        return null;
                    }
                }
            } catch (Exception e) {
                System.out.println("error in creating a file");
                e.printStackTrace();
                return null;
            }
        }

        public void onPostExecute(String str) {
            Utils.setToast(WhatsappStatusAdapter.this.context, WhatsappStatusAdapter.this.context.getResources().getString(R.string.gb_download_complete));
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    Context context = WhatsappStatusAdapter.this.context;
                    MediaScannerConnection.scanFile(context, new String[]{new File(Utils.RootDirectoryWhatsappShow + File.separator + WhatsappStatusAdapter.this.fileName).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                            onPostExecute0(str, uri);
                        }
                    });
                    return;
                }
                Context context2 = WhatsappStatusAdapter.this.context;
                context2.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Utils.RootDirectoryWhatsappShow + File.separator + WhatsappStatusAdapter.this.fileName))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onCancelled() {
            super.onCancelled();
        }
    }
}
