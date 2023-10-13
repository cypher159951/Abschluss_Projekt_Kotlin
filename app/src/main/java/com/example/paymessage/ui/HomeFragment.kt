package com.example.paymessage.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymessage.Adapter.NewsAdapter
import com.example.paymessage.MainActivity
import com.example.paymessage.R
import com.example.paymessage.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Ein Fragment, das die Startseite der Anwendung darstellt.
class HomeFragment : Fragment() {

    // Instanz des NewsViewModels, um die News-Daten zu verwalten.
    private val newsViewModel: NewsViewModel by activityViewModels()

    // Instanz des FireBaseViewModels, um die Firebase-Daten zu verwalten.
    val viewModel: FireBaseViewModel by activityViewModels()

    // Eine Instanz der View-Bindungsklasse für das Fragment.
    private lateinit var binding: FragmentHomeBinding


    // Die Methode, die das Layout des Fragments erstellt und zurückgibt.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    // Die Methode, die aufgerufen wird, nachdem die Ansicht des Fragments erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität einstellen.
        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        // Einrichten des RecyclerViews für die Liste der Nachrichten.
        val recyclerView = binding.newsListRV
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Überwachen des aktuellen Benutzers. Wenn nicht vorhanden, navigiere zur Login-Ansicht.
        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null)
                findNavController().navigate(R.id.loginFragment)
        }


        // Beobachten der Liste von Nachrichten und Aktualisieren des RecyclerView-Adapters entsprechend.
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


        // Funktion für das Pull-to-Refresh-Feature, um die Nachrichten zu aktualisieren.
        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d("refreshtest", "refreshing")
            val delayMillis: Long = 1000
            //Starte eine Coroutine
            CoroutineScope(Dispatchers.Main).launch {
                // Warte für delayMillis in Millisekunden
                delay(delayMillis)

                //Rufe die refreshNews-Methode auf
                newsViewModel.refreshNews()
                Log.d("refreshtest2", "refreshfinish")

                // Setze swipeRefreshLayout.isRefreshing auf false
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }


    // Die Position der RecyclerView-Liste speichern, um sie bei Bedarf wiederherstellen zu können.
    override fun onDestroyView() {
        val listState = binding.newsListRV.layoutManager?.onSaveInstanceState()
        listState?.let { newsViewModel.saveListState(it) }
        super.onDestroyView()
    }
}




