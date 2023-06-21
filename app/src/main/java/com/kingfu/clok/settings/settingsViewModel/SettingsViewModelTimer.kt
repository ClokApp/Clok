package com.kingfu.clok.settings.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.repository.preferencesDataStore.TimerPreferences
import com.kingfu.clok.variable.Variable.DYNAMIC_COLOR
import com.kingfu.clok.variable.Variable.settingsTimerSelectedFontStyle
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class SettingsViewModelTimer(
    private val timerPreferences: TimerPreferences,
) : ViewModel() {

    var timerLabelStyle by  mutableStateOf(value = DYNAMIC_COLOR)
        private set
    var timerCountOvertime by mutableStateOf(value = true)
        private set
    var timerNotification by mutableFloatStateOf(value = 5f)
        private set
    var timerEnableScrollsHapticFeedback by mutableStateOf(value = false)
        private set
    var timerBackgroundEffects by mutableStateOf(value = "Snow")
        private set
    var timerScrollsFontStyle by mutableStateOf(value = "Default")
        private set
    var timerTimeFontStyle by  mutableStateOf(value = "Default")
        private set

    private var _timerFontStyleRadioOptions = setOf("Default", "Inner stroke")
    val timerFontStyleRadioOptions: Set<String>
        get() = _timerFontStyleRadioOptions

    private var _fontStyleOptions = setOf("Scrolls", "Time")
    val fontStyleOptions: Set<String>
        get() = _fontStyleOptions
    init {
        viewModelScope.launch {
            loadTimerCountOvertime()
            loadTimerLabelStyleSelectedOption()
            loadTimerEnableScrollsHapticFeedback()
            loadTimerNotification()
            loadTimerBackgroundEffects()
            loadTimerScrollsFontStyleSelectedOption()
            loadTimerTimeFontStyleSelectedOption()
        }
    }
    fun fontStyleOptionSelected(index: Int, radioOptions: Set<String>) {
        if (settingsTimerSelectedFontStyle == fontStyleOptions.elementAt(index = 0)) {
            updateTimerScrollsFontStyle(
                string = radioOptions.elementAt(index = index)
            )
            saveTimerScrollsFontStyleSelectedOption()
        } else {
            updateTimerTimeFontStyle(
                string = radioOptions.elementAt(index = index)
            )
            saveTimerTimeFontStyleSelectedOption()
        }
    }

    fun timerToggleCountOvertime() {
        timerCountOvertime = !timerCountOvertime
    }


    fun updateTimerLabelStyle(string: String) {
        timerLabelStyle = string
    }

    fun updateTimerEnableScrollsHapticFeedback() {
        timerEnableScrollsHapticFeedback = !timerEnableScrollsHapticFeedback
    }

    fun updateTimerNotification(float: Float) {
        timerNotification = float
    }

    fun updateTimerBackgroundEffects(string: String) {
        timerBackgroundEffects = string
    }

    fun updateTimerScrollsFontStyle(string: String) {
        timerScrollsFontStyle = string
    }

    fun updateTimerTimeFontStyle(string: String) {
        timerTimeFontStyle = string
    }

    fun saveTimerLabelStyleSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerLabelStyleSelectedOption(timerLabelStyle)
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
            timerPreferences.setTimerBackgroundEffects(timerBackgroundEffects)
        }
    }

    fun saveTimerScrollsFontStyleSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerScrollFontStyle(timerScrollsFontStyle)
        }
    }

    fun saveTimerTimeFontStyleSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerTimeFontStyle(timerTimeFontStyle)
        }
    }

    suspend fun loadTimerCountOvertime() {
        timerCountOvertime = timerPreferences.getTimerCountOvertime.first()
    }

    suspend fun loadTimerLabelStyleSelectedOption() {
        timerLabelStyle = timerPreferences.getTimerLabelStyle.first()
    }

    suspend fun loadTimerEnableScrollsHapticFeedback() {
        timerEnableScrollsHapticFeedback =
            timerPreferences.getTimerEnableScrollsHapticFeedback.first()
    }

    suspend fun loadTimerNotification() {
        timerNotification = timerPreferences.getTimerNotification.first()
    }

    suspend fun loadTimerBackgroundEffects() {
        timerBackgroundEffects = timerPreferences.getTimerBackgroundEffects.first()
    }

    suspend fun loadTimerScrollsFontStyleSelectedOption() {
        timerScrollsFontStyle = timerPreferences.getTimerScrollsFontStyle.first()
    }

    suspend fun loadTimerTimeFontStyleSelectedOption() {
        timerTimeFontStyle = timerPreferences.getTimerTimeFontStyle.first()
    }


}

