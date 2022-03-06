package com.example.ejemplofragmentosespecficos

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import java.util.*

private const val STATE_CIVIL_STATE_INDEX: String = "STATE_CIVIL_STATE_INDEX"
private const val STATE_SIGN_UP_DATE: String = "STATE_SIGN_UP_DATE"

class ProfileViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _civilStateIndex = savedStateHandle.getLiveData<Int>(STATE_CIVIL_STATE_INDEX, 0)
    val civilStateIndex: LiveData<Int> get() = _civilStateIndex

    private val _signUpDate = savedStateHandle.getLiveData<Long>(
        STATE_SIGN_UP_DATE,
        Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
    )
    val signUpDate: LiveData<Long> get() = _signUpDate

    fun changeCivilState(index: Int) {
        _civilStateIndex.value = index
    }

    fun changeSignUpDate(utcTimeInMillis: Long) {
        _signUpDate.value = utcTimeInMillis
    }

}
