package com.hfad.bitsandpizzas;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarN);
        setSupportActionBar(toolbar);

        SectionPageAdapter sectionsPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(sectionsPageAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        /////////////////  В МЕНЮ НАХОДИМ ITEM ///////////////
        MenuItem menuItem = menu.findItem(R.id.action_share);
        //////ПОЛУЧАЕМ ССЫЛКУ НА ПРОВАЙДЕРА ДЕЙСТВИЯ и ПЕРЕДАЕМ ЕМУ НАШ ITEM///////////////
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        /////// СОЗДАЕМ МЕТОД КОТОРЫЙ СОЗДАЕТ ИНТЕРНТ И ПЕРЕДАЕТ ЕГО ПРОВАЙДЕРУ ДЕЙСТВИЯ ///////////
        setShareActionIntent("Want to join me for pizza?");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        /// ПЕРЕДАЧА ИНТЕНТА ПРОВАЙДЕРУ ДЕЙСТВИЯ /////
        shareActionProvider.setShareIntent(intent);
    }




    private class SectionPageAdapter extends FragmentPagerAdapter {
        public SectionPageAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {

                case 0:
                    fragment = new TopFragment();
                    break;
                case 1:
                    fragment = new PizzaFragment();
                    break;
                case 2:
                    fragment = new PastaFragment();
                    break;
                case 3:
                    fragment = new StoresFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

                case 0:
                    return getResources().getText(R.string.home_tab);
                case 1:
                    return getResources().getText(R.string.pizza_tab);
                case 2:
                    return getResources().getText(R.string.pasta_tab);
                case 3:
                    return getResources().getText(R.string.store_tab);
            }

            return null;
        }
    }

}
