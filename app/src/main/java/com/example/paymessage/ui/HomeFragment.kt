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

        // Scroll to Top Button
        binding.scrollToTopBTN.setOnClickListener {
            // Hier den RecyclerView nach oben scrollen
            binding.newsListRV.smoothScrollToPosition(0)
        }

        // Die Sichtbarkeit der Bottom Navigation Bar auf der Hauptaktivität einstellen.
        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        val recyclerView = binding.newsListRV
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Überwachen des aktuellen Benutzers. Wenn nicht vorhanden, navigiere zur Login-Ansicht.
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