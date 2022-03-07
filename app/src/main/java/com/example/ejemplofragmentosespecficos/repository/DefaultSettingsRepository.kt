package com.example.ejemplofragmentosespecficos.repository

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.ejemplofragmentosespecficos.R

class DefaultSettingsRepository(private val application: Application) : SettingsRepository {

    private val settings = PreferenceManager.getDefaultSharedPreferences(application)

    override fun queryConfirmSave(): Boolean =
        settings.getBoolean(
            application.getString(R.string.prefConfirmSave_key),
            application.resources.getBoolean(R.bool.prefConfirmSave_defaultValue)
        )

    override fun queryDefaultCivilState(): String =
        settings.getString(
            application.getString(R.string.prefCivilState_key),
            application.getString(R.string.prefCivilState_defaultValue)
        )!!

}
