package com.example.paymessage.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.databinding.NewsItemBinding
import com.example.paymessage.ui.HomeFragmentDirections
import com.example.paymessage.ui.NewsViewModel

class NewsAdapter(
    private val viewModel: NewsViewModel,
    private var dataset: List<News>,
    private val navController: NavController,

    ) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.newsTV.setText(item.title)


        holder.binding.contactCV.setOnClickListener {
            //Mit der id zum DetailFragment navigieren
            holder.itemView.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment (dataset[position].id!!))
        }

    }
}
