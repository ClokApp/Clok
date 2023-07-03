package com.kingfu.clok.timer.timerViewModel

import android.content.Context
import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.notification.timer.TimerNotificationService
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.timer.styles.TimerRGBStyle
import com.kingfu.clok.timer.styles.TimerRGBStyle.TimerRGBStyleVariable.timerRGBCounter
import com.kingfu.clok.timer.timerViewModel.TimerViewModel.TimerViewModelVariable.timerTotalTime
import com.kingfu.clok.variable.Variable.DYNAMIC_COLOR
import com.kingfu.clok.variable.Variable.RGB
import com.kingfu.clok.variable.Variable.isShowSnackbar
import com.kingfu.clok.variable.Variable.isShowTimerNotification
import com.kingfu.clok.variable.Variable.snackbarAction
import com.kingfu.clok.variable.Variable.snackbarIsWithDismissAction
import com.kingfu.clok.variable.Variable.snackbarLabelAction
import com.kingfu.clok.variable.Variable.snackbarMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TimerViewModel(
    private val timerPreferences: TimerPreferences
) : ViewModel() {

    object TimerViewModelVariable {
        var timerTotalTime by mutableDoubleStateOf(value = 0.0)
    }

    var timerTime by mutableLongStateOf(value = 0L)
        private set

    var timerIsFinished by mutableStateOf(value = false)
        private set

    var timerIsActive by mutableStateOf(value = false)
        private set

    var timerHour by mutableIntStateOf(value = 0)
        private set

    var timerMinute by mutableIntStateOf(value = 0)
        private set

    var timerSecond by mutableIntStateOf(value = 0)
        private set

    var timerCurrentTimePercentage by mutableFloatStateOf(value = 0.0f)
        private set

    var timerIsEditState by mutableStateOf(value = true)
        private set

    var timerInitialTime by mutableLongStateOf(value = 0L)
        private set

    var timerOffsetTime by mutableLongStateOf(value = 0L)
        private set

    var delay by mutableLongStateOf(value = 55L)
        private set


    init {
        viewModelScope.launch {
            loadTimerHour()
            loadTimerMinute()
            loadTimerSecond()
            loadTimerIsFinished()
            loadTimerIsEditState()
            loadTimerTotalTime()
            loadTimerCurrentTimePercentage()
            loadTimerOffsetTime()

            timerTime = timerOffsetTime

            if (timerIsEditState) {
                timerCurrentTimePercentage = 0f
            }
            loadTimerRGBCounter()
            initializeTimer()
        }
    }

    fun timerSetTotalTime() {
        timerTotalTime = timerTime.toDouble()
    }


    fun startTimer() {
        timerIsActive = true

        viewModelScope.launch {
            timerIsEditState = false
            saveTimerIsEditState()

            timerInitialTime = SystemClock.elapsedRealtime()
            delay(if (timerTime * 0.005 > 100) 100L else (timerTime * 0.005).toLong())
            while (timerIsActive) {

                if (timerTime >= 0L && !timerIsFinished) {
                    timerCurrentTimePercentage = (timerTime.toDouble() / timerTotalTime).toFloat()
                    timerTime = timerOffsetTime + (timerInitialTime - SystemClock.elapsedRealtime())
                    timerLabelStyles()
                } else {
                    if (!timerIsFinished) {
                        timerIsFinished = true
                        isShowTimerNotification = true
                        timerCurrentTimePercentage = 0f
                        timerOffsetTime = 0L
                        timerInitialTime = SystemClock.elapsedRealtime()
                        isShowSnackbar = true
                        snackbarMessage = "Timer is finished!"
                        snackbarIsWithDismissAction = false
                        snackbarLabelAction = "Cancel"
                        snackbarAction = { cancelTimer() }
                    }

                    if (timerPreferences.getTimerCountOvertime.first()) {
                        timerTime =
                            timerOffsetTime + (SystemClock.elapsedRealtime() - timerInitialTime)
                    } else {
                        timerTime = 0
                        pauseTimer()
                    }
                }
                delay(delay)


            }
        }
    }

    fun pauseTimer() {
        timerIsActive = false
        viewModelScope.launch {
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
            timerCurrentTimePercentage = 0f
            timerTotalTime = 0.0
            timerIsFinished = false
            timerOffsetTime = 0L
            timerIsEditState = true

            saveTimerIsFinished()
            saveTimerOffsetTime()
            saveTimerIsEditState()
            saveTimerTotalTime()
        }
    }

    fun resetTimer() {

        timerIsActive = false

        viewModelScope.launch {
            timerHour = 0
            timerMinute = 0
            timerSecond = 0

            saveTimerHour()
            saveTimerMinute()
            saveTimerSecond()

        }
    }

    private suspend fun timerLabelStyles() {
        when (timerPreferences.getTimerLabelStyle.first()) {
            DYNAMIC_COLOR -> {}
            RGB -> {
                TimerRGBStyle().timerUpdateStartAndEndRGB(initialize = false)
                saveTimerRGBCounter()
            }
        }
    }

    suspend fun timerNotification(context: Context) {
        for (i in 0 until timerPreferences.getTimerNotification.first().toInt()) {
            if (isShowTimerNotification && timerIsFinished) {
                TimerNotificationService(context = context).showNotification()
                delay(timeMillis = 2000)
            } else {
                break
            }
        }
    }
    fun isOverTime(): Boolean{
        var result = false
        viewModelScope.launch {
            if (timerIsFinished && timerPreferences.getTimerCountOvertime.first()) {
                result = true
            }
        }
        return result
    }

    fun formatTimerTimeHr(timeMillis: Long): String {
        val hours = timerTime / 1000 / 60 / 60 % 100
        return when {
            timeMillis >= 36_000_000L -> "%02d".format(hours)
            timeMillis in 3_600_000L until 36_000_000 -> "%01d".format(hours)
            else -> ""
        }
    }

    fun formatTimerTimeMin(timeMillis: Long): String {
        val minutes = timerTime / 1000 / 60 % 60
        return when{
            timeMillis >= 600_000L -> "%02d".format(minutes)
            timeMillis in 60_000 until 600_000L  -> "%01d".format(minutes)
            else -> ""
        }
    }

    fun formatTimerTimeSec(timeMillis: Long): String {
        val seconds = timerTime / 1000 % 60
        return when{
            timeMillis >= 10_000L -> "%02d".format(seconds)
            else -> "%01d".format(seconds)
        }
    }

    fun formatTimerTimeMs(timeMillis: Long): String {
        val milliseconds = timerTime % 1000 / 10
        return if (timeMillis in 1..9999) "%02d".format(milliseconds) else ""
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
        timerPreferences.setTimerHour(duration = timerHour)
    }

    suspend fun saveTimerMinute() {
        timerPreferences.setTimerMinute(duration = timerMinute)
    }

    suspend fun saveTimerSecond() {
        timerPreferences.setTimerSecond(duration = timerSecond)
    }

    private suspend fun saveTimerIsFinished() {
        timerPreferences.setTimerIsFinished(isFinished = timerIsFinished)
    }

    private suspend fun saveTimerTotalTime() {
        timerPreferences.setTimerTotalTime(double = timerTotalTime)
    }

    private suspend fun saveTimerIsEditState() {
        timerPreferences.setTimerIsEditState(boolean = timerIsEditState)
    }

    private suspend fun saveTimerCurrentPercentage() {
        timerPreferences.setTimerCurrentPercentage(float = timerCurrentTimePercentage)
    }

    private suspend fun saveTimerOffsetTime() {
        timerPreferences.setTimerOffsetTime(long = timerOffsetTime)
    }

    private suspend fun saveTimerRGBCounter() {
        timerPreferences.setTimerRGBCounter(double = timerRGBCounter)
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

    private suspend fun loadTimerIsEditState() {
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