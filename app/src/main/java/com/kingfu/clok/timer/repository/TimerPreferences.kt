package com.kingfu.clok.timer.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimerPreferences private constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: TimerPreferences? = null
        fun getInstance(context: Context): TimerPreferences =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: TimerPreferences(context).also { INSTANCE = it }
            }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TimerDataStore")
    private val preferences: DataStore<Preferences> = context.dataStore
    private val isFinished = booleanPreferencesKey(name = "isFinished")
    private val hour = intPreferencesKey(name = "hour")
    private val minute = intPreferencesKey(name = "minute")
    private val second = intPreferencesKey(name = "second")
    private val totalTime = doublePreferencesKey(name = "totalTime")
    private val isEdit = booleanPreferencesKey(name = "isEdit")
    private val offsetTime = longPreferencesKey(name = "OffsetTime")


    /************************************************ Clear ************************************************/

    suspend fun clearAll() {
        preferences.edit {
            it.clear()
        }
    }


    /************************************************ set ************************************************/

    suspend fun setTimerHour(duration: Int) {
        preferences.edit { preferences ->
            preferences[hour] = duration
        }
    }

    suspend fun setMinute(int: Int) {
        preferences.edit { preferences ->
            preferences[minute] = int
        }
    }

    suspend fun setSecond(int: Int) {
        preferences.edit { preferences ->
            preferences[second] = int
        }
    }

    suspend fun setIsFinished(boolean: Boolean) {
        preferences.edit { preferences ->
            preferences[isFinished] = boolean
        }
    }

    suspend fun setTotalTime(double: Double) {
        preferences.edit { preferences ->
            preferences[totalTime] = double
        }
    }

    suspend fun setIsEdit(boolean: Boolean) {
        preferences.edit { preferences ->
            preferences[isEdit] = boolean
        }
    }

    suspend fun setOffsetTime(long: Long) {
        preferences.edit { preferences ->
            preferences[offsetTime] = long
        }
    }


    /************************************************ get ************************************************/

    val getHour: Flow<Int> = preferences.data
        .map { preferences ->
            preferences[hour] ?: 0
        }

    val getMinute: Flow<Int> = preferences.data
        .map { preferences ->
            preferences[minute] ?: 0

        }

    val getSecond: Flow<Int> = preferences.data
        .map { preferences ->
            preferences[second] ?: 0
        }

    val getIsFinished: Flow<Boolean> = preferences.data
        .map { preferences ->
            preferences[isFinished] ?: false
        }


    val getTotalTime: Flow<Double> = preferences.data
        .map { preferences ->
            preferences[totalTime] ?: 0.0
        }

    val getIsEdit: Flow<Boolean> = preferences.data
        .map { preferences ->
            preferences[isEdit] ?: true
        }

    val getOffsetTime: Flow<Long> = preferences.data
        .map { preferences ->
            preferences[offsetTime] ?: 0L
        }


}