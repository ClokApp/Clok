package com.kingfu.clok.settings.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchBackgroundEffectsSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLabelStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchLapTimeFontStyleSelectedOption
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchRefreshRateValue
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchShowLabel
import com.kingfu.clok.settings.settingsViewModel.SettingsViewModelStopwatch.SettingsViewModelStopwatchVariable.stopwatchTimeFontStyleSelectedOption
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelStopwatch(
    private val stopwatchPreferences: StopwatchPreferences
) : ViewModel() {

    object SettingsViewModelStopwatchVariable {
        var stopwatchShowLabel by mutableStateOf(value = true)
        var stopwatchBackgroundEffectsSelectedOption by mutableStateOf(value = "Snow")
        var stopwatchRefreshRateValue by mutableStateOf(value = 51f)
        var stopwatchLabelStyleSelectedOption by mutableStateOf(value ="RGB")
        var stopwatchLabelFontStyleSelectedOption by mutableStateOf(value = "Default")
        var stopwatchTimeFontStyleSelectedOption by mutableStateOf(value = "Default")
        var stopwatchLapTimeFontStyleSelectedOption by mutableStateOf(value = "Default")
    }

    init {
        viewModelScope.launch {
            loadStopwatchRefreshRateValue()
            loadStopwatchShowLabel()
            loadStopwatchLabelStyleSelectedOption()
            loadStopwatchBackgroundEffectsSelectedOption()
            loadStopwatchLabelSelectedOption()
            loadStopwatchTimeSelectedOption()
            loadStopwatchLapTimeSelectedOption()
        }
    }

    fun setStopwatchLabelStyleSelectedOption(string: String) {
        stopwatchLabelStyleSelectedOption = string
    }

    fun setStopwatchBackgroundEffectSelectedOption(string: String) {
        stopwatchBackgroundEffectsSelectedOption = string
    }

    fun setStopwatchRefreshRateValue(float: Float) {
        stopwatchRefreshRateValue = float
    }

    fun stopwatchSetShowLabelCheckState() {
        stopwatchShowLabel = !stopwatchShowLabel
    }

    fun setStopwatchLabelFontStyleSelectedOption(string: String){
        stopwatchLabelFontStyleSelectedOption = string
    }

    fun setStopwatchTimeFontStyleSelectedOption(string: String){
        stopwatchTimeFontStyleSelectedOption = string
    }

    fun setStopwatchLapTimeFontStyleSelectedOption(string: String){
        stopwatchLapTimeFontStyleSelectedOption = string
    }

    fun saveStopwatchRefreshRateValue() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchRefreshRate(float = stopwatchRefreshRateValue)
        }
    }

    fun saveStopwatchShowLabel() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchShowLabel(boolean = stopwatchShowLabel)
        }
    }

    fun saveStopwatchLabelStyleSelectedOption() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLabelStyleSelectedOption(
                string = stopwatchLabelStyleSelectedOption
            )
        }
    }

    fun saveStopwatchBackgroundEffectsSelectedOption() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchBackgroundEffects(
                string = stopwatchBackgroundEffectsSelectedOption
            )
        }
    }

    fun saveStopwatchLabelFontStyleSelectedOption(){
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLabelFontStyle(
                string = stopwatchLabelFontStyleSelectedOption
            )
        }
    }

    fun saveStopwatchTimeFontStyleSelectedOption(){
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchTimeFontStyle(
                string = stopwatchTimeFontStyleSelectedOption
            )
        }
    }

    fun saveStopwatchLapTimeFontStyleSelectedOption(){
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLapTimeFontStyle(
                string = stopwatchLabelFontStyleSelectedOption
            )
        }
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

    suspend fun loadStopwatchLabelSelectedOption(){
        stopwatchLabelFontStyleSelectedOption = stopwatchPreferences.getStopwatchLabelFontStyle.first()
    }

    suspend fun loadStopwatchTimeSelectedOption(){
        stopwatchTimeFontStyleSelectedOption = stopwatchPreferences.getStopwatchTimeFontStyle.first()
    }

    suspend fun loadStopwatchLapTimeSelectedOption(){
        stopwatchLapTimeFontStyleSelectedOption = stopwatchPreferences.getStopwatchLapTimeFontStyle.first()
    }



}
