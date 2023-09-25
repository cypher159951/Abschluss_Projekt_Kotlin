package com.example.paymessage.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

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



    val newsDataList: LiveData<List<News>> = repository.getAll()

    val favoriteDataList: LiveData<List<News>> = repository.getliked()

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


