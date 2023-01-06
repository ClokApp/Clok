package com.kingfu.clok.timer.timerViewModel

import android.content.Context
import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerCountOvertime
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerLabelStyleSelectedOption
import com.kingfu.clok.timer.styles.TimerRGBStyle
import com.kingfu.clok.timer.styles.TimerRGBStyle.TimerRGBStyleVariable.timerRGBCounter
import com.kingfu.clok.timer.timerViewModel.TimerViewModel.TimerViewModelVariable.timerTime
import com.kingfu.clok.timer.timerViewModel.TimerViewModel.TimerViewModelVariable.timerTotalTime
import com.kingfu.clok.variable.Variable.showSnackbar
import com.kingfu.clok.variable.Variable.timerShowNotification
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TimerViewModel(
    private val timerPreferences: TimerPreferences,
) : ViewModel() {

    /******************************** Timer ********************************/

    object TimerViewModelVariable {
        var timerTime by mutableStateOf(0L)
        var timerTotalTime by mutableStateOf(0.0)
    }

    var timerIsFinished by mutableStateOf(false)
        private set

    var timerIsActive by mutableStateOf(false)
        private set

    var timerHour by mutableStateOf(0)
        private set

    var timerMinute by mutableStateOf(0)
        private set

    var timerSecond by mutableStateOf(0)
        private set

    var timerCurrentTimePercentage by mutableStateOf(0.0f)
        private set

    var timerIsEditState by mutableStateOf(true)
        private set

    var timerInitialTime by mutableStateOf(0L)
        private set

    var timerOffsetTime by mutableStateOf(0L)
        private set


    init {
        viewModelScope.launch {
            loadTimerHour()
            loadTimerMinute()
            loadTimerSecond()
            loadTimerIsFinished()
            loadTImerIsEditState()
            loadTimerTotalTime()
            loadTimerCurrentTimePercentage()
            loadTimerOffsetTime()

            timerTime = timerOffsetTime


            if (timerIsEditState) {
                timerCurrentTimePercentage = 0f
            }

            when (timerLabelStyleSelectedOption) {
                "RGB" -> {
                    loadTimerRGBCounter()
                    TimerRGBStyle().timerUpdateStartAndEndRGB(initialize = true)
                }
            }
            initializeTimer()
        }

    }

    fun timerSetTotalTime() {
        timerTotalTime = timerTime.toDouble()
    }

    fun startTimer() {
        timerIsActive = true
        viewModelScope.launch {
            delay(1)
            if (timerCountOvertime && timerIsFinished) {
                pauseTimer()
            }
            timerIsEditState = false
            timerInitialTime = SystemClock.elapsedRealtime()
            while (timerIsActive) {
                delay(1L)

                if (timerTime >= 0L && !timerIsFinished) {
                    timerCurrentTimePercentage = (timerTime.toDouble() / timerTotalTime).toFloat()
                    timerTime = timerOffsetTime + (timerInitialTime - SystemClock.elapsedRealtime())
                    timerLabelStyles()
                } else {
                    if (!timerIsFinished) {
                        timerIsFinished = true
                        timerShowNotification = true
                        timerCurrentTimePercentage = 0f
                        timerOffsetTime = 0L
                        timerInitialTime = SystemClock.elapsedRealtime()
                        showSnackbar = true
                    }

                    if (timerCountOvertime) {
                        timerTime =
                            timerOffsetTime + (SystemClock.elapsedRealtime() - timerInitialTime)
                    } else {
                        pauseTimer()
                    }
                }

            }
        }
    }

    fun pauseTimer() {
        timerIsActive = false
        viewModelScope.launch {
            delay(1)
            timerOffsetTime = timerTime
            saveTimerOffsetTime()
            saveTimerCurrentPercentage()
            saveTimerIsEditState()
            saveTimerTotalTime()
            saveTimerIsFinished()
        }
    }

    fun cancelTimer() {
        timerIsActive = false
        viewModelScope.launch {
            delay(1)
            timerCurrentTimePercentage = 0f
            timerIsFinished = false
            timerOffsetTime = 0L
            timerIsEditState = true

            saveTimerIsFinished()
            saveTimerOffsetTime()
            saveTimerIsEditState()
        }
    }

    fun resetTimer() {

        timerIsActive = false

        viewModelScope.launch {
            delay(1)
            timerIsFinished = false
            timerHour = 0
            timerMinute = 0
            timerSecond = 0
            saveTimerHour()
            saveTimerMinute()
            saveTimerSecond()
        }
    }

    private suspend fun timerLabelStyles() {
        when (timerLabelStyleSelectedOption) {
            "Cyan" -> {}
            "RGB" -> {
                TimerRGBStyle().timerUpdateStartAndEndRGB(initialize = false)
                saveTimerRGBCounter()
            }
        }
    }

    suspend fun timerNotification(context: Context) {
        if (timerIsFinished) {
            for (i in 0 until timerPreferences.getTimerNotification.first().toInt()) {
                if (timerShowNotification) {
                    TimerNotificationService(context).showNotification()
                    delay(2000)
                } else {
                    break
                }
            }
            timerShowNotification = false
        }
    }

    fun timerCancelNotification(context: Context) {
        timerShowNotification = false
        TimerNotificationService(context).notificationManager.cancel(1)
    }

    fun formatTimerTime(timeMillis: Long): String {

        val hours = timerTime / 1000 / 60 / 60 % 100
        val minutes = timerTime / 1000 / 60 % 60
        val seconds = timerTime / 1000 % 60
        val milliseconds = timerTime % 1000 / 10

        var time =
            when {
                timeMillis >= 36_000_000L -> "${"%02d".format(hours)}:${"%02d".format(minutes)}:${
                    "%02d".format(
                        seconds
                    )
                }"
                timeMillis in 3_600_000L until 36_000_000 -> "${"%01d".format(hours)}:${
                    "%02d".format(
                        minutes
                    )
                }:${"%02d".format(seconds)}"
                timeMillis in 600_000L until 3_600_000L -> "${"%02d".format(minutes)}:${
                    "%02d".format(
                        seconds
                    )
                }"
                timeMillis in 60_000 until 600_000L -> "${"%01d".format(minutes)}:${
                    "%02d".format(
                        seconds
                    )
                }"
                timeMillis in 10_000L until 60_000L -> "%02d".format(seconds)
                timeMillis in 0L until 10_000L -> "${"%01d".format(seconds)}.${
                    "%02d".format(
                        milliseconds
                    )
                }"
                else -> "0"
            }

        if (timerIsFinished && timerCountOvertime) {
            time = "-$time"
        }

        return time
    }

    fun convertHrMinSecToMillis() {
        timerTime = timerHour * 3_600_000L + timerMinute * 60_000L + timerSecond * 1_000L
        timerOffsetTime = timerTime
    }

    fun updateTimerHour(hour: Int) {
        timerHour = hour
    }

    fun updateTimerMinute(minute: Int) {
        timerMinute = minute
    }

    fun updateTimerSecond(second: Int) {
        timerSecond = second
    }

    private fun initializeTimer() {
        if (timerIsActive && !timerIsEditState && timerIsFinished) {
            timerIsActive = false
            timerIsEditState = true
            timerIsFinished = false
            viewModelScope.launch {
                saveTimerIsEditState()
                saveTimerIsFinished()
            }
        }
    }

    suspend fun clearAllDataPreferences() {
        timerPreferences.clearAll()
    }

    suspend fun saveTimerHour() {
        timerPreferences.setTimerHour(timerHour)
    }

    suspend fun saveTimerMinute() {
        timerPreferences.setTimerMinute(timerMinute)
    }

    suspend fun saveTimerSecond() {
        timerPreferences.setTimerSecond(timerSecond)
    }

    private suspend fun saveTimerIsFinished() {
        timerPreferences.setTimerIsFinished(timerIsFinished)
    }

    private suspend fun saveTimerTotalTime() {
        timerPreferences.setTimerTotalTime(timerTotalTime)
    }

    private suspend fun saveTimerIsEditState() {
        timerPreferences.setTimerIsEditState(timerIsEditState)
    }

    private suspend fun saveTimerCurrentPercentage() {
        timerPreferences.setTimerCurrentPercentage(timerCurrentTimePercentage)
    }

    private suspend fun saveTimerOffsetTime() {
        timerPreferences.setTimerOffsetTime(timerOffsetTime)
    }

    private suspend fun saveTimerRGBCounter() {
        timerPreferences.setTimerRGBCounter(timerRGBCounter)
    }

    private suspend fun loadTimerRGBCounter() {
        timerRGBCounter = timerPreferences.getTimerRGBCounter.first()
    }

    private suspend fun loadTimerHour() {
        timerHour = timerPreferences.getTimerHour.first()
    }

    private suspend fun loadTimerMinute() {
        timerMinute = timerPreferences.getTimerMinute.first()
    }

    private suspend fun loadTimerSecond() {
        timerSecond = timerPreferences.getTimerSecond.first()
    }

    private suspend fun loadTimerIsFinished() {
        timerIsFinished = timerPreferences.getTimerIsFinished.first()
    }

    private suspend fun loadTImerIsEditState() {
        timerIsEditState = timerPreferences.getTimerIsEditState.first()
    }

    private suspend fun loadTimerTotalTime() {
        timerTotalTime = timerPreferences.getTimerTotalTime.first()
    }

    private suspend fun loadTimerCurrentTimePercentage() {
        timerCurrentTimePercentage = timerPreferences.getTimerCurrentPercentage.first()
    }

    private suspend fun loadTimerOffsetTime() {
        timerOffsetTime = timerPreferences.getTimerOffsetTime.first()
    }

}