package com.example.paymessage.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.database.Tagesschau
import com.example.paymessage.data.datamodels.NewsData
import com.example.paymessage.data.datamodels.TagesschauDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "RepositoryTAG"
class AppRepository(val api: TagesschauApi, private val newsDatabase: TagesschauDataBase) {

    private val _news = MutableLiveData<List<NewsData>>()

    val news: LiveData<List<NewsData>>
        get() = _news

    private val _newsdetail = MutableLiveData<NewsData>()
    val newsdetail: MutableLiveData<NewsData>
        get() = _newsdetail


    suspend fun getNews() {
        try {
            val news = api.retrofitService.getNews()
            _news.postValue(listOf(news))
            insertNewsFromApi(news)
            Log.d(TAG, "getNews Data: $news")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Data from API: $e")
        }
    }
    fun insertNewsFromApi(itemData: NewsData) {
        try {
            GlobalScope.launch {
                Log.d(ContentValues.TAG, "getItems Data: $itemData")
                newsDatabase.dao.insertall(itemData)
            }
        } catch (e: java.lang.Exception) {
            Log.d(ContentValues.TAG, "Error inserting facts from API into database: $e")
        }
    }
    fun getAll(): LiveData<List<NewsData>> {
        return newsDatabase.dao.getAllItems()
    }

    fun getNewsDetail(id:Long):NewsData{
        return newsDatabase.dao.getItemById(id)
    }

    fun updateLikeStatus(isLike: NewsData){
        try {
            GlobalScope.launch {
                newsDatabase.dao.updateItem(isLike)
            }
        } catch (e: Exception){
            Log.e(ContentValues.TAG, "Error updating like Status in data")
        }
    }

    fun getliked(): LiveData<List<NewsData>> {
        return newsDatabase.dao.getLiked()
    }
}