package com.example.ejemplofragmentosespecficos.repository

interface SettingsRepository {
    fun queryConfirmSave(): Boolean
    fun queryDefaultCivilState(): String
}
