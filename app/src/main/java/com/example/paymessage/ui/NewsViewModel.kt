package com.example.paymessage.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.AppRepository
import com.example.paymessage.data.database.Tagesschau
import com.example.paymessage.data.datamodels.NewsData
import com.example.paymessage.data.datamodels.TagesschauDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val tagesschauDatabase = TagesschauDataBase(application)
    private val repository = AppRepository(TagesschauApi, tagesschauDatabase)



    val newsDataList: LiveData<List<NewsData>> = repository.getAll()

    val favoriteDataList: LiveData<List<NewsData>> = repository.getliked()

    val news = repository.news

    val newsDetail = repository.newsdetail


    init {
        loadData()
        repository.getAll()
    }

    fun loadData() {
        viewModelScope.launch {
            repository.getNews()
        }
    }

    fun insertDataFromApi(itemData: NewsData) {
        viewModelScope.launch {
            repository.insertNewsFromApi(itemData)
        }
    }

    fun loadFactsDetail(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = repository.getNewsDetail(id)

            newsDetail.postValue(detail)
        }
    }

    fun updateLikestatusInDb(isliked: NewsData){
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


