package com.whatsweb.whatswebscanner.gbwhats.gb_adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_GalleryActivity;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_GalleryDetailActivity;
import com.whatsweb.whatswebscanner.gbwhats.gb_customview.SquareImageView;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private String type;

    public GalleryAdapter(Activity activity2, ArrayList<String> arrayList, String str) {
        this.activity = activity2;
        this.stringArrayList = arrayList;
        this.type = str;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gb_item_gallery, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (this.type.equalsIgnoreCase("audio")) {
            viewHolder.imageView.setImageResource(R.drawable.gb_ic_audio);
            viewHolder.textView.setText(gb_GalleryActivity.stringArrayListname.get(i));
        } else {
            viewHolder.textView.setVisibility(View.GONE);
            if (this.stringArrayList.get(i).startsWith("content")) {
                Glide.with(this.activity).load(this.stringArrayList.get(i)).into(viewHolder.imageView);
            } else {
                RequestManager with = Glide.with(this.activity);
                with.load("file://" + this.stringArrayList.get(i)).into(viewHolder.imageView);
            }
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (GalleryAdapter.this.type.equalsIgnoreCase("images")) {
                    new gb_GalleryDetailActivity().setdata(GalleryAdapter.this.stringArrayList);
                    Intent intent = new Intent(GalleryAdapter.this.activity, gb_GalleryDetailActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("Is_From", "images");
                    GalleryAdapter.this.activity.startActivity(intent);
                    GalleryAdapter.this.activity.finish();
                } else if (GalleryAdapter.this.type.equalsIgnoreCase("video")) {
                    new gb_GalleryDetailActivity().setdata(GalleryAdapter.this.stringArrayList);
                    Intent intent2 = new Intent(GalleryAdapter.this.activity, gb_GalleryDetailActivity.class);
                    intent2.putExtra("position", i);
                    intent2.putExtra("Is_From", "video");
                    GalleryAdapter.this.activity.startActivity(intent2);
                    GalleryAdapter.this.activity.finish();
                } else if (GalleryAdapter.this.type.equalsIgnoreCase("audio")) {
                    new gb_GalleryDetailActivity().setdata(GalleryAdapter.this.stringArrayList);
                    Intent intent3 = new Intent(GalleryAdapter.this.activity, gb_GalleryDetailActivity.class);
                    intent3.putExtra("position", i);
                    intent3.putExtra("Is_From", "audio");
                    GalleryAdapter.this.activity.startActivity(intent3);
                    GalleryAdapter.this.activity.finish();
                } else if (GalleryAdapter.this.type.equalsIgnoreCase("documents")) {
                    new gb_GalleryDetailActivity().setdata(GalleryAdapter.this.stringArrayList);
                    Intent intent4 = new Intent(GalleryAdapter.this.activity, gb_GalleryDetailActivity.class);
                    intent4.putExtra("position", i);
                    intent4.putExtra("Is_From", "document");
                    GalleryAdapter.this.activity.startActivity(intent4);
                    GalleryAdapter.this.activity.finish();
                }
            }
        });
    }

    public int getItemCount() {
        return this.stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SquareImageView imageView;
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (SquareImageView) view.findViewById(R.id.imageView);
            this.textView = (TextView) view.findViewById(R.id.textaudioname);
            DisplayMetrics displayMetrics = GalleryAdapter.this.activity.getResources().getDisplayMetrics();
            ViewGroup.LayoutParams layoutParams = this.imageView.getLayoutParams();
            layoutParams.width = displayMetrics.widthPixels / 3;
            layoutParams.height = displayMetrics.widthPixels / 3;
            this.imageView.setLayoutParams(layoutParams);
        }
    }
}
