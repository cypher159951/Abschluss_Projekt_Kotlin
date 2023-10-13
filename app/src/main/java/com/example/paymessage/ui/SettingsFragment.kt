package com.example.paymessage.ui

import android.os.Bundle
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

    // Eine Variable, die den Status des Nachtmodus speichert.
    private var isNightModeEnabled = false


    // Die Methode, die das Layout des Fragments erstellt und zurückgibt.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Layout für das Fragment einrichten.
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität einstellen.
        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
        //TODO beim wechseln auf nachtmodus flackert der home button, ÜBERPRÜFEN!

        // Setzen des Nachtmodus-Switch-Listeners.
        binding.nachtModusSWITCH.setOnCheckedChangeListener { _, isChecked ->
            setDarkMode(isChecked)
        }
        return binding.root
    }


    // Eine private Funktion, die den Nachtmodus je nach Status aktiviert oder deaktiviert.
    private fun setDarkMode(status: Boolean) {
        if (status) {
            // Darkmode aktivieren
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // Darkmode deaktivieren
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

