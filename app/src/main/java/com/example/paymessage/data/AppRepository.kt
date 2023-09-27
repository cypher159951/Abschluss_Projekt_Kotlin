package com.example.paymessage.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TagesschauDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "RepositoryTAG"

class AppRepository(val api: TagesschauApi, private val newsDatabase: TagesschauDataBase) {

    private val _news = MutableLiveData<List<News>>()

    val news: LiveData<List<News>>
        get() = _news

    private val _newsdetail = MutableLiveData<News>()
    val newsdetail: MutableLiveData<News>
        get() = _newsdetail


    suspend fun getNews() {
        try {
            val news = api.retrofitService.getNews().news
            _news.postValue(news)
            for (oneNews in news) {
                insertNewsFromApi(oneNews)
            }
            Log.d(TAG, "getNews Data: $news")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Data from API: $e")
        }
    }


    fun insertNewsFromApi(itemData: News) {
        try {
            GlobalScope.launch {
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
}