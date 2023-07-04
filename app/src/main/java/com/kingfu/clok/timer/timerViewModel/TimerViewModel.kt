package com.kingfu.clok.timer.timerViewModel

import android.content.Context
import android.os.SystemClock
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
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
                    timerProgressBarStyles()
                } else {
                    if (!timerIsFinished) {
                        timerIsFinished = true
                        timerCurrentTimePercentage = 0f
                        timerOffsetTime = 0L
                        timerInitialTime = SystemClock.elapsedRealtime()
                        isShowTimerNotification = true
                        snackbarMessage = "Timer is finished!"
                        snackbarIsWithDismissAction = false
                        snackbarLabelAction = "Cancel"
                        snackbarAction = { cancelTimer() }
                        isShowSnackbar = true
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
        timerOffsetTime = timerTime
        saveTimerOffsetTime()
        saveTimerCurrentPercentage()
        saveTimerIsEditState()
        saveTimerTotalTime()
        saveTimerIsFinished()
    }

    fun cancelTimer() {
        timerIsActive = false
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

    fun resetTimer() {

        timerIsActive = false

        timerHour = 0
        timerMinute = 0
        timerSecond = 0

        saveTimerHour()
        saveTimerMinute()
        saveTimerSecond()

    }

    suspend fun timerProgressBarStyles() {
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

    fun isOverTime(): Boolean {
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
        return when {
            timeMillis >= 600_000L -> "%02d".format(minutes)
            timeMillis in 60_000 until 600_000L -> "%01d".format(minutes)
            else -> ""
        }
    }

    fun formatTimerTimeSec(timeMillis: Long): String {
        val seconds = timerTime / 1000 % 60
        return when {
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

    fun initializeTimer() {
        if ((!timerIsEditState && timerIsFinished) || timerOffsetTime == 0L ) {
            Log.d("Testing", "Executed")
            timerIsEditState = true
            timerIsFinished = false
            timerCurrentTimePercentage = 0f
            saveTimerIsEditState()
            saveTimerIsFinished()
        }
    }

    suspend fun clearAllDataPreferences() {
        timerPreferences.clearAll()
    }

    fun saveTimerHour() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerHour(duration = timerHour)
        }
    }

    fun saveTimerMinute() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerMinute(duration = timerMinute)
        }
    }

    fun saveTimerSecond() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerSecond(duration = timerSecond)
        }
    }

    fun saveTimerIsFinished() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerIsFinished(isFinished = timerIsFinished)
        }
    }

    fun saveTimerTotalTime() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerTotalTime(double = timerTotalTime)
        }
    }

    fun saveTimerIsEditState() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerIsEditState(boolean = timerIsEditState)
        }
    }

    fun saveTimerCurrentPercentage() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerCurrentPercentage(float = timerCurrentTimePercentage)
        }
    }

    fun saveTimerOffsetTime() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerOffsetTime(long = timerOffsetTime)
        }
    }

    fun saveTimerRGBCounter() {
        viewModelScope.launch(Dispatchers.IO) {
            timerPreferences.setTimerRGBCounter(double = timerRGBCounter)
        }
    }

    suspend fun loadTimerRGBCounter() {
        timerRGBCounter = timerPreferences.getTimerRGBCounter.first()
    }

    suspend fun loadTimerHour() {
        timerHour = timerPreferences.getTimerHour.first()
    }

    suspend fun loadTimerMinute() {
        timerMinute = timerPreferences.getTimerMinute.first()
    }

    suspend fun loadTimerSecond() {
        timerSecond = timerPreferences.getTimerSecond.first()
    }

    suspend fun loadTimerIsFinished() {
        timerIsFinished = timerPreferences.getTimerIsFinished.first()
    }

    suspend fun loadTimerIsEditState() {
        timerIsEditState = timerPreferences.getTimerIsEditState.first()
    }

    suspend fun loadTimerTotalTime() {
        timerTotalTime = timerPreferences.getTimerTotalTime.first()
    }

    suspend fun loadTimerCurrentTimePercentage() {
        timerCurrentTimePercentage = timerPreferences.getTimerCurrentPercentage.first()
    }

    suspend fun loadTimerOffsetTime() {
        timerOffsetTime = timerPreferences.getTimerOffsetTime.first()
    }

}