package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.paymessage.MainActivity
import com.example.paymessage.R
import com.example.paymessage.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    // Instanz des FireBaseViewModels, um die Firebase-Authentifizierung zu verwalten.
    val viewModel: FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität ausblenden.
        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.GONE
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registrierenBTN.setOnClickListener {
            val email = binding.emailET.text.toString()
            val passwort = binding.passwortET.text.toString()

            //Überprüfen ob Email und Passwort feld ausgefüllt sind
            if (email.isNotEmpty() && passwort.isNotEmpty() && passwort.length >= 6) {
                viewModel.signUp(email, passwort)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Felder müssen ausgefüllt sein. \nPasswort mindestens 6 Zeichen",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.loginBTN.setOnClickListener {
            val email = binding.emailET.text.toString()
            val passwort = binding.passwortET.text.toString()

            //Überprüfen ob Email und Passwort feld ausgefüllt sind
            if (email.isNotEmpty() && passwort.isNotEmpty() && passwort.length >= 6) {
                viewModel.signIn(email, passwort)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Felder müssen ausgefüllt sein. \nPasswort mindestens 6 Zeichen",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        // Überwachen des aktuellen Benutzers. Wenn vorhanden, navigiere zur Home-Ansicht.
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}
