package com.example.paymessage.data.datamodels


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TagesschauDataBaseDao {

    @Insert
    fun insertall(news: News)


    @Update
    fun updateItem(news: News)

    @Delete
    fun deleteItem(news: News)

    @Query("SELECT * FROM tagesschau WHERE id = :itemId")
    fun getItemById(itemId: Long): News


    @Query("SELECT * FROM tagesschau WHERE isLiked =1")
    fun getLiked(): LiveData<List<News>>

    @Query("SELECT * FROM tagesschau")
    fun getAllItems(): LiveData<List<News>>


}