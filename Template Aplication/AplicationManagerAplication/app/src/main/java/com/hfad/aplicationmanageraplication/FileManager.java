package com.hfad.aplicationmanageraplication;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    private static final String TAG = "Myt";
    private File currentDirectory;
    private File rootDirectory;
    String a = Environment.getExternalStorageState();

   //Получаем директронию, выше которой нельзя подниматься
    public FileManager(Context context) {
        File directory;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directory = Environment.getExternalStorageDirectory();
        } else {
            directory = ContextCompat.getDataDir(context);
        }
        rootDirectory = directory;
        navigateTo(directory);
    }

    public boolean navigateTo(File directory) {
        // Проверим, является ли файл директорией
        if (!directory.isDirectory()) {
            Log.e(TAG, directory.getAbsolutePath() + " is not a directory!");
            return false;
        }

        // Проверим, не поднялись ли мы выше rootDirectory
        if (!directory.equals(rootDirectory) &&
                rootDirectory.getAbsolutePath().contains(directory.getAbsolutePath())) {
            Log.w(TAG, "Trying to navigate upper than root directory to " + directory.getAbsolutePath());
            return false;
        }

        currentDirectory = directory;

        return true;
    }
    //Метод для навигации в директорию, которая находится выше. Пользователь будет попадать в неё, нажимая кнопку назад
    public boolean navigateUp() {
        return navigateTo(currentDirectory.getParentFile());
    }
    //Метод, с помощью которого мы будем получать список файлов в текущей директории:
    public List<File> getFiles() {
        List<File> files = new ArrayList<>();
        //Делаем список из массива
        files.addAll(Arrays.asList(currentDirectory.listFiles()));

        return files;
    }
}
