package com.example.paymessage.data.datamodels


/**
 * Eine einfache Datenklasse, die ein Benutzerprofil repräsentiert.
 * @param role Die Rolle des Benutzers als String. Standardmäßig leer.
 * @param extra Zusätzliche Informationen zum Benutzerprofil als String. Standardmäßig leer.
 */

data class Profile(
    val role: String = "",
    val extra: String = "",
)