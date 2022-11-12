package com.kingfu.clok.stopwatch.stopwatchViewModel

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.sin

class StopwatchViewModel(
    private val stopwatchPreferences: StopwatchPreferences,
) : ViewModel() {

    /******************************** Stopwatch ********************************/

    var stopwatchIsActive by mutableStateOf(false)
        private set

    var stopwatchTime by mutableStateOf(0L)
        private set

    var stopwatchMinColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        private set

    var stopwatchSecColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        private set

    var stopwatchMsColorList = mutableStateListOf(0, 0, 0, 0, 0, 0)
        private set

    var stopwatchColorCounter by mutableStateOf(0.0)
        private set

    private var _lapNumber = mutableListOf<String>()
    val lapNumber: List<String>
        get() = _lapNumber

    private var _lapTime = mutableListOf<String>()
    val lapTime: List<String>
        get() = _lapTime

    var lapCounter by mutableStateOf(0)
        private set

    var lapPreviousTime by mutableStateOf(0L)
        private set

    private var _lapTotalTime = mutableListOf<String>()
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
//            if (stopwatchPreferences.getStopwatchSaveTime.first()) {
//                stopwatchOffsetTime = stopwatchPreferences.getStopwatchOffsetTime.first()
//                stopwatchTime = stopwatchOffsetTime
//            } else {
//                stopwatchPreferences.clearStopwatchOffsetTime()
//            }

//            stopwatchOffsetTime = stopwatchPreferences.getStopwatchOffsetTime.first()
            loadStopwatchOffset()
            loadStopwatchTime()
//            stopwatchTime = stopwatchOffsetTime
            stopwatchOffsetTime = stopwatchTime

            stopwatchRefreshRate = stopwatchPreferences.getStopwatchRefreshRate.first()

//            if (stopwatchPreferences.getStopwatchSaveLapTime.first()) {
//                lapPreviousTime = stopwatchPreferences.getStopwatchLapPreviousTime.first()
//            } else {
////                stopwatchPreferences.clearStopwatchLapPreviousTime()
//                clearLapTimes()
//
//            }

            lapPreviousTime = stopwatchPreferences.getStopwatchLapPreviousTime.first()

//            if (!stopwatchPreferences.getStopwatchSaveLapTime.first()) {
//                clearLapTimes()
//            }
        }
    }

    fun addLap() {
        lapCounter += 1
        _lapNumber.add(0, lapCounter.toString())
        _lapTime.add(
            0,
            "${formatTimeStopWatchMin(stopwatchTime - lapPreviousTime)}:" +
                    "${formatTimeStopWatchSec(stopwatchTime - lapPreviousTime)}." +
                    formatTimeStopWatchMs(stopwatchTime - lapPreviousTime) + " $lapCounter"
        )
        _lapTotalTime.add(
            0,
            "${formatTimeStopWatchMin(stopwatchTime)}:${formatTimeStopWatchSec(stopwatchTime)}." +
                    formatTimeStopWatchMs(stopwatchTime) + " $lapCounter"
        )
        lapPreviousTime = stopwatchTime
//        viewModelScope.launch {
//            if (stopwatchPreferences.getStopwatchSaveTime.first()) {
//                saveStopwatchLapPreviousTime()
//            }
//        }
    }


    fun saveLapTimes() {
        viewModelScope.launch {
//            if (stopwatchPreferences.getStopwatchSaveLapTime.first()) {
            saveStopwatchLapNumber()
            saveStopwatchLapCounter()
            saveStopwatchLapTime()
            saveStopwatchLapTotalTimes()
//            }
        }
    }

    fun clearLapTimes() {
//        if (!stopwatchPreferences.getStopwatchSaveLapTime.first()) {
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
                stopwatchLabelStyle(stopwatchRefreshRate)
                stopwatchTime =
                    (SystemClock.elapsedRealtime() - stopwatchInitialTime) + stopwatchOffsetTime

                saveStopwatchLapPreviousTime()
                saveStopwatchOffsetTime()
                saveStopwatchTime()
                delay(stopwatchRefreshRate.toLong())
            }
        }
    }


    private suspend fun stopwatchLabelStyle(refreshRate: Float) {
        if (stopwatchShowLabel) {
            when (stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()) {
                "Gray" -> {}
                "RGB" -> stopwatchUpdateStartAndEndRGB(refreshRate)
                else -> {}
            }
        }
    }


    private fun stopwatchUpdateStartAndEndRGB(refreshRate: Float) {
        val frequency = 0.9
        val phase = 1.5
        val width = 128
        val center = 127
        val minOffset = 0
        val secOffset = 3
        val msOffset = 6

        val temp: Double =
            if (refreshRate <= 25) {
                0.025
            } else if (refreshRate > 25 && refreshRate <= 50) {
                0.0025
            } else if (refreshRate > 50 && refreshRate <= 75) {
                0.00025
            } else {
                0.000025
            }

        stopwatchColorCounter =
            (stopwatchColorCounter + ((refreshRate / 200) + temp)) % Double.MAX_VALUE
        for (i in 0 until 6) {
            stopwatchMinColorList[i] =
                (sin(frequency * stopwatchColorCounter + phase * (i + minOffset)) * width + center).toInt()
            stopwatchSecColorList[i] =
                (sin(frequency * stopwatchColorCounter + phase * (i + secOffset)) * width + center).toInt()
            stopwatchMsColorList[i] =
                (sin(frequency * stopwatchColorCounter + phase * (i + msOffset)) * width + center).toInt()
        }

    }

    fun pauseStopWatch() {
        stopwatchIsActive = false
//        stopwatchOffsetTime = stopwatchTime
//        viewModelScope.launch {
            stopwatchOffsetTime = stopwatchTime

//            saveStopwatchOffsetTime()

//            if (stopwatchPreferences.getStopwatchSaveTime.first()) {
//                saveStopwatchLapPreviousTime()
//            }
//            saveLapTimes()
//        }


    }

    fun resetStopWatch() {
        stopwatchIsActive = false
        stopwatchTime = 0L
//        lapPreviousTime = 0L
//        _lapTime.clear()
//        _lapNumber.clear()
//        _lapTotalTime.clear()
//        lapCounter = 0
        stopwatchOffsetTime = 0L
        viewModelScope.launch {
//            saveStopwatchLapNumber()
            saveStopwatchOffsetTime()
            saveStopwatchTime()
//            saveStopwatchLapTotalTime()
//            saveStopwatchLapCounter()
//            saveStopwatchLapPreviousTime()
        }
    }

    fun formatTimeStopWatchMin(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "mm",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

    fun formatTimeStopWatchSec(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "ss",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

    fun formatTimeStopWatchMs(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "SS",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

    fun formatTimeStopWatch(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "mm:ss.SS",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

    suspend fun stopwatchTimeClearAll() {
        stopwatchPreferences.clearAll()
    }


    suspend fun loadStopwatchRefreshRate() {
        stopwatchRefreshRate = stopwatchPreferences.getStopwatchRefreshRate.first()
    }

    suspend fun saveStopwatchTime() {
//        if (stopwatchPreferences.getStopwatchSaveTime.first()) {
            stopwatchPreferences.setStopwatchTime(stopwatchTime)
//        }
    }

    suspend fun loadStopwatchTime(){
        stopwatchTime = stopwatchPreferences.getStopwatchTime.first()
    }

    suspend fun loadStopwatchOffset(){
        stopwatchOffsetTime = stopwatchPreferences.getStopwatchOffsetTime.first()
    }

    suspend fun saveStopwatchLapNumber() {
        stopwatchPreferences.setStopwatchLapNumber(lapNumber.toSet())
    }

    suspend fun loadStopwatchLapNumber() {
        _lapNumber = stopwatchPreferences.getStopwatchLabNumber.first().toMutableList()
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
        _lapTime = stopwatchPreferences.getStopwatchLapTime.first().toMutableList()
    }

    suspend fun saveStopwatchLapTotalTimes() {
        stopwatchPreferences.setStopwatchLapTotalTime(_lapTotalTime.toSet())
    }

    suspend fun loadStopwatchLapTotalTimes() {
        _lapTotalTime = stopwatchPreferences.getStopwatchLapTotalTime.first().toMutableList()
    }

    suspend fun loadStopwatchShowLabel() {
        stopwatchShowLabel = stopwatchPreferences.getStopwatchShowLabel.first()
    }


    suspend fun loadStopwatchLabelStyle() {
        stopwatchLabelStyle = stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()
    }

    suspend fun saveStopwatchLapPreviousTime() {
//        if (stopwatchPreferences.getStopwatchSaveTime.first()) {
        stopwatchPreferences.setStopwatchLapPreviousTime(lapPreviousTime)
//        }
    }

    suspend fun saveStopwatchOffsetTime() {
//        if (stopwatchPreferences.getStopwatchSaveTime.first()) {
        stopwatchPreferences.setStopwatchOffsetTime(stopwatchOffsetTime)
//        }
    }

}