package com.example.paymessage.data.database

import androidx.room.TypeConverter
import com.example.paymessage.data.datamodels.Content
import com.example.paymessage.data.datamodels.News
import com.example.paymessage.data.datamodels.TeaserImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    //wird nicht benötigt
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


    //Converter für ImageBilder
    @TypeConverter
    fun fromStringToTeaserImage(value: String?): TeaserImage? {
        val listType = object : TypeToken<TeaserImage?>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromTeaserImageToString(list: TeaserImage?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }



    //Converter für den Content
    @TypeConverter
    fun fromStringToContent(value: String?): List<Content?>? {
        val listType = object : TypeToken<List<Content?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromContentToString(list: List<Content?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


}