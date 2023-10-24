package com.kingfu.clok.settings.viewModel.settingsViewModelStopwatch

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
@OptIn(SavedStateHandleSaveableApi::class)
class SettingsViewModelStopwatch(
    private val stopwatchPreferences: StopwatchPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by savedStateHandle.saveable { mutableStateOf(value = SettingsViewModelStopwatchState()) }
        private set

    init {
        viewModelScope.launch {
            loadAllStates()
        }
    }



    fun updateStopwatchLabelStyle(string: String) {
        state = state.copy(stopwatchLabelStyle = string)
    }

    fun updateStopwatchBackgroundEffect(string: String) {
        state = state.copy(stopwatchLabelBackgroundEffects = string)
    }

    fun updateStopwatchRefreshRateValue(refreshRate: Long) {
        state = state.copy(stopwatchRefreshRateValue = refreshRate)
    }

    fun toggleStopwatchIsShowLabel() {
        state = state.copy(stopwatchIsShowLabel = !state.stopwatchIsShowLabel)
    }

    fun updateStopwatchLabelFontStyle(string: String) {
        state = state.copy(stopwatchLabelFontStyle = string)
    }

    fun updateStopwatchTimeFontStyle(string: String) {
        state = state.copy(stopwatchTimeFontStyle = string)
    }

    fun updateStopwatchLapTimeFontStyle(string: String) {
        state = state.copy(stopwatchLapTimeFontStyle = string)
    }

    fun saveStopwatchRefreshRateValue() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchRefreshRate(long = state.stopwatchRefreshRateValue)
        }
    }

    fun saveStopwatchShowLabel() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchIsShowLabel(boolean = state.stopwatchIsShowLabel)
        }
    }

    fun saveStopwatchLabelStyle() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchLabelStyle(
                string = state.stopwatchLabelStyle
            )
        }
    }

    fun saveStopwatchBackgroundEffects() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchBackgroundEffects(
                string = state.stopwatchLabelBackgroundEffects
            )
        }
    }

    fun saveStopwatchLabelFontStyle() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchLabelFontStyle(string = state.stopwatchLabelFontStyle)
        }
    }

    fun saveStopwatchTimeFontStyle() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchTimeFontStyle(string = state.stopwatchTimeFontStyle)
        }
    }

    fun saveStopwatchLapTimeFontStyle() {
        viewModelScope.launch(context = Dispatchers.IO) {
            stopwatchPreferences.setStopwatchLapTimeFontStyle(string = state.stopwatchLapTimeFontStyle)
        }
    }

    private suspend fun loadAllStates() {
        state = state.copy(
            stopwatchRefreshRateValue = stopwatchPreferences.getStopwatchRefreshRate.first(),
            stopwatchIsShowLabel = stopwatchPreferences.getStopwatchIsShowLabel.first(),
            stopwatchLabelStyle = stopwatchPreferences.getStopwatchLabelStyle.first(),
            stopwatchLabelBackgroundEffects = stopwatchPreferences.getStopwatchLabelBackgroundEffects.first(),
            stopwatchLabelFontStyle = stopwatchPreferences.getStopwatchLabelFontStyle.first(),
            stopwatchTimeFontStyle = stopwatchPreferences.getStopwatchTimeFontStyle.first(),
            stopwatchLapTimeFontStyle = stopwatchPreferences.getStopwatchLapTimeFontStyle.first()
        )
    }
}
