package com.hfad.bitsandpizzas;

/**
 * Created by fsv on 26.06.2018.
 */

public class Pizza {
    private String name;
    private int imageResourceId;

    public static final Pizza[] pizza = {
            new Pizza("Diavolo", R.drawable.diavolo),
            new Pizza("Funghi", R.drawable.funghi)
    };


    public Pizza(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
