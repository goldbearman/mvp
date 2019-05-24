package com.hfad.swipecontrol;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fsv on 14.10.2018.
 */

class PlayersDataAdapter extends RecyclerView.Adapter<PlayersDataAdapter.PlayerViewHolder> {
    private List<Player> players;

    public PlayersDataAdapter(List<Player> players) {
        this.players = players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView name, nationality, club, rating, age;

        public PlayerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            nationality = (TextView) view.findViewById(R.id.nationality);
            club = (TextView) view.findViewById(R.id.club);
            rating = (TextView) view.findViewById(R.id.rating);
            age = (TextView) view.findViewById(R.id.age);
        }
    }


    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row, parent, false);

        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
//        Player player = players.get(position);
//        holder.name.setText(player.getName());
//        holder.nationality.setText(player.getNationality());
//        holder.club.setText(player.getClub());
//        holder.rating.setText(player.getRating().toString());
//        holder.age.setText(player.getAge().toString());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }





}
