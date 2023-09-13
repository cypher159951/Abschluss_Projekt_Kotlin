package com.example.paymessage.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tagesschau")
data class NewsData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val news: List<News>,

   // val teaserImage: List<teaserImage>,

    var isLiked: Boolean = false
)

