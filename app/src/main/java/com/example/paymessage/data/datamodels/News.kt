package com.example.paymessage.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey


data class News(

    val sophoraId: String,
    val externalId: String,
    val title: String,
    val date: String,


)

data class teaserImage(
    val alttext: String,
    )

