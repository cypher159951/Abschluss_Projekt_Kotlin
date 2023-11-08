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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

// Definiert ein Schlüsselwort für das Tagging von Lognachrichten.
const val TAG = "RepositoryTAG"

// 30 Minuten in Millisekunden
private const val UPDATE_INTERVAL: Long = 5000


// Definiert ein Schlüsselwort für das Tagging von Lognachrichten.
class AppRepository(
    private val context: Context,
    private val callback: RepositoryCallback,
    val api: TagesschauApi,
    private val newsDatabase: TagesschauDataBase,

) {

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
        startDataUpdate()
        observeDatabase()
    }


    // LiveData zur Verfolgung von Änderungen in der Datenbank
    // private val isDatabaseUpdated = MutableLiveData(false)
    private var appInitialStart = true
    private var databaseChanged = false
    private fun observeDatabase() {
        newsDataList.observeForever { newsList ->
            // Hier wird der Code ausgeführt, wenn sich die Datenbank ändert      && isDatabaseUpdated.value == true
            if (newsList.isNotEmpty() && !appInitialStart && databaseChanged) {
                if (isInitialData()) {
                    // Code für die Benachrichtigung implementieren
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

    //Keine Pushbenachrichtigung beim Start der App
    private fun isInitialData(): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("IS_INITIAL_DATA", false)
    }



    // Eine Variable, um den initialen Start der App zu verfolgen

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
                // Holen Sie die Daten von der API
                val newsFromApi = api.retrofitService.getNews().news

                // Löschen Sie alte Daten
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
                            // Neue Daten in die Datenbank einfügen
                            insertNewsFromApi(new)
                        }
                    }
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