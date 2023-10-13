package com.example.paymessage.data.datamodels


/**
 * Eine einfache Datenklasse, die den Inhalt eines Elements repräsentiert.
 * @param value Der Wert des Inhalts als String. Standardmäßig leer.
 * @param type Der Typ des Inhalts als String.
 */
data class Content(
    val value: String = "",
    val type: String,
    )