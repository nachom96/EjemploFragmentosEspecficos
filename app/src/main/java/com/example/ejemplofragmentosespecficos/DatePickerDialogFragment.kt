package com.example.ejemplofragmentosespecficos

import android.app.DatePickerDialog
import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.*

private const val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"
private const val ARG_UTC_TIME_IN_MILLIS = "ARG_UTC_TIME_IN_MILLIS"
private const val RESPONSE_KEY = "RESPONSE_KEY"

class DatePickerDialogFragment : DialogFragment() {

    private val requestKey by requiredStringArg(ARG_REQUEST_KEY)
    private val utcTimeInMillis by requiredLongArg(ARG_UTC_TIME_IN_MILLIS)

    private val onDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(year, month, dayOfMonth)
            }
            setFragmentResult(
                requestKey, bundleOf(RESPONSE_KEY to calendar.timeInMillis)
            )
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            .apply { timeInMillis = utcTimeInMillis }
        return DatePickerDialog(
            requireContext(),
            onDateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    companion object {

        fun getSelectedUtcTimeInMillis(responseBundle: Bundle): Long =
            responseBundle.getLong(RESPONSE_KEY)

        fun newInstance(
            requestKey: String,
            utcTimeInMillis: Long = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis,
        ) =
            DatePickerDialogFragment().apply {
                arguments = bundleOf(
                    ARG_REQUEST_KEY to requestKey,
                    ARG_UTC_TIME_IN_MILLIS to utcTimeInMillis,
                )
            }

    }

    fun Fragment.requiredStringArg(argKey: String) =
        lazy {
            arguments?.getString(argKey) ?: throw IllegalStateException("$argKey argument required")
        }

    fun Fragment.requiredLongArg(argKey: String) =
        lazy { requireArguments().getLong(argKey) }

}
