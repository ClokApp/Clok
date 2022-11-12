package com.kingfu.clok.timer.timerFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer
import com.kingfu.clok.timer.timerViewModel.TimerViewModel

class TimerFactory(
    private val timerPreferences: TimerPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(timerPreferences) as T
        }else if(modelClass.isAssignableFrom(SettingsViewModelTimer::class.java)){
            return SettingsViewModelTimer(timerPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}