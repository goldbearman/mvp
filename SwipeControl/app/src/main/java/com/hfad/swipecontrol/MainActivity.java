package com.hfad.swipecontrol;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.thesurix.gesturerecycler.GestureManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PlayersDataAdapter mAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Player> players;
    List<Player> searchPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPlayersDataAdapter();
        setupRecyclerView();

        SwipeController swipeController = new SwipeController(players,mAdapter,this);
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setPlayers(players);
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_my, menu);

        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

                mAdapter.setPlayers(setQuery(s));
                mAdapter.notifyDataSetChanged();
                return true;
            }
        });

        return true;
    }
    private List<Player> setQuery(String s) {
//        searchPlayers.clear();
        Log.d("my", s);
        searchPlayers = new ArrayList<>();
        if (s.isEmpty()) {
            Log.d("my", "Вошли");
            searchPlayers.addAll(players);
        } else {
            for (Player player : players) {
                if (player.getName().toLowerCase().contains(s)) {
                    searchPlayers.add(player);
                }
            }
        }
        Log.d("my", players.get(0).getName());
//        Log.d("my", searchPlayers.get(1).getName());
        return searchPlayers;
    }

    private void setPlayersDataAdapter() {
        players = new ArrayList<>();
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("players.csv"));

            BufferedReader reader = new BufferedReader(is);
            reader.readLine();
            String line;
            String[] st;
            while ((line = reader.readLine()) != null) {
                st = line.split(",");
                Player player = new Player();
                player.setName(st[0]);
                player.setNationality(st[1]);
                player.setClub(st[4]);
                player.setRating(Integer.parseInt(st[9]));
                player.setAge(Integer.parseInt(st[14]));
                players.add(player);
            }
        } catch (IOException e) {

        }
        mAdapter = new PlayersDataAdapter(players);
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mAdapter);
    }




}
