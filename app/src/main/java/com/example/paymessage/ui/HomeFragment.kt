package com.example.paymessage.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        (requireActivity() as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE

        val recyclerView = binding.newsListRV
        recyclerView.layoutManager = LinearLayoutManager(context)



        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null)
                findNavController().navigate(R.id.loginFragment)
        }

//        newsViewModel.sortedNews.observe(viewLifecycleOwner) { sortedNewsList ->
//          // if (sortedNewsList != null) {
//                val newsAdapter =
//                    sortedNewsList?.let { NewsAdapter(newsViewModel, it, findNavController()) }
//                binding.newsListRV.adapter = newsAdapter
//            if (newsAdapter != null) {
//                newsAdapter.submitList(sortedNewsList)
//            }
//                Log.e("sortedNewsList", "$sortedNewsList")
//            }
//        }
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

        //Pull-to-Refresh funktion aufrufen / Nachrichten aktualisieren
       // newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

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


    //position von RV speichern
    override fun onDestroyView() {
        val listState = binding.newsListRV.layoutManager?.onSaveInstanceState()
        listState?.let { newsViewModel.saveListState(it) }
        super.onDestroyView()
    }





}




