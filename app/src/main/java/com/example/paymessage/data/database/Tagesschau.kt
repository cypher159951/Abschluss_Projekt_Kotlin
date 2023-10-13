package com.example.paymessage.data.database


/**
 * Eine Datenklasse, die Informationen zu Tagesschau-Nachrichten enthält.
 * @param newStoriesCountLink Der Link zur Anzahl neuer Geschichten als String.
 * @param news Eine Liste von Objekten des Typs New, die die Nachrichten enthalten.
 * @param regional Eine Liste von Objekten des Typs Regional, die regionale Informationen enthalten.
 * @param type Der Typ der Tagesschau als String.
 * @param isLiked Gibt an, ob die Tagesschau als "geliked" markiert ist. Standardmäßig auf "false" gesetzt.
 */

data class Tagesschau(
    val newStoriesCountLink: String,
    val news: List<New>,
    val regional: List<Regional>,
    val type: String,

    var isLiked: Boolean = false
)