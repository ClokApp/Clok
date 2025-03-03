package com.kingfu.clok.settings.viewModel

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

    private fun saveTheme(string: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            settingsPreferences.setTheme(string = string)
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
            startRoute = enumValueOf(name = settingsPreferences.getStartRoute.first())
        )
    }


}