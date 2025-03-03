package com.kingfu.clok.timer.viewModel

import android.os.SystemClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.timer.repository.TimerPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TimerViewModel(
    private val timerPreferences: TimerPreferences,
) : ViewModel() {

    var state by mutableStateOf(value = TimerState())
        private set


    init { viewModelScope.launch { loadAllData() } }


    fun setTotalTime(long: Long) {
        state = state.copy(totalTime = long.toDouble())
    }


    fun start(long: Long) {
        viewModelScope.launch {
            setIsActive(boolean = true)
            if (state.isEdit) setTime(long = long)
            setIsEdit(boolean = false)
            setInitialTime(long = SystemClock.elapsedRealtime())
            while (state.isActive) {
                if (state.time >= 0L && !state.isFinished) {
                    setTime(long = state.offsetTime + state.initialTime - SystemClock.elapsedRealtime())
                } else {

                    if (!state.isFinished) {
                        setIsFinished(boolean = true)
                        setOffsetTime(long = 0)
                        setInitialTime(long = SystemClock.elapsedRealtime())
                    }

                    if(state.time < 360_000_000L - 2) {
                        setTime(long = state.offsetTime + SystemClock.elapsedRealtime() - state.initialTime)
                    }

                }
                delay(timeMillis = 1)
            }
        }
    }

    fun pause() {
        setOffsetTime(state.time)
        setIsActive(boolean = false)
        saveIsEdit()
        saveTotalTime()
        saveIsFinished()
    }

    fun cancel() {
        setIsActive(boolean = false)
        setIsTotalTime(double = 0.0)
        setIsFinished(boolean = false)
        setOffsetTime(long = 0L)
        setIsEdit(boolean = true)
        setTime(long = 0L)
    }

    fun reset() {
        setIsActive(boolean = false)
        setHour(int = 0)
        setMinute(int = 0)
        setSecond(int = 0)
        setTime(long = 0L)
    }

    fun setInitialTime(long: Long) {
        state = state.copy(initialTime = long)
    }

    fun setTime(long: Long) {
        state = state.copy(time = long)
    }

    fun setIsEdit(boolean: Boolean) {
        state = state.copy(isEdit = boolean)
        saveIsEdit()
    }

    fun setIsFinished(boolean: Boolean) {
        state = state.copy(isFinished = boolean)
        saveIsFinished()
    }

    fun setIsTotalTime(double: Double) {
        state = state.copy(totalTime = double)
        saveTotalTime()
    }

    fun setOffsetTime(long: Long) {
        state = state.copy(offsetTime = long)
        saveOffsetTime()
    }

    fun setIsActive(boolean: Boolean) {
        state = state.copy(isActive = boolean)
    }

    fun setHour(int: Int) {
        state = state.copy(hour = int)
        saveHour()
    }

    fun setMinute(int: Int) {
        state = state.copy(minute = int)
        saveMinute()
    }

    fun setSecond(int: Int) {
        state = state.copy(second = int)
        saveSecond()
    }

    private fun saveHour() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerHour(duration = state.hour)
        }
    }

    private fun saveMinute() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setMinute(int = state.minute)
        }
    }

    private fun saveSecond() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setSecond(int = state.second)
        }
    }

    private fun saveIsFinished() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setIsFinished(boolean = state.isFinished)
        }
    }

    private fun saveTotalTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTotalTime(double = state.totalTime)
        }
    }

    private fun saveIsEdit() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setIsEdit(boolean = state.isEdit)
        }
    }

    private fun saveOffsetTime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setOffsetTime(long = state.offsetTime)
        }
    }

    private suspend fun loadAllData() {
        state = state.copy(
            hour = timerPreferences.getHour.first(),
            minute = timerPreferences.getMinute.first(),
            second = timerPreferences.getSecond.first(),
            isFinished = timerPreferences.getIsFinished.first(),
            isEdit = timerPreferences.getIsEdit.first(),
            totalTime = timerPreferences.getTotalTime.first(),
            offsetTime = timerPreferences.getOffsetTime.first(),
        )
        state = state.copy(time = state.offsetTime)
        state = state.copy(isLoaded = true)

    }


}