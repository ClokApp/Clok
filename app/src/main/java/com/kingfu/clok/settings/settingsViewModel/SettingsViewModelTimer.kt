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
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerScrollsFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelTimer.SettingsViewModelTimerVariables.timerTimeFontStyleSelectedOption
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelTimer(
    private val timerPreferences: TimerPreferences
) : ViewModel() {

    object SettingsViewModelTimerVariables {
        var timerLabelStyleSelectedOption by mutableStateOf(value = "RGB")
        var timerCountOvertime by mutableStateOf(value = true)
        var timerEnableScrollsHapticFeedback by mutableStateOf(value = true)
        var timerBackgroundEffectsSelectedOption by mutableStateOf(value ="Snow")
        var timerScrollsFontStyleSelectedOption by mutableStateOf(value = "Default")
        var timerTimeFontStyleSelectedOption by mutableStateOf(value = "Default")
    }

    var timerNotification by mutableStateOf(value = 5f)
        private set
    init {
        viewModelScope.launch {
            loadTimerCountOvertime()
            loadTimerLabelStyleSelectedOption()
            loadTimerEnableScrollsHapticFeedback()
            loadTimerNotification()
            loadTimerBackgroundEffectsSelectedOption()
            loadTimerScrollsFontStyleSelectedOption()
            loadTimerTimeFontStyleSelectedOption()
        }
    }

    fun timerToggleCountOvertime() {
        timerCountOvertime = !timerCountOvertime
    }


    fun timerSetLabelStyleSelectedOption(name: String) {
        timerLabelStyleSelectedOption = name
    }

    fun timerSetEnableScrollsHapticFeedback() {
        timerEnableScrollsHapticFeedback = !timerEnableScrollsHapticFeedback
    }
    fun timerSetNotification(float: Float) {
        timerNotification = float
    }

    fun setTimerBackgroundEffectsSelectedOption(name: String) {
        timerBackgroundEffectsSelectedOption = name
    }

    fun setTimerScrollsFontStyleSelectedOption(name: String){
        timerScrollsFontStyleSelectedOption = name
    }

    fun setTimerTimeFontStyleSelectedOption(string: String){
        timerTimeFontStyleSelectedOption = string
    }

    fun saveTimerLabelStyleSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerLabelStyleSelectedOption(timerLabelStyleSelectedOption)
        }
    }

    fun saveTimerEnableScrollsHapticFeedback() {
        viewModelScope.launch {
            timerPreferences.setTimerEnableScrollsHapticFeedback(timerEnableScrollsHapticFeedback)
        }
    }

    fun saveTimerCountOvertime() {
        viewModelScope.launch {
            timerPreferences.setTimerCountOvertime(timerCountOvertime)
        }
    }


    fun saveTimerNotification() {
        viewModelScope.launch {
            timerPreferences.setTimerNotification(timerNotification)
        }
    }

    fun saveTimerBackgroundEffectsSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerBackgroundEffects(timerBackgroundEffectsSelectedOption)
        }
    }

    fun saveTimerScrollsFontStyleSelectedOption(){
        viewModelScope.launch {
            timerPreferences.setTimerScrollFontStyle(timerScrollsFontStyleSelectedOption)
        }
    }

    fun saveTimerTimeFontStyleSelectedOption(){
        viewModelScope.launch {
            timerPreferences.setTimerTimeFontStyle(timerTimeFontStyleSelectedOption)
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

    suspend fun loadTimerBackgroundEffectsSelectedOption(){
        timerBackgroundEffectsSelectedOption = timerPreferences.getTimerBackgroundEffects.first()
    }

    suspend fun loadTimerScrollsFontStyleSelectedOption(){
        timerScrollsFontStyleSelectedOption = timerPreferences.getTimerScrollsFontStyle.first()
    }

    suspend fun loadTimerTimeFontStyleSelectedOption(){
        timerTimeFontStyleSelectedOption = timerPreferences.getTimerTimeFontStyle.first()
    }



}