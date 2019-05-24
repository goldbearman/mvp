package com.sabirovfarit.android.rx;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sabirovfarit.android.rx.LearningWords.LearningWordsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearningFragment extends Fragment implements WordsListLearnAdapter.ListnerAllWords {

    RecyclerView rvMyList;
    RecyclerView rvListClass;
    WordViewModel viewModel;
    WordsListLearnAdapter adapterMyList;


    public LearningFragment() {
        // Required empty public constructor
    }

    public static LearningFragment newInstance() {

        Bundle args = new Bundle();

        LearningFragment fragment = new LearningFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learning, container, false);
        rvMyList = view.findViewById(R.id.rvMyList);
        rvListClass = view.findViewById(R.id.rvListClass);

        updateListClass();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMyList();
    }

    @Override
    public void onStop() {
        adapterMyList.setListnerAllWords(null);
        super.onStop();
    }

    private void updateListClass() {
        viewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);
        viewModel.getInClassTrue().observe(this, wordsLists -> {

            WordsListLearnAdapter adapter = new WordsListLearnAdapter(wordsLists);
            rvListClass.setAdapter(adapter);
        });

        GridLayoutManager gridlayoutManager = new GridLayoutManager(getActivity(), 3);
        rvListClass.setLayoutManager(gridlayoutManager);
    }

    private void updateMyList() {
        viewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);
        viewModel.getInClassFalse().observe(this, wordsLists -> {

            adapterMyList = new WordsListLearnAdapter(wordsLists);
            adapterMyList.setListnerAllWords(this);
            rvMyList.setAdapter(adapterMyList);

        });

        GridLayoutManager gridlayoutManager = new GridLayoutManager(getActivity(), 3);
        rvMyList.setLayoutManager(gridlayoutManager);
    }


    @Override
    public void onClick(long idWordList) {
        Intent intent = LearningWordsActivity.newIntent(getActivity(), idWordList);
        startActivity(intent);
    }
}
