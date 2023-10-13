package com.example.paymessage.data.database

/**
 * Eine Datenklasse, die regionale Informationen repräsentiert.
 * @param breakingNews Gibt an, ob es sich um eine Eilmeldung handelt.
 * @param details Die Details im Zusammenhang mit der Region als String.
 * @param detailsweb Die Details im Web im Zusammenhang mit der Region als String.
 * @param externalId Die externe ID der Region als String.
 * @param firstSentence Der erste Satz im Zusammenhang mit der Region als String.
 * @param geotags Die Geotags im Zusammenhang mit der Region als String.
 * @param regionId Die ID der Region als String.
 * @param shareURL Die URL zum Teilen im Zusammenhang mit der Region als String.
 * @param sophoraId Die Sophora-ID im Zusammenhang mit der Region als String.
 * @param title Der Titel im Zusammenhang mit der Region als String.
 * @param topline Die Überschrift im Zusammenhang mit der Region als String.
 * @param type Der Typ im Zusammenhang mit der Region als String.
 * @param updateCheckUrl Die URL zur Aktualisierungsüberprüfung im Zusammenhang mit der Region als String.
 */
data class Regional(
    val breakingNews: Boolean,
    val details: String,
    val detailsweb: String,
    val externalId: String,
    val firstSentence: String,
    val geotags: String,
    val regionId: String,
    val shareURL: String,
    val sophoraId: String,
    val title: String,
    val topline: String,
    val type: String,
    val updateCheckUrl: String
)