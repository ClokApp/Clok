package com.kingfu.clok.settings.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import com.kingfu.clok.repository.preferencesDataStore.StopwatchPreferences
import com.kingfu.clok.variable.Variable.DYNAMIC_COLOR
import com.kingfu.clok.variable.Variable.settingsStopwatchSelectedFontStyle
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModelStopwatch(
    private val stopwatchPreferences: StopwatchPreferences
) : ViewModel() {

    var stopwatchShowLabel by mutableStateOf(value = true)
        private set

    var stopwatchBackgroundEffects by mutableStateOf(value = "Snow")
        private set

    var stopwatchRefreshRateValue by mutableFloatStateOf(value = 51f)
        private set

    var stopwatchLabelStyle by mutableStateOf(value = DYNAMIC_COLOR)
        private set

    var stopwatchLabelFontStyle by mutableStateOf(value = "Default")
        private set

    var stopwatchTimeFontStyle by mutableStateOf(value = "Default")
        private set

    var stopwatchLapTimeFontStyle by mutableStateOf(value = "Default")
        private set

    private var _stopwatchStyleRadioOptions = setOf("Default", "Inner stroke")
    val stopwatchStyleRadioOptions: Set<String>
        get() = _stopwatchStyleRadioOptions

    private var _fontStyleOptions = setOf("Label", "Time", "Lap time")
    val fontStyleOptions: Set<String>
        get() = _fontStyleOptions
    init {
        viewModelScope.launch {
            loadStopwatchRefreshRateValue()
            loadStopwatchShowLabel()
            loadStopwatchLabelStyleSelectedOption()
            loadStopwatchBackgroundEffectsSelectedOption()
            loadStopwatchLabelSelectedOption()
            loadStopwatchTimeFontStyleSelectedOption()
            loadStopwatchLapTimeFontStyleSelectedOption()
        }
    }



    fun fontStyleOptionSelected(index: Int, radioOptions: Set<String>) {
        when (settingsStopwatchSelectedFontStyle) {
            fontStyleOptions.elementAt(index = 0) -> {
                updateStopwatchLabelFontStyle(string = radioOptions.elementAt(index = index))
                saveStopwatchLabelFontStyleSelectedOption()
            }

            fontStyleOptions.elementAt(index = 1) -> {
                updateStopwatchTimeFontStyle(string = radioOptions.elementAt(index = index))
                saveStopwatchTimeFontStyleSelectedOption()
            }

            else -> {
                updateStopwatchLapTimeFontStyle(string = radioOptions.elementAt(index = index))
                saveStopwatchLapTimeFontStyleSelectedOption()
            }
        }
    }

    fun updateStopwatchLabelStyle(string: String) {
        stopwatchLabelStyle = string
    }

    fun updateStopwatchBackgroundEffect(string: String) {
        stopwatchBackgroundEffects = string
    }

    fun updateStopwatchRefreshRateValue(float: Float) {
        stopwatchRefreshRateValue = float
    }

    fun stopwatchSetShowLabelCheckState() {
        stopwatchShowLabel = !stopwatchShowLabel
    }

    fun updateStopwatchLabelFontStyle(string: String) {
        stopwatchLabelFontStyle = string
    }

    fun updateStopwatchTimeFontStyle(string: String) {
        stopwatchTimeFontStyle = string
    }

    fun updateStopwatchLapTimeFontStyle(string: String) {
        stopwatchLapTimeFontStyle = string
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

    fun saveStopwatchLabelStyle() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLabelStyle(
                string = stopwatchLabelStyle
            )
        }
    }

    fun saveStopwatchBackgroundEffects() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchBackgroundEffects(
                string = stopwatchBackgroundEffects
            )
        }
    }

    fun saveStopwatchLabelFontStyleSelectedOption() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLabelFontStyle(string = stopwatchLabelFontStyle)
        }
    }

    fun saveStopwatchTimeFontStyleSelectedOption() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchTimeFontStyle(string = stopwatchTimeFontStyle)
        }
    }

    fun saveStopwatchLapTimeFontStyleSelectedOption() {
        viewModelScope.launch {
            stopwatchPreferences.setStopwatchLapTimeFontStyle(string = stopwatchLapTimeFontStyle)
        }
    }


    suspend fun loadStopwatchRefreshRateValue() {
        stopwatchRefreshRateValue = stopwatchPreferences.getStopwatchRefreshRate.first()
    }

    suspend fun loadStopwatchShowLabel() {
        stopwatchShowLabel = stopwatchPreferences.getStopwatchShowLabel.first()
    }

    suspend fun loadStopwatchLabelStyleSelectedOption() {
        stopwatchLabelStyle = stopwatchPreferences.getStopwatchLabelStyle.first()
    }

    suspend fun loadStopwatchBackgroundEffectsSelectedOption() {
        stopwatchBackgroundEffects = stopwatchPreferences.getStopwatchBackgroundEffects.first()
    }

    suspend fun loadStopwatchLabelSelectedOption() {
        stopwatchLabelFontStyle = stopwatchPreferences.getStopwatchLabelFontStyle.first()
    }

    suspend fun loadStopwatchTimeFontStyleSelectedOption() {
        stopwatchTimeFontStyle = stopwatchPreferences.getStopwatchTimeFontStyle.first()
    }

    suspend fun loadStopwatchLapTimeFontStyleSelectedOption() {
        stopwatchLapTimeFontStyle = stopwatchPreferences.getStopwatchLapTimeFontStyle.first()
    }


}
