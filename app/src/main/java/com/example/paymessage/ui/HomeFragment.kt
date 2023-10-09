package com.example.paymessage.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymessage.Adapter.NewsAdapter
import com.example.paymessage.MainActivity
import com.example.paymessage.R
import com.example.paymessage.databinding.FragmentHomeBinding
import com.example.paymessage.databinding.NewsItemBinding


class HomeFragment : Fragment() {

    private val newsViewModel: NewsViewModel by activityViewModels()
    val viewModel: FireBaseViewModel by activityViewModels()
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

        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE



        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null)
                findNavController().navigate(R.id.loginFragment)
        }

        binding.newsListRV.setHasFixedSize(true)
        newsViewModel.newsDataList.observe(viewLifecycleOwner) {
            binding.newsListRV.adapter =
                NewsAdapter(
                    newsViewModel,
                    it,
                    NavController(requireContext()),
                    binding.newsListRV.layoutManager
                )
            newsViewModel.listStateParcel?.let { parcelable ->
                binding.newsListRV.layoutManager?.onRestoreInstanceState(parcelable)
                newsViewModel.listStateParcel = null
            }
        }


    }


    //position von RV speichern
    override fun onDestroyView() {
        val listState = binding.newsListRV.layoutManager?.onSaveInstanceState()
        listState?.let { newsViewModel.saveListState(it) }
        super.onDestroyView()
    }

}