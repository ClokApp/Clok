package com.kingfu.clok.timer.timerViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer

class TimerFactory(
    private val timerPreferences: TimerPreferences,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(timerPreferences = timerPreferences) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModelTimer::class.java)) {
            return SettingsViewModelTimer(timerPreferences = timerPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}