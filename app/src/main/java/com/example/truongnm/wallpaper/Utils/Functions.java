package com.example.truongnm.wallpaper.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.truongnm.wallpaper.R;

/**
 * Created by truongnm on 7/8/18.
 */

public class Functions {
    public static void changeMainFragment(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    public static void changeMainFragmentWithBack(FragmentActivity fragmentActivity, Fragment fragment){
        fragmentActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
