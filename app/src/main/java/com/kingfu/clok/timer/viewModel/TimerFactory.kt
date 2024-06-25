package com.kingfu.clok.timer.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kingfu.clok.timer.repository.TimerPreferences

class TimerFactory(
    private val timerPreferences: TimerPreferences,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(timerPreferences = timerPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}