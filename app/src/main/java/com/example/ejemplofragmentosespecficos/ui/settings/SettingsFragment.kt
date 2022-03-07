package com.example.ejemplofragmentosespecficos.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.ejemplofragmentosespecficos.R
import com.example.ejemplofragmentosespecficos.databinding.SettingsFragmentBinding

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SettingsFragmentBinding.bind(requireView())
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.toolbar.run {
            title = getString(R.string.settings_title)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}