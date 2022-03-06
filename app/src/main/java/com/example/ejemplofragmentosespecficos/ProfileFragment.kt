package com.example.ejemplofragmentosespecficos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.ejemplofragmentosespecficos.databinding.ProfileFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

// TODO Quitar ToolBar Principal

private const val CIVIL_STATE_DIALOG_TAG = "CIVIL_STATE_DIALOG_TAG"
private const val CIVIL_STATE_REQUEST_KEY: String = "CIVIL_STATE_REQUEST_KEY"
private const val CONFIRMATION_DIALOG_TAG = "CONFIRMATION_DIALOG_TAG"
private const val CONFIRMATION_REQUEST_KEY = "CONFIRMATION_REQUEST_KEY"

class ProfileFragment : Fragment(R.layout.profile_fragment) {
    
    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat()

    private val viewModel: ProfileViewModel by activityViewModels()

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(CONFIRMATION_REQUEST_KEY) { _, responseBundle ->
            if (ConfirmationDialogFragment.isResponsePositive(responseBundle)) {
                save()
            }
        }

        setFragmentResultListener(CIVIL_STATE_REQUEST_KEY) { _, responseBundle ->
            viewModel.changeCivilState(
                SimpleSelectionDialogFragment.getSelectedIndex(responseBundle)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ProfileFragmentBinding.bind(requireView())
        setupViews()
        observeViewModel()
    }

    private fun setupViews() {
        with(binding) {
            with(toolbar) {
                title = getString(R.string.profile_title)
            }
            with(txtSignUpDate) {
                keyListener = null
                setOnClickListener {
                    //showSignUpDateSelectionDialog()
                }
            }
            with(txtCivilState) {
                keyListener = null
                setOnClickListener { showCivilStateSelectionDialog() }
            }
            btnSave.setOnClickListener { onSave() }
        }
    }

    private fun showConfirmationDialog() {
        ConfirmationDialogFragment.newInstance(
            title = getString(R.string.profile_confirmation),
            message = getString(R.string.profile_sure),
            yesText = getString(R.string.save),
            noText = getString(R.string.cancel),
            requestKey = CONFIRMATION_REQUEST_KEY,
        ).show(parentFragmentManager, CONFIRMATION_DIALOG_TAG)
    }

    private fun showCivilStateSelectionDialog() {
        val civilStates = resources.getStringArray(R.array.civil_states)
        SimpleSelectionDialogFragment.newInstance(
            title = getString(R.string.profile_civil_state),
            items = civilStates,
            confirmText = getString(R.string.profile_accept),
            defaultSelectedIndex = viewModel.civilStateIndex.value!!,
            requestKey = CIVIL_STATE_REQUEST_KEY,
        ).show(parentFragmentManager, CIVIL_STATE_DIALOG_TAG)
    }

    private fun save() {
        Toast.makeText(
            context,
            getString(R.string.profile_save_user),
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun observeViewModel() {
        viewModel.civilStateIndex.observe(viewLifecycleOwner) { index ->
            showCivilState(resources.getStringArray(R.array.civil_states)[index])
        }
        viewModel.signUpDate.observe(viewLifecycleOwner) { utcTimeInMillis ->
            showSignUpDate(utcTimeInMillis)
        }
    }

    private fun showSignUpDate(utcTimeInMillis: Long) {
        binding.txtSignUpDate.setText(simpleDateFormat.format(Date(utcTimeInMillis)))
    }

    private fun showCivilState(civilState: String) {
        binding.txtCivilState.setText(civilState)
    }

    private fun onSave() {
        showConfirmationDialog()
    }

    private fun showSignUpDateSelectionDialog(currentDate: String) {
        // ...
    }

    private fun showCivilStateSelectionDialog(civilState: String) {
        // ...
    }



}
