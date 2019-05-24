package com.hfad.workout;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    /// МЕТОД В ЛИСТФРАГМЕНТЕ, ИЗ КОТОРОГО МЫ ПОЛУЧАЕМ ID
    @Override
    public void itemClicked(long id) {
        ////  НА БОЛЬШОМ ЭКРАНЕ ИЩЕМ КОНТЕЙНЕР  ///
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            /// СОЗДАЕМ ОБЪЕКТ ФРАГМЕНТА ///
            WorkoutDetaleFragment details = new WorkoutDetaleFragment();
            //// ПОЛУЧАЕМ ТРАНЗАКЦИЮ ////
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            details.setWorkout(id);
            /// ЗАМЕНЯЕМ ФРАГМЕНТЫ (КУДА ПОМЕЩАЕМ,ЧТО ПОМЕЩАЕМ)/////
            ft.replace(R.id.fragment_container, details);
            ///// ДЛЯ ОПРЕДЕЛЕНИЯ ПЕРЕХОДНОЙ АНИМАЦИИ  //////
            //// TRANSIT_FRAGMENT_FADE - ФРАГМЕНТ РАСТВОРЯЕТСЯ И ПРОЯВЛЯЕТСЯ ////////
            //// TRANSIT_NONE - анимация отсутствует /////////////////
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            //// ДОБАВЛЯЕМ В БАКСТЕК ЧТОБЫ МОЖНО БЫЛО ПОЛЬЗОВАТЬС КНОПКОЙ НАЗАД //////
            ft.addToBackStack(null);
            /// ВЫПОЛНЯЕМ ///
            ft.commit();
/// ЕСЛИ ЭКРАН МАЛЕНЬКИЙ И НЕТ КОНТЕЙНЕРА, ПЕРЕХОДИМ НА ДРУГОЙ ЭКРАН ///
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORCKOUT_ID, (int) id);
            startActivity(intent);
        }
    }
}
