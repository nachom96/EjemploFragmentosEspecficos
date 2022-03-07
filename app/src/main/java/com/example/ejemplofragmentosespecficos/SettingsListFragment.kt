package com.example.ejemplofragmentosespecficos

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingsListFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}
