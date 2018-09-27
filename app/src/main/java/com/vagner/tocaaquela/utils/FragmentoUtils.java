package com.vagner.tocaaquela.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.vagner.tocaaquela.R;

public class FragmentoUtils {

    private static FragmentActivity activity;
    private static Fragment fragment;

    public static void replace(FragmentActivity activity, Fragment fragment){
        FragmentoUtils.activity = activity;
        FragmentoUtils.fragment = fragment;
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmento, fragment).commit();
    }
}
