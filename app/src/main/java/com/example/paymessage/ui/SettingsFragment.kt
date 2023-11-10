package com.example.paymessage.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.paymessage.MainActivity
import com.example.paymessage.data.SharedPreferences.AppPreferences
import com.example.paymessage.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    // Instanz des FireBaseViewModels, um die Firebase-Authentifizierung zu verwalten.
    val viewmodel: FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appPreferences: AppPreferences

    // Eine Variable, die den Status des Nachtmodus speichert.
    private var isNightModusSwitchChecked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Initialize AppPreferences
        appPreferences = AppPreferences(requireContext())

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        binding.nachtModusSWITCH.isChecked = appPreferences.isNightModeEnabled

        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität einstellen.
        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        binding.nachtModusSWITCH.isChecked = isNightModusSwitchChecked
        binding.nachtModusSWITCH.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(isChecked)
            appPreferences.isNightModeEnabled = isChecked
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.nachtModusSWITCH.isChecked = appPreferences.isNightModeEnabled
    }


    // Nachtmodus je nach Status aktiviert oder deaktiviert.
    private fun setDarkMode(isNightModeEnabled: Boolean) {
        if (isNightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutBTN.setOnClickListener {
            viewmodel.signOut()
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
            (requireActivity() as MainActivity).binding.bottomNavigationView.visibility =
                View.GONE
        }
    }
}

