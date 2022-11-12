package com.kingfu.clok.stopwatch.stopwatchFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch
import com.kingfu.clok.stopwatch.stopwatchViewModel.StopwatchViewModel

class StopwatchFactory(
    private val stopwatchPreferences: StopwatchPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopwatchViewModel::class.java)) {
            return StopwatchViewModel(stopwatchPreferences) as T
        }else if(modelClass.isAssignableFrom(SettingsViewModelStopwatch::class.java)){
            return SettingsViewModelStopwatch(stopwatchPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}