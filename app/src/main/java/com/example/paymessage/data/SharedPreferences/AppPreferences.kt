package com.example.paymessage.data.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

// Eine Hilfsklasse für den Zugriff auf SharedPreferences im Zusammenhang mit Nachtmodus-Einstellungen
class AppPreferences(context: Context) {

    // SharedPreferences-Instanz für den Zugriff auf gespeicherte Daten
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    // Nachtmodus Zustand lesen und schreiben
    var isNightModeEnabled: Boolean
        get() = sharedPreferences.getBoolean("isChecked", false)
        set(value) {
            // Den Wert in den SharedPreferences speichern
            sharedPreferences.edit().putBoolean("isChecked", value).apply()
        }

    companion object {
        // Methode zum Abrufen des Nachtmodus Zustandes aus der sharedPreferences
        fun getNightMode(context: Context): Boolean {
            val sharedPreferences =
                context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("isChecked", false)
        }
    }
}