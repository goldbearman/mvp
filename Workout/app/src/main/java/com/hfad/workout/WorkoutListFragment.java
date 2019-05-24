package com.hfad.workout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */    ///////// В ListFragment используется ListView /////
public class WorkoutListFragment extends ListFragment {


    public WorkoutListFragment() {
        // Required empty public constructor
    }

    static interface Listener{
        void itemClicked(long id);
    }

    private Listener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /////////////// СОЗДАЕМ АДАПТЕР (ОБЪЕКТЫ WorkOUT выводит toString)//////////////////
        ArrayAdapter<Workout> adapter = new ArrayAdapter<Workout>(inflater.getContext(), android.R.layout.simple_list_item_1, Workout.workouts);
        /////////////СВЯЗЫВАЕМСЯ С LISTVIEW , ПРИСВАИВАЕМ АДАПТЕР   ///////////
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    ///////  ЛИСТЕНЕР СВЯЗЫВАЕТСЯ С АКТИВНСТЬЮ В МЕТОДЕ СВЯЗКИ ФРАГМЕНТА С MAIN АКТИВНОСТЬЮ, ЕСЛИ ОНА ПОДПИСАНА НА НЕГО ////////
    public void onAttach(Context context) {    /// МЕТОД ВЫЗЫВАЕТСЯ ПРИ СВЯЗЫВАНИИ ФРАГМЕНТА С КОНТЕКСТОМ(АКТИВНОСТЬЮТ)
        super.onAttach(context);
        this.listener = (Listener) context;    /// ПОЛУЧАЕМ ССЫЛКУ НА АКТИНОСТЬ
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ///////////////  ЕСЛИ ЕСТЬ АКТИВНОСТЬ, ПЕРЕДАЕТСЯ ID ПРИ НАЖАТИИ НА ОДИН ИЗ ПУНКТ ЛИСТВЬЮ ////////////
        if (listener != null) {
            listener.itemClicked(id);
        }
    }
}
