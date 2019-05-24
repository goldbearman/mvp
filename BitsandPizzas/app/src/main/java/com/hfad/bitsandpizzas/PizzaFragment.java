package com.hfad.bitsandpizzas;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaFragment extends Fragment implements CaptionedImageAdapter.Listener{

    public PizzaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView pizzaRecycle = (RecyclerView) inflater.inflate(R.layout.fragment_pizza, container, false);

        String[] pizzaName = new String[Pizza.pizza.length];
        for (int i = 0; i < Pizza.pizza.length; i++) {
            pizzaName[i] = Pizza.pizza[i].getName();
        }
        int[] pizzaImage = new int[Pizza.pizza.length];
        for (int i = 0; i < Pizza.pizza.length; i++) {
            pizzaImage[i] = Pizza.pizza[i].getImageResourceId();
        }

        CaptionedImageAdapter adapter = new CaptionedImageAdapter(pizzaName, pizzaImage);
        pizzaRecycle.setAdapter(adapter);
        ////////  ВЫЗЫВАЕМ МЕНЕДЖЕР И ПРИСВАЕВАЕМ
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        pizzaRecycle.setLayoutManager(gridLayoutManager);

        adapter.setListener(new CaptionedImageAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.PIZZA_ID, position);
                getActivity().startActivity(intent);

            }
        });


        return pizzaRecycle;
    }

    @Override
    public void onClick(int position) {

    }
}
