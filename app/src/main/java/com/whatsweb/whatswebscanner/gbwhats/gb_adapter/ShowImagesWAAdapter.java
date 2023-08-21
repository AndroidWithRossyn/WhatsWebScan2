package com.whatsweb.whatswebscanner.gbwhats.gb_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_activity.gb_VideoPlayerActivity;

import java.io.File;
import java.util.ArrayList;

public class ShowImagesWAAdapter extends PagerAdapter {
    
    private Context context;
    private ArrayList<File> fileList;
    private ArrayList<File> imageList;
    private LayoutInflater inflater;
    private ArrayList<Uri> uriList;


    public int getItemPosition(Object obj) {
        return -2;
    }


    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }


    public Parcelable saveState() {
        return null;
    }

    public ShowImagesWAAdapter(Context context2, ArrayList<File> arrayList, ArrayList<Uri> arrayList2) {
        this.context = context2;
        this.fileList = arrayList;
        this.uriList = arrayList2;
        this.inflater = LayoutInflater.from(context2);
    }


    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }


    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.inflater.inflate(R.layout.gb_slidingimages_layout, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.im_fullViewImage);
        ImageView imageView2 = (ImageView) inflate.findViewById(R.id.im_vpPlay);
        ImageView imageView3 = (ImageView) inflate.findViewById(R.id.im_share);
        ImageView imageView4 = (ImageView) inflate.findViewById(R.id.im_delete);
        if (Build.VERSION.SDK_INT > 29) {
            Glide.with(this.context).load(this.uriList.get(i)).into(imageView);
        } else {
            Glide.with(this.context).load(this.fileList.get(i).getPath()).into(imageView);
            String substring = this.fileList.get(i).getName().substring(this.fileList.get(i).getName().lastIndexOf("."));
            if (substring.equals(".mp4") || substring.equals(".mov")) {
                imageView2.setVisibility(View.VISIBLE);
            } else {
                imageView2.setVisibility(View.GONE);
            }
        }
        viewGroup.addView(inflate, 0);
        imageView2.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                if (Build.VERSION.SDK_INT > 29) {
                    Intent intent = new Intent(context, gb_VideoPlayerActivity.class);
                    intent.putExtra("PathVideo", new File(uriList.get(i).toString()).getPath());
                    context.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(context, gb_VideoPlayerActivity.class);
                intent2.putExtra("PathVideo", fileList.get(i).getPath());
                context.startActivity(intent2);
            }
        });
        return inflate;
    }

    public int getCount() {
        ArrayList arrayList = this.fileList;
        if (arrayList == null) {
            arrayList = this.uriList;
        }
        return arrayList.size();
    }


    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }
}
