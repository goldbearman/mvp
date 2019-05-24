package com.hfad.aplicationmanageraplication;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Myf";
    SwipeRefreshLayout mSwipeRefreshLayout;
    AppManager mAppManager;
    AppsAdapter mAppsAdapter;
    private static final int REQUEST_CODE_PICK_APK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        mAppManager = new AppManager(this);
        List<AppInfo> installedApps = mAppManager.getInstalledApp();

        mAppsAdapter = new AppsAdapter();

        RecyclerView recyclerView = findViewById(R.id.apps_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mAppsAdapter);

        reloadApps();
    }

    private void reloadApps() {
        List<AppInfo> installedApps = mAppManager.getInstalledApp();
        mAppsAdapter.setAppInfos(this, installedApps);
        mAppsAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            reloadApps();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAppsAdapter.setQuery(newText.toLowerCase().trim());
                mAppsAdapter.notifyDataSetChanged();

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.install_item:
                startFilePickerActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startFilePickerActivity() {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_PICK_APK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_APK && resultCode == RESULT_OK) {
            String apkPath = data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH);
            Log.i(TAG, "APK: " + apkPath);

            startAppInstallation(apkPath);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void startAppInstallation(String apkPath) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider", new File(apkPath));
            Log.i(TAG, "TAG"+uri);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(new File(apkPath));
        }

        installIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Создаст новый процесс

        startActivity(installIntent);

    }

}
