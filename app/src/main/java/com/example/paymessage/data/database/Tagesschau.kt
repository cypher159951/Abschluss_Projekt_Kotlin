package com.example.paymessage.data.database

data class Tagesschau(
    val newStoriesCountLink: String,
    val news: List<New>,
    val regional: List<Regional>,
    val type: String,

    var isLiked: Boolean = false
)