package com.whatsweb.whatswebscanner.gbwhats.gb_fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.StatusDataAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Constant;

import java.util.ArrayList;

public class ImagesFragment extends Fragment {
    String deletestring;
    GridView gridView;
    private TextView noData;
    ArrayList<Uri> removableList;
    ActivityResultLauncher<IntentSenderRequest> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), ImagesFragment$$ExternalSyntheticLambda0.INSTANCE);
    StatusDataAdapter statusDataAdapter;

    static void lambda$new$0(ActivityResult activityResult) {
        Log.i("TAG", "onActivityResult: ");
        if (activityResult == null || activityResult.getResultCode() != -1) {
            Log.i("TAG", "onActivityResult: can not delete");
        } else {
            Log.i("TAG", "onActivityResult: deleted");
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.gb_imagevideo_fragment, viewGroup, false);
        this.gridView = (GridView) inflate.findViewById(R.id.gridImgVidStatus);
        this.noData = (TextView) inflate.findViewById(R.id.noData);
        if (Constant.recentImageArraylist != null && !Constant.recentImageArraylist.isEmpty()) {
            if (Constant.recentImageArraylist.size() == 0) {
                this.noData.setVisibility(View.VISIBLE);
            } else {
                this.noData.setVisibility(View.GONE);
            }
            StatusDataAdapter statusDataAdapter2 = new StatusDataAdapter(getActivity(), Constant.recentImageArraylist, 0, true);
            this.statusDataAdapter = statusDataAdapter2;
            this.gridView.setAdapter((ListAdapter) statusDataAdapter2);
        } else if (Constant.recentImageArraylist.size() == 0) {
            this.noData.setVisibility(View.VISIBLE);
        } else {
            this.noData.setVisibility(View.GONE);
        }
        return inflate;
    }
}
