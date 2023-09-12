package com.example.paymessage.data.database

import androidx.room.TypeConverter
import com.example.paymessage.data.datamodels.News
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromNews(value: String?): List<News?>? {
        val listType = object : TypeToken<List<News?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromArrayList(list: List<News?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


//    @TypeConverter
//    fun fromNews(value: String?): List<News?>? {
//        val listType = object : TypeToken<List<News?>?>() {}.type
//        return Gson().fromJson(value, listType)
//    }
//    @TypeConverter
//    fun fromArrayList(list: List<News?>?): String? {
//        val gson = Gson()
//        return gson.toJson(list)
//    }



}