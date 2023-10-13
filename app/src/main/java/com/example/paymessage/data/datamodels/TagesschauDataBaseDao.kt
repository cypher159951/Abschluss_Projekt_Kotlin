package com.example.paymessage.data.datamodels


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


// Definiert eine DAO-Schnittstelle für den Zugriff auf die Tagesschau-Datenbank.
@Dao
interface TagesschauDataBaseDao {

    // Fügt Nachrichten in die Datenbank ein. Bei Konflikt wird das bestehende Element ersetzt.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertall(news: News)


    // Ermittelt die Anzahl der Datensätze in der 'tagesschau'-Tabelle
    @Query("SELECT COUNT(*) FROM tagesschau ")
    fun checkDataCount(): Int


    // Aktualisiert ein bestimmtes Element in der Datenbank.
    @Update
    fun updateItem(news: News)


    // Ruft ein Element anhand seiner 'sophoraId' aus der Datenbank ab.
    @Query("SELECT * FROM tagesschau WHERE sophoraId = :itemId")
    fun getItemById(itemId: String): News


    // Ruft alle als 'liked' markierten Elemente aus der Datenbank ab.
    @Query("SELECT * FROM tagesschau WHERE isLiked = 1")
    fun getLiked(): LiveData<List<News>>


    // ASC für aufsteigend
    // Ruft alle Elemente aus der Datenbank in absteigender Reihenfolge nach 'date' ab.
    @Query("SELECT * FROM tagesschau ORDER by date DESC")
    fun getAllItems(): LiveData<List<News>>


    // Löscht Elemente aus der Datenbank, die älter als das angegebene Datum sind.
    @Query("DELETE FROM tagesschau WHERE date < :date")
    fun deleteOldItems(date: String)



}