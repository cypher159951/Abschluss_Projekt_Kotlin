package com.example.paymessage.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.paymessage.R
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.databinding.FavoriteItemBinding
import com.example.paymessage.ui.FavoriteFragmentDirections
import com.example.paymessage.ui.NewsViewModel

class FavoriteAdapter(
    private val viewModel: NewsViewModel,
    private var dataset: List<News>,
    private val layoutManager: RecyclerView.LayoutManager?,
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
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
        holder.binding.favoriteArtikel.setOnClickListener {
            holder.itemView.findNavController()
                .navigate(FavoriteFragmentDirections.actionFavoriteFragmentToArtikelFragment(dataset[position].sophoraId!!))
        }

        holder.binding.favoriteShareIV.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)

            //Das "/api2u" aus der URL heraus filtern damit der link funktioniert
            val updateUrl = item.updateCheckUrl.replace("/api2u", "")

            //Den share Text bestimmen was beim teilen angezeigt werden soll
            val shareText = "${item.title}\n${updateUrl}"

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
            shareIntent.type = "text/plain"
            val shareIntentChooser = Intent.createChooser(shareIntent, null)
            holder.binding.root.context.startActivity(shareIntentChooser)
        }

        val likeImageResource = if (item.isLiked) R.drawable.baseline_star_24
        else R.drawable.baseline_star_outline_24
        holder.binding.favoriteStarBTN.setImageResource(likeImageResource)

        // Bei Klick auf das Favoriten-Symbol wird der Like-Status aktualisiert und in der Datenbank gespeichert
        holder.binding.favoriteStarBTN.setOnClickListener {
            val like = dataset[position]
            like.isLiked = !like.isLiked

            // RecyclerView-Position speichern
            val listState = layoutManager?.onSaveInstanceState()
            listState?.let { viewModel.saveListStateFavorite(it) }

            // Like-Status in der Datenbank aktualisieren
            viewModel.updateLikestatusInDb(like)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}