package com.example.paymessage.data.datamodels

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.paymessage.data.database.Tagesschau

@Dao
interface TagesschauDataBaseDao {

    @Insert
    fun insertall(news: NewsData)

    @Update
    fun updateItem(news: NewsData)

    @Delete
    fun deleteItem(news: NewsData)

    @Query("SELECT * FROM tagesschau WHERE id = :itemId")
    fun getItemById(itemId: Long): NewsData


    @Query("SELECT * FROM tagesschau WHERE isLiked =1")
    fun getLiked(): LiveData<List<NewsData>>

    @Query("SELECT * FROM tagesschau")
    fun getAllItems(): LiveData<List<NewsData>>


}