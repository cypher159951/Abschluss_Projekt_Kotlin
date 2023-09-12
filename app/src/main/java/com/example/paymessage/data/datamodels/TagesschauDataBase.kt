package com.example.paymessage.data.datamodels

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.paymessage.data.database.Converter

@Database(entities = [NewsData::class], version = 1)
@TypeConverters(Converter::class)
abstract class TagesschauDataBase : RoomDatabase() {
    abstract val dao: TagesschauDataBaseDao

}

private lateinit var INSTANCE : TagesschauDataBase

fun TagesschauDataBase(context: Context): TagesschauDataBase {

    synchronized(TagesschauDataBase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                TagesschauDataBase::class.java,
                "tagesschau"
            ).build()
        }
        return INSTANCE
    }

}