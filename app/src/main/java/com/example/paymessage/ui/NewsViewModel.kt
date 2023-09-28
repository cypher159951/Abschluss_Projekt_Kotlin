package com.example.paymessage.ui

import android.annotation.SuppressLint
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



    var newsDataList: LiveData<List<News>>

    val favoriteDataList: LiveData<List<News>> = repository.getliked()

    val news = repository.news

    val newsDetail = repository.newsdetail


    init {
        loadData()
         newsDataList = repository.getAll()
    }

   // var loaddata = false


    @SuppressLint("SuspiciousIndentation")
    fun loadData() {
     //   if (loaddata)
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNews()
     //         loaddata = true
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


