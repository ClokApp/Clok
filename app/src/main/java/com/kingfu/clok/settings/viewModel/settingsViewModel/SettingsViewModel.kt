package com.kingfu.clok.settings.viewModel.settingsViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kingfu.clok.core.ThemeType
import com.kingfu.clok.settings.repository.SettingsPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {

    var state by mutableStateOf(value = SettingsState())
        private set

    init {
        viewModelScope.launch {
            loadAllData()
            setIsLoaded(boolean = true)
        }
    }

    fun setIsLoaded(boolean: Boolean) {
        state = state.copy(isLoaded = boolean)
    }

    fun setTheme(theme: ThemeType) {
        state = state.copy(theme = theme)
        saveTheme(string = theme.name)
    }


    fun setIsFullScreen(boolean: Boolean) {
        state = state.copy(isFullScreen = boolean)
        saveIsFullScreen(boolean = boolean)
    }


    fun setIsShowDialogTheme(boolean: Boolean) {
        state = state.copy(isShowDialogTheme = boolean)
    }


    private fun saveTheme(string: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            settingsPreferences.setTheme(string = string)
        }
    }

    private fun saveIsFullScreen(boolean: Boolean) {
        viewModelScope.launch(context = Dispatchers.IO) {
            settingsPreferences.setIsFullScreen(boolean = boolean)
        }
    }

    fun saveStartRoute(string: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            settingsPreferences.setStartRoute(string = string)
        }
    }

    private suspend fun loadAllData() {
        state = state.copy(
            theme = enumValueOf(name = settingsPreferences.getTheme.first()),
            isFullScreen = settingsPreferences.getIsFullScreen.first(),
            startRoute = settingsPreferences.getStartRoute.first()
        )
    }


}