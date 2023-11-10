package com.example.paymessage.Adapter


import android.content.Intent
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
    private val viewModel: NewsViewModel,
    private var dataset: List<News>,

    // NavController für die Navigation
    private val navController: NavController,

    private val layoutManager: RecyclerView.LayoutManager?

) : ListAdapter<News, NewsAdapter.ViewHolder>(NewsDiffCallBack) {
    inner class ViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.newsTV.setText(item.title)
        holder.binding.newsAvatarIV.load(item.teaserImage.imageVariants.image144)
        holder.binding.newsCV.setOnClickListener {
            holder.itemView.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(dataset[position].sophoraId!!))
        }


        //Share Funktion
        holder.binding.shareBTN.setOnClickListener {
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


        // Bestimmt, ob der Artikel als Favorite markiert ist oder nicht
        val likeArtikel = if (item.isLiked) R.drawable.baseline_star_24
        else R.drawable.baseline_star_outline_24
        holder.binding.likeBTN.setImageResource(likeArtikel)
        holder.binding.likeBTN.setOnClickListener {

            val like = dataset[position]
            like.isLiked = !like.isLiked

            val listState = layoutManager?.onSaveInstanceState()
            listState?.let { viewModel.saveListState(it) }

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



