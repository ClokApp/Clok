package com.kingfu.clok.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kingfu.clok.stopwatch.feature.fontStyle.StopwatchFontStyleType
import com.kingfu.clok.stopwatch.feature.labelBackgroundEffects.StopwatchLabelBackgroundEffectType
import com.kingfu.clok.stopwatch.feature.labelStyle.StopwatchLabelStyleType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StopwatchPreferences private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: StopwatchPreferences? = null

        fun getInstance(context: Context): StopwatchPreferences {
            return INSTANCE ?: synchronized(lock = this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = StopwatchPreferences(context = context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private val Context.stopwatchDataStore: DataStore<Preferences> by preferencesDataStore(name = "StopwatchDataStore")
    private val stopwatchDataStore: DataStore<Preferences> = context.stopwatchDataStore

    private val stopwatchTimeDefault = 0L
    private val stopwatchRefreshRateDefault = 55L
    private val stopwatchIsShowLabelDefault = true
    private val stopwatchLapPreviousTimeDefault = 0L
    private val stopwatchLabelStyleDefault = StopwatchLabelStyleType.DynamicColor.name
    private val stopwatchOffsetTimeDefault = 0L
    private val stopwatchLabelBackgroundEffectsDefault =
        StopwatchLabelBackgroundEffectType.Snow.name
    private val stopwatchStopwatchLabelFontStyleDefault = StopwatchFontStyleType.Default.name
    private val stopwatchTimeFontStyleDefault = StopwatchFontStyleType.Default.name
    private val stopwatchLapTimeFontStyDefault = StopwatchFontStyleType.Default.name
    private val stopwatchShortestLapIndexDefault = 0
    private val stopwatchLongestLapIndexDefault = 0

    private val _stopwatchTime = longPreferencesKey(name = "stopwatchTime")
    private val _stopwatchRefreshRate = longPreferencesKey(name = "stopwatchRefreshRate")
    private val _stopwatchIsShowLabel = booleanPreferencesKey(name = "stopwatchIsShowLabel")
    private val _stopwatchLapPreviousTime = longPreferencesKey(name = "stopwatchLapPreviousTime")
    private val _stopwatchLabelStyle = stringPreferencesKey(name = "stopwatchLabelStyle")
    private val _stopwatchOffsetTime = longPreferencesKey(name = "stopwatchOffsetTime")
    private val _stopwatchLabelBackgroundEffects =
        stringPreferencesKey(name = "stopwatchLabelBackgroundEffects")
    private val _stopwatchLabelStyleRGBColorCounter =
        doublePreferencesKey(name = "StopwatchLabelStyleRGBColorCounter")
    private val _stopwatchLabelFontStyle = stringPreferencesKey(name = "stopwatchLabelFontStyle")
    private val _stopwatchTimeFontStyle = stringPreferencesKey(name = "stopwatchTimeFontStyle")
    private val _stopwatchLapTimeFontStyle =
        stringPreferencesKey(name = "stopwatchLapTimeFontStyle")
    private val _stopwatchShortestLapIndex = intPreferencesKey(name = "stopwatchShortestLapIndex")
    private val _stopwatchLongestLapIndex = intPreferencesKey(name = "stopwatchLongestLapIndex")

    /************************************************ Clear ************************************************/

    suspend fun clearAll() {
        stopwatchDataStore.edit {
            it.clear()
        }
    }

    /************************************************ set ************************************************/

    suspend fun setStopwatchTime(long: Long) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchTime] = long
        }
    }

    //    suspend fun setStopwatchRefreshRate(float: Float) {
    suspend fun setStopwatchRefreshRate(long: Long) {
        stopwatchDataStore.edit { it[_stopwatchRefreshRate] = long }
    }

    suspend fun setStopwatchIsShowLabel(boolean: Boolean) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchIsShowLabel] = boolean
        }
    }

    suspend fun setStopwatchLapPreviousTime(long: Long) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLapPreviousTime] = long
        }
    }

    suspend fun setStopwatchLabelStyle(string: String) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLabelStyle] = string
        }
    }

    suspend fun setStopwatchOffsetTime(long: Long) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchOffsetTime] = long
        }
    }

    suspend fun setStopwatchBackgroundEffects(string: String) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLabelBackgroundEffects] = string
        }
    }

    suspend fun setLabelStyleRGBColorCounter(double: Double) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLabelStyleRGBColorCounter] = double
        }
    }

    suspend fun setStopwatchLabelFontStyle(string: String) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLabelFontStyle] = string
        }
    }

    suspend fun setStopwatchTimeFontStyle(string: String) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchTimeFontStyle] = string
        }
    }

    suspend fun setStopwatchLapTimeFontStyle(string: String) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLapTimeFontStyle] = string
        }
    }

    suspend fun setStopwatchShortestLapIndex(int: Int) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchShortestLapIndex] = int
        }
    }

    suspend fun setStopwatchLongestLapIndex(int: Int) {
        stopwatchDataStore.edit { preferences ->
            preferences[_stopwatchLongestLapIndex] = int
        }
    }

    /************************************************ get ************************************************/

    val getStopwatchTime: Flow<Long> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchTime] ?: stopwatchTimeDefault
        }

    val getStopwatchRefreshRate: Flow<Long> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchRefreshRate] ?: stopwatchRefreshRateDefault
        }

    val getStopwatchIsShowLabel: Flow<Boolean> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchIsShowLabel] ?: stopwatchIsShowLabelDefault
        }

    val getStopwatchLapPreviousTime: Flow<Long> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLapPreviousTime] ?: stopwatchLapPreviousTimeDefault
        }

    val getStopwatchLabelStyle: Flow<String> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLabelStyle] ?: stopwatchLabelStyleDefault
        }

    val getStopwatchOffsetTime: Flow<Long> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchOffsetTime] ?: stopwatchOffsetTimeDefault
        }

    val getStopwatchLabelBackgroundEffects: Flow<String> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLabelBackgroundEffects] ?: stopwatchLabelBackgroundEffectsDefault
        }

    val getStopwatchLabelFontStyle: Flow<String> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLabelFontStyle] ?: stopwatchStopwatchLabelFontStyleDefault
        }

    val getStopwatchTimeFontStyle: Flow<String> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchTimeFontStyle] ?: stopwatchTimeFontStyleDefault
        }

    val getStopwatchLapTimeFontStyle: Flow<String> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLapTimeFontStyle] ?: stopwatchLapTimeFontStyDefault
        }

    val getStopwatchShortestLapIndex: Flow<Int> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchShortestLapIndex] ?: stopwatchShortestLapIndexDefault
        }

    val getStopwatchLongestLapIndex: Flow<Int> = stopwatchDataStore.data
        .map { preferences ->
            preferences[_stopwatchLongestLapIndex] ?: stopwatchLongestLapIndexDefault
        }
}