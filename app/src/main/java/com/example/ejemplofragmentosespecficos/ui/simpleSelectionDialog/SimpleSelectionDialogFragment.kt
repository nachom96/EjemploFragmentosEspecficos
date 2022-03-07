package com.example.ejemplofragmentosespecficos.ui.simpleSelectionDialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val ARG_TITLE = "ARG_TITLE"
private const val ARG_ITEMS = "ARG_ITEMS"
private const val ARG_CONFIRM_TEXT = "ARG_CONFIRM_TEXT"
private const val ARG_DEFAULT_SELECTED_INDEX = "ARG_DEFAULT_SELECTED_INDEX"
private const val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"
private const val RESPONSE_KEY = "RESPONSE_KEY"

class SimpleSelectionDialogFragment : DialogFragment() {

    private val title by requiredStringArg(ARG_TITLE)
    private val items: Array<CharSequence> by requiredCharSequenceArray(ARG_ITEMS)
    private val confirmText by requiredStringArg(ARG_CONFIRM_TEXT)
    private val defaultSelectedIndex by requiredIntArg(ARG_DEFAULT_SELECTED_INDEX)
    private val requestKey by requiredStringArg(ARG_REQUEST_KEY)

    private var selectedIndex = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        selectedIndex = defaultSelectedIndex
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setSingleChoiceItems(items, defaultSelectedIndex) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton(confirmText) { dialog, _ ->
                dialog.dismiss()
                setFragmentResult(requestKey, bundleOf(RESPONSE_KEY to selectedIndex))
            }
            .create()
    }

    companion object {

        fun getSelectedIndex(responseBundle: Bundle): Int =
            responseBundle.getInt(RESPONSE_KEY)

        fun newInstance(
            title: String,
            items: Array<String>,
            confirmText: String,
            defaultSelectedIndex: Int,
            requestKey: String,
        ): SimpleSelectionDialogFragment =
            SimpleSelectionDialogFragment().apply {
                arguments = bundleOf(
                    ARG_TITLE to title,
                    ARG_ITEMS to items,
                    ARG_CONFIRM_TEXT to confirmText,
                    ARG_DEFAULT_SELECTED_INDEX to defaultSelectedIndex,
                    ARG_REQUEST_KEY to requestKey,
                )
            }

    }

    fun Fragment.requiredStringArg(argKey: String) =
        lazy {
            arguments?.getString(argKey) ?: throw IllegalStateException("$argKey argument required")
        }

    fun Fragment.requiredCharSequenceArray(argKey: String) =
        lazy {
            arguments?.getCharSequenceArray(argKey)
                ?: throw IllegalStateException("$argKey argument required")
        }

    fun Fragment.requiredIntArg(argKey: String) =
        lazy { requireArguments().getInt(argKey) }


}
