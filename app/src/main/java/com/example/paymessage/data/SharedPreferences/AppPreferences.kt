package com.example.paymessage.data.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    var isNightModeEnabled: Boolean
        get() = sharedPreferences.getBoolean("isChecked", false)
        set(value) {
            sharedPreferences.edit().putBoolean("isChecked", value).apply()
        }

    companion object {
        fun getNightMode(context: Context): Boolean {
            val sharedPreferences =
                context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("isChecked", false)
        }
    }
}