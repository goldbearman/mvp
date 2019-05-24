package com.hfad.aplicationmanageraplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private List<File> files = new ArrayList<>();
    private static final int TYPE_DIRECTORY = 0;
    private static final int TYPE_FILE = 1;
    private OnFileClickListener mOnFileClickListener;

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public interface OnFileClickListener {
        void onFileClick(File file);
    }

    public void setOnFileClickListener(OnFileClickListener onFileClickListener) {
        mOnFileClickListener = onFileClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == TYPE_DIRECTORY) {
            view = layoutInflater.inflate(R.layout.view_item_directory, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.view_item_file, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = files.get(position);

        holder.nameTv.setText(file.getName());
        holder.itemView.setTag(file);
    }


    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public int getItemViewType(int position) {
        File file = files.get(position);
        if (file.isDirectory()) {
            return TYPE_DIRECTORY;
        } else {
            return TYPE_FILE;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTv;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File file = (File) view.getTag();
                    if (mOnFileClickListener!=null) {
                    mOnFileClickListener.onFileClick(file);
                    }
                }
            });
        }

    }
}