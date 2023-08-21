package com.whatsweb.whatswebscanner.gbwhats.gb_fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.WhatsappStatusAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.FileListWhatsappClickInterface;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.WhatsappStatusModel;

import java.io.File;
import java.util.ArrayList;

public class WhatsappQVideoFragment extends Fragment implements FileListWhatsappClickInterface {
    private File[] allfiles;
    private ArrayList<Uri> fileArrayList;
    ArrayList<WhatsappStatusModel> statusModelArrayList;
    private WhatsappStatusAdapter whatsappStatusAdapter;
    private SwipeRefreshLayout swiperefresh;
    private TextView tvNoResult;
    private RecyclerView rv_fileList;

    public void getPosition(int i) {
    }

    public WhatsappQVideoFragment(ArrayList<Uri> arrayList) {
        this.fileArrayList = arrayList;
    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.gb_fragment_whatsapp_image, viewGroup, false);
        swiperefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        tvNoResult = (TextView) rootView.findViewById(R.id.tvNoResult);
        rv_fileList = (RecyclerView) rootView.findViewById(R.id.rv_fileList);
        initViews();
        return rootView;
    }

    private void initViews() {
        this.statusModelArrayList = new ArrayList<>();
        getData();
        this.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public final void onRefresh() {
                statusModelArrayList = new ArrayList<>();
                getData();
                swiperefresh.setRefreshing(false);
            }
        });
    }

    private void getData() {
        if (Build.VERSION.SDK_INT > 29) {
            for (int i = 0; i < this.fileArrayList.size(); i++) {
                try {
                    Uri uri = this.fileArrayList.get(i);
                    if (uri.toString().endsWith(".mp4")) {
                        this.statusModelArrayList.add(new WhatsappStatusModel("WhatsStatus: " + (i + 1), uri, new File(uri.toString()).getAbsolutePath(), new File(uri.toString()).getName()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (this.statusModelArrayList.size() != 0) {
                this.tvNoResult.setVisibility(View.GONE);
            } else {
                this.tvNoResult.setVisibility(View.VISIBLE);
            }
            this.whatsappStatusAdapter = new WhatsappStatusAdapter(getActivity(), this.statusModelArrayList, this);
            this.rv_fileList.setAdapter(this.whatsappStatusAdapter);
        }
    }

    class LoadAllFiles extends AsyncTask<String, String, String> {
        
        public void onProgressUpdate(String... strArr) {
        }

        LoadAllFiles() {
        }

        
        public String doInBackground(String... strArr) {
            DocumentFile[] listFiles = DocumentFile.fromTreeUri(WhatsappQVideoFragment.this.getActivity(), WhatsappQVideoFragment.this.getActivity().getContentResolver().getPersistedUriPermissions().get(0).getUri()).listFiles();
            for (DocumentFile documentFile : listFiles) {
                if (!documentFile.isDirectory()) {
                    System.out.println(documentFile.getName());
                    if (!documentFile.getName().equals(".nomedia") && documentFile.getName().endsWith(".mp4")) {
                        WhatsappQVideoFragment.this.statusModelArrayList.add(new WhatsappStatusModel("WhatsStatus: " + (System.currentTimeMillis() + 1), documentFile.getUri(), documentFile.getUri().getPath(), documentFile.getName()));
                    }
                }
            }
            return null;
        }

        
        public void onPostExecute(String str) {
            if (WhatsappQVideoFragment.this.statusModelArrayList.size() != 0) {
                WhatsappQVideoFragment.this.tvNoResult.setVisibility(View.GONE);
            } else {
                WhatsappQVideoFragment.this.tvNoResult.setVisibility(View.VISIBLE);
            }
            WhatsappQVideoFragment whatsappQVideoFragment = WhatsappQVideoFragment.this;
            whatsappQVideoFragment.whatsappStatusAdapter = new WhatsappStatusAdapter(whatsappQVideoFragment.getActivity(), WhatsappQVideoFragment.this.statusModelArrayList, WhatsappQVideoFragment.this);
            WhatsappQVideoFragment.this.rv_fileList.setAdapter(WhatsappQVideoFragment.this.whatsappStatusAdapter);
        }

        public void onCancelled() {
            super.onCancelled();
        }
    }
}
