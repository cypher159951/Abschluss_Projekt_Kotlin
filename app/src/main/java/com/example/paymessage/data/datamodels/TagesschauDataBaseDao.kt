package com.example.paymessage.data.datamodels


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagesschauDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertall(news: News)

    @Query("SELECT COUNT(*) FROM tagesschau ")
    fun checkDataCount(): Int

    @Update
    fun updateItem(news: News)

    @Delete
    fun deleteItem(news: News)

    @Query("SELECT * FROM tagesschau WHERE sophoraId = :itemId")
    fun getItemById(itemId: String): News


    @Query("SELECT * FROM tagesschau WHERE isLiked = 1")
    fun getLiked(): LiveData<List<News>>


    //ASC für aufsteigend
    @Query("SELECT * FROM tagesschau ORDER by date DESC")
    fun getAllItems(): LiveData<List<News>>

    @Query("DELETE FROM tagesschau WHERE date < :date")
    fun deleteOldItems(date: String)



}