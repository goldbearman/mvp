package com.hfad.aplicationmanageraplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> {

    private List<AppInfo> mAppInfos;
    private Context mContext;
    private List<AppInfo> mFilteredApps = new ArrayList<>();
    private String query = "";

    public void setAppInfos(Context context, List<AppInfo> appInfos) {
        mContext = context;
        mAppInfos = appInfos;

        filterApps();
    }


    private void filterApps() {
        mFilteredApps.clear();
        if (query.isEmpty()) {
            mFilteredApps.addAll(mAppInfos);
        } else {
            for (AppInfo app : mAppInfos) {
                if (app.getName().toLowerCase().contains(query)) {
                    mFilteredApps.add(app);
                }
            }
        }
    }

    public void setQuery(String query) {
        this.query = query;

        filterApps();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_item_app,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(mFilteredApps.get(position).getName());
        holder.versionName.setText(mFilteredApps.get(position).getVersionName());
        holder.icon.setImageDrawable(mFilteredApps.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return mFilteredApps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView versionName;
        private TextView name;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            versionName = itemView.findViewById(R.id.version_tv);
            name = itemView.findViewById(R.id.name_tv);
            icon = itemView.findViewById(R.id.icon_iv);
        }
    }



}
