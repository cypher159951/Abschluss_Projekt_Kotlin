package com.example.paymessage.api

import com.example.paymessage.data.database.Tagesschau
import com.example.paymessage.data.datamodels.NewsData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://www.tagesschau.de"

// Moshi konvertiert Serverantworten in Kotlin Objekte
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit übernimmt die Kommunikation und übersetzt die Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Das Interface bestimmt, wie mit dem Server kommuniziert wird
interface ApiService {

    /**
     * Diese Funktion spezifiziert die URL und holt die Liste an Informationen
     */
    @GET("/api2/homepage/")
    suspend fun getNews(): NewsData
}

// Das Objekt dient als Zugangspunkt für den Rest der App und stellt den API Service zur Verfügung
object TagesschauApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}