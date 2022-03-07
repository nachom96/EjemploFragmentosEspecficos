package com.example.ejemplofragmentosespecficos.ui.confirmationDialog

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val ARG_TITLE = "ARG_TITLE"
private const val ARG_MESSAGE = "ARG_MESSAGE"
private const val ARG_YES_TEXT = "ARG_YES_TEXT"
private const val ARG_NO_TEXT = "ARG_NO_TEXT"
private const val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"
private const val RESPONSE_KEY = "RESPONSE_KEY"

class ConfirmationDialogFragment : DialogFragment() {


    private val title by requiredStringArg(ARG_TITLE)
    private val message by requiredStringArg(ARG_MESSAGE)
    private val yesText by requiredStringArg(ARG_YES_TEXT)
    private val noText by requiredStringArg(ARG_NO_TEXT)
    private val requestKey by requiredStringArg(ARG_REQUEST_KEY)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(yesText) { _, _ ->
                setFragmentResult(
                    requestKey,
                    bundleOf(RESPONSE_KEY to true)
                )
            }
            .setNegativeButton(noText) { _, _ ->
                setFragmentResult(
                    requestKey,
                    bundleOf(RESPONSE_KEY to false)
                )
            }
            .create()

    companion object {

        fun isResponsePositive(responseBundle: Bundle): Boolean =
            responseBundle.getBoolean(RESPONSE_KEY)

        fun newInstance(
            title: String,
            message: String,
            yesText: String,
            noText: String,
            requestKey: String,
        ): ConfirmationDialogFragment =
            ConfirmationDialogFragment().apply {
                arguments = bundleOf(
                    ARG_TITLE to title,
                    ARG_MESSAGE to message,
                    ARG_YES_TEXT to yesText,
                    ARG_NO_TEXT to noText,
                    ARG_REQUEST_KEY to requestKey,
                )
            }

    }

    fun Fragment.requiredStringArg(argKey: String) =
        lazy {
            arguments?.getString(argKey) ?: throw IllegalStateException("$argKey argument required")
        }

}
