package com.whatsweb.whatswebscanner.gbwhats.gb_adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_DetailActivity;
import com.whatsweb.whatswebscanner.gbwhats.gb_customview.CircleImage;

import java.util.ArrayList;

public class StatusMainRecylerviewAdapter extends RecyclerView.Adapter<StatusMainRecylerviewAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<String> stringArrayList = new ArrayList<>();

    public StatusMainRecylerviewAdapter(Activity activity2, ArrayList<String> arrayList) {
        this.activity = activity2;
        this.stringArrayList = arrayList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gb_statuscircular_img, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (this.stringArrayList.get(i).startsWith("content")) {
            Glide.with(this.activity).load(this.stringArrayList.get(i)).into(viewHolder.imageView);
        } else {
            RequestManager with = Glide.with(this.activity);
            with.load("file://" + this.stringArrayList.get(i)).into(viewHolder.imageView);
        }
        if (this.stringArrayList.get(i).startsWith("content")) {
            this.stringArrayList.get(i);
        } else {
            this.stringArrayList.get(i);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new gb_DetailActivity().setdata(StatusMainRecylerviewAdapter.this.stringArrayList);
                Intent intent = new Intent(StatusMainRecylerviewAdapter.this.activity, gb_DetailActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("stringvalue", StatusMainRecylerviewAdapter.this.stringArrayList);
                intent.putExtra("Is_From", "true");
                intent.putExtra("adapter", true);
                StatusMainRecylerviewAdapter.this.activity.startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        try {
            return this.stringArrayList.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImage imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (CircleImage) view.findViewById(R.id.imageView);
        }
    }
}
