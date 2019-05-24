package com.hfad.workout;

/**
 * Created by fsv on 13.06.2018.
 */

public class Workout {
    private String name;
    private String description;

    public static Workout[] workouts = {

            new Workout("The Limb Loosener", "5 Handstand push-ups\n10 1-leggend squats\n15 Pull-ups"),
            new Workout("Core Agony", "100 Push-ups\n100 1-leggend squats\n100 Pull-ups"),
            new Workout("The Wimp Special", "5 Push-ups\n10 1-leggend squats\n15 Pull-ups"),
            new Workout("Strenght and Lenght", "500 meters run\n21 x 1.5 pood kettleball swing\n21 x Pull-ups"),


    };


    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}
