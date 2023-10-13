package com.example.paymessage.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.paymessage.R
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.databinding.NewsItemBinding
import com.example.paymessage.ui.HomeFragmentDirections
import com.example.paymessage.ui.NewsViewModel

class NewsAdapter(

    // ViewModel für den Zugriff auf Daten
    private val viewModel: NewsViewModel,

    // Liste von Nachrichten
    private var dataset: List<News>,

    // NavController für die Navigation
    private val navController: NavController,

    // LayoutManager für die RecyclerView
    private val layoutManager: RecyclerView.LayoutManager?

) : ListAdapter<News, NewsAdapter.ViewHolder>(NewsDiffCallBack) {

    inner class ViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    // Methode zum Erstellen eines ViewHolders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


//    fun setData(newData: List<News>) {
//        this.dataset = newData
//        notifyDataSetChanged()
//    }


    // Methode zum Festlegen der Anzahl von Elementen in der Liste
    override fun getItemCount(): Int {
        return dataset.size
    }


    // Methode zum Binden von Daten an die ViewHolders
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]

        // Titel setzen
        holder.binding.newsTV.setText(item.title)

        // Bild laden
        holder.binding.newsAvatarIV.load(item.teaserImage.imageVariants.image144)

        // Click-Listener für die RecyclerView-Elemente
        holder.binding.newsCV.setOnClickListener {
            //Mit der ID zum DetailFragment navigieren
            holder.itemView.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(dataset[position].sophoraId!!))
        }


        // Bestimmt, ob der Artikel als Favorit markiert ist oder nicht
        val likeArtikel = if (item.isLiked) R.drawable.baseline_star_24
        else R.drawable.baseline_star_outline_24
        holder.binding.likeBTN.setImageResource(likeArtikel)

        // Click-Listener für das Favoriten-Icon
        holder.binding.likeBTN.setOnClickListener {
            val like = dataset[position]
            like.isLiked = !like.isLiked

            val listState = layoutManager?.onSaveInstanceState()
            listState?.let { viewModel.saveListState(it) }
            //Datenbank updaten
            viewModel.updateLikestatusInDb(like)
        }
    }
}


// Benötigt für Listadadapter
// Callback für die Differenzierung von Nachrichten
object NewsDiffCallBack : DiffUtil.ItemCallback<News>() {
    override fun areItemsTheSame(oldItem: News, newItem: News) =
        oldItem.externalId == newItem.externalId

    override fun areContentsTheSame(oldItem: News, newItem: News) = oldItem == newItem
}
