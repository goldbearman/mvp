package com.sabirovfarit.android.rx.LearningWords;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sabirovfarit.android.rx.App;
import com.sabirovfarit.android.rx.R;
import com.sabirovfarit.android.rx.Word;
import com.sabirovfarit.android.rx.WordsList;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllWordsLearningWordsFragment extends Fragment {

    private static final String TAG = "AllWordsLearningWordsFr";


    public static final String ALL_WORDS_LEARNING_WORDS_FRAGMENT_ARGS = "AllWordsLearningWordsFragment args";
    @BindView(R.id.rv_all_words)
    RecyclerView rvAllWords;
    @BindView(R.id.tv_all_words)
    TextView tvAllWords;
    Unbinder unbinder;
    long idWordList;
    List<Word> wordsById;
    AllWordsAdapter adapter;

    //Передаем через intent Id нажатого списка
    public static AllWordsLearningWordsFragment newInstance(long idWordList) {

        Bundle args = new Bundle();
        args.putLong(ALL_WORDS_LEARNING_WORDS_FRAGMENT_ARGS, idWordList);

        AllWordsLearningWordsFragment fragment = new AllWordsLearningWordsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AllWordsLearningWordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_words_learning_words, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();
        idWordList = args.getLong(ALL_WORDS_LEARNING_WORDS_FRAGMENT_ARGS);

//        Executors.newFixedThreadPool(5).execute(() -> {
//
//            //Получаем список содержимое колонки listId по id
//            List<WordsList> idsFromListId = App.getInstance().getDatabase().wordsListDao().getIdsFromListId(idWordList);
//            //Получаем поле listId
//            List<Long> listId = idsFromListId.get(0).getListId();
//            //По полученному списку id idsFromListId получаем список Word
//            wordsById = App.getInstance().getDatabase().wordDao().getWordsById(listId);
//
//        });

//        Flowable.just(getWordListsById(idWordList))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(words -> {
//                            Log.i(TAG, "fillList: " + words.size());
//                            adapter = new AllWordsAdapter(words);
//                            rvAllWords.setAdapter(adapter);
//                        }
//                );

        App.getInstance().getDatabase().wordsListDao().getFlowableIdsFromListId(idWordList)
                .observeOn(Schedulers.io())
                .map(wordsList -> {
                    List<Long> listId = wordsList.getListId();
                    List<Word> wordsById = App.getInstance().getDatabase().wordDao().getWordsById(listId);
                    return wordsById;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Word>>() {
                    @Override
                    public void accept(List<Word> words) throws Exception {
                        adapter = new AllWordsAdapter(words);
                        rvAllWords.setAdapter(adapter);
                    }
                });
        rvAllWords.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    private List<Word> getWordListsById(long id) {
//        //Получаем список содержимое колонки listId по id
//        List<WordsList> idsFromListId = App.getInstance().getDatabase().wordsListDao().getIdsFromListId(id);
//        //Получаем поле listId
//        List<Long> listId = idsFromListId.get(0).getListId();
//        //По полученному списку id idsFromListId получаем список Word
//        wordsById = App.getInstance().getDatabase().wordDao().getWordsById(listId);
//        return wordsById;
//    }
}
