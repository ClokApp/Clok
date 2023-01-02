package com.kingfu.clok.settings.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchBackgroundEffectsSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchRefreshRateValue
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchShowLabel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelStopwatch(
    private val stopwatchPreferences: StopwatchPreferences
) : ViewModel() {

    object SettingsViewModelStopwatchVariable {
        var stopwatchShowLabel by mutableStateOf(true)
        var stopwatchBackgroundEffectsSelectedOption by mutableStateOf("Snow")
        var stopwatchRefreshRateValue by mutableStateOf(51f)
        var stopwatchLabelStyleSelectedOption by mutableStateOf("RGB")
    }

    init {
        viewModelScope.launch {
            loadStopwatchRefreshRateValue()
            loadStopwatchShowLabel()
            loadStopwatchLabelStyleSelectedOption()
            loadStopwatchBackgroundEffectsSelectedOption()
        }
    }

    fun stopwatchSetLabelStyleSelectedOption(string: String) {
        stopwatchLabelStyleSelectedOption = string
    }

    fun saveStopwatchLabelStyleSelectedOption() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLabelStyleSelectedOption(
                stopwatchLabelStyleSelectedOption
            )
        }
    }

    fun stopwatchRefreshRateValue(float: Float) {
        stopwatchRefreshRateValue = float
    }

    fun saveStopwatchRefreshRateValue() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchRefreshRate(stopwatchRefreshRateValue)
        }
    }

    fun saveStopwatchShowLabel() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchShowLabel(stopwatchShowLabel)
        }
    }

    fun stopwatchShowLabelCheckState() {
        stopwatchShowLabel = !stopwatchShowLabel
    }

    fun saveStopwatchBackgroundEffectsSelectedOption() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchBackgroundEffects(
                stopwatchBackgroundEffectsSelectedOption
            )
        }
    }

    fun stopwatchSetBackgroundEffectSelectedOption(string: String) {
        stopwatchBackgroundEffectsSelectedOption = string
    }


    suspend fun loadStopwatchRefreshRateValue(){
        stopwatchRefreshRateValue = stopwatchPreferences.getStopwatchRefreshRate.first()
    }

    suspend fun loadStopwatchShowLabel(){
        stopwatchShowLabel = stopwatchPreferences.getStopwatchShowLabel.first()
    }

    suspend fun loadStopwatchLabelStyleSelectedOption(){
        stopwatchLabelStyleSelectedOption = stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()
    }

    suspend fun loadStopwatchBackgroundEffectsSelectedOption(){
        stopwatchBackgroundEffectsSelectedOption = stopwatchPreferences.getStopwatchBackgroundEffects.first()
    }



}
