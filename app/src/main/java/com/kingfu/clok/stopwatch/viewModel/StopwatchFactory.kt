package com.kingfu.clok.stopwatch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingfu.clok.stopwatch.repository.StopwatchPreferences
import com.kingfu.clok.stopwatch.repository.stopwatchRoom.StopwatchLapDatabase
class StopwatchFactory(
    private val stopwatchPreferences: StopwatchPreferences,
    private val stopwatchLapDatabase: StopwatchLapDatabase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopwatchViewModel::class.java)) {
            return StopwatchViewModel(
                stopwatchPreferences = stopwatchPreferences,
                stopwatchLapDatabase = stopwatchLapDatabase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}