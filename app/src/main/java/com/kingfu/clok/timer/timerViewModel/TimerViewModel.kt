package com.kingfu.clok.timer.timerViewModel

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.view.WindowManager
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

    var timerTotalTime by mutableStateOf(0.0)
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
        if (timerIsEditState) {
            timerIsEditState = false
            timerTotalTime = timerTime.toDouble()
        }
    }

    fun startTimer(activity: Activity) {

        if (timerCountOvertime && timerIsFinished) {
            pauseTimer(activity)
        }
        timerIsActive = true
        timerInitialTime = SystemClock.elapsedRealtime()
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        viewModelScope.launch {
            while (timerIsActive) {
                timerLabelStyles()
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
                        showSnackbar = true
                    }

                    if (timerCountOvertime) {
                        timerTime =
                            timerOffsetTime + (SystemClock.elapsedRealtime() - timerInitialTime)
                    } else {
                        pauseTimer(activity)
                    }
                }
                delay(55L)
            }
        }
    }

    fun pauseTimer(activity: Activity) {
        timerIsActive = false
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
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

            saveTimerOffsetTime()
            saveTimerCurrentPercentage()
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

        val hr2 = (timerTime / 36_000_000) % 10
        val hr1 = (timerTime / 3_600_000) % 10
        val min2 = (timerTime / 600_000) % 6
        val min1 = (timerTime / 60_000) % 10
        val sec2 = (timerTime / 10_000) % 6
        val sec1 = (timerTime / 1_000) % 10
        val ms2 = (timerTime / 100) % 10
        val ms1 = (timerTime / 10) % 10

        var time =
            when {
                timeMillis >= 36_000_000L -> "$hr2$hr1:$min2$min1:$sec2$sec1"
                timeMillis in 3_600_000L until 36_000_000 -> "$hr1:$min2$min1:$sec2$sec1"
                timeMillis in 600_000L until 3_600_000L -> "$min2$min1:$sec2$sec1"
                timeMillis in 60_000 until 600_000L -> "$min1:$sec2$sec1"
                timeMillis in 10_000L until 60_000L -> "$sec2$sec1"
                timeMillis in 0L until 10_000L -> "$sec1.$ms2$ms1"
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

    suspend fun saveTimerOffsetTime() {
        timerPreferences.setTimerOffsetTime(timerOffsetTime)
    }

    suspend fun saveTimerRGBCounter() {
        timerPreferences.setTimerRGBCounter(timerRGBCounter)
    }

    suspend fun loadTimerRGBCounter() {
        timerRGBCounter = timerPreferences.getTimerRGBCounter.first()
    }

    suspend fun loadTimerHour(){
        timerHour = timerPreferences.getTimerHour.first()
    }

    suspend fun loadTimerMinute(){
        timerMinute = timerPreferences.getTimerMinute.first()
    }

    suspend fun loadTimerSecond(){
        timerSecond = timerPreferences.getTimerSecond.first()
    }

    suspend fun loadTimerIsFinished(){
        timerIsFinished = timerPreferences.getTimerIsFinished.first()
    }

    suspend fun loadTImerIsEditState(){
        timerIsEditState = timerPreferences.getTimerIsEditState.first()
    }

    suspend fun loadTimerTotalTime(){
        timerTotalTime = timerPreferences.getTimerTotalTime.first()
    }

    suspend fun loadTimerCurrentTimePercentage(){
        timerCurrentTimePercentage = timerPreferences.getTimerCurrentPercentage.first()
    }

    suspend fun loadTimerOffsetTime(){
        timerOffsetTime = timerPreferences.getTimerOffsetTime.first()
    }



}