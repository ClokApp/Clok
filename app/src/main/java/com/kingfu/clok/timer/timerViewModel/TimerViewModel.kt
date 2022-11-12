package com.kingfu.clok.timer.timerViewModel

import android.content.Context
import android.os.SystemClock
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.variable.Variable.timerShowNotification
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.sin

class TimerViewModel(
    private val timerPreferences: TimerPreferences,
) : ViewModel() {

    /******************************** Timer ********************************/

    var timerTime by mutableStateOf(0L)
        private set

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

    var timerLabelColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        private set

    var timerRGBCounter by mutableStateOf(0)
        private set

    var timerTotalTime by mutableStateOf(0.0)
        private set

    var timerCurrentTimePercentage by mutableStateOf(0.0f)
        private set

    var timerIsEditState by mutableStateOf(true)
        private set

    var timerLabelStyle by mutableStateOf("RGB")
        private set

    var timerEnableScrollsHapticFeedback by mutableStateOf(true)
        private set

    var timerInitialTime by mutableStateOf(0L)
        private set

    var timerOffsetTime by mutableStateOf(0L)
        private set

    init {
        viewModelScope.launch {
            timerHour = timerPreferences.getTimerHour.first()
            timerMinute = timerPreferences.getTimerMinute.first()
            timerSecond = timerPreferences.getTimerSecond.first()
            timerIsFinished = timerPreferences.getTimerIsFinished.first()
            timerIsEditState = timerPreferences.getTimerIsEditState.first()
            timerTotalTime = timerPreferences.getTimerTotalTime.first()
            timerCurrentTimePercentage = timerPreferences.getTimerCurrentPercentage.first()
            timerLabelStyle = timerPreferences.getTimerLabelStyleSelectedOption.first()
            timerOffsetTime = timerPreferences.getTimerOffsetTime.first()
            timerTime = timerOffsetTime

            timerRGBCounter = timerPreferences.getTimerRGBCounter.first()

            timerEnableScrollsHapticFeedback =
                timerPreferences.getTimerEnableScrollsHapticFeedback.first()
            if (timerIsEditState) {
                timerCurrentTimePercentage = 0f
            }
            initializeTimer()
        }
    }

    fun startTimer() {

        if (timerIsEditState) {
            timerIsEditState = false
            timerTotalTime = timerTime.toDouble()
        }
        timerIsActive = true
        timerInitialTime = SystemClock.elapsedRealtime()

        viewModelScope.launch {
            while (timerIsActive) {
                timerUpdateStartAndEndRGB()

                if (timerTime >= 0L && !timerIsFinished) {
                    timerCurrentTimePercentage = (timerTime.toDouble() / timerTotalTime).toFloat()
                    timerTime = timerOffsetTime + (timerInitialTime - SystemClock.elapsedRealtime())
                } else {
                    if (!timerIsFinished) {
                        timerIsFinished = true
                        timerShowNotification = true
                        timerCurrentTimePercentage = 0f
                        timerOffsetTime = 0L
                        timerInitialTime = SystemClock.elapsedRealtime()
                    }
                    if (timerPreferences.getTimerCountOvertime.first()) {
                        timerTime =
                            timerOffsetTime + (SystemClock.elapsedRealtime() - timerInitialTime)
                    } else {
                        pauseTimer()
                    }
                }
                delay(55L)
            }
        }
    }

    fun pauseTimer() {
        timerIsActive = false
        viewModelScope.launch {
            delay(100)
            timerOffsetTime = timerTime
            saveTimerOffsetTime()
            saveTimerCurrentPercentage()
            saveTimerIsEditState()
            saveTimerTotalTime()
            saveTimerIsFinished()
        }
    }

    fun cancelTimer() {
        timerIsEditState = true
        timerIsActive = false
        timerIsFinished = false
        timerTime = 0L
        timerCurrentTimePercentage = 0f
        timerOffsetTime = 0L
        viewModelScope.launch {
            saveTimerIsFinished()
            saveTimerIsEditState()
            saveTimerOffsetTime()
        }
    }

    fun resetTimer() {
        timerIsActive = false
        timerIsFinished = false
        timerTime = 0L
        timerHour = 0
        timerMinute = 0
        timerSecond = 0
        timerOffsetTime = 0L
        viewModelScope.launch {
            saveTimerHour()
            saveTimerMinute()
            saveTimerSecond()
            saveTimerOffsetTime()
        }
    }

    suspend fun timerUpdateStartAndEndRGB() {

        val frequency = 0.15
        val phase = 1.5
        val width = 128
        val center = 127

        timerRGBCounter = (timerRGBCounter + 1) % Int.MAX_VALUE
        saveTimerRGBCounter()

        for (i in 0 until 6) {
            timerLabelColorList[i] =
                (sin(frequency * timerRGBCounter + phase * i) * width + center).toInt()
        }
    }

    suspend fun timerNotification(context: Context) {
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

    fun timerCancelNotification(context: Context) {
        timerShowNotification = false
        TimerNotificationService(context).notificationManager.cancel(1)
    }

    suspend fun timerShowSnackBar(scaffoldState: ScaffoldState, context: Context) {

        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            "Timer is finished!",
            "cancel",
            SnackbarDuration.Short
        )

        when (snackbarResult) {
            SnackbarResult.Dismissed -> {}
            SnackbarResult.ActionPerformed -> {
                timerCancelNotification(context = context)
                cancelTimer()
            }
        }
    }

    fun formatTimerTime(timeMillis: Long): String {

        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.of("UTC")
        )

        val formatter = DateTimeFormatter.ofPattern(
            when {
                timeMillis >= 36_000_000L -> if (!timerIsFinished) "HH:mm:ss" else "-HH:mm:ss"
                timeMillis in 3_600_000L until 36_000_000 -> if (!timerIsFinished) "H:mm:ss" else "-H:mm:ss"
                timeMillis in 600_000L until 3_600_000L -> if (!timerIsFinished) "mm:ss" else "-mm:ss"
                timeMillis in 60_000 until 600_000L -> if (!timerIsFinished) "m:ss" else "-m:ss"
                timeMillis in 10_000L until 60_000L -> if (!timerIsFinished) "ss" else "-ss"
                timeMillis in 0L until 10_000L -> if (!timerIsFinished) "s.SS" else "-s.SS"
                else -> "0"
            }, Locale.getDefault()
        )
        return localDateTime.format(formatter)
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


    fun cancelButton() {
        cancelTimer()
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


    suspend fun saveTimerIsFinished() {
        timerPreferences.setTimerIsFinished(timerIsFinished)
    }

    suspend fun saveTimerTotalTime() {
        timerPreferences.setTimerTotalTime(timerTotalTime)
    }

    suspend fun saveTimerIsEditState() {
        timerPreferences.setTimerIsEditState(timerIsEditState)
    }

    suspend fun saveTimerCurrentPercentage() {
        timerPreferences.setTimerCurrentPercentage(timerCurrentTimePercentage)
    }

    suspend fun loadTimerLabelStyleOption() {
        timerLabelStyle = timerPreferences.getTimerLabelStyleSelectedOption.first()
    }

    suspend fun loadTimerEnableScrollsHapticFeedback() {
        timerEnableScrollsHapticFeedback =
            timerPreferences.getTimerEnableScrollsHapticFeedback.first()
    }

    suspend fun saveTimerOffsetTime() {
        timerPreferences.setTimerOffsetTime(timerOffsetTime)
    }

    suspend fun saveTimerRGBCounter() {
        timerPreferences.setTimerRGBCounter(timerRGBCounter)
    }

}