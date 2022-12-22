package com.kingfu.clok.repository.preferencesDataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimerPreferences(context: Context) {

    companion object {
        private var INSTANCE: TimerPreferences? = null

        fun getInstance(context: Context): TimerPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = TimerPreferences(context)
                INSTANCE = instance
                instance
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
    private val timerCountOvertimeDefault = true
    private val timerLabelStyleSelectedOptionDefault = "RGB"
    private val timerEnableScrollsHapticFeedbackDefault = true
    private val timerNotificationDefault = 5f
    private val timerOffsetTimeDefault = 0L
    private val timerRGBCCounterDefault = 255

    private val _timerIsFinished = booleanPreferencesKey("timerIsFinished")
    private val _timerHourKey = intPreferencesKey("timerHour")
    private val _timerMinuteKey = intPreferencesKey("timerMinute")
    private val _timerSecondKey = intPreferencesKey("timerSecond")
    private val _timerTotalTime = doublePreferencesKey("timeTotalTime")
    private val _timerIsEditState = booleanPreferencesKey("timerIsEditState")
    private val _timerCurrentPercentage = floatPreferencesKey("timerCurrentPercentage")
    private val _timerIsActive = booleanPreferencesKey("timerIsActive")
    private val _timerCounterOvertime = booleanPreferencesKey("timerCounterOvertime")
    private val _timerLabelStyleSelectedOption = stringPreferencesKey("timerLabelStyleSelectedOption")
    private val _timerEnableScrollsHapticFeedback = booleanPreferencesKey("timerEnableScrollsHapticFeedback")
    private val _timerNotification = floatPreferencesKey("timerNotification")
    private val _timerOffsetTime = longPreferencesKey("timerOffsetTime")
    private val _timerRGBCounter = intPreferencesKey("timerRGBCounter")


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

    suspend fun setTimerCountOvertime(boolean: Boolean) {
        timerDataStore.edit { preferences ->
            preferences[_timerCounterOvertime] = boolean
        }
    }

    suspend fun setTimerLabelStyleSelectedOption(string: String) {
        timerDataStore.edit { preferences ->
            preferences[_timerLabelStyleSelectedOption] = string
        }
    }

    suspend fun setTimerEnableScrollsHapticFeedback(boolean: Boolean) {
        timerDataStore.edit { preferences ->
            preferences[_timerEnableScrollsHapticFeedback] = boolean
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

    suspend fun setTimerRGBCounter(int: Int) {
        timerDataStore.edit { preferences ->
            preferences[_timerRGBCounter] = int
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


    val getTimerCountOvertime: Flow<Boolean> = timerDataStore.data
        .map { preferences ->
            preferences[_timerCounterOvertime] ?: timerCountOvertimeDefault
        }

    val getTimerLabelStyleSelectedOption: Flow<String> = timerDataStore.data
        .map { preferences ->
            preferences[_timerLabelStyleSelectedOption] ?: timerLabelStyleSelectedOptionDefault
        }

    val getTimerEnableScrollsHapticFeedback: Flow<Boolean> = timerDataStore.data
        .map { preferences ->
            preferences[_timerEnableScrollsHapticFeedback] ?: timerEnableScrollsHapticFeedbackDefault
        }

    val getTimerNotification: Flow<Float> = timerDataStore.data
        .map { preferences ->
            preferences[_timerNotification] ?: timerNotificationDefault
        }

    val getTimerOffsetTime: Flow<Long> = timerDataStore.data
        .map { preferences ->
            preferences[_timerOffsetTime] ?: timerOffsetTimeDefault
        }

    val getTimerRGBCounter: Flow<Int> = timerDataStore.data
        .map { preferences ->
            preferences[_timerRGBCounter] ?: timerRGBCCounterDefault
        }


}