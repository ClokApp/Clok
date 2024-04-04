package com.kingfu.clok.stopwatch.viewModel

import android.os.SystemClock
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.stopwatch.util.fontStyle.StopwatchFontStyleType
import com.kingfu.clok.stopwatch.util.labelBackgroundEffects.StopwatchLabelBackgroundEffectType
import com.kingfu.clok.stopwatch.util.labelStyle.StopwatchLabelStyleType
import com.kingfu.clok.stopwatch.util.styles.StopwatchRGBStyle
import com.kingfu.clok.util.Variable.isShowSnackbar
import com.kingfu.clok.util.Variable.snackbarMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

@OptIn(SavedStateHandleSaveableApi::class)
class StopwatchViewModel(
    private val stopwatchPreferences: StopwatchPreferences,
    private val stopwatchLapDatabase: StopwatchLapDatabase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by savedStateHandle.saveable { mutableStateOf(value = StopwatchState()) }
        private set


    var lapList: StateFlow<List<StopwatchLapData>> =
        stopwatchLapDatabase.itemDao().getAll().stateIn(
            viewModelScope, SharingStarted.Eagerly, emptyList()
        )

    init {

        viewModelScope.launch {
            loadAllData()

            state = state.copy(stopwatchOffsetTime = state.stopwatchTime)
            if (state.stopwatchIsActive) {
                startStopwatch()
            }
        }
    }


    fun lap() {
        if (lapList.value.size == 1_000_000) {
            isShowSnackbar = true
            snackbarMessage = "Max lap number is reached"
            return
        }
        state = state.copy(isLap = true)
    }

    fun toggleIsScrollLazyColumn() {
        state = state.copy(isScrollLazyColumn = !state.isScrollLazyColumn)
    }


    fun clearLapTimes() {
        viewModelScope.launch {
            state = state.copy(
                lapPreviousTime = 0L,
                shortestLapIndex = 0,
                longestLapIndex = 0
            )
            clearAllLap()
            saveStopwatchLapPreviousTime()
            saveShortestLapIndex()
            saveLongestLapIndex()
        }
    }

    fun startStopwatch() {
        state = state.copy(
            stopwatchIsActive = true,
            stopwatchInitialTime = SystemClock.elapsedRealtime()
        )

        viewModelScope.launch {
            while (state.stopwatchIsActive) {
                if (state.stopwatchTime >= 360_000_000 - 1) {
                    state = state.copy(stopwatchTime = 360_000_000 - 1)
                    pauseStopwatch()
                    break
                }

                state = state.copy(
                    stopwatchTime = SystemClock.elapsedRealtime() -
                            state.stopwatchInitialTime + state.stopwatchOffsetTime
                )

                if (state.isLap) {
                    state = state.copy(isLap = false)
                    addLap()
                    delay(timeMillis = 10)
                    recordShortestLap()
                    recordLongestLap()
                }

//                state = state.copy(
//                    stopwatchTime = state.stopwatchTime + 90_999
//                )

                saveStopwatchLapPreviousTime()
                saveStopwatchOffsetTime()
                saveStopwatchTime()
                delay(timeMillis = stopwatchPreferences.getStopwatchRefreshRate.first().toLong())

            }
        }
    }

    suspend fun addLap() {
        val curStopwatchTime = state.stopwatchTime - (state.stopwatchTime % 10)
        val curLapPreviousTime = state.lapPreviousTime - (state.lapPreviousTime % 10)

        val lap = StopwatchLapData(
            lapNumber = lapList.value.size + 1,
            lapTime = curStopwatchTime - curLapPreviousTime,
            lapTotalTime = curStopwatchTime
        )

        saveLap(lap = lap)

        state = state.copy(lapPreviousTime = curStopwatchTime)
        saveStopwatchLapPreviousTime()
    }

    fun recordShortestLap() {

        val size = lapList.value.size

        if (size <= 1) {
            return
        }

        state = state.copy(shortestLapIndex = state.shortestLapIndex + 1)

        val currentShortest = lapList.value[state.shortestLapIndex].lapTime
        val newLapTime = lapList.value[0].lapTime

        if (currentShortest > newLapTime) {
            state = state.copy(shortestLapIndex = 0)
        }
        saveShortestLapIndex()
    }

    fun recordLongestLap() {

        val size = lapList.value.size
        if (size <= 1) {
            return
        }

        state = state.copy(longestLapIndex = state.longestLapIndex + 1)

        val currentShortest = lapList.value[state.longestLapIndex].lapTime
        val newLapTime = lapList.value[0].lapTime

        if (currentShortest < newLapTime) {
            state = state.copy(longestLapIndex = 0)
        }

        saveLongestLapIndex()
    }

    fun updateStopwatchLabelStyle(selectedLabelStyle: StopwatchLabelStyleType) {
        when (selectedLabelStyle) {
            StopwatchLabelStyleType.DynamicColor -> {}
            StopwatchLabelStyleType.Rgb -> {
                StopwatchRGBStyle().rgbStyleUpdateColors()
                saveRGBColorCounter()
            }
        }
    }

    fun pauseStopwatch() {
        state = state.copy(
            stopwatchIsActive = false,
            stopwatchOffsetTime = state.stopwatchTime
        )
    }

    fun resetStopwatch() {
        viewModelScope.launch {
            state = state.copy(
                stopwatchIsActive = false,
                stopwatchTime = 0L,
                stopwatchOffsetTime = 0L,
                lapPreviousTime = 0L
            )
            saveStopwatchOffsetTime()
            saveStopwatchTime()
            saveStopwatchLapPreviousTime()
        }
    }

    suspend fun stopwatchTimeClearAll() {
        stopwatchPreferences.clearAll()
    }

    fun getStopwatchIsShowLabel(): Boolean {
        var result = true
        viewModelScope.launch {
            result = stopwatchPreferences.getStopwatchIsShowLabel.first()
        }
        return result
    }

    fun getStopwatchLabelBackgroundEffect(): StopwatchLabelBackgroundEffectType {
        var result: String? = null
        viewModelScope.launch {
            result = stopwatchPreferences.getStopwatchLabelBackgroundEffects.firstOrNull()
        }
        return if (result != null) enumValueOf(result!!) else StopwatchLabelBackgroundEffectType.Snow
    }

    fun getStopwatchLabelStyle(): StopwatchLabelStyleType {
        var result: String? = null
        viewModelScope.launch {
            result = stopwatchPreferences.getStopwatchLabelStyle.first()
        }
        return if (result != null) enumValueOf(result!!) else StopwatchLabelStyleType.DynamicColor

    }

    fun getStopwatchLabelFontStyle(): StopwatchFontStyleType {
        var result = StopwatchFontStyleType.Default.name
        viewModelScope.launch {
            result = stopwatchPreferences.getStopwatchLabelFontStyle.first()
        }
        return enumValueOf(result)
    }

    fun getStopwatchTimeFontStyle(): StopwatchFontStyleType {
        var result = StopwatchFontStyleType.Default.name
        viewModelScope.launch {
            result = stopwatchPreferences.getStopwatchTimeFontStyle.first()
        }
        return enumValueOf(result)
    }

    fun getStopwatchLapTimeFontStyle(): StopwatchFontStyleType {
        var result = StopwatchFontStyleType.Default.name
        viewModelScope.launch {
            result = stopwatchPreferences.getStopwatchLapTimeFontStyle.first()
        }
        return enumValueOf(result)
    }

    fun saveStopwatchTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchTime(long = state.stopwatchTime)
        }
    }

    fun saveStopwatchLapPreviousTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchLapPreviousTime(long = state.lapPreviousTime)
        }
    }

    fun saveStopwatchOffsetTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchOffsetTime(long = state.stopwatchOffsetTime)
        }
    }

    fun saveRGBColorCounter() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setLabelStyleRGBColorCounter(
                double = StopwatchRGBStyle.RGBVariable.RGBColorCounter
            )
        }
    }


    suspend fun saveLap(lap: StopwatchLapData) {
        // need to not save using Dispatchers.IO to work properly
        // because it's already using flow coroutine
//        viewModelScope.launch(context = Dispatchers.IO) {
        stopwatchLapDatabase.itemDao().insert(lap = lap)
//        }
    }

    fun saveShortestLapIndex() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchShortestLapIndex(int = state.shortestLapIndex)
        }
    }

    fun saveLongestLapIndex() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchLongestLapIndex(int = state.longestLapIndex)
        }
    }

    suspend fun clearAllLap() {
        stopwatchLapDatabase.itemDao().deleteAllItems()
    }

    private suspend fun loadAllData() {
        state = state.copy(
            stopwatchTime = stopwatchPreferences.getStopwatchTime.first(),
            stopwatchOffsetTime = stopwatchPreferences.getStopwatchOffsetTime.first(),
            shortestLapIndex = stopwatchPreferences.getStopwatchShortestLapIndex.first(),
            lapPreviousTime = stopwatchPreferences.getStopwatchLapPreviousTime.first(),
            longestLapIndex = stopwatchPreferences.getStopwatchLongestLapIndex.first()
        )
    }


}