package com.kingfu.clok.stopwatch.stopwatchViewModel

import android.os.SystemClock
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.bottomBar.showSnackbar
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchRefreshRateValue
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchShowLabel
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchIsActive
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchTime
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBColorCounter
import com.kingfu.clok.variable.Variable.showSnackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class StopwatchViewModel(
    private val stopwatchPreferences: StopwatchPreferences,
) : ViewModel() {

    /******************************** Stopwatch ********************************/

    object StopwatchViewModelVariable {
        var stopwatchIsActive by mutableStateOf(false)
        var stopwatchTime by mutableStateOf(0L)

    }

    private var _lapNumber = mutableStateListOf<String>()
    val lapNumber: List<String>
        get() = _lapNumber

    private var _lapTime = mutableStateListOf<String>()
    val lapTime: List<String>
        get() = _lapTime

    var lapCounter by mutableStateOf(0)
        private set

    var lapPreviousTime by mutableStateOf(0L)
        private set

    private var _lapTotalTime = mutableStateListOf<String>()
    val lapTotalTime: List<String>
        get() = _lapTotalTime

    var stopwatchInitialTime by mutableStateOf(0L)
        private set

    var stopwatchOffsetTime by mutableStateOf(0L)
        private set

    var labelStyleSelectedOption by mutableStateOf("RGB")
        private set

    init {
        viewModelScope.launch {
            loadStopwatchOffset()
            loadStopwatchTime()
            loadStopwatchLapCounter()
            loadStopwatchLapNumber()
            loadStopwatchLapTimes()
            loadStopwatchLapTotalTimes()
            loadLabelStyleSelectedOption()
            loadLapPreviousTime()

            stopwatchOffsetTime = stopwatchTime

            if (stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first() == "RGB") {
                RGBColorCounter = stopwatchPreferences.getLabelStyleRGBColorCounter.first()
            }

            when (labelStyleSelectedOption) {
                "RGB" -> loadRGBColorCounter()
            }
        }
    }

    fun addLap(scaffoldState: ScaffoldState) {
        if (lapCounter < 1_000_000) {
            lapCounter += 1
            _lapNumber.add(0, lapCounter.toString())
            _lapTime.add(
                0,
                formatTimeStopWatch(stopwatchTime - lapPreviousTime) + "-$lapCounter"
            )
            _lapTotalTime.add(
                0,
                formatTimeStopWatch(stopwatchTime) + "-$lapCounter"
            )
            lapPreviousTime = stopwatchTime
        } else {
            viewModelScope.launch {
                showSnackbar = true
                showSnackbar(
                    message = "Lap time is maxed",
                    actionLabel = "cancel",
                    duration = SnackbarDuration.Short,
                    scaffoldState = scaffoldState,
                    action = {},
                    dismiss = {}
                )
            }
        }
    }

    fun saveLapTimes() {
        viewModelScope.launch {
            saveStopwatchLapNumber()
            saveStopwatchLapCounter()
            saveStopwatchLapTime()
            saveStopwatchLapTotalTimes()
        }
    }

    fun clearLapTimes() {
        viewModelScope.launch {
            lapCounter = 0
            lapPreviousTime = 0
            _lapTime.clear()
            _lapNumber.clear()
            _lapTotalTime.clear()
            stopwatchPreferences.clearStopwatchLapNumber()
            stopwatchPreferences.clearStopwatchLapCounter()
            stopwatchPreferences.clearStopwatchLapTime()
            stopwatchPreferences.clearStopwatchLapTotalTime()
            stopwatchPreferences.clearStopwatchLapPreviousTime()
        }
    }

    fun startStopWatch() {
        stopwatchIsActive = true
        stopwatchInitialTime = SystemClock.elapsedRealtime()


        viewModelScope.launch {
            while (stopwatchIsActive) {
                delay(stopwatchRefreshRateValue.toLong())

                if (stopwatchTime >= 360_000_000) {
                    pauseStopWatch()
                    break
                }
                stopwatchLabelStyles()
                stopwatchTime =
                    (SystemClock.elapsedRealtime() - stopwatchInitialTime) + stopwatchOffsetTime

                saveStopwatchLapPreviousTime()
                saveStopwatchOffsetTime()
                saveStopwatchTime()
            }
        }
    }


    private suspend fun stopwatchLabelStyles() {
        if (stopwatchShowLabel) {
            when (stopwatchLabelStyleSelectedOption) {
                "Gray" -> {}
                "RGB" -> {
                    StopwatchRGBStyle().rgbStyleUpdateColors(stopwatchRefreshRateValue)
                    saveRGBColorCounter()
                }
            }
        }
    }

    fun pauseStopWatch() {
        stopwatchIsActive = false
        viewModelScope.launch {
            delay(100)
            stopwatchOffsetTime = stopwatchTime
        }
    }

    fun resetStopWatch() {
        stopwatchIsActive = false
        stopwatchTime = 0L

        stopwatchOffsetTime = 0L
        viewModelScope.launch {
            saveStopwatchOffsetTime()
            saveStopwatchTime()
        }
    }

    fun formatTimeStopWatchHr(timeMillis: Long): String {
        val hr2 = (timeMillis / 36_000_000) % 10
        val hr1 = (timeMillis / 3_600_000) % 10
        return "$hr2$hr1"
    }

    fun formatTimeStopWatchMin(timeMillis: Long): String {
        val min2 = (timeMillis / 600_000) % 6
        val min1 = (timeMillis / 60_000) % 10
        return "$min2$min1"
    }

    fun formatTimeStopWatchSec(timeMillis: Long): String {
        val sec2 = (timeMillis / 10_000) % 6
        val sec1 = (timeMillis / 1_000) % 10
        return "$sec2$sec1"
    }

    fun formatTimeStopWatchMs(timeMillis: Long): String {
        val ms2 = (timeMillis / 100) % 10
        val ms1 = (timeMillis / 10) % 10
        return "$ms2$ms1"
    }

    fun formatTimeStopWatch(timeMillis: Long): String {
        val hr2 = (timeMillis / 36_000_000) % 10
        val hr1 = (timeMillis / 3_600_000) % 10
        val min2 = (timeMillis / 600_000) % 6
        val min1 = (timeMillis / 60_000) % 10
        val sec2 = (timeMillis / 10_000) % 6
        val sec1 = (timeMillis / 1_000) % 10
        val ms2 = (timeMillis / 100) % 10
        val ms1 = (timeMillis / 10) % 10
        var result = "$min2$min1:$sec2$sec1.$ms2$ms1"

        if (stopwatchTime >= 3_600_000) {
            result = "$hr2$hr1:$result"
            return result
        }

        return result
    }

    fun getLapNumber(index: Int): String {
        return if (lapNumber[index].toInt() < 10) "0${lapNumber[index]}" else lapNumber[index]
    }

    fun getLapTimes(index: Int): String {
        return if (stopwatchTime > 3_600_000) lapTime[index].takeWhile { it != '-' } else lapTime[index].takeWhile { it != '-' }
    }

    fun getLapTotalTimes(index: Int): String {
        return if (stopwatchTime > 3_600_000) lapTotalTime[index].takeWhile { it != '-' } else lapTotalTime[index].takeWhile { it != '-' }
    }

    suspend fun stopwatchTimeClearAll() {
        stopwatchPreferences.clearAll()
    }

    suspend fun saveStopwatchTime() {
        stopwatchPreferences.setStopwatchTime(stopwatchTime)
    }

    suspend fun loadStopwatchTime() {
        stopwatchTime = stopwatchPreferences.getStopwatchTime.first()
    }

    suspend fun loadStopwatchOffset() {
        stopwatchOffsetTime = stopwatchPreferences.getStopwatchOffsetTime.first()
    }

    suspend fun saveStopwatchLapNumber() {
        stopwatchPreferences.setStopwatchLapNumber(lapNumber.toSet())
    }

    suspend fun loadStopwatchLapNumber() {
        _lapNumber = stopwatchPreferences.getStopwatchLabNumber.first().toMutableStateList()
    }

    suspend fun loadStopwatchLapCounter() {
        lapCounter = stopwatchPreferences.getStopwatchLapCounter.first()
    }

    suspend fun saveStopwatchLapCounter() {
        stopwatchPreferences.setStopwatchLapCounter(lapCounter)
    }

    suspend fun saveStopwatchLapTime() {
        stopwatchPreferences.setStopwatchLapTime(_lapTime.toSet())
    }

    suspend fun loadStopwatchLapTimes() {
        _lapTime = stopwatchPreferences.getStopwatchLapTime.first().toMutableStateList()
    }

    suspend fun saveStopwatchLapTotalTimes() {
        stopwatchPreferences.setStopwatchLapTotalTime(_lapTotalTime.toSet())
    }

    suspend fun loadStopwatchLapTotalTimes() {
        _lapTotalTime = stopwatchPreferences.getStopwatchLapTotalTime.first().toMutableStateList()
    }

    suspend fun saveStopwatchLapPreviousTime() {
        stopwatchPreferences.setStopwatchLapPreviousTime(lapPreviousTime)
    }

    suspend fun saveStopwatchOffsetTime() {
        stopwatchPreferences.setStopwatchOffsetTime(stopwatchOffsetTime)
    }

    suspend fun loadRGBColorCounter() {
        RGBColorCounter = stopwatchPreferences.getLabelStyleRGBColorCounter.first()
    }

    suspend fun saveRGBColorCounter() {
        stopwatchPreferences.setLabelStyleRGBColorCounter(RGBColorCounter)
    }

    suspend fun loadLabelStyleSelectedOption() {
        labelStyleSelectedOption = stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()
    }


    suspend fun loadLapPreviousTime() {
        lapPreviousTime = stopwatchPreferences.getStopwatchLapPreviousTime.first()
    }


}