package com.example.my_hotel.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.my_hotel.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}