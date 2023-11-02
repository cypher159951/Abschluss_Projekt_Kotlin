package com.example.paymessage.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.paymessage.MainActivity
import com.example.paymessage.databinding.FragmentSettingsBinding


// Ein Fragment, das die Einstellungen der Anwendung darstellt und verwaltet.
class SettingsFragment : Fragment() {

    // Instanz des FireBaseViewModels, um die Firebase-Authentifizierung zu verwalten.
    val viewmodel: FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences


    // Eine Variable, die den Status des Nachtmodus speichert.
    private var isNightModusSwitchChecked = false

    // Die Methode, die das Layout des Fragments erstellt und zurückgibt.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout für das Fragment einrichten.
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        binding.nachtModusSWITCH.isChecked = sharedPreferences.getBoolean("isChecked", false)

        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität einstellen.
        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE


        // Setzen des Nachtmodus-Switch-Listeners.
        binding.nachtModusSWITCH.isChecked = isNightModusSwitchChecked
        binding.nachtModusSWITCH.setOnCheckedChangeListener { _, isChecked ->
            isNightModusSwitchChecked = isChecked
            setDarkMode(isChecked)
            with(sharedPreferences.edit()) {
                putBoolean("isChecked", isChecked)
                apply()
            }
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.nachtModusSWITCH.isChecked = sharedPreferences.getBoolean("isChecked", false)
    }


    // Eine private Funktion, die den Nachtmodus je nach Status aktiviert oder deaktiviert.
    private fun setDarkMode(isNightModeEnabled: Boolean) {
        if (isNightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    // Die Methode, die aufgerufen wird, nachdem die Ansicht des Fragments erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setzen eines Click-Listeners für die Logout-Schaltfläche.
        binding.logoutBTN.setOnClickListener {
            viewmodel.signOut()
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
            (requireActivity() as MainActivity).binding.bottomNavigationView.visibility =
                View.GONE
        }
    }
}

