package com.kingfu.clok.stopwatch.stopwatchViewModel

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBColorCounter
import com.kingfu.clok.variable.Variable.DYNAMIC_COLOR
import com.kingfu.clok.variable.Variable.RGB
import com.kingfu.clok.variable.Variable.isShowSnackbar
import com.kingfu.clok.variable.Variable.snackbarMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StopwatchViewModel(
    private val stopwatchPreferences: StopwatchPreferences,
    private val stopwatchLapDatabase: StopwatchLapDatabase,
) : ViewModel() {
    var stopwatchIsActive by mutableStateOf(value = false)
        private set

    var stopwatchTime by mutableLongStateOf(value = 0L)
        private set

    var lapPreviousTime by mutableLongStateOf(value = 0L)
        private set

    var stopwatchInitialTime by mutableLongStateOf(value = 0L)
        private set

    var stopwatchOffsetTime by mutableLongStateOf(value = 0L)
        private set

    var labelStyleSelectedOption by mutableStateOf(value = DYNAMIC_COLOR)
        private set

    var isLap by mutableStateOf(value = false)
        private set

    var lapList: StateFlow<List<StopwatchLapData>> =
        stopwatchLapDatabase.itemDao().getAll().stateIn(
            viewModelScope, SharingStarted.Eagerly, emptyList()
        )
    var shortestLapIndex by mutableIntStateOf(value = 0)
        private set
    var longestLapIndex by mutableIntStateOf(value = 0)
        private set


    init {
        viewModelScope.launch {
            loadStopwatchOffset()
            loadStopwatchTime()
            loadStopwatchLabelStyleSelectedOption()
            loadStopwatchLapPreviousTime()
            loadStopwatchShortestLapIndex()
            loadStopwatchLongestLapIndex()
            stopwatchOffsetTime = stopwatchTime
        }
    }


    fun lap() {
        if (lapList.value.size == 1_000_000) {
            isShowSnackbar = true
            snackbarMessage = "Max lap number is reached"
            return
        }
        isLap = true
    }


    fun clearLapTimes() {
        viewModelScope.launch {
            clearAllLap()

            lapPreviousTime = 0
            stopwatchPreferences.clearStopwatchLapPreviousTime()

            shortestLapIndex = 0
            saveShortestLapIndex()

            longestLapIndex = 0
            saveLongestLapIndex()

        }
    }

    fun startStopWatch() {
        stopwatchIsActive = true
        stopwatchInitialTime = SystemClock.elapsedRealtime()


        viewModelScope.launch {
            while (stopwatchIsActive) {

                if (stopwatchTime >= 360_000_000) {
                    pauseStopWatch()
                    break
                }

                stopwatchLabelStyles()
                stopwatchTime =
                    (SystemClock.elapsedRealtime() - stopwatchInitialTime) + stopwatchOffsetTime

                if (isLap) {
                    isLap = false
                    addLap()
                    recordShortestLap()
                    recordLongestLap()
                }

//                stopwatchTime += 50_000
                saveStopwatchLapPreviousTime()
                saveStopwatchOffsetTime()
                saveStopwatchTime()
                delay(stopwatchPreferences.getStopwatchRefreshRate.first().toLong())

            }
        }
    }

    suspend fun addLap() {
        val curStopwatchTime = stopwatchTime - (stopwatchTime % 10)
        val curLapPreviousTime = lapPreviousTime - (lapPreviousTime % 10)

        val lap = StopwatchLapData(
            lapNumber = lapList.value.size + 1,
            lapTime = curStopwatchTime - curLapPreviousTime,
            lapTotalTime = curStopwatchTime
        )

        saveLap(lap = lap)

        lapPreviousTime = curStopwatchTime
        saveStopwatchLapPreviousTime()
    }

    suspend fun recordShortestLap() {

        val size = lapList.value.size

        if (size <= 1) {
            return
        }
        shortestLapIndex += 1

        val currentShortest = lapList.value[shortestLapIndex].lapTime
        val newLapTime = lapList.value[0].lapTime

        if (currentShortest > newLapTime) {
            shortestLapIndex = 0
        }
        saveShortestLapIndex()

    }

    suspend fun recordLongestLap() {

        val size = lapList.value.size
        if (size <= 1) {
            return
        }

        longestLapIndex += 1

        val currentShortest = lapList.value[longestLapIndex].lapTime
        val newLapTime = lapList.value[0].lapTime

        if (currentShortest < newLapTime) {
            longestLapIndex = 0
        }

        saveLongestLapIndex()
    }


    private suspend fun stopwatchLabelStyles() {
        if (stopwatchPreferences.getStopwatchShowLabel.first()) {
            when (stopwatchPreferences.getStopwatchLabelStyle.first()) {
                DYNAMIC_COLOR -> {}
                RGB -> {
                    StopwatchRGBStyle().rgbStyleUpdateColors(stopwatchPreferences.getStopwatchRefreshRate.first())
                    saveRGBColorCounter()
                }
            }
        }
    }

    fun pauseStopWatch() {
        stopwatchIsActive = false
        stopwatchOffsetTime = stopwatchTime
    }

    fun resetStopWatch() {
        viewModelScope.launch {
            shortestLapIndex = 0
            longestLapIndex = 0

            stopwatchIsActive = false
            stopwatchTime = 0L
            lapPreviousTime = 0

            stopwatchOffsetTime = 0L
            saveStopwatchOffsetTime()
            saveStopwatchTime()
        }
    }

    fun isAtLestOneHour(): Boolean {
        return stopwatchTime >= 3_600_000
    }

    fun formatTimeStopWatchHr(timeMillis: Long): String {
        val hours = timeMillis / 1000 / 60 / 60 % 100
        return "%02d".format(hours)
    }

    fun formatTimeStopWatchMin(timeMillis: Long): String {
        val minutes = timeMillis / 1000 / 60 % 60
        return "%02d".format(minutes)
    }

    fun formatTimeStopWatchSec(timeMillis: Long): String {
        val seconds = timeMillis / 1000 % 60
        return "%02d".format(seconds)
    }

    fun formatTimeStopWatchMs(timeMillis: Long): String {
        val milliseconds = timeMillis % 1000 / 10
        return "%02d".format(milliseconds)
    }

    fun formatLapTime(timeMillis: Long, index: Int): String {
        val hr = formatTimeStopWatchHr(timeMillis = timeMillis)
        val min = formatTimeStopWatchMin(timeMillis = timeMillis)
        val sec = formatTimeStopWatchSec(timeMillis = timeMillis)
        val ms = formatTimeStopWatchMs(timeMillis = timeMillis)


        return if (lapList.value[index].lapTotalTime >= 3_600_000) "$hr:$min:$sec.$ms" else "$min:$sec.$ms"
    }


    fun getLapNumber(index: Int): String {
        val lapNumber = lapList.value[index].lapNumber
        return if (lapNumber < 10) "0${lapNumber}" else lapNumber.toString()
    }

    fun getLapTime(index: Int): String {
        val time = lapList.value[index].lapTime
        return formatLapTime(timeMillis = time, index = index)
    }

    fun getLapTotalTime(index: Int): String {
        val time = lapList.value[index].lapTotalTime
        return formatLapTime(timeMillis = time, index = index)
    }

    suspend fun stopwatchTimeClearAll() {
        stopwatchPreferences.clearAll()
    }

    suspend fun loadStopwatchTime() {
        stopwatchTime = stopwatchPreferences.getStopwatchTime.first()
    }

    suspend fun loadStopwatchOffset() {
        stopwatchOffsetTime = stopwatchPreferences.getStopwatchOffsetTime.first()
    }

    suspend fun loadStopwatchShortestLapIndex() {
        shortestLapIndex = stopwatchPreferences.getStopwatchShortestLapIndex.first()
    }

    suspend fun loadStopwatchLabelStyleSelectedOption() {
        labelStyleSelectedOption = stopwatchPreferences.getStopwatchLabelStyle.first()
    }

    suspend fun loadStopwatchLapPreviousTime() {
        lapPreviousTime = stopwatchPreferences.getStopwatchLapPreviousTime.first()
    }

    suspend fun loadStopwatchLongestLapIndex() {
        longestLapIndex = stopwatchPreferences.getStopwatchLongestLapIndex.first()
    }

    suspend fun saveStopwatchTime() {
        stopwatchPreferences.setStopwatchTime(long = stopwatchTime)
    }

    suspend fun saveStopwatchLapPreviousTime() {
        stopwatchPreferences.setStopwatchLapPreviousTime(long = lapPreviousTime)
    }

    suspend fun saveStopwatchOffsetTime() {
        stopwatchPreferences.setStopwatchOffsetTime(long = stopwatchOffsetTime)
    }

    suspend fun saveRGBColorCounter() {
        stopwatchPreferences.setLabelStyleRGBColorCounter(double = RGBColorCounter)
    }


    suspend fun saveLap(lap: StopwatchLapData) {
        stopwatchLapDatabase.itemDao().insert(lap = lap)
    }

    suspend fun saveShortestLapIndex() {
        stopwatchPreferences.setStopwatchShortestLapIndex(int = shortestLapIndex)
    }

    suspend fun saveLongestLapIndex() {
        stopwatchPreferences.setStopwatchLongestLapIndex(int = longestLapIndex)
    }

    suspend fun clearAllLap() {
        stopwatchLapDatabase.itemDao().deleteAllItems()
    }


}