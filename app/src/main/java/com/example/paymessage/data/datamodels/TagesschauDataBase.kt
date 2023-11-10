package com.example.paymessage.data.datamodels

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.paymessage.data.database.Converter


/**
 * Eine abstrakte Klasse, die die Room-Datenbank repräsentiert und die zugehörigen Datenzugriffsmethoden definiert.
 * @Database definiert die Entitäten (News::class) und die Version (1) der Datenbank.
 * @TypeConverters gibt den Typenkonverter (Converter::class) an, der bei der Datenbankverarbeitung verwendet werden soll.
 */
@Database(entities = [News::class], version = 1)
@TypeConverters(Converter::class)
abstract class TagesschauDataBase : RoomDatabase() {
    abstract val dao: TagesschauDataBaseDao
}

private lateinit var INSTANCE: TagesschauDataBase

/**
 * Eine Funktion zur Erstellung der Tagesschau-Datenbank.
 * @param context Der Anwendungskontext.
 * @return Die erstellte oder bereits vorhandene Instanz der Datenbank.
 */
fun TagesschauDataBase(context: Context): TagesschauDataBase {
    synchronized(TagesschauDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TagesschauDataBase::class.java,
                "tagesschau"
            ).build()
        }
        return INSTANCE
    }
}