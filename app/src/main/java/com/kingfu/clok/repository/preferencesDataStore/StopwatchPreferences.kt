package com.kingfu.clok.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StopwatchPreferences(context: Context) {

    companion object {

        private var INSTANCE: StopwatchPreferences? = null

        fun getInstance(context: Context): StopwatchPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = StopwatchPreferences(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private val Context.stopwatchDataStore: DataStore<Preferences> by preferencesDataStore(name = "StopwatchDataStore")
    private val stopwatchDataStore: DataStore<Preferences> = context.stopwatchDataStore

    private val stopwatchTimeDefault = 0L
    private val stopwatchLapNumberDefault = setOf<String>()
    private val stopwatchLapCounterDefault = 0
    private val stopwatchLapTimeDefault = setOf("00:00.00")
    private val stopwatchLapTotalTimeDefault = setOf("00:00.00")
    private val stopwatchAutoSaveTimeDefault = true
    private val stopwatchAutoSaveLapTimeDefault = true
    private val stopwatchRefreshRateDefault = 55f
    private val stopwatchShowLabelDefault = true
    private val stopwatchLapPreviousTimeDefault = 0L
    private val stopwatchLabelStyleSelectedOptionDefault = "RGB"
    private val stopwatchOffsetTimeDefault = 0L

    private val _stopwatchTime = longPreferencesKey("stopwatchTime")
    private val _stopwatchLapNumber = stringSetPreferencesKey("stopwatchLapNumber")
    private val _stopwatchLapCounter = intPreferencesKey("stopwatchLapCounter")
    private val _stopwatchLapTime = stringSetPreferencesKey("stopwatchLapTime")
    private val _stopwatchLapTotalTime = stringSetPreferencesKey("stopwatchLapTotalTime")
    private val _stopwatchAutoSaveTime = booleanPreferencesKey("stopwatchAutoSaveTime")
    private val _stopwatchAutoSaveLapTime = booleanPreferencesKey("stopwatchAutoSaveLapTime")
    private val _stopwatchRefreshRate = floatPreferencesKey("stopwatchRefreshRate")
    private val _stopwatchShowLabel = booleanPreferencesKey("stopwatchShowLabel")
    private val _stopwatchLapPreviousTime = longPreferencesKey("stopwatchLapPreviousTime")
    private val _stopwatchLabelStyleSelectedOption =
        stringPreferencesKey("stopwatchLabelStyleSelectedOption")
    private val _stopwatchOffsetTime = longPreferencesKey("stopwatchOffsetTime")

    /************************************************ Clear ************************************************/

    suspend fun clearAll() {
        stopwatchDataStore.edit {
            it.clear()
        }
    }

    suspend fun clearStopwatchLapNumber() {
        stopwatchDataStore.edit { preferences ->
            preferences.remove(_stopwatchLapNumber)
        }
    }

    suspend fun clearStopwatchLapCounter() {
        stopwatchDataStore.edit { preferences ->
            preferences.remove(_stopwatchLapCounter)
        }
    }

    suspend fun clearStopwatchLapTime() {
        stopwatchDataStore.edit { preferences ->
            preferences.remove(_stopwatchLapTime)
        }
    }

    suspend fun clearStopwatchLapTotalTime() {
        stopwatchDataStore.edit { preferences ->
            preferences.remove(_stopwatchLapTotalTime)
        }
    }

    suspend fun clearStopwatchLapPreviousTime() {
        stopwatchDataStore.edit { preferences ->
            preferences.remove(_stopwatchLapPreviousTime)
        }
    }

    suspend fun clearStopwatchOffsetTime() {
        stopwatchDataStore.edit { preferences ->
            preferences.remove(_stopwatchOffsetTime)
        }
    }

    /************************************************ set ************************************************/

    suspend fun setStopwatchTime(long: Long) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchTime] = long
        }
    }

    suspend fun setStopwatchLapNumber(strings: Set<String>) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLapNumber] = strings
        }
    }

    suspend fun setStopwatchLapCounter(num: Int) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLapCounter] = num
        }
    }

    suspend fun setStopwatchLapTime(strings: Set<String>) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLapTime] = strings
        }
    }

    suspend fun setStopwatchLapTotalTime(strings: Set<String>) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLapTotalTime] = strings
        }
    }

    suspend fun setStopwatchAutoSaveTime(boolean: Boolean) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchAutoSaveTime] = boolean
        }
    }

    suspend fun setStopwatchAutoLapTime(boolean: Boolean) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchAutoSaveLapTime] = boolean
        }
    }

    suspend fun setStopwatchRefreshRate(float: Float) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchRefreshRate] = float
        }
    }

    suspend fun setStopwatchShowLabel(boolean: Boolean) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchShowLabel] = boolean
        }
    }

    suspend fun setStopwatchLapPreviousTime(long: Long) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLapPreviousTime] = long
        }
    }

    suspend fun setStopwatchLabelStyleSelectedOption(string: String) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLabelStyleSelectedOption] = string
        }
    }

    suspend fun setStopwatchOffsetTime(long: Long) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchOffsetTime] = long
        }
    }

    /************************************************ get ************************************************/
    val getStopwatchTime: Flow<Long> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchTime] ?: stopwatchTimeDefault
        }

    val getStopwatchLabNumber: Flow<Set<String>> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLapNumber] ?: stopwatchLapNumberDefault
        }

    val getStopwatchLapCounter: Flow<Int> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLapCounter] ?: stopwatchLapCounterDefault
        }

    val getStopwatchLapTime: Flow<Set<String>> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLapTime] ?: stopwatchLapTimeDefault
        }

    val getStopwatchLapTotalTime: Flow<Set<String>> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLapTotalTime] ?: stopwatchLapTotalTimeDefault
        }

    val getStopwatchSaveTime: Flow<Boolean> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchAutoSaveTime] ?: stopwatchAutoSaveTimeDefault
        }

    val getStopwatchSaveLapTime: Flow<Boolean> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchAutoSaveLapTime] ?: stopwatchAutoSaveLapTimeDefault
        }

    val getStopwatchRefreshRate: Flow<Float> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchRefreshRate] ?: stopwatchRefreshRateDefault
        }

    val getStopwatchShowLabel: Flow<Boolean> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchShowLabel] ?: stopwatchShowLabelDefault
        }

    val getStopwatchLapPreviousTime: Flow<Long> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLapPreviousTime] ?: stopwatchLapPreviousTimeDefault
        }

    val getStopwatchLabelStyleSelectedOption: Flow<String> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLabelStyleSelectedOption]
                ?: stopwatchLabelStyleSelectedOptionDefault
        }

    val getStopwatchOffsetTime: Flow<Long> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchOffsetTime] ?: stopwatchOffsetTimeDefault
        }

}