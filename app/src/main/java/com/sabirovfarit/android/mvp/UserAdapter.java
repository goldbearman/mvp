package com.sabirovfarit.android.mvp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sabirovfarit.android.mvp.DB.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserAdapterHolder> {

    private List<User> mUsers;

    public UserAdapter(List<User> users) {
        mUsers = users;
    }

    @NonNull
    @Override
    public UserAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_user_adapter_layout, parent, false);
        return new UserAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void setData(List<User> users) {
       this.mUsers = users;
    }

    public class UserAdapterHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvEmail;
        TextView tvId;

        public UserAdapterHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvId = itemView.findViewById(R.id.tv_id);
        }

        public void bind(int position) {
            User user = mUsers.get(position);
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvId.setText(String.valueOf(user.getId()));
        }
    }
}
