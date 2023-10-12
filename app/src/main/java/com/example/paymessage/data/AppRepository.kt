package com.example.paymessage.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TagesschauDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "RepositoryTAG"

class AppRepository(val api: TagesschauApi, private val newsDatabase: TagesschauDataBase) {

    private val _news = MutableLiveData<List<News>>()

    val news: LiveData<List<News>>
        get() = _news

    private val _newsdetail = MutableLiveData<News>()
    val newsdetail: MutableLiveData<News>
        get() = _newsdetail

    var newsDataList: LiveData<List<News>>

    init {
        newsDataList = newsDatabase.dao.getAllItems()
    }


    suspend fun getNews() {
        try {
            //  if (checkData() == 0) {
            val news = api.retrofitService.getNews().news
//            val sortedNewsList = news.sortedBy {
//                it.date
//            }
      //      Log.d("sorteslisttest", "$sortedNewsList")
      //      //  _news.postValue(news)
            for (oneNews in news) {
                insertNewsFromApi(oneNews)
            }
            //    }

            Log.d(TAG, "getNews Data: ${news.sortedByDescending { it.date }.map { it.title }}")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Data from API: $e")
        }
    }


    fun insertNewsFromApi(itemData: News) {
        try {
            GlobalScope.launch {
               // var sortedNewsList = itemData.value?.sortedByDescending
                Log.d(ContentValues.TAG, "getItems Data: $itemData")
                newsDatabase.dao.insertall(itemData)
            }
        } catch (e: java.lang.Exception) {
            Log.d(ContentValues.TAG, "Error inserting news from API into database: $e")
        }
    }

    fun getAll(): LiveData<List<News>> {
        return newsDatabase.dao.getAllItems()
    }

    fun checkData(): Int {
        return newsDatabase.dao.checkDataCount()
    }


    fun getNewsDetail(id: String): News {
        return newsDatabase.dao.getItemById(id)
    }

    fun updateLikeStatus(isLike: News) {
        try {
            GlobalScope.launch {
                newsDatabase.dao.updateItem(isLike)
            }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error updating like Status in data")
        }
    }

    fun getliked(): LiveData<List<News>> {
        return newsDatabase.dao.getLiked()
    }


    //Nachrichten nach Datum / Date sortieren
//    fun getSortedNews(): MutableLiveData<List<News>?> {
//        val sortedNewsLiveData = MutableLiveData<List<News>?>()
//        GlobalScope.launch(Dispatchers.IO) {
//            // Daten aus der Datenbank holen
//            val allNews = newsDatabase.dao.getAllItems()
//            // Nach Datum absteigend sortieren
//            val sortedNews = allNews.value?.sortedByDescending { it.date }
//            withContext(Dispatchers.Main) {
//                sortedNewsLiveData.value = sortedNews
//                Log.e("sortedNewsTest", "$allNews")
//            }
//        }
//        return sortedNewsLiveData
//    }
}



