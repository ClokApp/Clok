package com.kingfu.clok.settings.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelStopwatch(
    private val stopwatchPreferences: StopwatchPreferences
) : ViewModel() {
    var stopwatchSaveTimeCheckState by mutableStateOf(true)
        private set

    var stopwatchSaveLapTimeCheckState by mutableStateOf(true)
        private set

    var stopwatchRefreshRateValue by mutableStateOf(51F)
        private set

    var stopwatchShowLabel by mutableStateOf(true)
        private set

    var stopwatchLabelStyleSelectedOption by mutableStateOf("RGB")
        private set

    init {
        viewModelScope.launch {
            stopwatchSaveTimeCheckState = stopwatchPreferences.getStopwatchSaveTime.first()
            stopwatchSaveLapTimeCheckState = stopwatchPreferences.getStopwatchSaveLapTime.first()
            stopwatchRefreshRateValue = stopwatchPreferences.getStopwatchRefreshRate.first()
            stopwatchShowLabel = stopwatchPreferences.getStopwatchShowLabel.first()
            stopwatchLabelStyleSelectedOption = stopwatchPreferences.getStopwatchLabelStyleSelectedOption.first()
        }
    }

    fun stopwatchSetLabelStyleSelectedOption(name: String){
        stopwatchLabelStyleSelectedOption = name
    }


    fun saveStopwatchLabelStyleSelectedOption(){
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLabelStyleSelectedOption(stopwatchLabelStyleSelectedOption)
        }
    }

    fun saveStopwatchTimeCheckState() {
        stopwatchSaveTimeCheckState = !stopwatchSaveTimeCheckState
    }

    fun saveStopwatchSaveTimeCheckState() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchAutoSaveTime(stopwatchSaveTimeCheckState)
        }
    }

    fun stopwatchSaveLapTimeCheckState() {
        stopwatchSaveLapTimeCheckState = !stopwatchSaveLapTimeCheckState
    }

    fun saveStopwatchSaveLapTimeCheckState() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchAutoLapTime(stopwatchSaveLapTimeCheckState)
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


}
