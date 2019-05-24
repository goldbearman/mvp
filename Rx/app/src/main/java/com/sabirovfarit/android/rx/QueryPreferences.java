package com.sabirovfarit.android.rx;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {

    public static final String PREF_COLOR = "pref color";
    public static final String PREF_NAME = "pref name";
    public static final String PREF_EMAIL = "pref email";
    public static final String PREF_CLASS = "pref class";
    public static final String PREF_THEME = "pref theme";
    public static final String PREF_PHOTO = "pref photo";

    public static int getColorButton(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_COLOR, -256);
    }

    public static void setColorButton(Context context,int color) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_COLOR,color)
                .apply();
    }

    public static String getClass(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_CLASS, null);
    }

    public static void setClass(Context context,String s) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_CLASS,s)
                .apply();
    }


    public static String getName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_NAME, null);
    }

    public static void setName(Context context,String s) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_NAME,s)
                .apply();
    }

    public static String getEmail(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_EMAIL, null);
    }

    public static void setEmail(Context context,String s) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_EMAIL,s)
                .apply();
    }
    public static int getLastTheme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(PREF_THEME, R.style.GreenTheme);
    }

    public static void setLastTheme(Context context,int theme) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_THEME,theme)
                .apply();
    }

//    public static String getPhoto(Context context) {
//        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_PHOTO, null);
//    }
//
//    public static void setPhoto(Context context,String s) {
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .edit()
//                .putString(PREF_PHOTO,s)
//                .apply();
//    }


}
