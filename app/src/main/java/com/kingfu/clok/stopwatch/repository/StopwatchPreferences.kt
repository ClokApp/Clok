package com.kingfu.clok.stopwatch.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class StopwatchPreferences private constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: StopwatchPreferences? = null
        fun getInstance(context: Context): StopwatchPreferences =
            INSTANCE ?: synchronized(lock = this) {
                INSTANCE ?: StopwatchPreferences(context).also { INSTANCE = it }
            }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "StopwatchDataStore")
    private val preferences: DataStore<Preferences> = context.dataStore
    private val time = longPreferencesKey(name = "time")
    private val offsetTime = longPreferencesKey(name = "offsetTime")
    private val shortestLapIndex = intPreferencesKey(name = "shortestLapIndex")
    private val longestLapIndex = intPreferencesKey(name = "longestLapIndex")

    /************************************************ Clear ************************************************/

    suspend fun clearAll() {
        preferences.edit {
            it.clear()
        }
    }

    /************************************************ set ************************************************/

    suspend fun setTime(long: Long) {
        preferences.edit { preferences ->
            preferences[time] = long
        }
    }


    suspend fun setOffsetTime(long: Long) {
        preferences.edit { preferences ->
            preferences[offsetTime] = long
        }
    }

    suspend fun setShortestLapIndex(int: Int) {
        preferences.edit { preferences ->
            preferences[shortestLapIndex] = int
        }
    }

    suspend fun setLongestLapIndex(int: Int) {
        preferences.edit { preferences ->
            preferences[longestLapIndex] = int
        }
    }

    /************************************************ get ************************************************/

    val getTime: Flow<Long> = preferences.data
        .map { preferences ->
            preferences[time] ?: 0L
        }

    val getOffsetTime: Flow<Long> = preferences.data
        .map { preferences ->
            preferences[offsetTime] ?: 0L
        }

    val getShortestLapIndex: Flow<Int> = preferences.data
        .map { preferences ->
            preferences[shortestLapIndex] ?: 0
        }

    val getLongestLapIndex: Flow<Int> = preferences.data
        .map { preferences ->
            preferences[longestLapIndex] ?: 0
        }

}