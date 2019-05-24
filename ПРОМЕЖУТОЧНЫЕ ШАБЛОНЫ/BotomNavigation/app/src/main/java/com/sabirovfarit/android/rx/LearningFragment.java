package com.sabirovfarit.android.rx;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearningFragment extends Fragment {


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning, container, false);
    }

}
