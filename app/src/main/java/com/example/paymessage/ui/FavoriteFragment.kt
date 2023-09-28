package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.paymessage.Adapter.FavoriteAdapter
import com.example.paymessage.databinding.FragmentFavoriteBinding


class FavoriteFragment : Fragment() {

    private val NewsViewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentFavoriteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favoritenRV.setHasFixedSize(true)

        NewsViewModel.favoriteDataList.observe(viewLifecycleOwner) {
            binding.favoritenRV.adapter = FavoriteAdapter(NewsViewModel, it, binding.favoritenRV.layoutManager ,NavController(requireContext()))
            NewsViewModel.listStateFavorite?.let {parcelable ->
                binding.favoritenRV.layoutManager?.onRestoreInstanceState(parcelable)
                NewsViewModel.listStateFavorite = null
            }
        }

    }
}
