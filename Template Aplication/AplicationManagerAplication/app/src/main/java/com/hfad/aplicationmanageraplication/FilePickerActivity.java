package com.hfad.aplicationmanageraplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class FilePickerActivity extends AppCompatActivity {

    public static final int PERMISSION_REQUEST_CODE = 1;
    private FileManager fileManager;
    private FilesAdapter filesAdapter;
    public static final String EXTRA_FILE_PATH = "file_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);

        RecyclerView recyclerView = findViewById(R.id.files_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filesAdapter = new FilesAdapter();
        recyclerView.setAdapter(filesAdapter);

        initFileManager();

    }

    @Override
    protected void onStart() {
        super.onStart();
        filesAdapter.setOnFileClickListener(mOnFileClickListener);
    }

    @Override
    protected void onStop() {
        filesAdapter.setOnFileClickListener(null);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (fileManager != null && fileManager.navigateUp()) {
            updateFileList();
        } else {
            super.onBackPressed();

        }

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Если код совпадает
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Ответ пользователя приходит сюда. Если разрешает:
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initFileManager();
                // Если нет:
            } else {
                requestPermissions();
            }
        }
    }

    private void initFileManager() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//             Разрешение предоставлено
            fileManager = new FileManager(this);
            updateFileList();
        } else {
            requestPermissions();
        }
    }

    private void updateFileList() {
        List<File> files = fileManager.getFiles();

        filesAdapter.setFiles(files);
        filesAdapter.notifyDataSetChanged();
    }

    private FilesAdapter.OnFileClickListener mOnFileClickListener = new FilesAdapter.OnFileClickListener() {
        @Override
        public void onFileClick(File file) {

            if (file.isDirectory()) {
                fileManager.navigateTo(file);
                updateFileList();
            } else {
                if (file.getName().endsWith(".apk")) {
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_FILE_PATH, file.getAbsolutePath());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    };

}
