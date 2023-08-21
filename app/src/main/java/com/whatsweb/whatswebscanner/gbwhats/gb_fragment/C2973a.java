package com.whatsweb.whatswebscanner.gbwhats.gb_fragment;

import android.app.PendingIntent;
import android.content.Intent;
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

public class C2973a extends Fragment implements AdapterCallBackInterface {
    public static int DELETE_REQUEST_CODE = 1000;
    private GridView gridView;
    private TextView noData;
    ArrayList<Uri> removableList = new ArrayList<>();
    public String removableLists = new String();
    ActivityResultLauncher<IntentSenderRequest> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new C2973a$$ExternalSyntheticLambda0(this));
    private StatusDataAdapter statusDataAdapter;

    public void lambda$new$0$C2973a(ActivityResult activityResult) {
        Log.i("TAG", "onActivityResult: ");
        if (activityResult == null || activityResult.getResultCode() != -1) {
            Log.i("TAG", "onActivityResult: can not delete");
            return;
        }
        for (int i = 0; i < Constant.savedImagesArraylist.size(); i++) {
            if (Constant.savedImagesArraylist.get(i).equalsIgnoreCase(this.removableLists)) {
                Constant.savedImagesArraylist.remove(i);
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
        StatusDataAdapter statusDataAdapter2 = new StatusDataAdapter(this, getActivity(), Constant.savedImagesArraylist, 1, true);
        this.statusDataAdapter = statusDataAdapter2;
        this.gridView.setAdapter((ListAdapter) statusDataAdapter2);
        if (Constant.savedImagesArraylist == null || Constant.savedImagesArraylist.isEmpty()) {
            if (Constant.savedImagesArraylist.size() == 0) {
                this.noData.setVisibility(View.VISIBLE);
            } else {
                this.noData.setVisibility(View.GONE);
                this.statusDataAdapter.setOnClickListene(new StatusDataAdapter.onClickListene() {
                    /* class com.whatsweb.whatswebscanner.gbwhats.fragment.C2973a.AnonymousClass2 */

                    @Override // com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.onClickListene
                    public void onClick(String str) {
                        C2973a.this.removableList.add(Uri.parse(str));
                        C2973a.this.removableLists = str;
                        final ArrayList arrayList = new ArrayList();
                        MediaScannerConnection.scanFile(C2973a.this.getActivity(), new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() {
                            /* class com.whatsweb.whatswebscanner.gbwhats.fragment.C2973a.AnonymousClass2.AnonymousClass1 */

                            public void onScanCompleted(String str, Uri uri) {
                                PendingIntent pendingIntent;
                                arrayList.add(uri);
                                if (Build.VERSION.SDK_INT >= 30) {
                                    pendingIntent = MediaStore.createDeleteRequest(C2973a.this.getActivity().getContentResolver(), arrayList);
                                    C2973a.this.resultLauncher.launch(new IntentSenderRequest.Builder(pendingIntent).build());
                                } else {
                                    pendingIntent = null;
                                }
                                try {
                                    C2973a.this.getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(), C2973a.DELETE_REQUEST_CODE, null, 0, 0, 0, null);
                                } catch (Exception unused) {
                                }
                            }
                        });
                    }
                });
            }
        } else if (Constant.savedImagesArraylist.size() == 0) {
            this.noData.setVisibility(View.VISIBLE);
        } else {
            this.noData.setVisibility(View.GONE);
            this.statusDataAdapter.setOnClickListene(new StatusDataAdapter.onClickListene() {
                /* class com.whatsweb.whatswebscanner.gbwhats.fragment.C2973a.AnonymousClass1 */

                @Override // com.whatsweb.whatswebscanner.gbwhats.adapter.StatusDataAdapter.onClickListene
                public void onClick(String str) {
                    C2973a.this.removableList.add(Uri.parse(str));
                    C2973a.this.removableLists = str;
                    final ArrayList arrayList = new ArrayList();
                    MediaScannerConnection.scanFile(C2973a.this.getActivity(), new String[]{str}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        /* class com.whatsweb.whatswebscanner.gbwhats.fragment.C2973a.AnonymousClass1.AnonymousClass1 */

                        public void onScanCompleted(String str, Uri uri) {
                            PendingIntent pendingIntent;
                            arrayList.add(uri);
                            if (Build.VERSION.SDK_INT >= 30) {
                                pendingIntent = MediaStore.createDeleteRequest(C2973a.this.getActivity().getContentResolver(), arrayList);
                                C2973a.this.resultLauncher.launch(new IntentSenderRequest.Builder(pendingIntent).build());
                            } else {
                                pendingIntent = null;
                            }
                            try {
                                C2973a.this.getActivity().startIntentSenderForResult(pendingIntent.getIntentSender(), C2973a.DELETE_REQUEST_CODE, null, 0, 0, 0, null);
                            } catch (Exception unused) {
                            }
                        }
                    });
                }
            });
        }
        return inflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == DELETE_REQUEST_CODE && i2 == -1) {
            for (int i3 = 0; i3 <= Constant.savedImagesArraylist.size(); i3++) {
                if (Constant.savedImagesArraylist.get(i3).equalsIgnoreCase(this.removableLists)) {
                    Constant.savedImagesArraylist.remove(i3);
                    StatusDataAdapter statusDataAdapter2 = new StatusDataAdapter(this, getActivity(), Constant.savedImagesArraylist, 1, true);
                    this.statusDataAdapter = statusDataAdapter2;
                    this.gridView.setAdapter((ListAdapter) statusDataAdapter2);
                }
            }
        }
    }

    @Override // com.whatsweb.whatswebscanner.gbwhats.whatsapp_interFace.AdapterCallBackInterface
    public void ondeleteclick(String str) {
        Log.i("TAG", "removableList : " + this.removableLists);
        try {
            startIntentSenderForResult((Build.VERSION.SDK_INT >= 30 ? MediaStore.createDeleteRequest(getActivity().getContentResolver(), new ArrayList()) : null).getIntentSender(), DELETE_REQUEST_CODE, null, 0, 0, 0, null);
        } catch (Exception unused) {
        }
    }
}
