package com.example.paymessage.data

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.paymessage.api.TagesschauApi
import com.example.paymessage.data.autoRefresh.RepositoryCallback
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TagesschauDataBase
import com.example.paymessage.data.pushNotification.NotificationHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

const val TAG = "RepositoryTAG"
private const val UPDATE_INTERVAL: Long = 5000


class AppRepository(
    private val context: Context,
    private val callback: RepositoryCallback,
    val api: TagesschauApi,
    private val newsDatabase: TagesschauDataBase,

    ) {

    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>>
        get() = _news


    private val _newsdetail = MutableLiveData<News>()
    val newsdetail: MutableLiveData<News>
        get() = _newsdetail


    var newsDataList: LiveData<List<News>>


    // Initialisierung der newsDataList durch Abrufen aller Elemente aus der Datenbank.
    init {
        newsDataList = newsDatabase.dao.getAllItems()
        startDataUpdate()
        observeDatabase()
    }


    // LiveData zur Verfolgung von Änderungen in der Datenbank
    private var appInitialStart = true
    private var databaseChanged = false
    private fun observeDatabase() {
        newsDataList.observeForever { newsList ->
            // Hier wird der Code ausgeführt, wenn sich die Datenbank ändert
            if (newsList.isNotEmpty() && !appInitialStart && databaseChanged) {
                if (isInitialData() && hasNewNews(newsList)) {
                    //Benachrichtigung implementieren
                    val notificationHandler = NotificationHandler(context)
                    notificationHandler.displayNotification(
                        "Neue Nachrichten", "Schau dir die aktuellen News an."
                    )
                }
            }
            appInitialStart = false
            databaseChanged = true
        }
    }


    // Überprüfe, ob es neue Nachrichten gibt, die noch nicht angezeigt wurden
    private fun hasNewNews(newsList: List<News>): Boolean {
        val latestNewsId = newsList.firstOrNull()?.sophoraId ?: ""
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val lastDisplayedNewsId = sharedPreferences.getString("LAST_DISPLAYED_NEWS_ID", "")

        return latestNewsId != lastDisplayedNewsId
    }

    //Keine Pushbenachrichtigung beim Start der App
    private fun isInitialData(): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("IS_INITIAL_DATA", false)
    }


    // Startet die automatische Aktualisierung der Daten von der API
    private fun startDataUpdate() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Log.d(TAG, "Automatische Aktualisierung gestartet")
                // Führe hier Aktualisierungsaktionen über die API durch
                if (!appInitialStart) {
                    fetchDataFromApiAndUpdateDB()
                } else {
                    appInitialStart = false
                }
            }
        }, 0, UPDATE_INTERVAL)
    }


    private fun fetchDataFromApiAndUpdateDB() {
        // Hier die Logik zum Abrufen von Daten von der API und Aktualisieren der lokalen Datenbank einfügen
        GlobalScope.launch(Dispatchers.IO) {
            val handler = Handler(Looper.getMainLooper())

            // Zeige den Ladebalken oder die Ladekreis-Animation an
            handler.post {
                callback.showLoading()
            }

            try {
                // Daten von der API abfragen
                val newsFromApi = api.retrofitService.getNews().news
                deleteOldData()

                // Vergleiche die neuen Daten mit den in der Datenbank vorhandenen Daten
                val oldNews = newsDataList.value
                if (oldNews != null && oldNews.isNotEmpty()) {
                    for (new in newsFromApi) {
                        var isNew = true
                        for (old in oldNews) {
                            if (new.sophoraId == old.sophoraId) {
                                isNew = false
                                break
                            }
                        }
                        if (isNew) {
                            insertNewsFromApi(new)
                        }
                    }
                    // Aktualisiere die zuletzt angezeigte Nachrichten-ID
                    updateLastDisplayedNewsId(oldNews.firstOrNull()?.sophoraId ?: "")
                } else {
                    // Wenn die Datenbank leer ist, füge einfach alle Daten hinzu
                    for (oneNews in newsFromApi) {
                        insertNewsFromApi(oneNews)
                    }
                }

                // Verberge den Ladebalken oder die Ladekreis-Animation nach Abschluss des Ladevorgangs
                handler.post {
                    callback.hideLoading()
                }
                Log.d(TAG, "Automatische Aktualisierung erfolgreich")
            } catch (e: Exception) {
                Log.e(TAG, "Fehler bei der automatischen Aktualisierung: $e")
            }
        }
    }

    // Aktualisiert die zuletzt angezeigte Nachrichten-ID in den SharedPreferences.
    private fun updateLastDisplayedNewsId(lastDisplayedNewsId: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("LAST_DISPLAYED_NEWS_ID", lastDisplayedNewsId)
        editor.apply()
    }

    //News von  API abrufen und in die Datenbank einfügen
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


    // Einzelnes News-Objekt in die Datenbank einfügen
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
        val daysAgo = Calendar.getInstance()
        //7 Tage vom aktuellen Datum zurück gehen
        daysAgo.add(Calendar.DAY_OF_YEAR, -7)

        //Kalender umformatieren damit es mit dem in der Datenbank übereinstimmt
        val formattedDaysAgo =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(daysAgo.time)

        //Daten aus der Datenbank löschen die älter als 7 Tage sind
        try {
            newsDatabase.dao.deleteOldItems(formattedDaysAgo)
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting old data from the database: $e")
        }
    }


    // Eine Funktion, die ein einzelnes News-Objekt anhand seiner ID aus der Datenbank abruft.
    fun getNewsDetail(id: String): News {
        return newsDatabase.dao.getItemById(id)
    }


    // Like-Status eines News-Objekts in der Datenbank aktualisieren
    fun updateLikeStatus(isLike: News) {
        try {
            GlobalScope.launch {
                newsDatabase.dao.updateItem(isLike)
            }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error updating like Status in data")
        }
    }


    // Alle als 'liked' markierten News-Objekte aus der Datenbank abrufen
    fun getliked(): LiveData<List<News>> {
        return newsDatabase.dao.getLiked()
    }
}