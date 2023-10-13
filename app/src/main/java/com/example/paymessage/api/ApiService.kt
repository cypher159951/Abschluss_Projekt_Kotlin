package com.example.paymessage.api

import com.example.paymessage.data.datamodels.NewsData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Die Basis-URL für die Kommunikation mit dem Server
const val BASE_URL = "https://www.tagesschau.de"

// Moshi wird verwendet, um Serverantworten in Kotlin-Objekte zu konvertieren
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit wird für die Kommunikation mit dem Server verwendet und übersetzt die Antwort
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Das Interface definiert die API-Endpunkte und die Kommunikation mit dem Server
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