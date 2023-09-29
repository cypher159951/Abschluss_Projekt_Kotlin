package com.example.paymessage.ui

import android.annotation.SuppressLint
import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.viewModelScope
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.AppRepository
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TagesschauDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val tagesschauDatabase = TagesschauDataBase(application)
    private val repository = AppRepository(TagesschauApi, tagesschauDatabase)



    var newsDataList: LiveData<List<News>> = repository.newsDataList


    val favoriteDataList: LiveData<List<News>> = repository.getliked()

    val news = repository.news

    val newsDetail = repository.newsdetail


    init {
        loadData()
    }


    @SuppressLint("SuspiciousIndentation")
    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews()

        }
    }


    //für HomeNews position speichern
    var listStateParcel: Parcelable? = null

    fun saveListState(parcel: Parcelable) {
        listStateParcel = parcel
    }



    //für favoriten position speichern
    var listStateFavorite: Parcelable? = null

    fun saveListStateFavorite(parcel: Parcelable) {
        listStateFavorite = parcel
    }


    fun insertDataFromApi(itemData: News) {
        viewModelScope.launch {
            repository.insertNewsFromApi(itemData)
        }
    }



    fun loadNewsDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = repository.getNewsDetail(id)

            newsDetail.postValue(detail)

        }
    }

    fun updateLikestatusInDb(isliked: News){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateLikeStatus(isliked)
        }
    }

    fun loadLiked(){
        viewModelScope.launch {
            repository.getliked()
        }
    }


}


