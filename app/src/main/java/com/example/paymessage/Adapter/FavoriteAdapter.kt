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

    // ViewModel für die Datenverarbeitung
    private val viewModel: NewsViewModel,

    // Die Datensatzliste, die im Adapter angezeigt wird
    private var dataset: List<News>,

    // LayoutManager für die RecyclerView
    private val layoutManager: RecyclerView.LayoutManager?,

    // RecyclerView.Adapter als Basisklasse
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    // ViewHolder für die RecyclerView-Elemente
    inner class ViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    // Erstellt den ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    // Bindet die Daten an die Elemente der RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Aktuelles Element aus dem Datensatz
        val item = dataset[position]

        // Setze den Titel und das Bild des Favoriten
        holder.binding.favoriteTV.text = item.title
        holder.binding.favoriteIV.load(item.teaserImage.imageVariants.image144)

        // Bei Klick auf den Favoriten wird die Detailansicht aufgerufen
        holder.binding.favoriteArtikel.setOnClickListener {
            holder.itemView.findNavController()
                .navigate(FavoriteFragmentDirections.actionFavoriteFragmentToArtikelFragment(dataset[position].sophoraId!!))
        }


        //Share Funktion
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





        // Überprüfen und Setzen des Favoriten-Symbols basierend auf dem "liked" Status
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

    // Gibt die Anzahl der Elemente im Datensatz zurück
    override fun getItemCount(): Int {
        return dataset.size
    }
}
