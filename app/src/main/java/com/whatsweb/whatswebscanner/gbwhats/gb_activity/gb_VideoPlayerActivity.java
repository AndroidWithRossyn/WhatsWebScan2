package com.whatsweb.whatswebscanner.gbwhats.gb_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.pesonal.adsdk.AppManage;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_AllKeyHub;
import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces.onInterstitialAdsClose;

public class gb_VideoPlayerActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        AppManage.getInstance(gb_VideoPlayerActivity.this).showInterstitialAd(gb_VideoPlayerActivity.this, new AppManage.MyCallback() {
            public void callbackCall() {
                gb_VideoPlayerActivity.super.onBackPressed();
            }
        }, "", AppManage.app_mainClickCntSwAd);
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gb_activity_video_player);
        AppManage.getInstance(gb_VideoPlayerActivity.this).loadInterstitialAd(this);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        String stringExtra = getIntent().getStringExtra("PathVideo");
        ((ImageView) findViewById(R.id.imBack)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                gb_VideoPlayerActivity.this.onBackPressed();
            }
        });

        try {
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            Uri parse = Uri.parse(stringExtra);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(parse);
            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {

                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mediaPlayer) {
                    gb_VideoPlayerActivity.this.finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}