package com.hfad.workout;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetaleFragment extends Fragment {

    private long workoutId;
    public static final String ID = "id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
        workoutId = savedInstanceState.getLong(ID);
        }
        ///   ЕСЛИ ФРАГМЕНТ СОЗДАН, ОН НЕ ДОЛЖЕН ПЕРЕСОЗДАВАТЬСЯ ///
        ///   ЭТО ВЕСЬ КОД ВСТАВКИ ФРАГМЕНТА  ///
        if (savedInstanceState == null) {
            StopFragment stopFragment = new StopFragment();
            ////  getChildFragmentManager() ЧТОБЫ НЕ ДОБАВЛЯЛСЯ В СТЕК ОТДЕЛЬНО ОТ ВСЕГО ФРАГМЕНТА  ///
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.stopwatch_container, stopFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_detale, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        /////////// ПОЛУЧАЕМ ССЫЛКУ НА КОРНЕВОЕ ПРЕДСТАВЛЕНИЕ ФРАГМЕНТА  ///////////////
        View view = getView();
        if (view != null) {
            ///////// ПО ССЫЛКЕ ИЩЕМ НУЖНОЕ ПРЕДСТАВЛЕНИЕ //////
            TextView title = view.findViewById(R.id.textTitle);
            TextView description = view.findViewById(R.id.textDescription);
            Workout workout = Workout.workouts[(int) workoutId];
            title.setText(workout.getName());
            description.setText(workout.getDescription());
        }
    }
    public void setWorkout(long workoutId) {
        this.workoutId = workoutId;
    }
    @Override
    public void onSaveInstanceState( Bundle outState) {
        outState.putLong(ID, workoutId);
        super.onSaveInstanceState(outState);
    }
}
