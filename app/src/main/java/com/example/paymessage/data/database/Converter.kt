package com.example.paymessage.data.database

import androidx.room.TypeConverter
import com.example.paymessage.data.datamodels.Content
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TeaserImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    //--------------------------------------------------------------------------------------------
    // Wird nicht benötigt
    // Methode zur Konvertierung von String in eine Liste von News-Objekten
    @TypeConverter
    fun fromNews(value: String?): List<News?>? {
        val listType = object : TypeToken<List<News?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    // Methode zur Konvertierung einer Liste von News-Objekten in einen String
    @TypeConverter
    fun fromArrayList(list: List<News?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    //--------------------------------------------------------------------------------------------
    // Methode zur Konvertierung von String in ein TeaserImage-Objekt
    @TypeConverter
    fun fromStringToTeaserImage(value: String?): TeaserImage? {
        val listType = object : TypeToken<TeaserImage?>() {}.type
        return Gson().fromJson(value, listType)
    }

    // Methode zur Konvertierung eines TeaserImage-Objekts in einen String
    @TypeConverter
    fun fromTeaserImageToString(list: TeaserImage?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


    //--------------------------------------------------------------------------------------------
    // Methode zur Konvertierung von String in eine Liste von Content-Objekten
    @TypeConverter
    fun fromStringToContent(value: String?): List<Content?>? {
        val listType = object : TypeToken<List<Content?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    // Methode zur Konvertierung einer Liste von Content-Objekten in einen String
    @TypeConverter
    fun fromContentToString(list: List<Content?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


}