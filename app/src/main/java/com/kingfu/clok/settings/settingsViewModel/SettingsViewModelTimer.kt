package com.kingfu.clok.settings.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerBackgroundEffectsSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerCountOvertime
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerEnableScrollsHapticFeedback
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerLabelStyleSelectedOption
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelTimer(
    private val timerPreferences: TimerPreferences
) : ViewModel() {

    object SettingsViewModelTimerVariables {
        var timerLabelStyleSelectedOption by mutableStateOf("RGB")
        var timerCountOvertime by mutableStateOf(true)
        var timerEnableScrollsHapticFeedback by mutableStateOf(true)
        var timerBackgroundEffectsSelectedOption by mutableStateOf("Snow")
    }

    var timerNotification by mutableStateOf(5f)
        private set

    init {
        viewModelScope.launch {
            loadTimerCountOvertime()
            loadTimerLabelStyleSelectedOption()
            loadTimerEnableScrollsHapticFeedback()
            loadTimerNotification()
            loadTimerBackgroundEffectsSelectedOption()
        }
    }

    fun timerCountOvertime() {
        timerCountOvertime = !timerCountOvertime
    }

    fun saveTimerCountOvertime() {
        viewModelScope.launch {
            timerPreferences.setTimerCountOvertime(timerCountOvertime)
        }
    }

    fun timerSetLabelStyleSelectedOption(name: String) {
        timerLabelStyleSelectedOption = name
    }

    fun saveTimerLabelStyleSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerLabelStyleSelectedOption(timerLabelStyleSelectedOption)
        }
    }

    fun timerSetEnableScrollsHapticFeedback() {
        timerEnableScrollsHapticFeedback = !timerEnableScrollsHapticFeedback
    }

    fun saveTimerEnableScrollsHapticFeedback() {
        viewModelScope.launch {
            timerPreferences.setTimerEnableScrollsHapticFeedback(timerEnableScrollsHapticFeedback)
        }
    }

    fun timerSetNotification(float: Float) {
        timerNotification = float
    }

    fun saveTimerNotification() {
        viewModelScope.launch {
            timerPreferences.setTimerNotification(timerNotification)
        }
    }

    suspend fun loadTimerCountOvertime() {
        timerCountOvertime = timerPreferences.getTimerCountOvertime.first()
    }

    suspend fun loadTimerLabelStyleSelectedOption() {
        timerLabelStyleSelectedOption = timerPreferences.getTimerLabelStyleSelectedOption.first()
    }

    suspend fun loadTimerEnableScrollsHapticFeedback() {
        timerEnableScrollsHapticFeedback =
            timerPreferences.getTimerEnableScrollsHapticFeedback.first()
    }

    suspend fun loadTimerNotification() {
        timerNotification = timerPreferences.getTimerNotification.first()
    }

    fun setTimerBackgroundEffectsSelectedOption(string: String) {
        timerBackgroundEffectsSelectedOption = string
    }

    fun saveTimerBackgroundEffectsSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerBackgroundEffects(timerBackgroundEffectsSelectedOption)
        }
    }

    suspend fun loadTimerBackgroundEffectsSelectedOption(){
        timerBackgroundEffectsSelectedOption = timerPreferences.getTimerBackgroundEffects.first()
    }

}