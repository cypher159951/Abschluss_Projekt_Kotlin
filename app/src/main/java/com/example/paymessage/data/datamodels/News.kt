package com.example.paymessage.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paymessage.data.database.Tagesschau
import com.squareup.moshi.Json


@Entity(tableName = "tagesschau")

data class News(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sophoraId: String,
    val externalId: String,
    val title: String,
    val date: String,
    val content: List<Content>,
    val teaserImage: TeaserImage,





    var isLiked: Boolean = false,


)



data class ImageVariant(

    @Json(name = "16x9-960") val image144: String

)