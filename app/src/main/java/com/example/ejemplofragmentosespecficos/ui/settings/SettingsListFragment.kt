package com.example.ejemplofragmentosespecficos.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.ejemplofragmentosespecficos.R

class SettingsListFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}
