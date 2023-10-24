package com.kingfu.clok.timer.timerViewModel

import android.content.Context
import android.os.SystemClock
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.timer.feature.timerFontStyle.TimerFontStyleType
import com.kingfu.clok.timer.feature.timerProgressBarBackgroundEffects.TimerProgressBarBackgroundEffectType
import com.kingfu.clok.timer.feature.timerProgressBarStyle.TimerProgressBarRgbStyle
import com.kingfu.clok.timer.feature.timerProgressBarStyle.TimerProgressBarRgbStyle.TimerProgressBarRgbStyleVariable.timerRgbCounter
import com.kingfu.clok.timer.feature.timerProgressBarStyle.TimerProgressBarStyleType
import com.kingfu.clok.timer.feature.timerScrollsHapticFeedback.TimerScrollsHapticFeedbackType
import com.kingfu.clok.util.formatTimeHr
import com.kingfu.clok.util.formatTimeMin
import com.kingfu.clok.util.formatTimeMs
import com.kingfu.clok.util.formatTimeSec
import com.kingfu.clok.variable.Variable.isShowSnackbar
import com.kingfu.clok.variable.Variable.isShowTimerNotification
import com.kingfu.clok.variable.Variable.snackbarAction
import com.kingfu.clok.variable.Variable.snackbarIsWithDismissAction
import com.kingfu.clok.variable.Variable.snackbarLabelAction
import com.kingfu.clok.variable.Variable.snackbarMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.ceil

