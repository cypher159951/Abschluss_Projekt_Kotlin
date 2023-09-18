package com.example.paymessage.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

data class NewsData(
    val news: List<News>,

    val teaserImage: List<teaserImage>,

    var isLiked: Boolean = false
)

