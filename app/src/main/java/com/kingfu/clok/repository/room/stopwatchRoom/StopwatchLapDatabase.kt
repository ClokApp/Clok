package com.kingfu.clok.repository.room.stopwatchRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StopwatchLapData::class], version = 1)
abstract class StopwatchLapDatabase: RoomDatabase() {
    abstract fun itemDao(): StopwatchLapDAO

    companion object{
        private var INSTANCE: StopwatchLapDatabase? = null

        fun getInstance(context: Context): StopwatchLapDatabase {
            return INSTANCE ?: synchronized(lock = this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context = context.applicationContext,
                        klass = StopwatchLapDatabase::class.java,
                        name = "item_lap_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
