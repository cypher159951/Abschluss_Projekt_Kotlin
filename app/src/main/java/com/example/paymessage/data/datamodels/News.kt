package com.example.paymessage.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tagesschau")

data class News(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sophoraId: String,
    val externalId: String,
    val title: String,
    val date: String,
    var isLiked: Boolean = false


)

data class teaserImage(
    val alttext: String,
    )