@OptIn(SavedStateHandleSaveableApi::class)
class TimerViewModel(
    private val timerPreferences: TimerPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

//    object TimerViewModelVariable {
//        var timerTotalTime by mutableDoubleStateOf(value = 0.0)
//    }


    var state by savedStateHandle.saveable { mutableStateOf(value = TimerState()) }
        private set


    init {
        viewModelScope.launch {

            loadAllData()
            state = state.copy(timerTime = state.timerOffsetTime)
            initializeTimer()

            if (state.timerIsActive) {
                state = state.copy(
                    timerOffsetTime = state.timerTime,
                    timerCurrentTimePercentage = (state.timerTime.toDouble() / state.timerTotalTime).toFloat()
                )

                startTimer()
            }
        }
    }

    fun setIsLoadInitialTimeToFalse() {
        state = state.copy(isLoadInitialTime = false)
    }

    fun timerSetTotalTime() {
        state = state.copy(timerTotalTime = state.timerTime.toDouble())
    }


    fun startTimer() {
        viewModelScope.launch {
            state = state.copy(
                timerIsActive = true,
                timerIsEditState = false,
                timerInitialTime = SystemClock.elapsedRealtime()
            )
            saveTimerIsEditState()
            delay(timeMillis = if (state.timerTime * 0.005 > 100) 100L else (state.timerTime * 0.005).toLong())
            while (state.timerIsActive) {
                if (state.timerTime >= 0L && !state.timerIsFinished) {
                    state = state.copy(
                        timerCurrentTimePercentage = state.timerTime / state.timerTotalTime.toFloat(),
                        timerTime = state.timerOffsetTime + (state.timerInitialTime - SystemClock.elapsedRealtime())
                    )

                } else {
                    if (timerPreferences.getTimerIsCountOvertime.first()) {
                        state = state.copy(
                            timerTime = state.timerOffsetTime + (SystemClock.elapsedRealtime() - state.timerInitialTime)
                        )
                    } else {
                        state = state.copy(timerTime = 0)
                        pauseTimer()
                    }

                    if (!state.timerIsFinished) {
                        state = state.copy(
                            timerIsFinished = true,
                            timerCurrentTimePercentage = 0f,
                            timerOffsetTime = 0L,
                            timerInitialTime = SystemClock.elapsedRealtime()
                        )
                        isShowTimerNotification = true
                        snackbarMessage = "Timer is finished!"
                        snackbarIsWithDismissAction = false
                        snackbarLabelAction = "Cancel"
                        snackbarAction = { cancelTimer() }
                        isShowSnackbar = true
                    }
                }
                delay(timeMillis = state.delay)
            }
        }
    }

    fun pauseTimer() {
        state = state.copy(
            timerIsActive = false,
            timerOffsetTime = state.timerTime
        )
        saveTimerOffsetTime()
        saveTimerCurrentPercentage()
        saveTimerIsEditState()
        saveTimerTotalTime()
        saveTimerIsFinished()
    }

    fun cancelTimer() {
        state = state.copy(
            timerIsActive = false,
            timerCurrentTimePercentage = 0f,
            timerTotalTime = 0.0,
            timerIsFinished = false,
            timerOffsetTime = 0L,
            timerIsEditState = true
        )
        saveTimerIsFinished()
        saveTimerOffsetTime()
        saveTimerIsEditState()
        saveTimerTotalTime()
    }

    fun resetTimer() {
        state = state.copy(
            timerIsActive = false,
            timerHour = 0,
            timerMinute = 0,
            timerSecond = 0
        )
        saveTimerHour()
        saveTimerMinute()
        saveTimerSecond()
    }

    fun updateTimerStyle(selectedProgressBarStyle: TimerProgressBarStyleType) {
        when (selectedProgressBarStyle) {
            TimerProgressBarStyleType.DynamicColor -> {}
            TimerProgressBarStyleType.RGB -> {
                viewModelScope.launch {
                    TimerProgressBarRgbStyle().updateTimerProgressBarRgbStyleStartAndEndColor()
                    saveTimerRGBCounter()
                }
            }
        }
    }

    fun timerNotification(context: Context) {
        viewModelScope.launch {
            val timerNotifications = ceil(timerPreferences.getTimerNotification.first()).toInt()
            for (i in 0 until timerNotifications) {
                if (isShowTimerNotification && state.timerIsFinished) {
                    TimerNotificationService(context = context).showNotification()
                    delay(timeMillis = 3000)
                } else {
                    break
                }
            }
            isShowTimerNotification = false
        }
    }

    fun isOverTime(): Boolean {
        var result = false
        viewModelScope.launch {
            if (state.timerIsFinished && timerPreferences.getTimerIsCountOvertime.first()) {
                result = true
            }
        }
        return result
    }

    fun formatTimerTimeHr(timeMillis: Long): String {
        return when {
            timeMillis >= 36_000_000L -> timeMillis.formatTimeHr()
            timeMillis in 3_600_000L until 36_000_000 -> timeMillis.formatTimeHr(format = "%01d")
            else -> ""
        }
    }

    fun formatTimerTimeMin(timeMillis: Long): String {
        return when {
            timeMillis >= 600_000L -> timeMillis.formatTimeMin()
            timeMillis in 60_000 until 600_000L -> timeMillis.formatTimeMin(format = "%01d")
            else -> ""
        }
    }

    fun formatTimerTimeSec(timeMillis: Long): String {
        return if (timeMillis >= 10_000L){
            timeMillis.formatTimeSec()
        } else {
            timeMillis.formatTimeSec(format = "%01d")
        }
    }

    fun formatTimerTimeMs(timeMillis: Long): String {
        return if (timeMillis in 1..9999) timeMillis.formatTimeMs() else ""
    }

    fun convertHrMinSecToMillis() {
        state = state.copy(
            timerTime = state.timerHour * 3_600_000L + state.timerMinute * 60_000L + state.timerSecond * 1_000L,
            timerOffsetTime = state.timerHour * 3_600_000L + state.timerMinute * 60_000L + state.timerSecond * 1_000L
        )
    }

    fun updateTimerHour(hour: Int) {
        state = state.copy(timerHour = hour)
    }

    fun updateTimerMinute(minute: Int) {
        state = state.copy(timerMinute = minute)
    }

    fun updateTimerSecond(second: Int) {
        state = state.copy(timerSecond = second)
    }

    private fun initializeTimer() {
        if ((!state.timerIsEditState && state.timerIsFinished) || state.timerOffsetTime == 0L) {
            state = state.copy(
                timerIsEditState = true,
                timerIsFinished = false,
                timerCurrentTimePercentage = 0f
            )
            saveTimerIsEditState()
            saveTimerIsFinished()
        }
    }

    suspend fun clearAllDataPreferences() {
        timerPreferences.clearAll()
    }

    fun getTimerScrollsHapticFeedback(): TimerScrollsHapticFeedbackType {
        var result = TimerScrollsHapticFeedbackType.Strong.name
        viewModelScope.launch {
            result = timerPreferences.getTimerScrollsHapticFeedback.first()
        }
        return enumValueOf(result)
    }

    fun getTimerScrollsFontStyle(): TimerFontStyleType {
        var result = TimerFontStyleType.Default.name
        viewModelScope.launch {
            result = timerPreferences.getTimerScrollsFontStyle.first()
        }
        return enumValueOf(result)
    }

    fun getTimerTimeFontStyle(): TimerFontStyleType {
        var result = TimerFontStyleType.Default.name
        viewModelScope.launch {
            result = timerPreferences.getTimerTimeFontStyle.first()
        }
        return enumValueOf(result)
    }

    fun getTimerProgressBarBackgroundEffects(): TimerProgressBarBackgroundEffectType {
        var result = TimerProgressBarBackgroundEffectType.Snow.name
        viewModelScope.launch {
            result = timerPreferences.getTimerProgressBarBackgroundEffects.first()
        }
        return enumValueOf(result)
    }

    fun getTimerProgressBarStyle(): TimerProgressBarStyleType {
        var result = TimerProgressBarStyleType.DynamicColor.name
        viewModelScope.launch {
            result = timerPreferences.getTimerProgressBarStyle.first()
        }
        return enumValueOf(result)
    }

    fun getTimerIsCountOverTime(): Boolean {
        var result = false
        viewModelScope.launch {
            result = timerPreferences.getTimerIsCountOvertime.first()
        }
        return result
    }

    fun saveTimerHour() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerHour(duration = state.timerHour)
        }
    }

    fun saveTimerMinute() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerMinute(duration = state.timerMinute)
        }
    }

    fun saveTimerSecond() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerSecond(duration = state.timerSecond)
        }
    }

    private fun saveTimerIsFinished() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerIsFinished(isFinished = state.timerIsFinished)
        }
    }

    private fun saveTimerTotalTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerTotalTime(double = state.timerTotalTime)
        }
    }

    private fun saveTimerIsEditState() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerIsEditState(boolean = state.timerIsEditState)
        }
    }

    private fun saveTimerCurrentPercentage() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerCurrentPercentage(float = state.timerCurrentTimePercentage)
        }
    }

    private fun saveTimerOffsetTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerOffsetTime(long = state.timerOffsetTime)
        }
    }

    private fun saveTimerRGBCounter() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerRGBCounter(double = timerRgbCounter)
        }
    }

    private suspend fun loadAllData() {
        timerRgbCounter = timerPreferences.getTimerRGBCounter.first()

        state = state.copy(
            timerHour = timerPreferences.getTimerHour.first(),
            timerMinute = timerPreferences.getTimerMinute.first(),
            timerSecond = timerPreferences.getTimerSecond.first(),
            timerIsFinished = timerPreferences.getTimerIsFinished.first(),
            timerIsEditState = timerPreferences.getTimerIsEditState.first(),
            timerTotalTime = timerPreferences.getTimerTotalTime.first(),
            timerCurrentTimePercentage = timerPreferences.getTimerCurrentPercentage.first(),
            timerOffsetTime = timerPreferences.getTimerOffsetTime.first()
        )
    }


}