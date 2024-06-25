package com.kingfu.clok.stopwatch.repository.stopwatchRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StopwatchLapData::class], version = 1, exportSchema = false)
abstract class StopwatchLapDatabase : RoomDatabase() {
    abstract fun itemDao(): StopwatchLapDAO

    companion object {
        @Volatile
        private var INSTANCE: StopwatchLapDatabase? = null

        fun getInstance(context: Context): StopwatchLapDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StopwatchLapDatabase::class.java,
                    "lap_database"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
                instance
            }
        }
    }
}

