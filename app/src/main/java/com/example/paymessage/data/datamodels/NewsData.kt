package com.example.paymessage.data.datamodels


/**
 * Eine Datenklasse, die eine Liste von Nachrichten und den Like-Status repräsentiert.
 * @param news Eine Liste von Nachrichten.
 * @param isLiked Gibt an, ob die Liste als "geliked" markiert ist. Standardmäßig auf "false" gesetzt.
 */
data class NewsData(
    val news: List<News>,
    var isLiked: Boolean = false
)

