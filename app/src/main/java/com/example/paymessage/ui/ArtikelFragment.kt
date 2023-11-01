package com.example.paymessage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.paymessage.data.AppRepository
import com.example.paymessage.databinding.FragmentArtikelBinding

// Ein Fragment, das dazu dient, einen einzelnen Artikel in der Anwendung anzuzeigen.
class ArtikelFragment : Fragment() {

    // Initialisierung des ViewModels, das für die Anzeige des Artikels verantwortlich ist.
    private val viewModel: NewsViewModel by activityViewModels()

    // Eine Instanz der View-Bindungsklasse für das Fragment.
    private lateinit var binding: FragmentArtikelBinding


    // Die Methode, die das Layout des Fragments erstellt und zurückgibt.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtikelBinding.inflate(inflater, container, false)
        return binding.root
    }


    // Die Methode, die aufgerufen wird, nachdem die Ansicht des Fragments erstellt wurde.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Überprüfen, ob Argumente vorhanden sind, und Laden der Detailinformationen für den Artikel.
        arguments?.let {
            val id = it.getString("id")
            if (id != null) {
                viewModel.loadNewsDetail(id)
            }
            viewModel.newsDetail.observe(viewLifecycleOwner) {
                if (it.content.isNotEmpty()) {


                    val htmlCodeTitle = it.content[0].value
                    binding.titleArtikelTV.text =
                        htmlCodeTitle.replace("<strong>", "").replace("</strong>", "")
                    // Setzen des Titels und Inhalts des Artikels in die entsprechenden Ansichtselemente.
                    //   binding.titleArtikelTV.text = it.content[0].value

                    var text: String = ""
                    for (i in 1..it.content.size - 1) {
                        text += it.content[i].value
                    }


                    // Hier iteriere ich durch diese Liste und führe die Ersetzungen in filteredHtml durch.
                    // Das Ergebnis wird dann dem artikelTV zugewiesen.
                    val htmlCode = text
                    val replacements = listOf(
                        "<ul" to "",
                        "<li" to "",
                        "</li" to " ",
                        "</ul>" to "",
                        "<h2>" to "",
                        "</h2>" to "\n",
                        ">>" to "\n\n",
                        "<em>" to "",
                        "</em>" to "",
                        "<strong>" to "",
                        "</strong>" to "",
                        "</a" to "",
                        "<br" to "",
                        "/>" to ""
                    )

                    var filteredHtml = htmlCode
                    for ((oldValue, newValue) in replacements) {
                        filteredHtml = filteredHtml.replace(oldValue, newValue)
                    }
                    binding.artikelTV.text = filteredHtml


                    // Laden und Anzeigen des Teaser-Bilds des Artikels.
                    binding.artikelImageIV.load(it.teaserImage.imageVariants.image144)
                }
            }
        }

        // Setzen des Klick-Listeners für die Zurück-Schaltfläche, um zur vorherigen Ansicht zu navigieren.
        binding.backBTNIV.setOnClickListener {
            val navController = findNavController()
            navController.navigate(ArtikelFragmentDirections.actionArtikelFragmentToHomeFragment())
        }
    }
}