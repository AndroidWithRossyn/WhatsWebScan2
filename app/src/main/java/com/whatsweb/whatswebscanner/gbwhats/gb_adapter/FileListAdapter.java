package com.whatsweb.whatswebscanner.gbwhats.gb_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whatsweb.whatswebscanner.gbwhats.R;
import com.whatsweb.whatswebscanner.gbwhats.gb_interFace.FileListClickInterface;

import java.io.File;
import java.util.ArrayList;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<File> fileArrayList;
    private FileListClickInterface fileListClickInterface;
    private LayoutInflater layoutInflater;

    public FileListAdapter(Context context2, ArrayList<File> arrayList, FileListClickInterface fileListClickInterface2) {
        this.context = context2;
        this.fileArrayList = arrayList;
        this.fileListClickInterface = fileListClickInterface2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        View inflatedView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gb_items_file_view, viewGroup, false);
        return new ViewHolder(inflatedView);
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final File file = this.fileArrayList.get(i);
        try {
            String substring = file.getName().substring(file.getName().lastIndexOf("."));
            if (!substring.equals(".mp4")) {
                if (!substring.equals(".mov")) {
                    if (substring.equals(".png") || substring.equals(".jpg") || substring.equals(".jpeg")) {
                        viewHolder.iv_play.setVisibility(View.GONE);
                    }
                    Glide.with(this.context).load(file.getPath()).into(viewHolder.pc);
                    viewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            FileListAdapter.this.fileListClickInterface.getPosition(i, file);
                        }
                    });
                }
            }
            Glide.with(this.context).load(file.getPath()).into(viewHolder.pc);
        } catch (Exception unused) {
        }
        viewHolder.rl_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FileListAdapter.this.fileListClickInterface.getPosition(i, file);
            }
        });
    }


    public int getItemCount() {
        return fileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_play;
        ImageView pc;
        RelativeLayout rl_main;

        public ViewHolder(View view) {
            super(view);
            iv_play = (ImageView) view.findViewById(R.id.iv_play);
            pc = (ImageView) view.findViewById(R.id.pc);
            rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        }
    }
}
