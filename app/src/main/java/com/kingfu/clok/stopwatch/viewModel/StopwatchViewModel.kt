package com.kingfu.clok.stopwatch.viewModel

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.stopwatch.repository.StopwatchPreferences
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapData
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

class StopwatchViewModel(
    private val stopwatchPreferences: StopwatchPreferences,
    private val stopwatchLapDatabase: StopwatchLapDatabase,
) : ViewModel() {

    var state by mutableStateOf(value = StopwatchState())
        private set


    var laps: StateFlow<List<StopwatchLapData>> =
        stopwatchLapDatabase.itemDao().getAll().stateIn(
            viewModelScope, SharingStarted.Eagerly, emptyList()
        )

    init {
        viewModelScope.launch {
            loadAllData()
        }
    }


    fun start() {
        setIsActive(boolean = true)
        setInitialTime(long = SystemClock.elapsedRealtime())

        viewModelScope.launch {
            while (state.isActive) {
                if (state.time >= 360_000_000 - 1) {
                    state = state.copy(time = 360_000_000 - 1)
                    pause()
                    break
                }

                setTime(long = SystemClock.elapsedRealtime() - state.initialTime + state.offsetTime)

//                setTime(long = state.time + 90_999)

                saveOffsetTime()
                saveTime()
                delay(timeMillis = 1)
                if (state.isLap) {
                    setIsLap(boolean = false)
                    lap()
                }
            }
        }
    }

    private fun lap() {
        val laps = laps.value
        if (laps.size == 1_000_000) return

        val lapTotalTime = state.time - (state.time % 10)
        val latestTotalLapTime = if(laps.isEmpty()) lapTotalTime else laps.last().lapTotalTime

        val lap = StopwatchLapData(
            lapNumber = laps.size + 1,
            lapTime = if(laps.isEmpty()) lapTotalTime else lapTotalTime - latestTotalLapTime,
            lapTotalTime = lapTotalTime
        )
        saveLap(lap = lap)
    }

    fun setMaxAndMinLapIndex() {
        val laps = laps.value
        if (laps.size == 1_000_000 || laps.isEmpty()) return

        val lapTime = laps.last().lapTime

        if (laps[state.shortestLapIndex].lapTime > lapTime) setShortestLapIndex(laps.lastIndex)
        if (laps[state.longestLapIndex].lapTime < lapTime) setLongestLapIndex(laps.lastIndex)
    }


    fun setTime(long: Long) {
        state = state.copy(time = long)
        saveTime()
    }

    fun setIsLap(boolean: Boolean) {
        state = state.copy(isLap = boolean)
    }

    fun setInitialTime(long: Long) {
        state = state.copy(initialTime = long)
    }

    fun setShortestLapIndex(int: Int) {
        state = state.copy(shortestLapIndex = int)
        saveShortestLapIndex()
    }

    fun setLongestLapIndex(int: Int) {
        state = state.copy(longestLapIndex = int)
        saveLongestLapIndex()
    }

    fun pause() {
        setIsActive(boolean = false)
        setOffsetTime(long = state.time)
    }

    fun setIsActive(boolean: Boolean) {
        state = state.copy(isActive = boolean)
    }

    private fun setOffsetTime(long: Long) {
        state = state.copy(offsetTime = long)
        saveOffsetTime()
    }

    fun reset() {
        viewModelScope.launch {
            clearAllLaps()
            setIsActive(boolean = false)
            setTime(long = 0L)
            setOffsetTime(long = 0L)
            setShortestLapIndex(int = 0)
            setLongestLapIndex(int = 0)
            setIsLap(boolean = false)
        }
    }

    suspend fun clearAllData() {
        stopwatchPreferences.clearAll()
    }

    private fun saveTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setTime(long = state.time)
        }
    }

    private fun saveOffsetTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setOffsetTime(long = state.offsetTime)
        }
    }

    fun saveLap(lap: StopwatchLapData) {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchLapDatabase.itemDao().insert(lap = lap)
        }
    }

    private fun saveShortestLapIndex() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setShortestLapIndex(int = state.shortestLapIndex)
        }
    }

    private fun saveLongestLapIndex() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setLongestLapIndex(int = state.longestLapIndex)
        }
    }

    private suspend fun clearAllLaps() {
        stopwatchLapDatabase.itemDao().deleteAllItems()
    }

    private suspend fun loadAllData() {
        state = state.copy(
            time = stopwatchPreferences.getTime.first(),
            offsetTime = stopwatchPreferences.getOffsetTime.first(),
            shortestLapIndex = stopwatchPreferences.getShortestLapIndex.first(),
            longestLapIndex = stopwatchPreferences.getLongestLapIndex.first(),
        )

        state = state.copy(offsetTime = state.time)
    }


}
