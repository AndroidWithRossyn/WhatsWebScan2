package com.whatsweb.whatswebscanner.gbwhats.gb_fragment;

import android.app.PendingIntent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.AdapterCallBackInterface;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_adapter.StatusDataAdapter;
import com.whatsweb.whatswebscanner.gbwhats.gb_util.Constant;

import java.util.ArrayList;

public class C2974b extends Fragment implements AdapterCallBackInterface {
    public static int DELETE_REQUEST_CODE = 1001;
    private GridView gridView;
    private TextView noData;
    ArrayList<Uri> removableList;
    public String removableLists = new String();
    ActivityResultLauncher<IntentSenderRequest> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new C2974b$$ExternalSyntheticLambda0(this));
    private StatusDataAdapter statusDataAdapter;

    @Override
    public void ondeleteclick(String str) {
    }

    public void lambda$new$0$C2974b(ActivityResult activityResult) {
        Log.i("TAG", "onActivityResult: ");
        if (activityResult == null || activityResult.getResultCode() != -1) {
            Log.i("TAG", "onActivityResult: can not delete");
            return;
        }
        for (int i = 0; i < Constant.savedVideoArraylist.size(); i++) {
            if (Constant.savedVideoArraylist.get(i).equalsIgnoreCase(this.removableLists)) {
                Constant.savedVideoArraylist.remove(i);
                this.statusDataAdapter.notifyDataSetChanged();
            }
        }
        Log.i("TAG", "onActivityResult: deleted");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.gb_imagevideo_fragment, viewGroup, false);
        this.gridView = (GridView) inflate.findViewById(R.id.gridImgVidStatus);
        this.noData = (TextView) inflate.findViewById(R.id.noData);
        StatusDataAdapter statusDataAdapter2 = new StatusDataAdapter(this, getActivity(), Constant.savedVideoArraylist, 1, false);
        this.statusDataAdapter = statusDataAdapter2;
        this.gridView.setAdapter((ListAdapter) statusDataAdapter2);
        if (Constant.savedVideoArraylist == null || Constant.savedVideoArraylist.isEmpty()) {
            if (Constant.savedVideoArraylist.size() == 0) {
                this.noData.setVisibility(View.VISIBLE);
            } else {
                this.noData.setVisibility(View.GONE);
                this.statusDataAdapter.setOnClickListene(new StatusDataAdapter.onClickListene() {

                    @Override
                    public void onClick(String str) {
                        final ArrayList arrayList = new ArrayList();
                        MediaScannerConnection.scanFile(C2974b.this.getActivity(), new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String str, Uri uri) {
                                arrayList.add(uri);
                                try {
                                    C2974b.this.getActivity().startIntentSenderForResult((Build.VERSION.SDK_INT >= 30 ? MediaStore.createDeleteRequest(C2974b.this.getActivity().getContentResolver(), arrayList) : null).getIntentSender(), C2974b.DELETE_REQUEST_CODE, null, 0, 0, 0, null);
                                } catch (Exception unused) {
                                }
                            }
                        });
                    }
                });
            }
        } else if (Constant.savedVideoArraylist.size() == 0) {
            this.noData.setVisibility(View.VISIBLE);
        } else {
            this.noData.setVisibility(View.GONE);
            this.statusDataAdapter.setOnClickListene(new StatusDataAdapter.onClickListene() {
                @Override
                public void onClick(String str) {
                    C2974b.this.removableList.add(Uri.parse(str));
                    C2974b.this.removableLists = str;
                    final ArrayList arrayList = new ArrayList();
                    MediaScannerConnection.scanFile(C2974b.this.getActivity(), new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String str, Uri uri) {
                            PendingIntent pendingIntent;
                            arrayList.add(uri);
                            if (Build.VERSION.SDK_INT >= 30) {
                                pendingIntent = MediaStore.createDeleteRequest(C2974b.this.getActivity().getContentResolver(), arrayList);
                                C2974b.this.resultLauncher.launch(new IntentSenderRequest.Builder(pendingIntent).build());
                            } else {
                                pendingIntent = null;
                            }
                            try {
                                C2974b.this.getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(), C2974b.DELETE_REQUEST_CODE, null, 0, 0, 0, null);
                            } catch (Exception unused) {
                            }
                        }
                    });
                }
            });
        }
        return inflate;
    }
}
