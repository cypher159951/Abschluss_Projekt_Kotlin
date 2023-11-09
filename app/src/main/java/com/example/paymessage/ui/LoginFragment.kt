package com.example.paymessage.ui

import android.os.Bundle
import android.util.Log
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


// Ein Fragment, das für die Anzeige und Verarbeitung des Login-Vorgangs verantwortlich ist.
class LoginFragment : Fragment() {

    // Instanz des FireBaseViewModels, um die Firebase-Authentifizierung zu verwalten.
    val viewModel: FireBaseViewModel by activityViewModels()

    // Eine Instanz der View-Bindungsklasse für das Fragment.
    private lateinit var binding: FragmentLoginBinding


    // Die Methode, die das Layout des Fragments erstellt und zurückgibt.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität ausblenden.
        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.GONE


        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    // Die Methode, die aufgerufen wird, nachdem die Ansicht des Fragments erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setzen von Click-Listenern für die Registrierungs- und Login-Schaltflächen.
        binding.registrierenBTN.setOnClickListener {
            try {
                val email = binding.emailET.text.toString()
                val passwort = binding.passwortET.text.toString()
                viewModel.signUp(email, passwort)
            } catch (e: Exception) {
                Log.e("registrieren", "Fehler bei registrieren")
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Fehler", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginBTN.setOnClickListener {
            try {
                val email = binding.emailET.text.toString()
                val passwort = binding.passwortET.text.toString()
                viewModel.signIn(email, passwort)
            } catch (e: Exception) {
                Log.e("Login", "Fehler bei login")
                  requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Fehler", Toast.LENGTH_SHORT).show()
                  }
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
