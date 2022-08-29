package com.kingfu.clok.viewModel.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelTimer(
    private val timerPreferences: TimerPreferences
) : ViewModel() {

    var timerCountOvertime by mutableStateOf(true)
        private set

    var timerLabelStyleSelectedOption by mutableStateOf("RGB")
        private set

    var timerEnableScrollsHapticFeedback by mutableStateOf(true)
        private set

    var timerNotification by mutableStateOf(5f)
        private set

    init {
        viewModelScope.launch {
            timerCountOvertime = timerPreferences.getTimerCountOvertime.first()
            timerLabelStyleSelectedOption = timerPreferences.getTimerLabelStyleSelectedOption.first()
            timerEnableScrollsHapticFeedback = timerPreferences.getTimerEnableScrollsHapticFeedback.first()
            timerNotification = timerPreferences.getTimerNotification.first()
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

    fun timerSetLabelStyleSelectedOption(name: String){
        timerLabelStyleSelectedOption = name
    }

    fun saveTimerLabelStyleSelectedOption(){
        viewModelScope.launch {
            timerPreferences.setTimerLabelStyleSelectedOption(timerLabelStyleSelectedOption)
        }
    }

    fun timerSetEnableScrollsHapticFeedback(){
        timerEnableScrollsHapticFeedback = !timerEnableScrollsHapticFeedback
    }

    fun saveTimerEnableScrollsHapticFeedback(){
        viewModelScope.launch {
            timerPreferences.setTimerEnableScrollsHapticFeedback(timerEnableScrollsHapticFeedback)
        }
    }

    fun timerSetNotification(float: Float){
        timerNotification = float
    }

    fun saveTimerNotification(){
        viewModelScope.launch {
            timerPreferences.setTimerNotification(timerNotification)
        }
    }
}