package com.bignerdranch.criminalintent;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends android.support.v4.app.DialogFragment {

    public static final String PATH = "path";
    private String mPath;

    public static DialogFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString(PATH,path);

        DialogFragment fragment = new DialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        Bundle args = getArguments();
        mPath = args.getString(PATH);

        ImageView imageView = view.findViewById(R.id.dialog_photo);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        Bitmap bitmap = PictureUtils.getScaledBitmap(mPath, size.x,size.y);
        Log.i("myyy",mPath);
        imageView.setImageBitmap(bitmap);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS );
        return dialog;
    }
}
