package com.hfad.starbuzz;

/**
 * Created by fsv on 07.06.2018.
 */

public class Drink {

   private String name;
   private String deskription;
   private int imageResourceId;


    public static final Drink[] drinks = {

            new Drink("Late","A couple of espresso short with steames milk",R.drawable.latte),
            new Drink("Cappuccino","Expresso, hot milk, and stream milk foam",R.drawable.cappuccino),
            new Drink("Filter","Highest quality beans roasted and brewed fresh",R.drawable.filter),

    };


    public Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.deskription = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return deskription;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
