package com.itvdn.fragitvdn;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SizeF;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = getSupportFragmentManager();

         firstFragment = new FirstFragment();
         secondFragment = new SecondFragment();


    }

    public void onClickFragment(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.btnAdd:
                if (fragmentManager.findFragmentByTag(FirstFragment.TAG)==null && fragmentManager.findFragmentByTag(SecondFragment.TAG)==null) {
                fragmentTransaction.add(R.id.containerMy, firstFragment,FirstFragment.TAG);
                }

                break;
            case R.id.btnReplace:
                if (fragmentManager.findFragmentByTag(FirstFragment.TAG)!=null) {
                    fragmentTransaction.replace(R.id.containerMy, secondFragment, SecondFragment.TAG);
                }

                if (fragmentManager.findFragmentByTag(SecondFragment.TAG)!=null) {
                    fragmentTransaction.replace(R.id.containerMy, firstFragment, FirstFragment.TAG);
                }
                break;
            case R.id.btnRemove:
                if (fragmentManager.findFragmentByTag(FirstFragment.TAG)!=null) {

                    fragmentTransaction.remove(firstFragment);
                }
                if (fragmentManager.findFragmentByTag(SecondFragment.TAG)!=null) {

                    fragmentTransaction.remove(secondFragment);
                }
                break;


        }

        fragmentTransaction.commit();
    }


}
