package com.example.ejemplofragmentosespecficos.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.*
import com.example.ejemplofragmentosespecficos.R
import com.example.ejemplofragmentosespecficos.ui.settings.SettingsFragment
import com.example.ejemplofragmentosespecficos.ui.simpleSelectionDialog.SimpleSelectionDialogFragment
import com.example.ejemplofragmentosespecficos.databinding.ProfileFragmentBinding
import com.example.ejemplofragmentosespecficos.repository.DefaultSettingsRepository
import com.example.ejemplofragmentosespecficos.ui.confirmationDialog.ConfirmationDialogFragment
import com.example.ejemplofragmentosespecficos.ui.datePicker.DatePickerDialogFragment
import java.text.SimpleDateFormat
import java.util.*

private const val CIVIL_STATE_DIALOG_TAG = "CIVIL_STATE_DIALOG_TAG"
private const val CIVIL_STATE_REQUEST_KEY: String = "CIVIL_STATE_REQUEST_KEY"
private const val CONFIRMATION_DIALOG_TAG = "CONFIRMATION_DIALOG_TAG"
private const val CONFIRMATION_REQUEST_KEY = "CONFIRMATION_REQUEST_KEY"
private const val SIGN_UP_DATE_DIALOG_TAG = "SIGN_UP_DIALOG_TAG"
private const val SIGN_UP_DATE_REQUEST_KEY: String = "SIGN_UP_DATE_REQUEST_KEY"

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat()

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.Factory(DefaultSettingsRepository(requireActivity().application), this)
    }

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

        setFragmentResultListener(SIGN_UP_DATE_REQUEST_KEY) { _, responseBundle ->
            viewModel.changeSignUpDate(
                DatePickerDialogFragment.getSelectedUtcTimeInMillis(responseBundle)
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
                inflateMenu(R.menu.profile_fragment)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.mnuSettings -> navigateToSettings().let { true }
                        else -> false
                    }
                }
            }
            with(txtSignUpDate) {
                keyListener = null
                setOnClickListener {
                    showSignUpDateSelectionDialog()
                }
            }
            with(txtCivilState) {
                keyListener = null
                setOnClickListener { showCivilStateSelectionDialog() }
            }
            btnSave.setOnClickListener { onSave() }
        }
    }

    private fun navigateToSettings() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SettingsFragment>(R.id.fcContent)
            addToBackStack(null)
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
        if (viewModel.shouldConfirmSave) {
            showConfirmationDialog()
        } else {
            save()
        }
    }

    private fun showSignUpDateSelectionDialog() {
        DatePickerDialogFragment.newInstance(
            requestKey = SIGN_UP_DATE_REQUEST_KEY,
            utcTimeInMillis = viewModel.signUpDate.value!!
        ).show(parentFragmentManager, SIGN_UP_DATE_DIALOG_TAG)
    }


    private fun showCivilStateSelectionDialog(civilState: String) {
        // ...
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
