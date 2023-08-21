package com.whatsweb.whatswebscanner.gbwhats.gb_local;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.whatsweb.whatswebscanner.gbwhats.R;

public class RatingDialogBuilder {
    Dialog mDialog;
    ImageView[] mStar = new ImageView[5];
    int rate = 0;
    LinearLayout tvMayBeLater;
    TextView tvSubmit;
    ReviewManager manager;
    ReviewInfo reviewInfo;

    public interface Callback {
        void onDialogDismiss();
    }

    public RatingDialogBuilder(Context context, Callback callback) {
        Dialog dialog = new Dialog(context, R.style.CustomDialog);
        this.mDialog = dialog;
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(R.color.gb_semi_transparent);
        this.mDialog.setContentView(R.layout.gb_dialog_custom_rating);
        this.tvSubmit = (TextView) this.mDialog.findViewById(R.id.tv_submit);
        this.tvMayBeLater = (LinearLayout) this.mDialog.findViewById(R.id.tv_maybe_later);
        this.mStar[0] = (ImageView) this.mDialog.findViewById(R.id.rate1);
        this.mStar[1] = (ImageView) this.mDialog.findViewById(R.id.rate2);
        this.mStar[2] = (ImageView) this.mDialog.findViewById(R.id.rate3);
        this.mStar[3] = (ImageView) this.mDialog.findViewById(R.id.rate4);
        this.mStar[4] = (ImageView) this.mDialog.findViewById(R.id.rate5);

        manager = ReviewManagerFactory.create(context);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(tasks -> {
            if (tasks.isSuccessful()) {
                // We can get the ReviewInfo object
                reviewInfo = tasks.getResult();
            } /*else {
                Log.d("Rating_Dialog"," error is -> "+tasks.getException().toString());
                Log.d("Rating_Dialog"," error Msg is -> "+tasks.getException().getMessage());
            }*/
        });


//        if (frameAds.getChildCount() == 0)
//            this.frameAds.addView(LayoutInflater.from(context).inflate(R.layout.gb_shimmer_container_native_ads_playlist, null));
//        final NativeAdView nativeAdLayout = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.gb_custom_native_ads_playlist, null);
//        Admod.getInstance().loadVideoNativeAd(context, nativeAdLayout, frameAds);


        this.tvMayBeLater.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RatingDialogBuilder.this.lambda$new$0$RatingDialogBuilder(callback, context, view);
            }
        });
        this.tvSubmit.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RatingDialogBuilder.this.lambda$new$1$RatingDialogBuilder(context, callback, view);
            }
        });
        OnClickListener r1 = new OnClickListener() {
            public final void onClick(View view) {
                RatingDialogBuilder.this.lambda$new$2$RatingDialogBuilder(context, callback, view);
            }
        };
        this.mStar[0].setOnClickListener(r1);
        this.mStar[1].setOnClickListener(r1);
        this.mStar[2].setOnClickListener(r1);
        this.mStar[3].setOnClickListener(r1);
        this.mStar[4].setOnClickListener(r1);
    }

    public void lambda$new$0$RatingDialogBuilder(Callback callback, Context context, View view) {
        this.mDialog.dismiss();
    }

    public void lambda$new$1$RatingDialogBuilder(Context context, Callback callback, View view) {
        this.mDialog.dismiss();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName()));
        intent.setFlags(268468224);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context, "You don't have Google Play installed", 1).show();
        }

    }

    public void lambda$new$2$RatingDialogBuilder(Context context, Callback callback, View view) {
        switch (view.getId()) {
            case R.id.rate1:
                this.rate = 1;
                break;
            case R.id.rate2:
                this.rate = 2;
                break;
            case R.id.rate3:
                this.rate = 3;
                break;
            case R.id.rate4:
                this.rate = 4;
                break;
            case R.id.rate5:
                this.rate = 5;
                break;
        }
        for (ImageView imageView : mStar) {
            imageView.setBackgroundResource(R.drawable.gb_ic_star_inactive);
        }
        for (int i = 0; i < rate; i++) {
            mStar[i].setBackgroundResource(R.drawable.gb_ic_star_active);
        }
        setContentRate(this.rate, context, callback);
    }

    private void setContentRate(int i, Context context, Callback callback) {

        this.tvSubmit.setVisibility(View.VISIBLE);
        this.tvMayBeLater.setVisibility(View.GONE);

    }

    public Dialog build() {
        return this.mDialog;
    }
}
