package com.example.paymessage.data.database


/**
 * Eine Datenklasse, die Informationen zu einer Nachricht repräsentiert.
 * @param breakingNews Gibt an, ob es sich um eine Eilmeldung handelt.
 * @param copyright Das Urheberrecht im Zusammenhang mit der Nachricht als String.
 * @param details Die Details der Nachricht als String.
 * @param detailsweb Die Details der Nachricht im Web als String.
 * @param externalId Die externe ID der Nachricht als String.
 * @param firstSentence Der erste Satz der Nachricht als String.
 * @param geotags Die Geotags im Zusammenhang mit der Nachricht als String.
 * @param regionId Die ID der Region im Zusammenhang mit der Nachricht als String.
 * @param ressort Das Ressort im Zusammenhang mit der Nachricht als String.
 * @param shareURL Die URL zum Teilen der Nachricht als String.
 * @param sophoraId Die Sophora-ID im Zusammenhang mit der Nachricht als String.
 * @param title Der Titel der Nachricht als String.
 * @param topline Die Überschrift im Zusammenhang mit der Nachricht als String.
 * @param type Der Typ der Nachricht als String.
 * @param updateCheckUrl Die URL zur Aktualisierungsüberprüfung im Zusammenhang mit der Nachricht als String.
 */

data class New(
    val breakingNews: Boolean,
    val copyright: String,
    val details: String,
    val detailsweb: String,
    val externalId: String,
    val firstSentence: String,
    val geotags: String,
    val regionId: String,
    val ressort: String,
    val shareURL: String,
    val sophoraId: String,
    val title: String,
    val topline: String,
    val type: String,
    val updateCheckUrl: String
)