package com.kingfu.clok.stopwatch.stopwatchViewModel

import android.os.SystemClock
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.kingfu.clok.components.showSnackBar
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchRefreshRateValue
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchShowLabel
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchIsActive
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel.StopwatchViewModelVariable.stopwatchTime
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle
import com.kingfu.clok.stopwatch.styles.StopwatchRGBStyle.RGBVariable.RGBColorCounter
import com.kingfu.clok.variable.Variable.showSnackBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(SavedStateHandleSaveableApi::class)
class StopwatchViewModel(
    private val stopwatchPreferences: StopwatchPreferences,
    private val stopwatchLapDatabase: StopwatchLapDatabase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    /******************************** Stopwatch ********************************/

    object StopwatchViewModelVariable {
        var stopwatchIsActive by mutableStateOf(value = false)
        var stopwatchTime by mutableStateOf(value = 0L)
    }

    var lapPreviousTime by savedStateHandle.saveable { mutableStateOf(value = 0L) }
        private set

    private var _lapList = mutableStateListOf<StopwatchLapData>()
    val lapList: List<StopwatchLapData>
        get() = _lapList

    var stopwatchInitialTime by savedStateHandle.saveable { mutableStateOf(value = 0L) }
        private set

    var stopwatchOffsetTime by savedStateHandle.saveable { mutableStateOf(value = 0L) }
        private set

    var labelStyleSelectedOption by savedStateHandle.saveable { mutableStateOf(value = "RGB") }
        private set

    init {
        viewModelScope.launch {
            loadStopwatchOffset()
            loadStopwatchTime()
            withContext(Dispatchers.IO) {
                loadLap()
            }
            loadLabelStyleSelectedOption()
            loadLapPreviousTime()

            stopwatchOffsetTime = stopwatchTime

            if (stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first() == "RGB") {
                RGBColorCounter = stopwatchPreferences.getStopwatchLabelStyleRGBColorCounter.first()
            }

            when (labelStyleSelectedOption) {
                "RGB" -> loadRGBColorCounter()
            }
        }
    }

    fun clearLapTimes() {
        viewModelScope.launch {
            lapPreviousTime = 0
            _lapList.clear()
            withContext(Dispatchers.IO) {
                clearAllLap()
            }
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

//                stopwatchTime += 50_000
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
            delay(timeMillis = 100)
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

        return if (lapList[index].lapTotalTime >= 3_600_000) "$hr:$min:$sec.$ms" else "$min:$sec.$ms"
    }

    fun formatTimeStopWatch(timeMillis: Long): String {
        var result =
            "${formatTimeStopWatchMin(timeMillis)}:${formatTimeStopWatchSec(timeMillis)}.${
                formatTimeStopWatchMs(timeMillis)}"

        if (stopwatchTime >= 3_600_000) {
            result = "${formatTimeStopWatchHr(timeMillis)}:$result"
            return result
        }

        return result
    }

    fun getLapNumber(index: Int): String {
        val lapNumber = lapList[index].lapNumber
        return if (lapNumber < 10) "0${lapNumber}" else lapNumber.toString()
    }

    fun getLapTime(index: Int): String {
        val time = lapList[index].lapTime
        return formatLapTime(timeMillis = time, index = index)
    }

    fun getLapTotalTime(index: Int): String {
        val time = lapList[index].lapTotalTime
        return formatLapTime(timeMillis = time, index = index)
    }

    suspend fun stopwatchTimeClearAll() {
        stopwatchPreferences.clearAll()
    }

    suspend fun saveStopwatchTime() {
        stopwatchPreferences.setStopwatchTime(long = stopwatchTime)
    }

    suspend fun loadStopwatchTime() {
        stopwatchTime = stopwatchPreferences.getStopwatchTime.first()
    }

    suspend fun loadStopwatchOffset() {
        stopwatchOffsetTime = stopwatchPreferences.getStopwatchOffsetTime.first()
    }

    suspend fun saveStopwatchLapPreviousTime() {
        stopwatchPreferences.setStopwatchLapPreviousTime(long = lapPreviousTime)
    }

    suspend fun saveStopwatchOffsetTime() {
        stopwatchPreferences.setStopwatchOffsetTime(long = stopwatchOffsetTime)
    }

    suspend fun loadRGBColorCounter() {
        RGBColorCounter = stopwatchPreferences.getStopwatchLabelStyleRGBColorCounter.first()
    }

    suspend fun saveRGBColorCounter() {
        stopwatchPreferences.setLabelStyleRGBColorCounter(double = RGBColorCounter)
    }

    suspend fun loadLabelStyleSelectedOption() {
        labelStyleSelectedOption = stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()
    }

    suspend fun loadLapPreviousTime() {
        lapPreviousTime = stopwatchPreferences.getStopwatchLapPreviousTime.first()
    }

    suspend fun loadLap() {
        _lapList.addAll(stopwatchLapDatabase.itemDao().getAll())
    }

    fun addLapHelper(scaffoldState: ScaffoldState) {

        if (lapList.size + 1 < 1_000_000) {
            val lap = StopwatchLapData(
                lapNumber = lapList.size + 1,
                lapTime = stopwatchTime - lapPreviousTime,
                lapTotalTime = stopwatchTime
            )
            addLap(lap)
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    saveLap(lap)
                }
            }
            lapPreviousTime = stopwatchTime
        } else {
            viewModelScope.launch {
                showSnackBar = true
                showSnackBar(
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

    private fun addLap(lap: StopwatchLapData) {
        _lapList.add(index = 0, lap)
    }

    suspend fun saveLap(lap: StopwatchLapData) {
        stopwatchLapDatabase.itemDao().insert(lap)
    }

    suspend fun clearAllLap() {
        stopwatchLapDatabase.itemDao().deleteAllItems()
    }


}