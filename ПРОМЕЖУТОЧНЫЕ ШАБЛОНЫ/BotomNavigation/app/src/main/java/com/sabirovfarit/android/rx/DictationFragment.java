package com.sabirovfarit.android.rx;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class DictationFragment extends Fragment {


    public DictationFragment() {
        // Required empty public constructor
    }

    public static DictationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DictationFragment fragment = new DictationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictation, container, false);
    }

}
