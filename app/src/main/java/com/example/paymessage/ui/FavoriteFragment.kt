package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.paymessage.Adapter.FavoriteAdapter
import com.example.paymessage.databinding.FragmentFavoriteBinding

// Ein Fragment, das die Favoriten anzeigt.
class FavoriteFragment : Fragment() {

    // ViewModel für den Zugriff auf die Daten
    private val NewsViewModel: NewsViewModel by activityViewModels()

    // View Binding-Objekt für das Fragment-Layout
    private lateinit var binding: FragmentFavoriteBinding


    // Methode zum Erstellen der Ansicht des Fragments
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Das Layout des Fragments aufblasen und das View Binding-Objekt initialisieren
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root

    }

    // Die Methode, die aufgerufen wird, nachdem die Ansicht des Fragments erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Die Größe der RecyclerView fixieren
        binding.favoritenRV.setHasFixedSize(true)

        // Die Liste der favorisierten Daten im ViewModel beobachten und den RecyclerView-Adapter setzen
        NewsViewModel.favoriteDataList.observe(viewLifecycleOwner) {
            binding.favoritenRV.adapter = FavoriteAdapter(NewsViewModel, it, binding.favoritenRV.layoutManager)

            // Den vorherigen Zustand der RecyclerView wiederherstellen, falls vorhanden
            NewsViewModel.listStateFavorite?.let {parcelable ->
                binding.favoritenRV.layoutManager?.onRestoreInstanceState(parcelable)
                NewsViewModel.listStateFavorite = null
            }
        }

    }
}
