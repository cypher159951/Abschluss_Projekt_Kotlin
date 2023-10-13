package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import com.example.paymessage.Adapter.FavoriteAdapter
import com.example.paymessage.databinding.FragmentFavoriteBinding

// Ein Fragment, das dazu dient, die favorisierten Artikel in der Anwendung anzuzeigen.
class FavoriteFragment : Fragment() {

    // Eine Instanz des NewsViewModels, das für die Anzeige der favorisierten Artikel verantwortlich ist.
    private val NewsViewModel: NewsViewModel by activityViewModels()

    // Eine Instanz der View-Bindungsklasse für das Fragment.
    private lateinit var binding: FragmentFavoriteBinding


    // Die Methode, die das Layout des Fragments erstellt und zurückgibt.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root

    }


    // Die Methode, die aufgerufen wird, nachdem die Ansicht des Fragments erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setzen des RecyclerViews auf eine feste Größe, um die Leistung zu optimieren.
        binding.favoritenRV.setHasFixedSize(true)

        // Beobachten der favorisierten Daten im ViewModel und Aktualisieren des RecyclerView-Adapters entsprechend.
        NewsViewModel.favoriteDataList.observe(viewLifecycleOwner) {
            binding.favoritenRV.adapter = FavoriteAdapter(
                NewsViewModel,
                it,
                binding.favoritenRV.layoutManager,
                NavController(requireContext())
            )

            // Wiederherstellen des vorherigen Zustands der RecyclerView-Liste, falls vorhanden.
            NewsViewModel.listStateFavorite?.let { parcelable ->
                binding.favoritenRV.layoutManager?.onRestoreInstanceState(parcelable)
                NewsViewModel.listStateFavorite = null
            }
        }

    }
}
