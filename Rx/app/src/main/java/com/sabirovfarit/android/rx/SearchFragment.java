package com.sabirovfarit.android.rx;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    public static final String ADD_SELECTED_BOTTOM_FRAGMENT = "add_selected_bottom_fragment";

    Context context;
    RecyclerView recyclerView;
    TextView tvAddInMyList;
    TextView tvSelectAll;
    TextView tvCancelAll;
    private View view;
    private View toastView;
    WordViewModel viewModel;
    List<Word> list = new ArrayList<>();


    WordsListSearchAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.search_recycle);
        tvAddInMyList = view.findViewById(R.id.add_in_my_list);
        tvSelectAll = view.findViewById(R.id.tv_select_all);
        tvCancelAll = view.findViewById(R.id.tv_cancel_all);

        updateUI();

        RxView.clicks(tvAddInMyList).subscribe(o -> AddInMyList());
        RxView.clicks(tvSelectAll).subscribe(o -> selectCheckBoxBooleanSearch(true));
        RxView.clicks(tvCancelAll).subscribe(o -> selectCheckBoxBooleanSearch(false));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search,menu);
        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setQuery(newText.toLowerCase().trim());
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void updateUI() {

        viewModel.getData().observe(this, words -> {
            adapter = new WordsListSearchAdapter(words);
            recyclerView.setAdapter(adapter);
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
    }

    private void selectCheckBoxBooleanSearch(Boolean b) {
        adapter.selectCheckBoxBoolean(b);
        adapter.notifyDataSetChanged();
    }

    private void AddInMyList() {

        List<Long> idsList = new ArrayList<>();
        List<Word> list = adapter.getList();
        Log.i(TAG, "list.size(): " + list.size());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isInList()) {
                idsList.add(list.get(i).getId());
            }
        }
        Log.i(TAG, "AddInMyList: " + idsList.size());
        if (idsList.size() == 0) {
            ToastInfomation.showToast(getActivity(),"Не выбрано ни одно слово!");

        } else {
            viewModel.setIdsList(idsList);
            AddSelectedBottomFragment.newInstance().show(getActivity().getSupportFragmentManager(), ADD_SELECTED_BOTTOM_FRAGMENT);
        }
    }
}

