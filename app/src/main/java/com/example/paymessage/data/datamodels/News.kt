package com.example.paymessage.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paymessage.data.database.Tagesschau
import com.squareup.moshi.Json
import java.net.URL


/**
 * Eine Datenklasse, die eine Tabelle in der Datenbank repräsentiert und Nachrichteninformationen enthält.
 * @param sophoraId Die ID des Nachrichtenelements.
 * @param externalId Die externe ID des Nachrichtenelements.
 * @param title Der Titel der Nachricht.
 * @param date Das Datum, an dem die Nachricht veröffentlicht wurde.
 * @param content Eine Liste von Inhalten, die in der Nachricht enthalten sind. Standardmäßig eine leere Liste.
 * @param teaserImage Ein Teaser-Bild, das mit der Nachricht verknüpft ist.
 * @param isLiked Gibt an, ob die Nachricht als "geliked" markiert ist. Standardmäßig auf "false" gesetzt.
 */

@Entity(tableName = "tagesschau")
data class News(
    @PrimaryKey
    val sophoraId: String,
    val externalId: String,
    val title: String,
    val date: String,
    val content: List<Content> = emptyList(),
    val teaserImage: TeaserImage,
    val updateCheckUrl: String,



    var isLiked: Boolean = false,
    )



/**
 * Eine Datenklasse, die ein Bild in verschiedenen Größen repräsentiert.
 * @param image144 Das Bild in der Größe 16x9-960, gespeichert als String.
 */

data class ImageVariant(
    @Json(name = "16x9-960") val image144: String
)