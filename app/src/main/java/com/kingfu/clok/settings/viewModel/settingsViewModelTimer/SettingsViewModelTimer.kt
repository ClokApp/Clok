package com.kingfu.clok.settings.viewModel.settingsViewModelTimer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SettingsViewModelTimer(
    private val timerPreferences: TimerPreferences,
) : ViewModel() {

    var state by mutableStateOf(value = SettingViewModelTimerState())
        private set

    init {
        viewModelScope.launch {
            loadAllStates()
        }
    }

    fun updateScrollHapticFeedback(string: String) {
        state = state.copy(timerScrollsHapticFeedback = string)
    }

    fun timerIsEdit(): Boolean {
        var result = false
        viewModelScope.launch {
            if (timerPreferences.getTimerIsEditState.first()) {
                result = true
            }
        }
        return result
    }

    fun timerToggleCountOvertime() {
        state = state.copy(timerIsCountOvertime = !state.timerIsCountOvertime)
    }


    fun updateTimerLabelStyle(string: String) {
        state = state.copy(timerProgressBarStyle = string)
    }

    fun updateTimerNotification(float: Float) {
        state = state.copy(timerNotification = float)
    }

    fun updateTimerBackgroundEffects(string: String) {
        state = state.copy(timerProgressBarBackgroundEffect = string)
    }

    fun updateTimerScrollsFontStyle(string: String) {
        state = state.copy(timerScrollsFontStyle = string)
    }

    fun updateTimerTimeFontStyle(string: String) {
        state = state.copy(timerTimeFontStyle = string)
    }

    fun saveTimerProgressBarStyle() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerProgressBarStyle(string = state.timerProgressBarStyle)
        }
    }

    fun saveScrollsHapticFeedback() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerScrollsHapticFeedback(string = state.timerScrollsHapticFeedback)
        }
    }

    fun saveTimerIsCountOvertime() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerIsCountOvertime(boolean = state.timerIsCountOvertime)
        }
    }


    fun saveTimerNotification() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerNotification(float = state.timerNotification)
        }
    }

    fun saveTimerBackgroundEffects() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerBackgroundEffects(string = state.timerProgressBarBackgroundEffect)
        }
    }

    fun saveTimerScrollsFontStyle() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerScrollFontStyle(string = state.timerScrollsFontStyle)
        }
    }

    fun saveTimerTimeFontStyle() {
        viewModelScope.launch(context = Dispatchers.IO) {
            timerPreferences.setTimerTimeFontStyle(string = state.timerTimeFontStyle)
        }
    }

    private suspend fun loadAllStates() {
        state = state.copy(
            timerIsCountOvertime = timerPreferences.getTimerIsCountOvertime.first(),
            timerProgressBarStyle = timerPreferences.getTimerProgressBarStyle.first(),
            timerNotification = timerPreferences.getTimerNotification.first(),
            timerProgressBarBackgroundEffect = timerPreferences.getTimerProgressBarBackgroundEffects.first(),
            timerScrollsFontStyle = timerPreferences.getTimerScrollsFontStyle.first(),
            timerTimeFontStyle = timerPreferences.getTimerTimeFontStyle.first(),
            timerScrollsHapticFeedback = timerPreferences.getTimerScrollsHapticFeedback.first()
        )
    }


}

