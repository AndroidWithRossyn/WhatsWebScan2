package com.whatsweb.whatswebscanner.gbwhats.gb_fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.StatusDataAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Constant;

import java.util.ArrayList;

public class VidoesFragment extends Fragment {
    private GridView gridView;
    private TextView noData;
    ArrayList<Uri> removableList;
    private StatusDataAdapter statusDataAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.gb_imagevideo_fragment, viewGroup, false);
        this.gridView = (GridView) inflate.findViewById(R.id.gridImgVidStatus);
        this.noData = (TextView) inflate.findViewById(R.id.noData);
        if (Constant.recentVideoArraylist != null && !Constant.recentVideoArraylist.isEmpty()) {
            if (Constant.recentVideoArraylist.size() == 0) {
                this.noData.setVisibility(View.VISIBLE);
            } else {
                this.noData.setVisibility(View.GONE);
            }
            StatusDataAdapter statusDataAdapter2 = new StatusDataAdapter(getActivity(), Constant.recentVideoArraylist, 0, false);
            this.statusDataAdapter = statusDataAdapter2;
            this.gridView.setAdapter((ListAdapter) statusDataAdapter2);
        } else if (Constant.recentVideoArraylist.size() == 0) {
            this.noData.setVisibility(View.VISIBLE);
        } else {
            this.noData.setVisibility(View.GONE);
        }
        return inflate;
    }
}
