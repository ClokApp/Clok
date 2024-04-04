package com.kingfu.clok.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kingfu.clok.timer.util.timerFontStyle.TimerFontStyleType
import com.kingfu.clok.timer.util.timerProgressBarBackgroundEffects.TimerProgressBarBackgroundEffectType
import com.kingfu.clok.timer.util.timerProgressBarStyle.TimerProgressBarStyleType
import com.kingfu.clok.timer.util.timerScrollsHapticFeedback.TimerScrollsHapticFeedbackType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimerPreferences private constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: TimerPreferences? = null
        fun getInstance(context: Context): TimerPreferences {
            return INSTANCE ?: synchronized(lock = this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = TimerPreferences(context = context)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private val Context.timerDataStore: DataStore<Preferences> by preferencesDataStore(name = "TimerDataStore")
    private val timerDataStore: DataStore<Preferences> = context.timerDataStore

    private val timerHourDefault = 0
    private val timerMinuteDefault = 0
    private val timerSecondDefault = 0
    private val timerIsFinishedDefault = false
    private val timerTotalTimeDefault = 0.0
    private val timerIsEditStateDefault = true
    private val timerCurrentPercentageDefault = 0.0f
    private val timerIsCountOvertimeDefault = true
    private val timerProgressBarStyleDefault =  TimerProgressBarStyleType.DynamicColor.name
    private val timerNotificationDefault = 5f
    private val timerOffsetTimeDefault = 0L
    private val timerRGBCCounterDefault = 255.0
    private val timerProgressBarBackgroundEffectsDefault = TimerProgressBarBackgroundEffectType.Snow.name
    private val timerScrollsFontStyleDefault = TimerFontStyleType.Default.name
    private val timerTimeFontStyleDefault = TimerFontStyleType.Default.name
    private val timerScrollsHapticFeedbackDefault = TimerScrollsHapticFeedbackType.Strong.name

    private val _timerIsFinished = booleanPreferencesKey(name = "timerIsFinished")
    private val _timerHourKey = intPreferencesKey(name = "timerHour")
    private val _timerMinuteKey = intPreferencesKey(name = "timerMinute")
    private val _timerSecondKey = intPreferencesKey(name = "timerSecond")
    private val _timerTotalTime = doublePreferencesKey(name = "timeTotalTime")
    private val _timerIsEditState = booleanPreferencesKey(name = "timerIsEditState")
    private val _timerCurrentPercentage = floatPreferencesKey(name = "timerCurrentPercentage")
    private val _timerIsCounterOvertime = booleanPreferencesKey(name = "timerIsCounterOvertime")
    private val _timerProgressBarStyle = stringPreferencesKey(name = "timerProgressBarStyle")
    private val _timerNotification = floatPreferencesKey(name = "timerNotification")
    private val _timerOffsetTime = longPreferencesKey(name = "timerOffsetTime")
    private val _timerRGBCounter = doublePreferencesKey(name = "timerRGBCounter")
    private val _timerProgressBarBackgroundEffect = stringPreferencesKey(name = "timerProgressBarBackgroundEffect")
    private val _timerScrollsFontStyle = stringPreferencesKey(name = "timerScrollsFontStyle")
    private val _timerTimeFontStyle = stringPreferencesKey(name = "timerTimeFontStyle")
    private val _timerScrollsHapticFeedback = stringPreferencesKey(name = "timerScrollsHapticFeedback")


    /************************************************ Clear ************************************************/

    suspend fun clearAll() {
        timerDataStore.edit {
            it.clear()
        }
    }


    /************************************************ set ************************************************/

    suspend fun setTimerHour(duration: Int) {
        timerDataStore.edit { preferences ->
            preferences[_timerHourKey] = duration
        }
    }

    suspend fun setTimerMinute(duration: Int) {
        timerDataStore.edit { preferences ->
            preferences[_timerMinuteKey] = duration
        }
    }

    suspend fun setTimerSecond(duration: Int) {
        timerDataStore.edit { preferences ->
            preferences[_timerSecondKey] = duration
        }
    }

    suspend fun setTimerIsFinished(isFinished: Boolean) {
        timerDataStore.edit { preferences ->
            preferences[_timerIsFinished] = isFinished
        }
    }

    suspend fun setTimerTotalTime(double: Double) {
        timerDataStore.edit { preferences ->
            preferences[_timerTotalTime] = double
        }
    }

    suspend fun setTimerIsEditState(boolean: Boolean) {
        timerDataStore.edit { preferences ->
            preferences[_timerIsEditState] = boolean
        }
    }

    suspend fun setTimerCurrentPercentage(float: Float) {
        timerDataStore.edit { preferences ->
            preferences[_timerCurrentPercentage] = float
        }
    }

    suspend fun setTimerIsCountOvertime(boolean: Boolean) {
        timerDataStore.edit { preferences ->
            preferences[_timerIsCounterOvertime] = boolean
        }
    }

    suspend fun setTimerProgressBarStyle(string: String) {
        timerDataStore.edit { preferences ->
            preferences[_timerProgressBarStyle] = string
        }
    }

    suspend fun setTimerNotification(float: Float) {
        timerDataStore.edit { preferences ->
            preferences[_timerNotification] = float
        }
    }

    suspend fun setTimerOffsetTime(long: Long) {
        timerDataStore.edit { preferences ->
            preferences[_timerOffsetTime] = long
        }
    }

    suspend fun setTimerRGBCounter(double: Double) {
        timerDataStore.edit { preferences ->
            preferences[_timerRGBCounter] = double
        }
    }

    suspend fun setTimerBackgroundEffects(string: String){
        timerDataStore.edit { preferences ->
            preferences[_timerProgressBarBackgroundEffect] = string
        }
    }

    suspend fun setTimerScrollFontStyle(string: String){
        timerDataStore.edit { preferences ->
            preferences[_timerScrollsFontStyle] = string
        }
    }

    suspend fun setTimerTimeFontStyle(string: String){
        timerDataStore.edit { preferences ->
            preferences[_timerTimeFontStyle] = string
        }
    }

    suspend fun setTimerScrollsHapticFeedback(string: String){
        timerDataStore.edit { preferences ->
            preferences[_timerScrollsHapticFeedback] = string
        }
    }


    /************************************************ get ************************************************/

    val getTimerHour: Flow<Int> = timerDataStore.data
        .map { preferences ->
            preferences[_timerHourKey] ?: timerHourDefault
        }

    val getTimerMinute: Flow<Int> = timerDataStore.data
        .map { preferences ->
            preferences[_timerMinuteKey] ?: timerMinuteDefault

        }

    val getTimerSecond: Flow<Int> = timerDataStore.data
        .map { preferences ->
            preferences[_timerSecondKey] ?: timerSecondDefault
        }

    val getTimerIsFinished: Flow<Boolean> = timerDataStore.data
        .map { preferences ->
            preferences[_timerIsFinished] ?: timerIsFinishedDefault
        }


    val getTimerTotalTime: Flow<Double> = timerDataStore.data
        .map { preferences ->
            preferences[_timerTotalTime] ?: timerTotalTimeDefault
        }

    val getTimerIsEditState: Flow<Boolean> = timerDataStore.data
        .map { preferences ->
            preferences[_timerIsEditState] ?: timerIsEditStateDefault
        }

    val getTimerCurrentPercentage: Flow<Float> = timerDataStore.data
        .map { preferences ->
            preferences[_timerCurrentPercentage] ?: timerCurrentPercentageDefault
        }


    val getTimerIsCountOvertime: Flow<Boolean> = timerDataStore.data
        .map { preferences ->
            preferences[_timerIsCounterOvertime] ?: timerIsCountOvertimeDefault
        }

    val getTimerProgressBarStyle: Flow<String> = timerDataStore.data
        .map { preferences ->
            preferences[_timerProgressBarStyle] ?: timerProgressBarStyleDefault
        }

    val getTimerNotification: Flow<Float> = timerDataStore.data
        .map { preferences ->
            preferences[_timerNotification] ?: timerNotificationDefault
        }

    val getTimerOffsetTime: Flow<Long> = timerDataStore.data
        .map { preferences ->
            preferences[_timerOffsetTime] ?: timerOffsetTimeDefault
        }

    val getTimerRGBCounter: Flow<Double> = timerDataStore.data
        .map { preferences ->
            preferences[_timerRGBCounter] ?: timerRGBCCounterDefault
        }

    val getTimerProgressBarBackgroundEffects: Flow<String> = timerDataStore.data
        .map { preferences ->
            preferences[_timerProgressBarBackgroundEffect] ?: timerProgressBarBackgroundEffectsDefault
        }

    val getTimerScrollsFontStyle: Flow<String> = timerDataStore.data
        .map{ preferences ->
            preferences[_timerScrollsFontStyle] ?: timerScrollsFontStyleDefault
        }

    val getTimerTimeFontStyle: Flow<String> = timerDataStore.data
        .map { preferences ->
            preferences[_timerTimeFontStyle] ?: timerTimeFontStyleDefault
        }

    val getTimerScrollsHapticFeedback: Flow<String> = timerDataStore.data
        .map { preferences ->
            preferences[_timerScrollsHapticFeedback] ?: timerScrollsHapticFeedbackDefault
        }

}