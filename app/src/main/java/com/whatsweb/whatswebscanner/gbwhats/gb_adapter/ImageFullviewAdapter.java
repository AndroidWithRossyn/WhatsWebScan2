package com.whatsweb.whatswebscanner.gbwhats.gb_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.whatsweb.whatswebscanner.gbwhats.R;

import java.io.File;
import java.util.ArrayList;

public class ImageFullviewAdapter extends PagerAdapter {
    private Context mContext;
    public ArrayList<String> stringArrayList;
    private String type;

    @Override
    public int getItemPosition(Object obj) {
        return -2;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ImageFullviewAdapter(Context context, ArrayList<String> arrayList, String str) {
        this.mContext = context;
        this.stringArrayList = arrayList;
        this.type = str;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        ViewGroup viewGroup2 = (ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.gb_viewpager, viewGroup, false);
        ImageView imageView = (ImageView) viewGroup2.findViewById(R.id.imgView);
        ImageView imageView2 = (ImageView) viewGroup2.findViewById(R.id.img_play);
        Log.d("TAG", "instantiateItem: " + this.type);
        if (this.type.equalsIgnoreCase("images")) {
            imageView2.setVisibility(View.GONE);
        } else if (this.type.equalsIgnoreCase("recent")) {
            imageView2.setVisibility(View.GONE);
        } else if (this.type.equalsIgnoreCase("video")) {
            imageView2.setVisibility(View.VISIBLE);
        } else if (this.type.equalsIgnoreCase("audio")) {
            imageView2.setVisibility(View.VISIBLE);
        } else {
            imageView2.setVisibility(View.GONE);
        }
        if (this.stringArrayList.get(i).startsWith("content")) {
            Glide.with(this.mContext).load(this.stringArrayList.get(i)).into(imageView);
        } else {
            RequestManager with = Glide.with(this.mContext);
            with.load("file://" + this.stringArrayList.get(i)).into(imageView);
        }
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri;
                Uri uri2;
                if (ImageFullviewAdapter.this.type.equalsIgnoreCase("video")) {
                    File file = new File(ImageFullviewAdapter.this.stringArrayList.get(i));
                    if (Build.VERSION.SDK_INT >= 29) {
                        uri2 = Uri.parse(ImageFullviewAdapter.this.stringArrayList.get(i));
                    } else if (Build.VERSION.SDK_INT >= 24) {
                        try {
                            Context context = ImageFullviewAdapter.this.mContext;
                            uri2 = FileProvider.getUriForFile(context, ImageFullviewAdapter.this.mContext.getPackageName() + ".provider", file);
                        } catch (Exception unused) {
                            uri2 = Uri.fromFile(file);
                        }
                    } else {
                        uri2 = Uri.fromFile(file);
                    }
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(uri2, "video/*");
                    intent.addFlags(268435456);
                    intent.addFlags(1);
                    ImageFullviewAdapter.this.mContext.startActivity(intent);
                } else if (ImageFullviewAdapter.this.type.equalsIgnoreCase("audio")) {
                    File file2 = new File(ImageFullviewAdapter.this.stringArrayList.get(i));
                    if (Build.VERSION.SDK_INT >= 29) {
                        uri = Uri.parse(ImageFullviewAdapter.this.stringArrayList.get(i));
                    } else if (Build.VERSION.SDK_INT >= 24) {
                        try {
                            Context context2 = ImageFullviewAdapter.this.mContext;
                            uri = FileProvider.getUriForFile(context2, ImageFullviewAdapter.this.mContext.getPackageName() + ".provider", file2);
                        } catch (Exception unused2) {
                            uri = Uri.fromFile(file2);
                        }
                    } else {
                        uri = Uri.fromFile(file2);
                    }
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.VIEW");
                    intent2.setDataAndType(uri, "audio/*");
                    intent2.addFlags(268435456);
                    intent2.addFlags(1);
                    ImageFullviewAdapter.this.mContext.startActivity(intent2);
                }
            }
        });
        viewGroup.addView(viewGroup2);
        return viewGroup2;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override
    public int getCount() {
        return this.stringArrayList.size();
    }
}
