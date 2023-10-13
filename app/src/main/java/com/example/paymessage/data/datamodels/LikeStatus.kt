package com.example.paymessage.data.datamodels


/**
 * Eine einfache Datenklasse, die den Like-Status eines Elements repräsentiert.
 * @param isLiked Gibt an, ob das Element als "geliked" markiert ist. Standardmäßig auf "false" gesetzt.
 */
data class LikeStatus(
    var isLiked: Boolean = false
)
