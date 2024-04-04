package com.kingfu.clok.stopwatch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.repository.room.stopwatchRoom.StopwatchLapDatabase
import com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch.SettingsViewModelStopwatch

class StopwatchFactory(
    private val stopwatchPreferences: StopwatchPreferences,
    private val stopwatchLapDatabase: StopwatchLapDatabase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(StopwatchViewModel::class.java)) {
            return StopwatchViewModel(
                stopwatchPreferences = stopwatchPreferences,
                stopwatchLapDatabase = stopwatchLapDatabase,
                savedStateHandle = extras.createSavedStateHandle()
            ) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModelStopwatch::class.java)) {
            return SettingsViewModelStopwatch(
                stopwatchPreferences = stopwatchPreferences,
                savedStateHandle = extras.createSavedStateHandle()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}