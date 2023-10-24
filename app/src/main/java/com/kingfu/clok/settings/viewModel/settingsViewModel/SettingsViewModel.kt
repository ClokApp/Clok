package com.kingfu.clok.settings.viewModel.settingsViewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.kingfu.clok.repository.preferencesDataStore.SettingsPreferences
import com.kingfu.clok.settings.settingsScreen.settingsApp.settingsThemeScreen.ThemeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(SavedStateHandleSaveableApi::class)
class SettingsViewModel(
    private val settingsPreferences: SettingsPreferences,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by savedStateHandle.saveable { mutableStateOf(value = SettingsViewModelState()) }
        private set

    init {
        viewModelScope.launch {
            loadAllData()
        }
    }

    fun updateAppTheme(theme: ThemeType) {
        state = state.copy(appTheme = theme)
    }

    fun saveAppTheme(){
        viewModelScope.launch(context = Dispatchers.IO) {
            settingsPreferences.setAppTheme(string = state.appTheme.name)
        }
    }

    private suspend fun loadAllData() {
        state = state.copy(appTheme = enumValueOf(settingsPreferences.getAppTheme.first()))
    }


}