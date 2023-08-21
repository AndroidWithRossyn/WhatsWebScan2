package com.whatsweb.whatswebscanner.gbwhats.gb_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_FullViewActivity;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.FileListAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.FileListClickInterface;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Utils;

import java.io.File;
import java.util.ArrayList;

public class AllinOneGalleryFragment extends Fragment implements FileListClickInterface {
    private Activity activity;
    private ArrayList<File> fileArrayList;
    private FileListAdapter fileListAdapter;
    String type;
    private SwipeRefreshLayout swiperefresh;
    private RecyclerView rvFileList;
    private TextView tvNoResult;

    public AllinOneGalleryFragment(String str) {
        this.type = str;
    }


    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }


    public void onResume() {
        super.onResume();
        this.activity = getActivity();
        getAllFiles();
    }


    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.gb_fragment_history, viewGroup, false);

        swiperefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        rvFileList = (RecyclerView) rootView.findViewById(R.id.rvFileList);
        tvNoResult = (TextView) rootView.findViewById(R.id.tvNoResult);

        initViews();
        return rootView;
    }

    private void initViews() {
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public final void onRefresh() {
                getAllFiles();
                swiperefresh.setRefreshing(false);
            }
        });
    }

    public void getPosition(int i, File file) {
        Intent intent = new Intent(this.activity, gb_FullViewActivity.class);
        intent.putExtra("ImageDataFile", this.fileArrayList);
        intent.putExtra("Position", i);
        this.activity.startActivity(intent);
    }

    public void getAllFolders(String str) {
        File[] listFiles = new File(str).listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                getAllFilesList(file.getAbsolutePath());
            }
        }
    }

    public void getAllFilesList(String str) {
        File file = new File(str);
        ArrayList arrayList = new ArrayList();
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (file2.isFile()) {
                if (this.type.equals("Videos")) {
                    if (file2.getName().substring(file2.getName().lastIndexOf(".")).equals(".mp4")) {
                        arrayList.add(file2);
                    }
                } else if (this.type.equals("Images")) {
                    String substring = file2.getName().substring(file2.getName().lastIndexOf("."));
                    if (substring.equals(".png") || substring.equals(".jpg") || substring.equals(".jpeg")) {
                        arrayList.add(file2);
                    }
                }
            }
        }
        this.fileArrayList.addAll(arrayList);
    }

    private void getAllFiles() {
        try {
            this.fileArrayList = new ArrayList<>();
            getAllFolders(String.valueOf(Utils.RootDirectoryMain));
            ArrayList<File> arrayList = this.fileArrayList;
            if (arrayList != null) {
                this.fileListAdapter = new FileListAdapter(this.activity, arrayList, this);
                rvFileList.setAdapter(this.fileListAdapter);
                if (this.fileArrayList.size() > 0) {
                    tvNoResult.setVisibility(View.GONE);
                    swiperefresh.setVisibility(View.VISIBLE);
                    return;
                }
                swiperefresh.setVisibility(View.GONE);
                tvNoResult.setVisibility(View.VISIBLE);
                return;
            }
            swiperefresh.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
