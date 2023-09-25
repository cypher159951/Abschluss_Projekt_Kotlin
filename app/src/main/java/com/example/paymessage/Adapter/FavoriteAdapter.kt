package com.example.paymessage.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.paymessage.R
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.databinding.FavoriteItemBinding
import com.example.paymessage.ui.NewsViewModel

class FavoriteAdapter(

    private val viewModel: NewsViewModel,
    private var dataset: List<News>,
    navController: NavController
): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.favoriteTV.text = item.title
        holder.binding.favoriteIV.load(item.teaserImage.imageVariants.image144)


        val likeImageResource = if (item.isLiked) R.drawable.baseline_star_24
        else R.drawable.baseline_star_outline_24
        holder.binding.favoriteStarBTN.setImageResource(likeImageResource)

        holder.binding.favoriteStarBTN.setOnClickListener {
            val like = dataset[position]
            like.isLiked = !like.isLiked
            notifyItemChanged(position)

            //Datenbank updaten
            viewModel.updateLikestatusInDb(like)
        }



    }


        override fun getItemCount(): Int {
            return dataset.size
        }
    }
