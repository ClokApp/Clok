package com.kingfu.clok.stopwatch.stopwatchViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch

class StopwatchFactory(
    private val stopwatchPreferences: StopwatchPreferences,
    private val StopwatchLapDatabase: StopwatchLapDatabase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopwatchViewModel::class.java)) {
            return StopwatchViewModel(
                stopwatchPreferences = stopwatchPreferences,
                stopwatchLapDatabase = StopwatchLapDatabase,
                savedStateHandle = savedStateHandle
            ) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModelStopwatch::class.java)) {
            return SettingsViewModelStopwatch(stopwatchPreferences = stopwatchPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}