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

    var timerLabelStyle by mutableStateOf(value = DYNAMIC_COLOR)
        private set
    var timerCountOvertime by mutableStateOf(value = true)
        private set
    var timerNotification by mutableFloatStateOf(value = 5f)
        private set
    var timerEnableScrollsHapticFeedback by mutableStateOf(value = true)
        private set
    var timerBackgroundEffects by mutableStateOf(value = "Snow")
        private set
    var timerScrollsFontStyle by mutableStateOf(value = "Default")
        private set
    var timerTimeFontStyle by mutableStateOf(value = "Default")
        private set

    private var _timerFontStyleRadioOptions = setOf("Default", "Inner stroke")
    val timerFontStyleRadioOptions: Set<String>
        get() = _timerFontStyleRadioOptions

    private var _fontStyleOptions = setOf("Scrolls", "Time")
    val fontStyleOptions: Set<String>
        get() = _fontStyleOptions
    var timerScrollsHapticFeedback by mutableStateOf(value = "Strong")
        private set

    init {
        viewModelScope.launch {
            loadTimerCountOvertime()
            loadTimerLabelStyleSelectedOption()
            loadTimerNotification()
            loadTimerBackgroundEffects()
            loadTimerScrollsFontStyleSelectedOption()
            loadTimerTimeFontStyleSelectedOption()
            loadTimerHapticFeedback()
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

    fun updateScrollHapticFeedback(string: String) {
        timerScrollsHapticFeedback = string
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
        timerCountOvertime = !timerCountOvertime
    }


    fun updateTimerLabelStyle(string: String) {
        timerLabelStyle = string
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
            timerPreferences.setTimerLabelStyleSelectedOption(string = timerLabelStyle)
        }
    }

    fun saveScrollsHapticFeedback() {
        viewModelScope.launch {
            timerPreferences.setTimerScrollsHapticFeedback(string = timerScrollsHapticFeedback)
        }
    }

    fun saveTimerCountOvertime() {
        viewModelScope.launch {
            timerPreferences.setTimerCountOvertime(boolean = timerCountOvertime)
        }
    }


    fun saveTimerNotification() {
        viewModelScope.launch {
            timerPreferences.setTimerNotification(float = timerNotification)
        }
    }

    fun saveTimerBackgroundEffectsSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerBackgroundEffects(string = timerBackgroundEffects)
        }
    }

    fun saveTimerScrollsFontStyleSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerScrollFontStyle(string = timerScrollsFontStyle)
        }
    }

    fun saveTimerTimeFontStyleSelectedOption() {
        viewModelScope.launch {
            timerPreferences.setTimerTimeFontStyle(string = timerTimeFontStyle)
        }
    }

    suspend fun loadTimerCountOvertime() {
        timerCountOvertime = timerPreferences.getTimerCountOvertime.first()
    }

    suspend fun loadTimerLabelStyleSelectedOption() {
        timerLabelStyle = timerPreferences.getTimerLabelStyle.first()
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

    suspend fun loadTimerHapticFeedback() {
        timerScrollsHapticFeedback = timerPreferences.getTimerHapticFeedback.first()
    }


}

