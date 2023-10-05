package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.paymessage.MainActivity
import com.example.paymessage.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    val viewmodel: FireBaseViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutBTN.setOnClickListener {
            viewmodel.signOut()
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
            (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        }



//        binding.nachtModusSWITCH.setOnClickListener {
//            val navController = findNavController()
//        }
    }

}