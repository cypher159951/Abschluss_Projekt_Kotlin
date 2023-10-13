package com.example.paymessage.data

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.datamodels.Content
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TagesschauDataBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale

// Definiert ein Schlüsselwort für das Tagging von Lognachrichten.
const val TAG = "RepositoryTAG"

// Definiert ein Schlüsselwort für das Tagging von Lognachrichten.
class AppRepository(val api: TagesschauApi, private val newsDatabase: TagesschauDataBase) {

    // Mutable LiveData-Instanz für News-Objekte.
    private val _news = MutableLiveData<List<News>>()

    // LiveData-Instanz, die von außerhalb lesbar ist und auf die _news verweist.
    val news: LiveData<List<News>>
        get() = _news


    // Mutable LiveData-Instanz für ein einzelnes News-Objekt.
    private val _newsdetail = MutableLiveData<News>()

    // LiveData-Instanz, die von außerhalb lesbar ist und auf _newsdetail verweist.
    val newsdetail: MutableLiveData<News>
        get() = _newsdetail


    // LiveData-Liste von News-Objekten, die aus der Datenbank gelesen werden.
    var newsDataList: LiveData<List<News>>


    // Initialisierung der newsDataList durch Abrufen aller Elemente aus der Datenbank.
    init {
        newsDataList = newsDatabase.dao.getAllItems()
    }


    // Eine suspend-Funktion, die News von der API abruft und in die Datenbank einfügt.
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


    // Eine Funktion, die ein einzelnes News-Objekt in die Datenbank einfügt.
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

    // Eine Funktion, die alte Datensätze in der Datenbank löscht.
    fun deleteOldData() {

        //Kalender erstellen
        val twoDaysAgo = Calendar.getInstance()
        //2 Tage vom aktuellen Datum zurück gehen
        twoDaysAgo.add(Calendar.DAY_OF_YEAR, -2)

        //Kalender umforamtieren damit es mit dem in der Datenbank übereinstimmt
        val formattedTwoDaysAgo =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(twoDaysAgo.time)

        //Daten aus der Datenbank löschen die älter als 2 Tage sind
        try {
            newsDatabase.dao.deleteOldItems(formattedTwoDaysAgo)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting old data from the database: $e")
        }
    }


    // Eine Funktion, die ein einzelnes News-Objekt anhand seiner ID aus der Datenbank abruft.
    fun getNewsDetail(id: String): News {
        return newsDatabase.dao.getItemById(id)
    }


    // Eine Funktion, die den Like-Status eines News-Objekts in der Datenbank aktualisiert.
    fun updateLikeStatus(isLike: News) {
        try {
            GlobalScope.launch {
                newsDatabase.dao.updateItem(isLike)
            }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error updating like Status in data")
        }
    }


    // Eine Funktion, die alle als 'liked' markierten News-Objekte aus der Datenbank abruft.
    fun getliked(): LiveData<List<News>> {
        return newsDatabase.dao.getLiked()
    }




}






