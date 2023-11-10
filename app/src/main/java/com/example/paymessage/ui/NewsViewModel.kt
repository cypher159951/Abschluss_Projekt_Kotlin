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

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val tagesschauDatabase = TagesschauDataBase(application)
    private val repositoryCallback = NewsRepositoryCallback(application)

    // Instanz des Repositories, das die Schnittstelle zwischen Datenbank und API bildet.
    private val repository =
        AppRepository(application, repositoryCallback, TagesschauApi, tagesschauDatabase)
    var newsDataList: LiveData<List<News>> = repository.newsDataList
    val favoriteDataList: LiveData<List<News>> = repository.getliked()
    val newsDetail = repository.newsdetail

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

    fun loadNewsDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = repository.getNewsDetail(id)
            newsDetail.postValue(detail)
        }
    }

    fun updateLikestatusInDb(isliked: News) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLikeStatus(isliked)
        }
    }

    // Funktion für pull-to-refresh, um die neuesten News abzurufen.
    fun refreshNews() {
        viewModelScope.launch(Dispatchers.IO) {
            loadData()
        }
    }
}


