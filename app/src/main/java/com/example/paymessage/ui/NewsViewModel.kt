package com.example.paymessage.ui

import android.annotation.SuppressLint
import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import androidx.lifecycle.viewModelScope
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.AppRepository
import com.example.paymessage.data.autoRefresh.NewsRepositoryCallback
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TagesschauDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Eine Klasse, die als ViewModel für die News-Funktionalität der Anwendung fungiert.
class NewsViewModel(application: Application) : AndroidViewModel(application) {

    // Instanz der Datenbank für Tagesschau.
    private val tagesschauDatabase = TagesschauDataBase(application)

    // Instanz des Repository-Callbacks
    private val repositoryCallback = NewsRepositoryCallback()

    // Instanz des Repositories, das die Schnittstelle zwischen Datenbank und API bildet.
    private val repository = AppRepository(repositoryCallback, TagesschauApi, tagesschauDatabase)

    // LiveData-Liste von News-Objekten, die aus dem Repository abgerufen werden.
    var newsDataList: LiveData<List<News>> = repository.newsDataList

    // LiveData-Liste von als 'liked' markierten News-Objekten, die aus dem Repository abgerufen werden.
    val favoriteDataList: LiveData<List<News>> = repository.getliked()

    // LiveData-Objekt, das das Detail einer einzelnen News aus dem Repository enthält.
    val newsDetail = repository.newsdetail


    // Initialisierungsfunktion, die die Daten lädt, wenn das ViewModel erstellt wird.
    init {
        loadData()
    }


    // Funktion, die Daten vom Repository abruft und lädt.
    @SuppressLint("SuspiciousIndentation")
    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews()

        }
    }


    // Variable zum Speichern des Zustands der Liste für HomeNews. - Hilfe von Delrich
    var listStateParcel: Parcelable? = null


    // Funktion zum Speichern des Listenzustands für HomeNews.   - Hilfe von Delrich
    fun saveListState(parcel: Parcelable) {
        listStateParcel = parcel
    }


    // Variable zum Speichern des Zustands der Liste für Favoriten.
    var listStateFavorite: Parcelable? = null


    // Funktion zum Speichern des Listenzustands für Favoriten.
    fun saveListStateFavorite(parcel: Parcelable) {
        listStateFavorite = parcel
    }


    // Funktion zum Laden von Detailinformationen zu einer bestimmten News.
    fun loadNewsDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = repository.getNewsDetail(id)
            newsDetail.postValue(detail)
        }
    }


    // Funktion zum Aktualisieren des Like-Status eines News-Objekts in der Datenbank.
    fun updateLikestatusInDb(isliked: News) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLikeStatus(isliked)
        }
    }


    // Funktion für pull-to-refresh, um die neuesten News abzurufen.
    fun refreshNews() {
        viewModelScope.launch(Dispatchers.IO) {

            repository.getNews()

        }
    }
}


