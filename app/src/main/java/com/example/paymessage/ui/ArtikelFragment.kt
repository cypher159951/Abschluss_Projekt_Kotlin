package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.paymessage.databinding.FragmentArtikelBinding




class ArtikelFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var binding: FragmentArtikelBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtikelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val id = it.getLong("id")
            viewModel.loadNewsDetail(id)
            viewModel.newsDetail.observe(viewLifecycleOwner) {
                if (it.content.isNotEmpty()){
                    binding.titleAtikelTV.text = it.content[0].value
                    var text: String =""
                    for (i in 1..it.content.size-1){
                        text += it.content[i].value
                    }

                    binding.artikelTV.text = text
                    binding.artikelImageIV.load(it.teaserImage.imageVariants.image144)
                }

            }
        }

        binding.backBTNIV.setOnClickListener{
            val navController = findNavController()
            navController.navigate(ArtikelFragmentDirections.actionDetailFragmentToHomeFragment())
        }
    }


}