package com.example.ejemplofragmentosespecficos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ejemplofragmentosespecficos.databinding.ProfileFragmentBinding
import com.example.ejemplofragmentosespecficos.databinding.SettingsFragmentBinding

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    private var _binding: SettingsFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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