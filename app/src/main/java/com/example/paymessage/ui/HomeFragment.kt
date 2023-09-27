package com.example.paymessage.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymessage.Adapter.NewsAdapter
import com.example.paymessage.databinding.FragmentHomeBinding



class HomeFragment : Fragment() {

    private val NewsViewModel: NewsViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
   

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newsListRV.setHasFixedSize(true)
        NewsViewModel.newsDataList.observe(viewLifecycleOwner) {
            binding.newsListRV.adapter =
                NewsAdapter(NewsViewModel, it, NavController(requireContext()))
        }
    }

}