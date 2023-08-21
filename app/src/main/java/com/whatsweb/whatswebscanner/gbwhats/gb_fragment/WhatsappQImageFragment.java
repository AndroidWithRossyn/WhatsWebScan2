package com.whatsweb.whatswebscanner.gbwhats.gb_fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_FullViewHomeWAActivity;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.WhatsappStatusAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.FileListWhatsappClickInterface;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.WhatsappStatusModel;

import java.io.File;
import java.util.ArrayList;

public class WhatsappQImageFragment extends Fragment implements FileListWhatsappClickInterface {
    private File[] allfiles;
    private ArrayList<Uri> fileArrayList;
    private ArrayList<Uri> fileArrayListImages;
    ArrayList<WhatsappStatusModel> statusModelArrayList;
    private WhatsappStatusAdapter whatsappStatusAdapter;
    private SwipeRefreshLayout swiperefresh;
    private TextView tvNoResult;
    private RecyclerView rv_fileList;

    public WhatsappQImageFragment(ArrayList<Uri> arrayList) {
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
        this.fileArrayListImages = new ArrayList<>();
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
                    if (uri.toString().endsWith(".png") || uri.toString().endsWith(".jpg")) {
                        this.statusModelArrayList.add(new WhatsappStatusModel("WhatsStatus: " + (i + 1), uri, new File(uri.toString()).getAbsolutePath(), new File(uri.toString()).getName()));
                        this.fileArrayListImages.add(uri);
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

    public void getPosition(int i) {
        Intent intent = new Intent(getActivity(), gb_FullViewHomeWAActivity.class);
        intent.putExtra("ImageDataFile", this.fileArrayListImages);
        intent.putExtra("Position", i);
        getActivity().startActivity(intent);
    }
}
