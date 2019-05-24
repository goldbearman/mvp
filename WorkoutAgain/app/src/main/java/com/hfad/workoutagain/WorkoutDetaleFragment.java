package com.hfad.workoutagain;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetaleFragment extends Fragment {

    private long workoutId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_detale, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        //////////////// instead fvbi (in fragment no fvbi) //////////////
        View view = getView();
        if (view != null) {
            Workout workout = Workout.workouts[(int) workoutId];
            TextView title = view.findViewById(R.id.textTitle);
            TextView description = view.findViewById(R.id.textDescription);
            title.setText(workout.getName());
            description.setText(workout.getDescription());

        }
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }
}
