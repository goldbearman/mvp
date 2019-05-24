package com.sabirovfarit.android.rx;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewListBottomFragment extends BottomSheetDialogFragment {

    private static final String TAG = "CreateNewListBottomFrag";

    Button btnChooseColor;
    EditText etNewListName;
    Button btnCreateList;
    int newColor;

    public static CreateNewListBottomFragment newInstance() {

        Bundle args = new Bundle();

        CreateNewListBottomFragment fragment = new CreateNewListBottomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CreateNewListBottomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_list, container, false);

        btnChooseColor = view.findViewById(R.id.btn_choose_color);
        etNewListName = view.findViewById(R.id.et_new_list_name);
        btnCreateList = view.findViewById(R.id.btn_create_list);

        int defaultColorButton = QueryPreferences.getColorButton(getActivity());
        assignColor(defaultColorButton);

        btnChooseColor.setOnClickListener(v -> setColorNewList());
        WordViewModel viewModel = ViewModelProviders.of(getActivity()).get(WordViewModel.class);
        Log.i(TAG, "onCreateView: " + viewModel.getIdsList().size());


        btnCreateList.setOnClickListener(v -> {

            Log.i(TAG, "onClick: " + "Вошли");
            String text = etNewListName.getText().toString();
            if (text.length() > 0) {
                Log.i(TAG, "onClick: " + "И сюда Вошли");
                Executors.newFixedThreadPool(5).execute(() -> {
                    //Получаем список id выбранных в SearchFragment элементов
                    List<Long> idsList = viewModel.getIdsList();
                    //Получаем word по id, которые мы выбрали в checkBox
                    List<Word> wordsById = App.getInstance().getDatabase().wordDao().getWordsById(idsList);
                    Log.i(TAG, "onCreateView: "+wordsById);
                    //Создаем новый список, копирую данные старых слов, чтобы убрать Id.
                    List<Word> wordsListWithoutId = new ArrayList<>();
                    for (int i = 0; i < wordsById.size(); i++) {
                        Word wordsWithoutId = new Word();
                        wordsWithoutId.setValue(wordsById.get(i).getValue());
                        wordsWithoutId.setDiffLetter(wordsById.get(i).getDiffLetter());
                        wordsWithoutId.setWordsClass(wordsById.get(i).getWordsClass());
                        wordsWithoutId.setInList(true);
                        wordsListWithoutId.add(wordsWithoutId);
                    }
                    //Вставляем новый список и получаем их id
                    List<Long> idInsertWords = App.getInstance().getDatabase().wordDao().addReturnIds(wordsListWithoutId);
                    Log.i(TAG, "onCreateView: "+idInsertWords);
                    //Создаем новую строчку в таблице
                    WordsList wordsList = new WordsList(text, newColor, idInsertWords, false, false);
                    App.getInstance().getDatabase().wordsListDao().add(wordsList);
                });



                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();


            } else {
                ToastInfomation.showToast(getActivity(), "Введите название");
            }
        });

        return view;
    }

    private void setColorNewList() {
        int currentBackgroundColor = getActivity().getResources().getColor(R.color.colorPrimary);
        ColorPickerDialogBuilder
                .with(getActivity())
                .setTitle("Выберите цвет сборки")
                .initialColor(currentBackgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {

                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                        assignColor(selectedColor);
                        QueryPreferences.setColorButton(getActivity(), selectedColor);

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void assignColor(int selectedColor) {
        Drawable drawable = getActivity().getResources().getDrawable(R.drawable.botton_shoose_color);
        drawable.setColorFilter(selectedColor, PorterDuff.Mode.MULTIPLY);
        btnChooseColor.setBackground(drawable);
        newColor = selectedColor;
    }

}
