package com.kingfu.clok.stopwatch.stopwatchViewModel

import android.os.SystemClock
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchIsActive
import com.kingfu.clok.stopwatch.styles.RGB
import com.kingfu.clok.variable.Variable.keepScreenOn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StopwatchViewModel(
    private val stopwatchPreferences: StopwatchPreferences,
) : ViewModel() {

    /******************************** Stopwatch ********************************/

    object StopwatchViewModelVariable {
        var stopwatchIsActive by mutableStateOf(false)
//        var stopwatchColorCounter by mutableStateOf(0.0)
//        var stopwatchHrColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
//        var stopwatchMinColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
//        var stopwatchSecColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
//        var stopwatchMsColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
    }

//    companion object {
//        var stopwatchIsActive by mutableStateOf(false)
//            private set
//    }

//    var stopwatchIsActive by mutableStateOf(false)
//        private set

    var stopwatchTime by mutableStateOf(0L)
        private set

//    var stopwatchHrColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
//        private set
//
//    var stopwatchMinColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
//        private set
//
//    var stopwatchSecColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
//        private set
//
//    var stopwatchMsColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
//        private set

//    var stopwatchColorCounter by mutableStateOf(0.0)
//        private set

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

    var stopwatchRefreshRate by mutableStateOf(55F)
        private set

    var stopwatchShowLabel by mutableStateOf(true)
        private set

    var stopwatchLabelStyle by mutableStateOf("RGB")
        private set

    var stopwatchInitialTime by mutableStateOf(0L)
        private set

    var stopwatchOffsetTime by mutableStateOf(0L)
        private set


    init {
        viewModelScope.launch {
            loadStopwatchOffset()
            loadStopwatchTime()
            stopwatchOffsetTime = stopwatchTime

            stopwatchRefreshRate = stopwatchPreferences.getStopwatchRefreshRate.first()

            lapPreviousTime = stopwatchPreferences.getStopwatchLapPreviousTime.first()

            loadStopwatchLapCounter()
            loadStopwatchLapNumber()
            loadStopwatchLapTimes()
            loadStopwatchLapTotalTimes()
        }
    }

    fun addLap() {
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
        keepScreenOn = true
        stopwatchInitialTime = SystemClock.elapsedRealtime()

        viewModelScope.launch {
            while (stopwatchIsActive) {
                if (stopwatchTime >= 360_000_000) {
                    pauseStopWatch()
                    break
                }
                stopwatchLabelStyle()
                stopwatchTime =
                    (SystemClock.elapsedRealtime()  - stopwatchInitialTime) + stopwatchOffsetTime

                saveStopwatchLapPreviousTime()
                saveStopwatchOffsetTime()
                saveStopwatchTime()
                delay(stopwatchRefreshRate.toLong())
            }
        }
    }


    private suspend fun stopwatchLabelStyle() {
        if (stopwatchShowLabel) {
            when (stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()) {
//                "Gray" -> {Gray().grayStyle()}
                "Gray" -> {}
                "RGB" -> { RGB().rgbStyle(stopwatchRefreshRate)}
                else -> {}
            }
        }
    }

    fun pauseStopWatch() {
        stopwatchIsActive = false
        stopwatchOffsetTime = stopwatchTime
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

//    fun displayLapTimes(index: Int): String {
//        viewModelScope.launch {
//            delay(1000)
//        }
//        return if (stopwatchTime > 3_600_000) lapTime[index].takeWhile { it != '-' } else lapTime[index].drop(
//            3
//        ).takeWhile { it != '-' }
//    }
//
//    fun displayTotalTimes(index: Int): String {
//        viewModelScope.launch {
//            delay(1000)
//        }
//        return if (stopwatchTime > 3_600_000) lapTotalTime[index].takeWhile { it != '-' } else lapTotalTime[index].drop(
//            3
//        ).takeWhile { it != '-' }
//    }

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

    suspend fun stopwatchTimeClearAll() {
        stopwatchPreferences.clearAll()
    }

    suspend fun loadStopwatchRefreshRate() {
        stopwatchRefreshRate = stopwatchPreferences.getStopwatchRefreshRate.first()
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

    suspend fun loadStopwatchShowLabel() {
        stopwatchShowLabel = stopwatchPreferences.getStopwatchShowLabel.first()
    }

    suspend fun loadStopwatchLabelStyle() {
        stopwatchLabelStyle = stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()
    }

    suspend fun saveStopwatchLapPreviousTime() {
        stopwatchPreferences.setStopwatchLapPreviousTime(lapPreviousTime)
    }

    suspend fun saveStopwatchOffsetTime() {
        stopwatchPreferences.setStopwatchOffsetTime(stopwatchOffsetTime)
    }

}