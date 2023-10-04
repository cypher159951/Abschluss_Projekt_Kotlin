package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.paymessage.R
import com.example.paymessage.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    val viewModel : FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registrierenBTN.setOnClickListener {
            val email = binding.emailET.text.toString()
            val passwort = binding.passwortET.text.toString()

            viewModel.signUp(email,passwort)
        }

        binding.loginBTN.setOnClickListener {
            val email = binding.emailET.text.toString()
            val passwort = binding.passwortET.text.toString()

            viewModel.signIn(email, passwort)
        }

        //Wenn User eingeloggt ist, navigiere weiter
        viewModel.user.observe(viewLifecycleOwner){
            if(it != null){
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }

}