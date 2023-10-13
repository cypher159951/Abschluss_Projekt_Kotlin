package com.example.paymessage.data

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
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
import java.util.Locale

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
        deleteOldData()
        try {
            val news = api.retrofitService.getNews().news
            for (oneNews in news) {
                insertNewsFromApi(oneNews)
            }
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

    suspend fun deleteOldData() {
        //Kalender erstellen
        val twoDaysAgo = Calendar.getInstance()
        //2 Tage vom aktuellen Datum zurück gehen
        twoDaysAgo.add(Calendar.DAY_OF_YEAR, -2)

        //Kalender umforamtieren damit es mit dem in der Datenbank übereinstimmt
        val formattedTwoDaysAgo = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(twoDaysAgo.time)

        //Daten aus der Datenbank löschen die älter als 2 Tage sind
        try {
            newsDatabase.dao.deleteOldItems(formattedTwoDaysAgo)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting old data from the database: $e")
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

}






